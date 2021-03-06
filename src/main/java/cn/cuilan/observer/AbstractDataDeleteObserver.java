package cn.cuilan.observer;

import cn.cuilan.common.BaseObservableEntity;
import cn.cuilan.observer.handler.DeleteHandler;
import cn.cuilan.observer.handler.DeleteHandlerWithContext;
import cn.cuilan.utils.ListenJvmShutdown;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 抽象实体删除观察者
 *
 * @param <T> BaseObservableEntity子类实体
 * @author zhang.yan
 * @date 2019-12-31
 */
@Slf4j
public abstract class AbstractDataDeleteObserver<T extends BaseObservableEntity<Long>> implements InitializingBean {

    static Map<Class<BaseObservableEntity<Long>>, AbstractDataDeleteObserver<?>> deleteObserverMap = new HashMap<>();

    private final static ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder().setNamePrefix("observer-pool-%d").build();

    protected static ExecutorService executorService = new ThreadPoolExecutor(0, 50,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(), NAMED_THREAD_FACTORY);

    Class entityClass;

    BaseMapper<T> baseMapper;

    private final List<DeleteHandlerWithContext<T>> beforeHandlerList = new ArrayList<>();
    private final List<DeleteHandlerWithContext<T>> afterHandlerList = new ArrayList<>();
    private final List<DeleteHandlerWithContext<T>> afterCommitHandlerList = new ArrayList<>();

    public AbstractDataDeleteObserver(BaseMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }

    <R> R delete(T deleted, Function<T, R> deleteFunction) {
        return delete(deleted, deleteFunction, new ObserverContext<T>());
    }

    <R> R delete(T deleted, Function<T, R> deleteFunction, ObserverContext<T> context) {
        for (DeleteHandlerWithContext<T> processor : beforeHandlerList) {
            try {
                processor.handler(deleted, context);
            } catch (Exception e) {
                log.error("delete observer before handler, entity: {}", JSON.toJSONString(deleted), e);
                throw e;
            }
        }
        R result = deleteFunction.apply(deleted);
        for (DeleteHandlerWithContext<T> processor : afterHandlerList) {
            try {
                processor.handler(deleted, context);
            } catch (Exception e) {
                log.error("delete observer before handler, entity: {}", JSON.toJSONString(deleted), e);
                throw e;
            }
        }

        Supplier<?> afterCommit = () -> {
            executorService.execute(() -> {
                for (DeleteHandlerWithContext<T> processor : afterCommitHandlerList) {
                    try {
                        processor.handler(deleted, context);
                    } catch (Exception e) {
                        log.error("delete observer before handler, entity: {}", JSON.toJSONString(deleted), e);
                        throw e;
                    }
                }
            });
            return null;
        };

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            // 如果事务开启
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    afterCommit.get();
                }
            });
        } else {
            // 如果事务没有开启
            afterCommit.get();
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() {
        regDeleteObserver(new Register());
        if (entityClass == null) {
            Type[] typeParams = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
            if (typeParams.length != 1) {
                throw new RuntimeException(String.format("初始化观察者失败，观察者父类必须设置泛型-[%s]", this.getClass().getSimpleName()));
            }
            entityClass = (Class) typeParams[0];
        }
        AbstractDataDeleteObserver<?> dataDeleteObserver = deleteObserverMap.get(entityClass);

        if (dataDeleteObserver != null) {
            throw new RuntimeException(String.format("%s的观察者存在多个[%s,%s]",
                    entityClass.getSimpleName(),
                    dataDeleteObserver.getClass().getCanonicalName(),
                    this.getClass().getCanonicalName()));
        }
        deleteObserverMap.put(entityClass, this);
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                ListenJvmShutdown.shutdownThreadPool(executorService, "DataDeleteObserver-Shutdown")));
    }

    protected abstract void regDeleteObserver(Register register);

    public class Register {
        public void beforeDelete(String desc, DeleteHandlerWithContext<T> processor) {
            beforeHandlerList.add(processor);
        }

        public void beforeDelete(String desc, DeleteHandler<T> processor) {
            beforeHandlerList.add(((obj, context) -> processor.handler(obj)));
        }

        public void afterDelete(String desc, DeleteHandlerWithContext<T> processor) {
            afterHandlerList.add(processor);
        }

        public void afterDelete(String desc, DeleteHandler<T> processor) {
            afterHandlerList.add(((obj, context) -> processor.handler(obj)));
        }

        public void afterDeleteCommit(String desc, DeleteHandlerWithContext<T> processor) {
            afterCommitHandlerList.add(processor);
        }

        public void afterDeleteCommit(String desc, DeleteHandler<T> processor) {
            afterCommitHandlerList.add(((obj, context) -> processor.handler(obj)));
        }
    }
}

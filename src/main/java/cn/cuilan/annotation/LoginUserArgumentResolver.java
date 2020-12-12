package cn.cuilan.annotation;

import cn.cuilan.exception.BaseException;
import cn.cuilan.utils.TokenManagerUtils;
import cn.cuilan.utils.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Logined 注解参数解析器
 *
 * @author zhang.yan
 */
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Logined methodAnnotation = methodParameter.getMethodAnnotation(Logined.class);
        if (methodAnnotation != null) {
            return true;
        }
        Logined loginUser = methodParameter.getParameterAnnotation(Logined.class);
        return loginUser != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        // 获取当前登录用户
        String token = request.getHeader("token");
        Long sysUserId = TokenManagerUtils.getUserIdByToken(token);
        if (sysUserId != null && sysUserId != 0L) {
            return sysUserId;
        }

        // 未登录
        throw new BaseException(Result.NO_AUTH.getValue(), "请登录后重试");
    }
}

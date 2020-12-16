package cn.cuilan.config;

import cn.cuilan.annotation.IPArgumentResolver;
import cn.cuilan.annotation.LoginUserArgumentResolver;
import cn.cuilan.exception.handler.BaseExceptionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Configuration
public class MVCConfig implements WebMvcConfigurer {

//    @Resource
//    private IPArgumentResolver ipArgumentResolver;

    @Resource
    private LoginUserArgumentResolver loginUserArgumentResolver;

    /**
     * 统一输出编码
     */
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

    /**
     * 统一异常处理
     */
    @Bean
    public BaseExceptionResolver baseExceptionResolver() {
        return new BaseExceptionResolver();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 统一输出编码
        converters.add(responseBodyConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // IP注解解析器
        // resolvers.add(ipArgumentResolver);
        // 获取当前登录用户解析器
        resolvers.add(loginUserArgumentResolver);
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        // 统一异常处理
        resolvers.add(baseExceptionResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 获取当前class绝对路径
        String currentPath = System.getProperty("user.dir");
        // 拼接前端页面: /web/dist/ 路径
        String projectPath = currentPath + File.separator + "web" + File.separator + "dist" + File.separator;
        log.info("projectPath: {}", projectPath);
        registry.addResourceHandler("/**")
                .addResourceLocations("file://" + projectPath);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 设置默认页面
        registry.addViewController("/").setViewName("forward:index.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置跨域请求
        registry.addMapping("/**")
                // 允许以下类型请求跨域访问
                .allowedMethods("GET", "POST", "PUT");
    }
}

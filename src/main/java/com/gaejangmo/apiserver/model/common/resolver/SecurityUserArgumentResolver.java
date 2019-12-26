package com.gaejangmo.apiserver.model.common.resolver;

import com.gaejangmo.apiserver.config.oauth.SecurityUser;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class SecurityUserArgumentResolver implements HandlerMethodArgumentResolver {
    public static final Long NOT_EXISTED_ID = -1L;
    private static final String ANONYMOUS_USER = "anonymousUser";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SecurityUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public SecurityUser resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (ANONYMOUS_USER.equals(user)) {
            return SecurityUser.builder().id(NOT_EXISTED_ID).build();
        }

        return (SecurityUser) user;
    }
}

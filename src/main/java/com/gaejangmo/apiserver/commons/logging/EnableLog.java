package com.gaejangmo.apiserver.commons.logging;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @EnableLog를 Class, Method에 붙여주면 메소드 호출시 로그를 남겨줍니다.
 * com.gaejangmo.apiserver.commons.logging.LoggingAspect. 참고
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface EnableLog {

}

package com.cmic.GoAppiumTest.helper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当前该注解只是为了标识方法的风险点和注意点，仅存在源码阶段
 */
@Retention(RetentionPolicy.SOURCE) // 注解仅存在于源码中，在class字节码文件中不包含
@Target({ ElementType.METHOD, ElementType.FIELD }) // 定义注解的作用目标**作用范围字段、枚举的常量/方法
@Documented // 说明该注解将被包含在文档中
public @interface Tips {
	// 描述
	String description() default "";

	// 预期风险
	String riskPoint() default "";

	// 触发时机
	String triggerTime() default "";
}

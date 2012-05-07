package com.raygroupintl.bnf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Characters {
	public char[] chars() default {};
	public char[] ranges() default {};
	public char[] excludechars() default {};
	public char[] excluderanges() default{};
}
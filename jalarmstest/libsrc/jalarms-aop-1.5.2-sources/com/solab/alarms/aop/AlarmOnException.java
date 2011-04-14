package com.solab.alarms.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** This interface can be used on classes or methods, to send an alarm message if an
 * exception is thrown that is not handled by the method. When used on a class, it has
 * the same effect as annotating all the methods of that class.
 * 
 * @author Enrique Zamudio
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AlarmOnException {
	/** You can specify the alarm message to be sent along with the exception. */
	String message() default "";
	/** You can specify a message source. */
	String source() default "";
	int stack() default 1;
}

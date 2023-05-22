package fr.webmaker.hateos.annotation;

import java.lang.annotation.ElementType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnore
@java.lang.annotation.Target(value={java.lang.annotation.ElementType.FIELD, ElementType.TYPE })
@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface HateosType {

	public abstract String value() default "";
}

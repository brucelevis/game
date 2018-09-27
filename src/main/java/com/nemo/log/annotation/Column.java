package com.nemo.log.annotation;

import com.nemo.log.FieldType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface Column {
    FieldType fieldType();

    String commit() default "";

    boolean allowNull() default false;

    boolean autoIncrement() default false;

    int size() default 0;

    String colName() default "";
}

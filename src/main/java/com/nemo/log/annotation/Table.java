package com.nemo.log.annotation;

import com.nemo.log.TableCycle;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.TYPE})
public @interface Table {
    String primaryKey() default "id";

    TableCycle cycle() default TableCycle.SINGLE;

    String tableName() default "";
}

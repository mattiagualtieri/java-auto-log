package com.guti;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AutoLog {

  boolean input() default true;

  boolean output() default true;

  Mapping[] mappings() default {};

  @interface Mapping {
    Class<?> type();

    String method();

    int order() default -1;
  }
}

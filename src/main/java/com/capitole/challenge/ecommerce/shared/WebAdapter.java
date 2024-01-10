package com.capitole.challenge.ecommerce.shared;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
public @interface WebAdapter {
    @AliasFor(annotation = RestController.class)
    String value() default "";
}

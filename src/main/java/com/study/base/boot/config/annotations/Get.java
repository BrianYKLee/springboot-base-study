package com.study.base.boot.config.annotations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ResponseStatus(HttpStatus.OK)
@RequestMapping(method = RequestMethod.GET)
public @interface Get {
}

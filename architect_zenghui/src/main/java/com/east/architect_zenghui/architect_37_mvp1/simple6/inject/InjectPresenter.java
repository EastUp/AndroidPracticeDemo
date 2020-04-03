package com.east.architect_zenghui.architect_37_mvp1.simple6.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hcDarren on 2018/1/6.
 */
@Target(ElementType.FIELD)// 属性
@Retention(RetentionPolicy.RUNTIME)// 运行时
public @interface InjectPresenter {
}

package com.project.coindispenser.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.project.coindispenser.model.Bill;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = BillValueValidator.class)
public @interface BillValue {

    String message() default "Bill value is not valid! Value should be from the list: ";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    Class<? extends Enum<Bill>> enumClass();

}

package com.tth.commonlibrary.validator.constraint;

import com.tth.commonlibrary.validator.RoleBasedValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // Áp dụng cho class (RegisterRequest)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleBasedValidator.class) // Trỏ tới class xử lý logic
public @interface RoleBasedConstraint {

    String message() default "Invalid fields for the specified role";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

package main.project.server.annotation.validation.filed;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AddressValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ValidAddress {
    String value() default  "Invalid Address Array";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}

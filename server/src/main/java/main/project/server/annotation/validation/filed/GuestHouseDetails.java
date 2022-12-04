package main.project.server.annotation.validation.filed;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GuestHouseDetailsValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GuestHouseDetails {
    String value() default  "Invalid Guest House Details";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

package main.project.server.annotation.validation.filed;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TagValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ValidTag {
    String value() default  "Invalid Format TagString";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}

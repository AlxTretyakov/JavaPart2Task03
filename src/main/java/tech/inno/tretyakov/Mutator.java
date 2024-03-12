package tech.inno.tretyakov;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Mutators.class)
public @interface Mutator {
    String fieldName();
}

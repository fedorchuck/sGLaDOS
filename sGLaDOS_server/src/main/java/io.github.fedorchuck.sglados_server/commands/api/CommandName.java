package io.github.fedorchuck.sglados_server.commands.api;

import java.lang.annotation.*;


/**
 * Created by v on 26.05.2015.
 */
@Retention(RetentionPolicy.RUNTIME) // Make this annotation accessible at runtime via reflection.
@Target({ElementType.TYPE})       // This annotation can only be applied to class methods.
@Documented
public @interface CommandName {
    String value();
}


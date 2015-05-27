package io.github.fedorchuck.sglados_server.utils;

/**
 * Created by v on 22.05.2015.
 */
public class ThrowThrowable {
    public <Th extends Throwable, Ob extends Object, Wh extends Object> void throwError(
            final Ob problemClass,
            final Th error,
            final Wh what) {
        System.err.println("in class: " +  problemClass);
        System.err.println(error.getClass().getName() + ": " + error.getMessage());
        System.err.println(what);
        //throw error;
        //System.exit(-1);
    }
}

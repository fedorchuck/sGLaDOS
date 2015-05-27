package io.github.fedorchuck.sglados_server.utils;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by v on 26.05.2015.
 * This static class is temporary until we edit agent structure and then there'll
 * normal DI by constructors
 *
 * Now it is used in tests
 */
public class InstanceCreator {
    private static Injector injector = null;

    private InstanceCreator() {
    }

    public static <T extends Object> T getInstance(Class<T> type) {
        if (injector == null) {
            injector = Guice.createInjector();//(new CommandsBindingModule());
        }
        return injector.getInstance(type);
    }
}

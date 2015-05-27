package io.github.fedorchuck.sglados_server.inject_config_module;

import io.github.fedorchuck.sglados_server.commands.ICommand;
import io.github.fedorchuck.sglados_server.commands.api.CommandName;

import com.google.inject.multibindings.MapBinder;
import org.reflections.Reflections;
import java.util.Set;

/**
 * Created by v on 26.05.2015.
 */
public class CommandsBindingModule extends BindingModule {
    @Override
    public void configure() {
        super.configure();
        MapBinder<String, ICommand> mapBinder = MapBinder.newMapBinder(binder(), String.class, ICommand.class);

        // Go through all command names
        Reflections reflections = new Reflections("io.github.fedorchuck.sglados_server.commands.impl");

        // load all classes-commands in project to memory
        Set<Class<?>> annotated =
                (Set<Class<?>>) reflections.getTypesAnnotatedWith(CommandName.class);

        // fill commands classes and its names hash map
        for (Class<?> aClass : annotated) {
            String commandName = ((CommandName) aClass.getAnnotations()[0]).value(); // read the annotation value
            @SuppressWarnings("unchecked")
            Class<ICommand> command = (Class<ICommand>) aClass;

            mapBinder.addBinding(commandName).to(command);
        }
    }
}
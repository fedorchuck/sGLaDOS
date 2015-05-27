package io.github.fedorchuck.sglados_server.commands;

import io.github.fedorchuck.sglados_server.commands.api.CommandParameter;
import io.github.fedorchuck.sglados_server.commands.api.CommandResponse;

/**
 * Created by v on 26.05.2015.
 */
public interface ICommand {
    CommandResponse run(CommandParameter parameter);// TODO: throws runtime exception
}

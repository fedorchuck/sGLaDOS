package io.github.fedorchuck.sglados_server.commands.api;

import java.util.UUID;

/**
 * Created by v on 26.05.2015.
 */
public class CommandResponse {
    private UUID id = null;
    private CommandResult status;
    private Object result = null;

    public CommandResponse() {    }

    public CommandResponse(CommandResult status)
    {
        this.status = status;
    }
    public CommandResponse(CommandResult status, Object result)
    {
        this(status);
        this.result = result;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CommandResult getStatus() {
        return status;
    }

    public void setStatus(CommandResult status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static CommandResponse ok()
    {
        return new CommandResponse(CommandResult.OK);
    }

    public static CommandResponse ok(Object result)
    {
        return new CommandResponse(CommandResult.OK, result);
    }

    public static CommandResponse error(String message)
    {
        return new CommandResponse(CommandResult.Error, message);
    }
}

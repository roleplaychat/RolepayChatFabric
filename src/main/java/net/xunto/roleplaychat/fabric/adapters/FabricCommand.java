package net.xunto.roleplaychat.fabric.adapters;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.xunto.roleplaychat.api.ICommand;
import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.framework.commands.CommandException;

public class FabricCommand {

  private static final String ARGS = "args";

  private final ICommand command;

  public FabricCommand(ICommand command) {
    this.command = command;
  }

  public String getName() {
    return command.getCommandName();
  }

  public boolean requires(ServerCommandSource source) {
    Entity entity = source.getEntity();
    return entity != null && command.canExecute(new FabricSpeaker(entity));
  }

  public int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
    ServerCommandSource source = context.getSource();
    Entity entity = source.getEntityOrThrow();
    ISpeaker speaker = new FabricSpeaker(entity);

    String[] args;
    try {
      args = StringArgumentType.getString(context, ARGS).split(" ");
    } catch (IllegalArgumentException e) {
      args = new String[]{};
    }

    try {
      command.execute(speaker, args);
    } catch (CommandException e) {
      source.sendError(Text.literal(e.getMessage()));
      return -1;
    }

    return 1;
  }

  public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(
        literal(this.getName())
            .requires(this::requires)
            .then(
                argument("args", greedyString())
                    .executes(this::execute)
            )
            .executes(this::execute)
    );
  }
}

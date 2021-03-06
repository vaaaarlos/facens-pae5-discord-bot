package com.lojfacens.pitchy.module.developer.command;

import static com.lojfacens.pitchy.util.DisUtils.responseEmbedBuilder;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.lojfacens.pitchy.service.command.meta.Command;
import com.lojfacens.pitchy.service.command.meta.CommandContext;

public class ArgsCommand extends Command {

  public ArgsCommand() {
    super("args");
  }

  @Override
  public void onCommand(CommandContext context) {
    var commandName = "`" + context.getCommand().getName() + "`";
    var arguments = Arrays.stream(context.getArguments()).map(s -> "`" + s + "`").collect(Collectors.joining(", "));

    var fullNormalized = context.getArguments(3);
    var normalizedArgs = Arrays.stream(Arrays.copyOfRange(fullNormalized, 0, 3)).map(s -> "`" + s + "`")
        .collect(Collectors.joining(", "));

    var options = context.getOptionalArguments().entrySet().stream().map(
        e -> "`" + e.getKey() + "` = "
        + e.getValue().stream().map(s -> "`" + s + "`").collect(Collectors.joining(", "))
    ).collect(Collectors.joining("\n"));

    var embedResponse = responseEmbedBuilder()
        .setTitle("Args Command")
        .setDescription("Command to test argument splitting")
        .addField("Command", commandName, false)
        .addField("Arguments", arguments, false)
        .addField("Options",     options, false)
        .addField("Normalized Arguments (to 3 args)", String.format(
            "`Normalized` = %s%n`Remaining` = `%s`", normalizedArgs, fullNormalized[3]),
            false);
    context.reply(embedResponse.build());
  }

}

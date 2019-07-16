package me.zebbzz.brotatobot.functionality;

import me.zebbzz.brotatobot.commands.textcommands.CrapsCommand;
import me.zebbzz.brotatobot.commands.textcommands.HelpCommand;
import me.zebbzz.brotatobot.commands.textcommands.PingCommand;
import me.zebbzz.brotatobot.commands.moderation.KickCommand;
import me.zebbzz.brotatobot.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    //Using the CommandManager class allows new commands to be built without being used until complete.

    public CommandManager() {
        addCommand(new PingCommand());
        addCommand(new KickCommand());
        addCommand(new CrapsCommand());
        addCommand(new HelpCommand(this));
    }

    private void addCommand(ICommand command) {
        if (!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public ICommand getCommand(@NotNull String name) {
        return commands.get(name);
    }

    void handleCommand(GuildMessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(Constants.PREFIX), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            commands.get(invoke).handle(args, event);
        }
    }
}
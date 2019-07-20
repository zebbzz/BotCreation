package me.zebbzz.brotatobot.functionality;

import me.zebbzz.brotatobot.commands.memecommands.CatCommand;
import me.zebbzz.brotatobot.commands.moderation.MuteCommand;
import me.zebbzz.brotatobot.commands.moderation.TagFreeCommand;
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



    public CommandManager() {
        addCommand(new PingCommand());
        addCommand(new KickCommand());
        addCommand(new CrapsCommand());
        addCommand(new HelpCommand(this));
        addCommand(new CatCommand());
        addCommand(new TagFreeCommand());
        addCommand(new MuteCommand());
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
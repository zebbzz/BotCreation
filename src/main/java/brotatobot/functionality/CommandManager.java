package brotatobot.functionality;

import brotatobot.commands.memecommands.CatCommand;
import brotatobot.commands.memecommands.DogCommand;
import brotatobot.commands.memecommands.MemeCommand;
import brotatobot.commands.moderation.KickCommand;
import brotatobot.commands.moderation.MuteCommand;
import brotatobot.commands.moderation.TagFreeCommand;
import brotatobot.commands.textcommands.CrapsCommand;
import brotatobot.commands.textcommands.HelpCommand;
import brotatobot.commands.textcommands.PingCommand;
import brotatobot.objects.ICommand;
import me.zebbzz.brotatobot.commands.moderation.UnmuteCommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Reflections reflections = new Reflections("brotatobot.commands");
    private final Map<String, ICommand> commands = new HashMap<>();


    public CommandManager(Random random) {
        addCommand(new PingCommand());
        addCommand(new KickCommand());
        addCommand(new CrapsCommand());
        addCommand(new HelpCommand(this));
        addCommand(new CatCommand());
        addCommand(new TagFreeCommand());
        addCommand(new MuteCommand());
        addCommand(new UnmuteCommand());
        addCommand(new DogCommand());
        addCommand(new MemeCommand(random));
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

            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, event);
        }
    }
}
package brotatobot.functionality;

import brotatobot.commands.admin.SetPrefixCommand;
import brotatobot.commands.audiocommands.*;
import brotatobot.commands.memecommands.CatCommand;
import brotatobot.commands.memecommands.DogCommand;
import brotatobot.commands.memecommands.MemeCommand;
import brotatobot.commands.moderation.*;
import brotatobot.commands.textcommands.CrapsCommand;
import brotatobot.commands.textcommands.HelpCommand;
import brotatobot.commands.textcommands.PingCommand;
import brotatobot.commands.textcommands.UICommand;
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
        /** Fun Commands */
        addCommand(new CrapsCommand()); //Play craps [in production]

        /** Bot help and server Commands */
        addCommand(new PingCommand()); //Check bots latency
        addCommand(new SetPrefixCommand());//Change the prefix to desired prefix
        addCommand(new HelpCommand(this));
        addCommand(new UICommand());//User Info
        addCommand(new TagFreeCommand()); //Tag a help server as free, in production

        /** Meme and Image Commands */
        addCommand(new CatCommand()); //Random Cat Images
        addCommand(new DogCommand()); //Random dog picture API
        addCommand(new MemeCommand(random)); //Random memes API

        /** Moderation Commands */
        addCommand(new KickCommand()); //Kick user from the server
        addCommand(new MuteCommand()); //Mute user [adds role if non-existent]
        addCommand(new UnmuteCommand()); //Unmute user [removes role]
        addCommand(new BanCommand());//Ban Users
        addCommand(new UnBanCommand()); //UnBan Users

        /** Music Commands */
        addCommand(new JoinCommand()); //Join voice chat command
        addCommand(new LeaveCommand()); //Leave voice chat command
        addCommand(new PlayCommand()); //Plays one song right now
        addCommand(new StopCommand()); //Stops player and clears queue
        addCommand(new QueueCommand()); //Shows list of current songs in the queue.
        addCommand(new SkipCommand());
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

        final String prefix =  Constants.PREFIXES.get(event.getGuild().getIdLong());


        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(prefix), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, event);
        }
    }
}
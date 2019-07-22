package brotatobot.commands.textcommands;

import brotatobot.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class JoinEvent implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        //DB stuff
    }

    @Override
    public String getHelp() {
        return "Add a join msg, using `" + getInvoke() + "`, to welcome users when they join!";
    }

    @Override
    public String getInvoke() {
        return "addjoinmsg";
    }
}

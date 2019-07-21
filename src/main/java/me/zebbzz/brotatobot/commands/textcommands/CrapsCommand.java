package me.zebbzz.brotatobot.commands.textcommands;
import me.zebbzz.brotatobot.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class CrapsCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Oh shit! [coming soon!]").queue((message ->
                message.editMessageFormat("You rolled a %s").queue()));
    }

    @Override
    public String getHelp() {
        return "Testing phase\n";
    }

    @Override
    public String getInvoke() {
        return "craps";
    }
    public int getRoll () {
        return 1;
    }
}


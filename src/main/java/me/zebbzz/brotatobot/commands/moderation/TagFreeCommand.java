package me.zebbzz.brotatobot.commands.moderation;

import me.zebbzz.brotatobot.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class TagFreeCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

    }

    @Override
    public String getHelp() {
        return "Update later";
    }

    @Override
    public String getInvoke() {
        return "tag free";
    }
}

package me.zebbzz.brotatobot.commands.memecommands;

import me.zebbzz.brotatobot.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class CatCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

    }

    @Override
    public String getHelp() {
        return "Shows you a random cat meme.";
    }

    @Override
    public String getInvoke() {
        return "cat";
    }
}

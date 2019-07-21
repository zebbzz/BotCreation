package me.zebbzz.brotatobot.commands.memecommands;

import me.duncte123.botcommons.web.WebUtils;
import me.zebbzz.brotatobot.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class CatCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        WebUtils.ins.scrapeWebPage("https://api.thecatapi.com/api/images/get?format=xml&results_per_page=1").async( (document) -> {
            System.out.println(document.html());
        });

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

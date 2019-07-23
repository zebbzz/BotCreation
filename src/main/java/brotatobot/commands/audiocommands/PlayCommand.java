package brotatobot.commands.audiocommands;

import brotatobot.functionality.Constants;
import brotatobot.music.PlayerManager;
import brotatobot.objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();

        if (args.isEmpty()) {
            channel.sendMessage("Please provide some arguements").queue();

            return;
        }

        String input = String.join(" ", args);

        if (!isUrl(input) && input.startsWith("ytsearch:")) {
            //Use the youtube API for search instead, making a lot of search requests with "ytsearch:" will get you blocked
            channel.sendMessage("Please provide a valid YoutTube, SoundCloud, or BandCamp link").queue();
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(event.getChannel(), input);
//        manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
    }

    private boolean isUrl(String input) {
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Plays a song\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + "[song url]`";
    }

    @Override
    public String getInvoke() {
        return "play";
    }
}

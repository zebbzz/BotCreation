package brotatobot.commands.audiocommands;

import brotatobot.music.GuildMusicManager;
import brotatobot.music.PlayerManager;
import brotatobot.music.TrackScheduler;
import brotatobot.objects.ICommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class SkipCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        TrackScheduler scheduler = musicManager.scheduler;
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {

            channel.sendMessage("There is no song playing!").queue();

            return;
        }


        scheduler.getQueue();
        channel.sendMessage("Skipping the current song").queue();

    }

    @Override
    public String getHelp() {
        return "Skips the current song";
    }

    @Override
    public String getInvoke() {
        return "skip";
    }
}

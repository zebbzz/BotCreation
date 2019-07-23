package brotatobot.commands.audiocommands;

import brotatobot.music.GuildMusicManager;
import brotatobot.music.PlayerManager;
import brotatobot.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class StopCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        PlayerManager playerManager =PlayerManager.getInstance();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(event.getGuild());

        guildMusicManager.scheduler.getQueue().clear();
        guildMusicManager.player.stopTrack();
        guildMusicManager.player.setPaused(false);

        event.getChannel().sendMessage("Stopping the music player and clearing the queue").queue();

    }

    @Override
    public String getHelp() {
        return "Stops the music player";
    }

    @Override
    public String getInvoke() {
        return "stop";
    }
}

package brotatobot.commands.moderation;

import brotatobot.functionality.Constants;
import brotatobot.objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class KickCommand implements ICommand {
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        TextChannel channel = event.getChannel();
        Member member = event.getMember();
        Member selfMember = event.getGuild().getSelfMember();
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

        if (args.isEmpty() || mentionedMembers.isEmpty()) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }

        Member target = mentionedMembers.get(0);
        String reason = String.join(" ", args.subList(1, args.size()));

        if (!member.hasPermission(Permission.KICK_MEMBERS) || !member.canInteract(target)) {
            channel.sendMessage("You don't have permission to use this command").queue();
            return;
        }


        if (!selfMember.hasPermission(Permission.KICK_MEMBERS) || !selfMember.canInteract(target)) {
            channel.sendMessage("I can't kick that user or I don't have the kick members permission").queue();
            return;
        }


        event.getGuild().getController().kick(target, String.format("Kicked by: %#s, with reason: %s",
                event.getAuthor(), reason)).queue();

        channel.sendMessage("Successfully kicked " + target + "!").queue();

    }

    @Override
    public String getHelp() {
        return "Kicks a user off the server.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "kick";
    }
}

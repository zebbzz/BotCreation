package brotatobot.commands.moderation;

import brotatobot.functionality.Constants;
import brotatobot.objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class BanCommand implements ICommand {
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return; //Ignores messages sent by other bots

        Message message = event.getMessage(); // Get the message
        String content = message.getContentRaw(); //Raw message content
        MessageChannel channel = event.getChannel(); //Get the channel message is sent from
        Member member = event.getMember(); //Identify the member who sent the message
        Member selfMember = event.getGuild().getSelfMember();
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

        if (args.isEmpty() || mentionedMembers.isEmpty()) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }
        Member target = mentionedMembers.get(0);
        String reason = String.join(" ", args.subList(1, args.size()));

        if (!member.hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessage("You do not have permission to use this command!").queue();
            return;
        }

        if (!selfMember.hasPermission(Permission.BAN_MEMBERS) && !selfMember.canInteract(target)) {
            channel.sendMessage("I can't kick that user or I don't have the kick members permission.").queue();
        }

        if (event.getAuthor().getIdLong() == Constants.OWNER) {
            //event.getGuild().getController().ban(target, , String.format("Banned by: %s, [Reason]: %s", event.getAuthor(), reason)).queue();
        }

        event.getGuild().getController().kick(target, String.format("Kicked by: %s, with reason: %s", event.getAuthor(), reason)).queue();

    }

    @Override
    public String getHelp() {
        return "Testing!\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "ban";
    }
}

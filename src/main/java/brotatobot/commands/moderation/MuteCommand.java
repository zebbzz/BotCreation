package brotatobot.commands.moderation;

import brotatobot.objects.ICommand;
import brotatobot.functionality.Constants;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

import java.awt.*;
import java.util.List;

public class MuteCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        Member member = event.getMember();
        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();
        Member selfMember = event.getGuild().getSelfMember();
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        if (content.charAt(0) != Constants.PREFIX.charAt(0)) return;

        if (args.isEmpty() || mentionedMembers.isEmpty()) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }
        Member target = mentionedMembers.get(0);
        if (!member.hasPermission(Permission.KICK_MEMBERS)) {
            channel.sendMessage("You do not have permissions!").queue();
            return;
        }
        if (target.hasPermission(Permission.MANAGE_ROLES)) {
            channel.sendMessage("Can't kick an admin!").queue();
            return;
        }
        if (!selfMember.hasPermission(Permission.MANAGE_ROLES) && !selfMember.canInteract(target)) {
            channel.sendMessage("I can't kick that user or I don't have the kick members permission.").queue();
            return;
        }
        List<Role> roles = event.getGuild().getRoles();
        Role mutedrole = null;
        boolean hasMuted = false;
        for (Role r : roles) {
            if (r.getName().equals("muted")) {
                hasMuted = true;
            }
        }
        if (hasMuted) {
            List<Role> m = event.getGuild().getRolesByName("muted", false);
            GuildController controller = event.getGuild().getController();
            controller.addSingleRoleToMember(target, m.get(0)).queue();
            channel.sendMessage("Muted " + target.getAsMention()).queue();
        } else {
            mutedrole = event.getGuild().getController().
                    createRole().setName("muted").
                    setColor(Color.RED).complete();
            for (TextChannel tc : event.getGuild().getTextChannels()) {
                tc.createPermissionOverride(mutedrole).setDeny(Permission.MESSAGE_WRITE, Permission.MESSAGE_ADD_REACTION).queue();
            }
            event.getGuild().getController().modifyRolePositions().selectPosition(mutedrole).moveTo(1);
            channel.sendMessage("Since `muted` role did not exist, it was added! Rerun this command! (This should only happen once)").queue();
        }
    }

    @Override
    public String getHelp() {
        return "`Usage: " + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "mute";
    }
}

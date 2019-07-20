package me.zebbzz.brotatobot.commands.moderation;

import me.zebbzz.brotatobot.functionality.Constants;
import me.zebbzz.brotatobot.objects.ICommand;
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
        if(content.charAt(0) != Constants.PREFIX.charAt(0)) return;

        if (args.isEmpty() || mentionedMembers.isEmpty()) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }
        Member target = mentionedMembers.get(0);
        if (!member.hasPermission(Permission.KICK_MEMBERS)){
            channel.sendMessage("You do not have permissions!").queue();
            return;
        }
        if (!selfMember.hasPermission(Permission.KICK_MEMBERS) && !selfMember.canInteract(target)) {
            channel.sendMessage("I can't kick that user or I don't have the kick members permission.").queue();
            return;
        }
        List<Role> roles = event.getGuild().getRoles();
        Role mutedrole = null;
        boolean hasMuted = false;
        for (Role r : roles){
            if(r.getName().equals("muted")){
                hasMuted = true;
            }
        }
        if(hasMuted){
            GuildController controller = event.getGuild().getController();
            controller.addSingleRoleToMember(target, mutedrole).queue();
        }
        else{
            mutedrole = event.getGuild().getController().
                    createRole().setName("muted").
                    setColor(Color.RED).complete();
            for(TextChannel tc : event.getGuild().getTextChannels()){
                tc.createPermissionOverride(mutedrole).setDeny(Permission.ALL_PERMISSIONS).setAllow(Permission.MESSAGE_READ).queue();
            }
            event.getGuild().getController().modifyRolePositions().selectPosition(mutedrole).moveTo(1).submit();
            channel.sendMessage("Since `muted` role did not exist, it was added! Rerun this command!").queue();
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

package me.zebbzz.brotatobot.commands.moderation;

import me.zebbzz.brotatobot.functionality.Constants;
import me.zebbzz.brotatobot.objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

import java.util.List;

public class UnmuteCommand implements ICommand {
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
        if(!hasMuted){
            channel.sendMessage("Role is not yet created!").queue();
        }
        else{
            boolean hasRole = false;
            List<Role> targetuserroles = target.getRoles();
            for (Role role : targetuserroles){
                if(role.getName().equals("muted")){
                    hasRole = true;
                }
            }
            if(hasRole){
                List<Role> m = event.getGuild().getRolesByName("muted", false);
                GuildController controller = event.getGuild().getController();
                controller.removeSingleRoleFromMember(target, m.get(0)).queue();
                channel.sendMessage("Unmuted " + target.getAsMention()).queue();
            }
            else{
                channel.sendMessage("This user is not muted!").queue();
            }
        }
    }

    @Override
    public String getHelp() {
        return "`Usage: " + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "unmute";
    }
}

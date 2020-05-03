package nl.itz_kiwisap.spigot.fruitlabjoinme.commands.commands;

import java.util.Set;
import java.util.stream.Collectors;

import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PermissionNode;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import nl.itz_kiwisap.spigot.fruitlabjoinme.Main;
import nl.itz_kiwisap.spigot.fruitlabjoinme.commands.AbstractCommand;
import nl.itz_kiwisap.spigot.fruitlabjoinme.object.Player;
import nl.itz_kiwisap.spigot.fruitlabjoinme.utils.Utils;

public class JoinMeCommand extends AbstractCommand {

	public static final String NAME = "JoinMe";
    public static final String DESCRIPTION = "Use a joinme";
    public static final String PERMISSION = "";
    public static final String USAGE = "/joinme";
    public static final String[] SUB_PERMISSIONS = {""};
    
    public JoinMeCommand(CommandSender sender) {
    	super(sender, NAME, DESCRIPTION, PERMISSION, SUB_PERMISSIONS, USAGE);
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {

        if(isSenderPlayer()) {
        	ProxiedPlayer p = (ProxiedPlayer) sender;
        	Player pl = Main.getInstance().getManager().getPlayer(p);
        	int cooldown = getCooldown(p);
        	
        	if(pl != null && pl.getJoinMe() > 0) {
				if(Main.getInstance().getManager().getCooldowns().containsKey(p.getUniqueId())) {
					long secondsleft = ((Main.getInstance().getManager().getCooldowns().get(p.getUniqueId()) / 1000) + (cooldown * 60)) - (System.currentTimeMillis() / 1000);
					
					if(secondsleft > 0) {
						int seconds = (int) (secondsleft % 60);
						int minutes = (int) (secondsleft / 60);
						Utils.sendMessage(p, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.JoinMe.InCooldown").replace("%seconds%", String.valueOf(seconds)).replace("%minutes%", String.valueOf(minutes)), true);
						return;
					} else Main.getInstance().getManager().getCooldowns().remove(p.getUniqueId());
				}
				
				for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
					for(String s : Main.getInstance().getResources().getMessages().getConfig().getStringList("Messages.JoinMe.Announce")) {					
						if(s.contains("%json%") && s.contains("%/json%")) {
								
							TextComponent msg = new TextComponent(s.substring(0, s.indexOf("%json%")).replace('&', '§'));
							String jsonClick = textBetweenWords(s, "%json%", "%/json%").replace('&', '§');
								
							TextComponent c = new TextComponent(jsonClick.replace('&', '§'));
							c.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/joinme dajglieja;ijg " + player.getName() + " " + p.getServer().getInfo().getName()));
							TextComponent[] hover = { new TextComponent(Main.getInstance().getResources().getMessages().getConfig().getString("Messages.JoinMe.HoverText").replace('&', '§').replace("%player%", p.getName())) };
							c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
								
							msg.addExtra(c);
							msg.addExtra(s.substring(s.indexOf("%/json%") + 7).replace('&', '§'));
							Utils.sendMessage(player, msg, false);
						} else Utils.sendMessage(player, String.valueOf(s.replace("%player%", p.getName()).replace("%server%", p.getServer().getInfo().getName())), false);
					}
				}
				pl.setJoinMe(pl.getJoinMe() - 1);
				Main.getInstance().getManager().getCooldowns().put(p.getUniqueId(), System.currentTimeMillis());
        	} else {
				Utils.sendMessage(p, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.JoinMe.NoJoinMes"), true);
			}  	
        }
    }
    
    private int getCooldown(ProxiedPlayer player) {
    	User user = Main.getInstance().getLuckPerms().getUserManager().getUser(player.getUniqueId());		
    	Group group = Main.getInstance().getLuckPerms().getGroupManager().getGroup(user.getPrimaryGroup());
    	Set<String> permissions = group.getNodes().stream().filter(NodeType.PERMISSION::matches).map(NodeType.PERMISSION::cast).map(PermissionNode::getPermission).collect(Collectors.toSet());
    	
    	for(String perm : permissions) {
			if(perm.contains("fruitlab.joinme.time.")) {
				int cooldown = Integer.valueOf(perm.split("\\.")[3]);
				return cooldown;
			}
		}
		return Main.getInstance().getResources().getConfig().getConfig().getInt("CooldownDefault");
	}
    
    public String textBetweenWords(String sentence, String firstWord, String secondWord) {
	    return sentence.substring(sentence.indexOf(firstWord) + firstWord.length(), sentence.indexOf(secondWord));
	}
}

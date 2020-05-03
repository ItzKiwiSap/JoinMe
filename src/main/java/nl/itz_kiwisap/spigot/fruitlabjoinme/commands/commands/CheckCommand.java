package nl.itz_kiwisap.spigot.fruitlabjoinme.commands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import nl.itz_kiwisap.spigot.fruitlabjoinme.Main;
import nl.itz_kiwisap.spigot.fruitlabjoinme.commands.AbstractCommand;
import nl.itz_kiwisap.spigot.fruitlabjoinme.object.Player;
import nl.itz_kiwisap.spigot.fruitlabjoinme.utils.Utils;

public class CheckCommand extends AbstractCommand {

	public static final String NAME = "Check";
    public static final String DESCRIPTION = "Check how many joinmes you have";
    public static final String PERMISSION = "fruitlab.joinme.admin";
    public static final String USAGE = "/joinme check";
    public static final String[] SUB_PERMISSIONS = {""};
    
    public CheckCommand(CommandSender sender) {
    	super(sender, NAME, DESCRIPTION, PERMISSION, SUB_PERMISSIONS, USAGE);
    }

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(isSenderPlayer()) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			
			switch(args.length) {
				case 1:
					if(Main.getInstance().getManager().getPlayers() == null || Main.getInstance().getManager().getPlayers().isEmpty() || Main.getInstance().getManager().getPlayer(p) == null) {
						Main.getInstance().getManager().addPlayer(new Player(p, 0));
					}
					
					for(String string : Main.getInstance().getResources().getMessages().getConfig().getStringList("Messages.Check.Check")) {
						Utils.sendMessage(p, string.replace("%joinme%", String.valueOf(Main.getInstance().getManager().getPlayer(p).getJoinMe())), false);
					}
					break;
				case 2:
					if (!hasPermission()) {
			            Utils.sendMessage(sender, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.General.NoPermission"), true);
			            return;
			        }	
					
					String name = args[1];
					ProxiedPlayer player = Main.getInstance().getProxy().getPlayer(name);
					
					if(Main.getInstance().getManager().getPlayers() == null || Main.getInstance().getManager().getPlayers().isEmpty() || Main.getInstance().getManager().getPlayer(player) == null) {
						Main.getInstance().getManager().addPlayer(new Player(player, 0));
					}
					
					for(String string : Main.getInstance().getResources().getMessages().getConfig().getStringList("Messages.Check.CheckOther")) {
						String msg = string.replace("%player%", player.getName()).replace("%joinme%", String.valueOf(Main.getInstance().getManager().getPlayer(player).getJoinMe()));
						Utils.sendMessage(p, msg, false);
					}
					break;
			}
		} else {			
			switch(args.length) {
				case 1:
					Utils.sendMessage(sender, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.General.MissingArguments"), true);
					break;
				case 2:
					if (!hasPermission()) {
			            Utils.sendMessage(sender, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.General.NoPermission"), true);
			            return;
			        }
					
					String name = args[1];
					ProxiedPlayer player = Main.getInstance().getProxy().getPlayer(name);
					
					if(Main.getInstance().getManager().getPlayers() == null || Main.getInstance().getManager().getPlayers().isEmpty() || Main.getInstance().getManager().getPlayer(player) == null) {
						Main.getInstance().getManager().addPlayer(new Player(player, 0));
					}
					
					for(String string : Main.getInstance().getResources().getMessages().getConfig().getStringList("Messages.Check.CheckOther")) {
						String msg = string.replace("%player%", player.getName()).replace("%joinme%", String.valueOf(Main.getInstance().getManager().getPlayer(player).getJoinMe()));
						Utils.sendMessage(sender, msg, false);
					}
					break;
			}
		}
	}
}
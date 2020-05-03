package nl.itz_kiwisap.spigot.fruitlabjoinme.commands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import nl.itz_kiwisap.spigot.fruitlabjoinme.Main;
import nl.itz_kiwisap.spigot.fruitlabjoinme.commands.AbstractCommand;
import nl.itz_kiwisap.spigot.fruitlabjoinme.object.Player;
import nl.itz_kiwisap.spigot.fruitlabjoinme.utils.Utils;

public class RemoveCommand extends AbstractCommand {

	public static final String NAME = "Remove";
    public static final String DESCRIPTION = "Remove joinmes from a player";
    public static final String PERMISSION = "fruitlab.joinme.admin";
    public static final String USAGE = "/joinme remove <player> <amount>";
    public static final String[] SUB_PERMISSIONS = {""};
    
    public RemoveCommand(CommandSender sender) {
    	super(sender, NAME, DESCRIPTION, PERMISSION, SUB_PERMISSIONS, USAGE);
    }
    
    @Override
	public void execute(CommandSender sender, String[] args) {
		
		if (!hasPermission()) {
            Utils.sendMessage(sender, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.General.NoPermission"), true);
            return;
        }
		
		if(args.length == 1 || args.length == 2) {
			Utils.sendMessage(sender, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.General.MissingArguments"), true);
			return;
		}
		
		if(isSenderPlayer()) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			
			String name = args[1];
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);
			
			if(player == null) {
				Utils.sendMessage(sender, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.General.PlayerOffline"), true);
				return;
			}
			
			String am = args[2];
			if(isInt(am)) {
				int amount = Integer.valueOf(am);
				
				if(Main.getInstance().getManager().getPlayers() == null || Main.getInstance().getManager().getPlayers().isEmpty() || Main.getInstance().getManager().getPlayer(player) == null) {
					Main.getInstance().getManager().addPlayer(new Player(player, 0));
				}
				
				Main.getInstance().getManager().getPlayer(player).setJoinMe(Main.getInstance().getManager().getPlayer(player).getJoinMe() - amount);
				
				if(Main.getInstance().getManager().getPlayer(player).getJoinMe() < 0) {
					Main.getInstance().getManager().getPlayer(player).setJoinMe(0);
					Utils.sendMessage(p, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.Remove.LessThanZero"), true);
					return;
				}
				
				Utils.sendMessage(p, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.Remove.Removed").replace("%player%", player.getName()).replace("%amount%", String.valueOf(amount)), true);
				Utils.sendMessage(player, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.Remove.Received").replace("%amount%", String.valueOf(amount)), true);
			}
		} else {
			String name = args[1];
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);
			
			if(player == null) {
				Utils.sendMessage(sender, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.General.PlayerOffline"), true);
				return;
			}
			
			String am = args[2];
			if(isInt(am)) {
				int amount = Integer.valueOf(am);
				
				if(Main.getInstance().getManager().getPlayers() == null || Main.getInstance().getManager().getPlayers().isEmpty() || Main.getInstance().getManager().getPlayer(player) == null) {
					Main.getInstance().getManager().addPlayer(new Player(player, 0));
				}
				
				Main.getInstance().getManager().getPlayer(player).setJoinMe(Main.getInstance().getManager().getPlayer(player).getJoinMe() - amount);
				
				if(Main.getInstance().getManager().getPlayer(player).getJoinMe() < 0) {
					Main.getInstance().getManager().getPlayer(player).setJoinMe(0);
					Utils.sendMessage(sender, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.Remove.LessThanZero"), true);
					return;
				}
				
				Utils.sendMessage(sender, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.Remove.Removed").replace("%player%", player.getName()).replace("%amount%", String.valueOf(amount)), true);
				Utils.sendMessage(player, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.Remove.Received").replace("%amount%", String.valueOf(amount)), true);
			}
		}
    }
    
    private boolean isInt(String string) {
		try {
			Integer.valueOf(string);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

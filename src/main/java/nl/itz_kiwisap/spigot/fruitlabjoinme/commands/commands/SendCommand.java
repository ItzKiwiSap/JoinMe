package nl.itz_kiwisap.spigot.fruitlabjoinme.commands.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import nl.itz_kiwisap.spigot.fruitlabjoinme.Main;
import nl.itz_kiwisap.spigot.fruitlabjoinme.commands.AbstractCommand;
import nl.itz_kiwisap.spigot.fruitlabjoinme.utils.Utils;

public class SendCommand extends AbstractCommand {

	public static final String NAME = "dajglieja;ijg";
    public static final String DESCRIPTION = "Join a other player";
    public static final String PERMISSION = "";
    public static final String USAGE = "/joinme dajglieja;ijg <player> <server>";
    public static final String[] SUB_PERMISSIONS = {""};
    
    public SendCommand(CommandSender sender) {
    	super(sender, NAME, DESCRIPTION, PERMISSION, SUB_PERMISSIONS, USAGE);
    }

	@Override
	public void execute(CommandSender sender, String[] args) {
		
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
			
			String servername = args[2];
			ServerInfo server = ProxyServer.getInstance().getServerInfo(servername);
			
			player.connect(server);
			Utils.sendMessage(player, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.Send.Sending").replace("%player%", p.getName()), true);
			Utils.sendMessage(p, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.Send.Join").replace("%player%", player.getName()), true);
		}
	}
}
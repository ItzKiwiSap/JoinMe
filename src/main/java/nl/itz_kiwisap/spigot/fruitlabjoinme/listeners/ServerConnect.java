package nl.itz_kiwisap.spigot.fruitlabjoinme.listeners;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import nl.itz_kiwisap.spigot.fruitlabjoinme.Main;

public class ServerConnect implements Listener {

	private Main plugin;
	
	public ServerConnect(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onConnect(ServerConnectEvent event) {
		ProxiedPlayer player = event.getPlayer();
		
		this.plugin.getDatabase().addPlayer(player);
		this.plugin.getResources().saveAndAddPlayer(player);
	}
}

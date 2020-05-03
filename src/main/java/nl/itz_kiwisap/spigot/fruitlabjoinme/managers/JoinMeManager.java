package nl.itz_kiwisap.spigot.fruitlabjoinme.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import nl.itz_kiwisap.spigot.fruitlabjoinme.Main;
import nl.itz_kiwisap.spigot.fruitlabjoinme.object.Player;

public class JoinMeManager {

	public Main plugin;
	private List<Player> players;
	private Map<UUID, Long> cooldowns;
	
	public JoinMeManager(Main plugin) {
		this.plugin = plugin;
		this.players = new ArrayList<>();
		this.cooldowns = new HashMap<>();
	}
	
	public void addPlayer(Player player) {
		if(players.contains(player)) return;
		else players.add(player);
	}
	
	public Player getPlayer(ProxiedPlayer player) {
		if(this.players == null || this.players.isEmpty()) return null;
		for(Player pl : this.players) {
			if(pl.getUniqueId().toString().equals(player.getUniqueId().toString())) {
				return pl;
			}
		}
		return null;
	}
	
	public List<Player> getPlayers() { return this.players; }
	public Map<UUID, Long> getCooldowns() { return this.cooldowns; }
}

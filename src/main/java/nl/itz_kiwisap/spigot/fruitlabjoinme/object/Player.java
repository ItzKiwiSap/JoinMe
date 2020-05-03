package nl.itz_kiwisap.spigot.fruitlabjoinme.object;

import java.util.UUID;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Player {

	private ProxiedPlayer player;
	private String name;
	private UUID uuid;
	private int joinme;
	
	public Player(ProxiedPlayer player, int joinme) {
		this.player = player;
		this.name = player.getName();
		this.uuid = player.getUniqueId();
		this.joinme = joinme;
	}
	
	public void setJoinMe(int joinme) { this.joinme = joinme; }
	
	public ProxiedPlayer getPlayer() { return this.player; }
	public String getName() { return this.name; }
	public UUID getUniqueId() { return this.uuid; }
	public int getJoinMe() { return this.joinme; }
}
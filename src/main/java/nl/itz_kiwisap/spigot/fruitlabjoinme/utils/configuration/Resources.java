package nl.itz_kiwisap.spigot.fruitlabjoinme.utils.configuration;

import java.io.File;
import java.util.UUID;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import nl.itz_kiwisap.spigot.fruitlabjoinme.Main;
import nl.itz_kiwisap.spigot.fruitlabjoinme.object.Player;

public class Resources {
	
	private Main plugin;
	private Resource config;
	private Resource messages;
	private Resource storage;
	
	public Resources(Main plugin) {
		this.plugin = plugin;
		this.config = new Resource((Plugin) this.plugin, "config.yml");
		this.messages = new Resource((Plugin) this.plugin, "messages.yml");
		this.storage = new Resource((Plugin) this.plugin, "storage.yml");
	}
	
	public void load() {
		this.config.load();
		this.messages.load();
		this.storage.load();
	}
	
	public void save() {
		this.config.save();
		this.messages.save();
		this.storage.save();
	}
	
	public void saveAndRemovePlayer(Player p) {
		if(!plugin.getDatabase().isEnabled()) {			
			this.storage.getConfig().set("Players." + p.getPlayer().getUniqueId() + ".JoinMe", p.getJoinMe());
			this.storage.save();
		}
	}
	
	public void saveAndAddPlayer(ProxiedPlayer p) {
		if(!plugin.getDatabase().isEnabled()) {
			File file = new File(plugin.getDataFolder(), "storage.yml");
			
			if(file.exists()) {
				for(String string : this.storage.getConfig().getSection("Players").getKeys()) {
					UUID uuid = UUID.fromString(string);
					ProxiedPlayer player = plugin.getProxy().getPlayer(uuid);
					
					int joinmes = Integer.valueOf(this.storage.getConfig().getInt("Players." + string + ".JoinMe"));
						
					plugin.getManager().addPlayer(new Player(player, joinmes));
				}
			}
		}
	}
	
	public Resource getConfig() { return this.config; }
	public Resource getMessages() { return this.messages; }
	public Resource getStorage() { return this.storage; }
}
package nl.itz_kiwisap.spigot.fruitlabjoinme;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.plugin.Plugin;
import nl.itz_kiwisap.spigot.fruitlabjoinme.commands.CommandHandler;
import nl.itz_kiwisap.spigot.fruitlabjoinme.listeners.ServerConnect;
import nl.itz_kiwisap.spigot.fruitlabjoinme.managers.JoinMeManager;
import nl.itz_kiwisap.spigot.fruitlabjoinme.object.Player;
import nl.itz_kiwisap.spigot.fruitlabjoinme.utils.configuration.Resources;
import nl.itz_kiwisap.spigot.fruitlabjoinme.utils.database.Database;

public class Main extends Plugin {
	
	private static Main instance;
	private Resources resources = new Resources(this);
	private Database database;
	private JoinMeManager manager;
	private LuckPerms api;
	
	@Override
	public void onEnable() {
		instance = this;
		
		this.resources.load();
		this.manager = new JoinMeManager(this);
		this.database = new Database(this, "Database");
		this.api = LuckPermsProvider.get();
		
		if(this.getResources().getConfig().getConfig().getBoolean("Database.Enabled")) {
			this.database.setup();
			this.database.holdConnection();
			this.database.createData();
		}
		
		getProxy().getPluginManager().registerListener((Plugin) this, new ServerConnect(this));	
		getProxy().getPluginManager().registerCommand(this, new CommandHandler("joinme"));
	}
	
	@Override
	public void onDisable() {
		this.resources.getStorage().save();
		for(Player player : this.manager.getPlayers()) {
			this.database.saveAndRemovePlayer(player);
			this.resources.saveAndRemovePlayer(player);
		}
		this.manager.getPlayers().clear();
	}
	
	public static Main getInstance() { return instance; }
	public Resources getResources() { return this.resources; }
	public Database getDatabase() { return this.database; }
	public JoinMeManager getManager() { return this.manager; }
	public LuckPerms getLuckPerms() { return this.api; }
}

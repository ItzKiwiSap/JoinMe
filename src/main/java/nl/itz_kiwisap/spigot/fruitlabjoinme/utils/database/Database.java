package nl.itz_kiwisap.spigot.fruitlabjoinme.utils.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import nl.itz_kiwisap.spigot.fruitlabjoinme.Main;
import nl.itz_kiwisap.spigot.fruitlabjoinme.object.Player;
import nl.itz_kiwisap.spigot.fruitlabjoinme.utils.Utils;

public class Database {

	private Main plugin;
	private boolean isEnabled = false;
	private Connection connection;
	private String host, database, username, password, table;
	private int port;
	
	public Database(Main plugin, String path) {
		this.plugin = plugin;

		this.host = plugin.getResources().getConfig().getConfig().getString(String.valueOf(path) + ".Address").split(":")[0];
		this.database = plugin.getResources().getConfig().getConfig().getString(String.valueOf(path) + ".Database");
		this.table = plugin.getResources().getConfig().getConfig().getString(String.valueOf(path) + ".Table");
		this.port = Integer.valueOf(plugin.getResources().getConfig().getConfig().getString(String.valueOf(path) + ".Address").split(":")[1]);
		this.username = plugin.getResources().getConfig().getConfig().getString(String.valueOf(path) + ".Username");
		this.password = plugin.getResources().getConfig().getConfig().getString(String.valueOf(path) + ".Password");
	}
	
	public void setup() {
		try {
			synchronized(this) {
				if(this.connection != null && !this.connection.isClosed()) return;

				Class.forName("com.mysql.jdbc.Driver");
				this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
				plugin.getProxy().getConsole().sendMessage(new TextComponent(Utils.tr("§a§lDatabase succesfully connected!")));
				this.isEnabled = true;
			}
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void holdConnection() {
		for(ProxiedPlayer p : plugin.getProxy().getPlayers()) {
			plugin.getDatabase().savePlayer(plugin.getManager().getPlayer(p));
		}

		plugin.getProxy().getScheduler().schedule(this.plugin, () -> holdConnection(), 24000 / 20L, TimeUnit.SECONDS);
	}
	
	public void createData() {
		boolean isTableCreated = false;

		try {
			DatabaseMetaData databasemeta = this.connection.getMetaData();
			ResultSet result = databasemeta.getTables(null, null, this.table, null);

			if(result.next()) isTableCreated = true;

			String playerTable = "CREATE TABLE " + this.table + " (" +
					"ID int NOT NULL AUTO_INCREMENT," +
					"UUID varchar(255)," +
					"NAME varchar(255)," +
					"JOINME int(10)," +
					"PRIMARY KEY (ID)" +
					")";

			if(!isTableCreated) {
				PreparedStatement statement = this.connection.prepareStatement(playerTable);
				statement.executeUpdate();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addPlayer(ProxiedPlayer p) {
		if(this.isEnabled) {
			try {
				PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM " + this.table + " WHERE UUID=?");
				statement.setString(1, p.getUniqueId().toString());
				ResultSet result = statement.executeQuery();
				boolean hasPlayerData = false;

				while(result.next()) {
					hasPlayerData = true;

					int joinme = result.getInt("JOINME");
					
					plugin.getManager().addPlayer(new Player(p, joinme));
				}
				
				if(!hasPlayerData) {
					PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO " + this.table + " (UUID, NAME, JOINME) VALUES (?, ?, ?)");
					stmt.setString(1, p.getUniqueId().toString());
					stmt.setString(2, p.getName());
					stmt.setInt(3, 0);
					stmt.executeUpdate();
					
					plugin.getManager().addPlayer(new Player(p, 0));
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void savePlayer(Player p) {
		if (this.isEnabled) {
			try {
				PreparedStatement statement = this.connection.prepareStatement("UPDATE " + this.table + 
						" SET JOINME=? WHERE UUID=?");
				statement.setInt(1, p.getJoinMe());
				statement.setString(2, p.getPlayer().getUniqueId().toString());
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
	}
	
	public void saveAndRemovePlayer(Player p) {
		if (this.isEnabled) {
			try {
				PreparedStatement statement = this.connection.prepareStatement("UPDATE " + this.table + 
						" SET JOINME=? WHERE UUID=?");
				statement.setInt(1, p.getJoinMe());
				statement.setString(2, p.getPlayer().getUniqueId().toString());
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.plugin.getManager().getPlayers().remove(p);
		} 
	}
	
	public boolean isEnabled() { return this.isEnabled; }
}

package nl.itz_kiwisap.spigot.fruitlabjoinme.utils.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Resource {

	private String name;
	private File file;
	private Configuration config;
	
	public Resource(Plugin plugin, String name) {
		this.name = name;
		this.file = new File(plugin.getDataFolder(), name);
		
		if(!this.file.getParentFile().exists())	this.file.getParentFile().mkdirs();
		if (!this.file.exists()) {
            try (InputStream in = plugin.getResourceAsStream(name)) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	public void load() {
		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() { return this.name; }
	public File getFile() { return this.file; }
	public Configuration getConfig() { return this.config; }
}

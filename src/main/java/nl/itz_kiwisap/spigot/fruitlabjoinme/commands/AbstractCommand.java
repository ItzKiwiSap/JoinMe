package nl.itz_kiwisap.spigot.fruitlabjoinme.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class AbstractCommand {

	private final CommandSender sender;
    private final String name;
    private final String description;
    private final String permission;
    private final String usage;
    private final String[] subPermissions;

    public AbstractCommand(CommandSender sender, String name, String description, String permission, String[] subPermissions, String usage) {
        this.sender = sender;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.subPermissions = subPermissions;
        this.usage = usage;
    }

    public CommandSender getSender() { return sender; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPermission() { return permission; }
    public String[] getSubPermissions() { return subPermissions; }
    public String getUsage() { return usage; }
    public boolean hasPermission() { return sender.hasPermission(permission); }
    public boolean isSenderPlayer() { return (sender instanceof ProxiedPlayer); }
    public abstract void execute(CommandSender sender, String[] args);
    
    public boolean hasPermission(String sub) {
        String permission = this.permission + "." + sub;
        return sender.hasPermission(permission);
    }
}

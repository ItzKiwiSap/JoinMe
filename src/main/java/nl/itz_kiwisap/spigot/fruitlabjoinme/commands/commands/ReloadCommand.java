package nl.itz_kiwisap.spigot.fruitlabjoinme.commands.commands;

import net.md_5.bungee.api.CommandSender;
import nl.itz_kiwisap.spigot.fruitlabjoinme.Main;
import nl.itz_kiwisap.spigot.fruitlabjoinme.commands.AbstractCommand;
import nl.itz_kiwisap.spigot.fruitlabjoinme.utils.Utils;

public class ReloadCommand extends AbstractCommand {

	public static final String NAME = "Reload";
    public static final String DESCRIPTION = "Reload configuration files";
    public static final String PERMISSION = "fruitlab.joinme.admin";
    public static final String USAGE = "/joinme reload";
    public static final String[] SUB_PERMISSIONS = {""};
    
    public ReloadCommand(CommandSender sender) {
    	super(sender, NAME, DESCRIPTION, PERMISSION, SUB_PERMISSIONS, USAGE);
    }

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if (!hasPermission()) {
            Utils.sendMessage(sender, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.General.NoPermission"), true);
            return;
        }
		
		Main.getInstance().getResources().load();
		Utils.sendMessage(sender, Main.getInstance().getResources().getMessages().getConfig().getString("Messages.General.Reload"), true);
	}
}

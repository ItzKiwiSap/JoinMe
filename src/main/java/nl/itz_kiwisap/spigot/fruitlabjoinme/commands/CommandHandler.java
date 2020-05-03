package nl.itz_kiwisap.spigot.fruitlabjoinme.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import nl.itz_kiwisap.spigot.fruitlabjoinme.Main;
import nl.itz_kiwisap.spigot.fruitlabjoinme.commands.commands.AddCommand;
import nl.itz_kiwisap.spigot.fruitlabjoinme.commands.commands.CheckCommand;
import nl.itz_kiwisap.spigot.fruitlabjoinme.commands.commands.JoinMeCommand;
import nl.itz_kiwisap.spigot.fruitlabjoinme.commands.commands.ReloadCommand;
import nl.itz_kiwisap.spigot.fruitlabjoinme.commands.commands.RemoveCommand;
import nl.itz_kiwisap.spigot.fruitlabjoinme.commands.commands.SendCommand;

public class CommandHandler extends Command implements TabExecutor {

	public CommandHandler(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		AbstractCommand cmd = new JoinMeCommand(sender);
		
		if(args.length == 0) {
			cmd.execute(sender, args);
			return;
		}
		
		switch(args[0].toLowerCase()) {
			case "add":
				cmd = new AddCommand(sender);
				break;
			case "remove":
				cmd = new RemoveCommand(sender);
				break;
			case "reload":
				cmd = new ReloadCommand(sender);
				break;
			case "check":
				cmd = new CheckCommand(sender);
				break;
			case "dajglieja;ijg":
				cmd = new SendCommand(sender);
				break;
		}
	
		cmd.execute(sender, args);
		return;
	}
	
	private static final String[] COMMANDS = { "add", "remove", "reload", "check" };
	
	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("fruitlab.joinme.admin")) {			
			if (args.length == 0) {
			    return Collections.emptyList();
			}
			
			if(args.length == 1) {
				List<String> commands = new ArrayList<>(Arrays.asList(COMMANDS));
				return commands;
			}
			
			if(args.length == 2) {
				List<String> names = new ArrayList<>();
				for(ProxiedPlayer player : Main.getInstance().getProxy().getPlayers())
					names.add(player.getName());
				return names;
			}
		}	
		return Collections.emptyList();
	}
}

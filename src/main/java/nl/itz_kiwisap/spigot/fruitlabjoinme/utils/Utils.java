package nl.itz_kiwisap.spigot.fruitlabjoinme.utils;

import java.util.Collection;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import nl.itz_kiwisap.spigot.fruitlabjoinme.Main;

public class Utils {

	public static void sendMessage(ProxiedPlayer p, String s, boolean prefix) {
		if(prefix)
			p.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getResources().getMessages().getConfig().getString("Messages.General.Prefix") + s)));
		else
			p.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', s)));
	}
	
	public static void sendMessage(CommandSender sender, String s, boolean prefix) {
		if(prefix)
			sender.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getResources().getMessages().getConfig().getString("Messages.General.Prefix") + s)));
		else
			sender.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', s)));
	}
	
	public static void sendMessage(ProxiedPlayer p, TextComponent s, boolean prefix) {
		if(prefix) {
			p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getResources().getMessages().getConfig().getString("Messages.General.Prefix") + s.getText())));
		} else
			p.sendMessage(s);
	}
	
	public static String tr(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static <T extends Collection<? super String>> T copyPartialMatches(final String token, final Iterable<String> originals, final T collection) throws UnsupportedOperationException, IllegalArgumentException {

        for (String string : originals) {
            if (startsWithIgnoreCase(string, token)) {
                collection.add(string);
            }
        }

        return collection;
    }
	
	private static boolean startsWithIgnoreCase(final String string, final String prefix) throws IllegalArgumentException, NullPointerException {
        if (string.length() < prefix.length()) {
            return false;
        }
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
    }
}

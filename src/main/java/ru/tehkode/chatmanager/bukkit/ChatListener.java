/*
 * ChatManager - PermissionsEx Chat management plugin for Bukkit
 * Copyright (C) 2011 t3hk0d3 http://www.tehkode.ru
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package ru.tehkode.chatmanager.bukkit;

import java.util.Calendar;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author t3hk0d3
 */
public class ChatListener implements Listener {
	protected static Pattern chatColorPattern = Pattern.compile("(?i)&([0-9A-F])");
	protected static Pattern chatMagicPattern = Pattern.compile("(?i)&([K])");
	protected static Pattern chatBoldPattern = Pattern.compile("(?i)&([L])");
	protected static Pattern chatStrikethroughPattern = Pattern.compile("(?i)&([M])");
	protected static Pattern chatUnderlinePattern = Pattern.compile("(?i)&([N])");
	protected static Pattern chatItalicPattern = Pattern.compile("(?i)&([O])");
	protected static Pattern chatResetPattern = Pattern.compile("(?i)&([R])");
	
	public final static String MESSAGE_FORMAT = "<%prefix%player%suffix> %message";
	protected String messageFormat = MESSAGE_FORMAT;
	protected String displayNameFormat = "%prefix%player%suffix";
        
        private ChatManager plugin = null;

	public ChatListener(FileConfiguration config, ChatManager plugin) {
		this.messageFormat = config.getString("message-format", this.messageFormat);
		this.displayNameFormat = config.getString("display-name-format", this.displayNameFormat);
                
                this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (event.isCancelled()) {
                    return;
		}

		Player player = event.getPlayer();

		String worldName = player.getWorld().getName();

		String message = messageFormat;
                
		String chatMessage = event.getMessage();

		message = this.translateColorCodes(message);

		chatMessage = this.translateColorCodes(chatMessage, worldName);

		message = message.replace("%message", "%2$s").replace("%displayname", "%1$s");
		message = this.replacePlayerPlaceholders(player, message);
		message = this.replaceTime(message);

		event.setFormat(message);
		event.setMessage(chatMessage);
	}

	protected void updateDisplayNames() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			updateDisplayName(player);
		}
	}

	protected void updateDisplayName(Player player) {
                if (player == null) return;

		player.setDisplayName(this.translateColorCodes(this.replacePlayerPlaceholders(player, this.displayNameFormat)));
	}

	protected String replacePlayerPlaceholders(Player player, String format) {
		String worldName = player.getWorld().getName();
		return format.replace("%prefix", this.translateColorCodes(plugin.chat.getPlayerPrefix(player)))
                        .replace("%suffix", this.translateColorCodes(plugin.chat.getPlayerSuffix(player)))
                        .replace("%world", worldName)
                        .replace("%player", player.getDisplayName())
                        .replace("%group", plugin.permission.getPrimaryGroup(player));
	}

	protected String replaceTime(String message) {
		Calendar calendar = Calendar.getInstance();

		if (message.contains("%h")) {
			message = message.replace("%h", String.format("%02d", calendar.get(Calendar.HOUR)));
		}

		if (message.contains("%H")) {
			message = message.replace("%H", String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
		}

		if (message.contains("%g")) {
			message = message.replace("%g", Integer.toString(calendar.get(Calendar.HOUR)));
		}

		if (message.contains("%G")) {
			message = message.replace("%G", Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)));
		}

		if (message.contains("%i")) {
			message = message.replace("%i", String.format("%02d", calendar.get(Calendar.MINUTE)));
		}

		if (message.contains("%s")) {
			message = message.replace("%s", String.format("%02d", calendar.get(Calendar.SECOND)));
		}

		if (message.contains("%a")) {
			message = message.replace("%a", (calendar.get(Calendar.AM_PM) == 0) ? "am" : "pm");
		}

		if (message.contains("%A")) {
			message = message.replace("%A", (calendar.get(Calendar.AM_PM) == 0) ? "AM" : "PM");
		}

		return message;
	}

	protected String translateColorCodes(String string) {
		if (string == null) {
			return "";
		}

		String newstring = string;
		newstring = chatColorPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatMagicPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatBoldPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatStrikethroughPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatUnderlinePattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatItalicPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatResetPattern.matcher(newstring).replaceAll("\u00A7$1");
		return newstring;
	}

	protected String translateColorCodes(String string, String worldName) {
		if (string == null) {
			return "";
		}

		String newstring = string;
                newstring = chatColorPattern.matcher(newstring).replaceAll("\u00A7$1");
                newstring = chatMagicPattern.matcher(newstring).replaceAll("\u00A7$1");
                newstring = chatBoldPattern.matcher(newstring).replaceAll("\u00A7$1");
                newstring = chatStrikethroughPattern.matcher(newstring).replaceAll("\u00A7$1");
                newstring = chatUnderlinePattern.matcher(newstring).replaceAll("\u00A7$1");
                newstring = chatItalicPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatResetPattern.matcher(newstring).replaceAll("\u00A7$1");
		return newstring;
	}
}

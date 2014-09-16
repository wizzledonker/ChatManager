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
import java.util.Map;
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
        protected String defaultGroup = "hobo";
        protected Map swearWords;
        
        private ChatManager plugin = null;

	public ChatListener(FileConfiguration config, ChatManager plugin) {
		this.messageFormat = config.getString("message-format", this.messageFormat);
		this.displayNameFormat = config.getString("display-name-format", this.displayNameFormat);
                this.defaultGroup = config.getString("default_class", this.defaultGroup);
                this.swearWords = config.getConfigurationSection("swear_words").getValues(true);
                
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
                
                for (Object word : this.swearWords.keySet()) {
                    if (chatMessage.toLowerCase().contains(((String) word).toLowerCase())) {
                        chatMessage = this.replaceAll((String) word, (String) swearWords.get(word), chatMessage, true);
                    }
                }
                
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
                String formatted = format.replace("%prefix", this.translateColorCodes(determinePrefix(player)));
                
                formatted = formatted.replace("%suffix", this.translateColorCodes(determineSuffix(player)));
                formatted = formatted.replace("%world", worldName);
                formatted = formatted.replace("%player", player.getDisplayName());
                
                String group = plugin.permission.getPrimaryGroup(worldName, player.getUniqueId().toString());
                
                if (group != null) {
                    formatted = formatted.replace("%group", group);
                } else {
                    formatted = formatted.replace("%group", this.defaultGroup);
                }
                
                
		return formatted;
	}
        
        private String determinePrefix(Player player) {
            String finalPrefix = "";
            
            if (plugin.chat.getPlayerPrefix(player) != "") {
                finalPrefix = plugin.chat.getPlayerPrefix(player);
            } else {
                finalPrefix = plugin.chat.getGroupPrefix(plugin.permission.getPrimaryGroup(player.getWorld().getName(), player.getUniqueId().toString()));
            }
            
            return finalPrefix;
        }
        
        private String determineSuffix(Player player) {
            String finalSuffix = "";

            if (plugin.chat.getPlayerSuffix(player) != "") {
                finalSuffix = plugin.chat.getPlayerSuffix(player);
            } else {
                finalSuffix = plugin.chat.getGroupSuffix(plugin.permission.getPrimaryGroup(player.getWorld().getName(), player.getUniqueId().toString()));
            }
            
            return finalSuffix;
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
        
        public String replaceAll(String findtxt, String replacetxt, String str, boolean isCaseInsensitive) {
            if (str == null) {
                return null;
            }
            if (findtxt == null || findtxt.length() == 0) {
                return str;
            }
            if (findtxt.length() > str.length()) {
                return str;
            }
            int counter = 0;
            String thesubstr = "";
            while ((counter < str.length()) 
                    && (str.substring(counter).length() >= findtxt.length())) {
                thesubstr = str.substring(counter, counter + findtxt.length());
                if (isCaseInsensitive) {
                    if (thesubstr.equalsIgnoreCase(findtxt)) {
                        str = str.substring(0, counter) + replacetxt 
                            + str.substring(counter + findtxt.length());
                        // Failing to increment counter by replacetxt.length() leaves you open
                        // to an infinite-replacement loop scenario: Go to replace "a" with "aa" but
                        // increment counter by only 1 and you'll be replacing 'a's forever.
                        counter += replacetxt.length();
                    } else {
                        counter++; // No match so move on to the next character from
                                   // which to check for a findtxt string match.
                    }
                } else {
                    if (thesubstr.equals(findtxt)) {
                        str = str.substring(0, counter) + replacetxt 
                            + str.substring(counter + findtxt.length());
                        counter += replacetxt.length();
                    } else {
                        counter++;
                    }
                }
            }
            return str;
        }
}

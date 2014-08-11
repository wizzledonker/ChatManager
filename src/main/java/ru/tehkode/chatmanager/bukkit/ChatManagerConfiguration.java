/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.tehkode.chatmanager.bukkit;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Winfried
 */
public class ChatManagerConfiguration{

    private static ChatManager plugin;
    
    private File mainConfigFile = null;
    private FileConfiguration mainConfig = null;
    
    public ChatManagerConfiguration(ChatManager instance) {
        plugin = instance;
    }
    
    private void reloadmainConfig() {
        if (mainConfigFile == null) {
            mainConfigFile = new File(plugin.getDataFolder(), "groups.yml");
        }
        mainConfig = YamlConfiguration.loadConfiguration(mainConfigFile);
    }
    
    private FileConfiguration getmainConfig() {
        if (mainConfig == null) {
            reloadmainConfig();
        }
        return mainConfig;
    }
    
    private void savemainConfig() {
        if (mainConfigFile == null || mainConfig == null) {
            return;
        }
        try {
            mainConfig.save(mainConfigFile);
        } catch (IOException ex) {
            plugin.log("oops! Error saving player config file. Here's the details: " + ex.toString());
        }
    }
    
    public void setPlayerPrefix(Player player, String prefix) {
        this.getmainConfig().set("players." + player.getUniqueId() + ".prefix", prefix);
        this.savemainConfig();
    }
    
    public void setGroupPrefix(String group, String prefix) {
        this.getmainConfig().set("groups." + group + ".prefix", prefix);
        this.savemainConfig();
    }
    
    public void setPlayerSuffix(Player player, String suffix) {
        this.getmainConfig().set("players." + player.getUniqueId() + ".suffix", suffix);
        this.savemainConfig();
    }
    
    public void setGroupSuffix(String group, String suffix) {
        this.getmainConfig().set("groups." + group + ".suffix", suffix);
        this.savemainConfig();
    }
    
    public String getGroupPrefix(String group) {
        if (this.getmainConfig().getString("groups." + group + ".prefix") != null) {
            return this.getmainConfig().getString("groups." + group + ".prefix");
        }
        
        return "";
    }
    
    public String getPlayerPrefix(Player player) {
        if (this.getmainConfig().getString("players." + player.getUniqueId().toString() + ".prefix") != null) {
            return this.getmainConfig().getString("players." + player.getUniqueId() + ".prefix");
        }
        
        return "";
    }
    
    public String getGroupSuffix(String group) {
        if (this.getmainConfig().getString("groups." + group + ".prefix") != null) {
            return this.getmainConfig().getString("groups." + group + ".prefix");
        }
        
        return "";
    }
    
    public String getPlayerSuffix(Player player) {
        if (this.getmainConfig().getString("players." + player.getUniqueId().toString() + ".suffix") != null) {
            return this.getmainConfig().getString("players." + player.getUniqueId() + ".suffix");
        }
        
        return "";
    }
    
}

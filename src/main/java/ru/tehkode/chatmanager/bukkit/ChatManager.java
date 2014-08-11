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

import java.io.File;
import java.util.logging.Logger;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author t3hk0d3
 */
public class ChatManager extends JavaPlugin {

    protected static Logger log;
    public Permission permission = null;
    protected ChatListener listener;
    
    public ChatManagerConfiguration chat = new ChatManagerConfiguration(this);

    @Override
    public void onEnable() {
    	log = this.getLogger();

        FileConfiguration config = this.getConfig();
        
        this.initializeConfiguration(config);
        this.listener = new ChatListener(config, this);

        if (config.getBoolean("enable", false) && setupPermissions()) {
            this.getServer().getPluginManager().registerEvents(listener, this);
            log.info("ChatManager enabled! (Custom for oblicom)");
        } else {
            log.info("ChatManager disabled. Check config.yml!");
            this.getPluginLoader().disablePlugin(this);
        }

        this.saveConfig();
    }

    @Override
    public void onDisable() {
        this.listener = null;
        
        log.info("ChatManager disabled!");
    }
    
    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    protected void initializeConfiguration(FileConfiguration config) {
        // Flags
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            config.set("enable", true);
            config.set("message-format", ChatListener.MESSAGE_FORMAT);
            config.set("display-name-format", "%prefix%player%suffix");
        }

        
        saveConfig();
    }

    void log(String string) {
        System.out.println("[" + this.getName() + "] " + string);
    }

}

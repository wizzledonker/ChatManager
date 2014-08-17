/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.tehkode.chatmanager.bukkit;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

/**
 *
 * @author Winfried
 */
public class VaultHandler extends Chat {
    private final String name = "OblicomChatManager";
    
    private ChatManager plugin;
    
    public VaultHandler(ChatManager instance, Permission perms) {
        super(perms);
        
        plugin = instance;
        
        plugin.log("Vault support enabled.");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isEnabled() {
        return plugin != null;
    }

    @Override
    public String getPlayerPrefix(String string, String string1) {
        return plugin.chat.getPlayerPrefix(plugin.getServer().getPlayer(string1));
    }

    @Override
    public void setPlayerPrefix(String string, String string1, String string2) {
        plugin.chat.setPlayerPrefix(plugin.getServer().getPlayer(string1), string2);
    }
    
    @Override
    public void setPlayerPrefix(Player player, String string1) {
        plugin.chat.setPlayerPrefix(player, string1);
    }

    @Override
    public String getPlayerSuffix(String string, String string1) {
        return plugin.chat.getPlayerSuffix(plugin.getServer().getPlayer(string1));
    }

    @Override
    public void setPlayerSuffix(String string, String string1, String string2) {
        plugin.chat.setPlayerSuffix(plugin.getServer().getPlayer(string1), string2);
    }

    @Override
    public String getGroupPrefix(String string, String string1) {
        return plugin.chat.getGroupPrefix(string1);
    }

    @Override
    public void setGroupPrefix(String string, String string1, String string2) {
        plugin.chat.setGroupPrefix(string1, string2);
    }

    @Override
    public String getGroupSuffix(String string, String string1) {
        return plugin.chat.getGroupSuffix(string1);
    }

    @Override
    public void setGroupSuffix(String string, String string1, String string2) {
        plugin.chat.setGroupSuffix(string1, string2);
    }

    @Override
    public int getPlayerInfoInteger(String string, String string1, String string2, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPlayerInfoInteger(String string, String string1, String string2, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getGroupInfoInteger(String string, String string1, String string2, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGroupInfoInteger(String string, String string1, String string2, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getPlayerInfoDouble(String string, String string1, String string2, double d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPlayerInfoDouble(String string, String string1, String string2, double d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getGroupInfoDouble(String string, String string1, String string2, double d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGroupInfoDouble(String string, String string1, String string2, double d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getPlayerInfoBoolean(String string, String string1, String string2, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPlayerInfoBoolean(String string, String string1, String string2, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getGroupInfoBoolean(String string, String string1, String string2, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGroupInfoBoolean(String string, String string1, String string2, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPlayerInfoString(String string, String string1, String string2, String string3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPlayerInfoString(String string, String string1, String string2, String string3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getGroupInfoString(String string, String string1, String string2, String string3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGroupInfoString(String string, String string1, String string2, String string3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

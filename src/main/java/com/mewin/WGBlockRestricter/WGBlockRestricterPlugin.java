/*
 * Copyright (C) 2014 mewin <mewin001@hotmail.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mewin.WGBlockRestricter;

import com.mewin.WGBlockRestricter.flags.BlockMaterialFlag;
import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.mewin.WGCustomFlags.flags.CustomSetFlag;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author mewin <mewin001@hotmail.de>
 */
public class WGBlockRestricterPlugin extends JavaPlugin {
    public static final BlockMaterialFlag BLOCK_TYPE_FLAG = new BlockMaterialFlag("block-type");
    public static final CustomSetFlag ALLOW_BLOCK_FLAG = new CustomSetFlag("allow-blocks", BLOCK_TYPE_FLAG);
    public static final CustomSetFlag DENY_BLOCK_FLAG = new CustomSetFlag("deny-blocks", BLOCK_TYPE_FLAG);
    public static final CustomSetFlag ALLOW_PLACE_FLAG = new CustomSetFlag("allow-place", BLOCK_TYPE_FLAG);
    public static final CustomSetFlag DENY_PLACE_FLAG = new CustomSetFlag("deny-place", BLOCK_TYPE_FLAG);
    public static final CustomSetFlag ALLOW_BREAK_FLAG = new CustomSetFlag("allow-break", BLOCK_TYPE_FLAG);
    public static final CustomSetFlag DENY_BREAK_FLAG = new CustomSetFlag("deny-break", BLOCK_TYPE_FLAG);
    
    private BlockListener listener;
    private WorldGuardPlugin wgPlugin;
    private WGCustomFlagsPlugin custPlugin;
    
    @Override
    public void onEnable() {
        wgPlugin = getWorldGuard();
        custPlugin = getWGCustomFlags();
        
        if (wgPlugin == null) {
            getLogger().warning("This plugin requires WorldGuard, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        if (custPlugin == null) {
            getLogger().warning("This plugin requires WorldGuard Custom Flags, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        listener = new BlockListener(this, wgPlugin);
        
        getServer().getPluginManager().registerEvents(listener, this);
        
        loadConfig();
        
        Utils.init();
    }
    
    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        
        return (WorldGuardPlugin) plugin;
    }
    
    private WGCustomFlagsPlugin getWGCustomFlags() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WGCustomFlags");
        
        if (plugin == null || !(plugin instanceof WGCustomFlagsPlugin)) {
            return null;
        }
        
        return (WGCustomFlagsPlugin) plugin;
    }
    
    private void loadConfig()
    {
        File configFile = new File(this.getDataFolder(), "config.yml");
        if (!this.getDataFolder().exists())
        {
            this.getDataFolder().mkdirs();
        }
        
        if (!configFile.exists())
        {
            createDefaultConfig(configFile);
        }
        
        try
        {
            this.getConfig().load(configFile);
        }
        catch(FileNotFoundException x)
        {
            getLogger().log(Level.WARNING, "Creating default configuration failed!");
        }
        catch(IOException ex)
        {
            getLogger().log(Level.WARNING, "Could not load configuration file: ", ex);
        }
        catch(Exception ex)
        {
            getLogger().log(Level.WARNING, "Invalid configuration: ", ex);
        }
    }
    
    private void createDefaultConfig(File configFile)
    {
        FileOutputStream out = null;
        InputStream in = null;
        try
        {
            configFile.createNewFile();
            out = new FileOutputStream(configFile);
            in = WGBlockRestricterPlugin.class.getResourceAsStream("/config.yml");
            int r;
            while ((r = in.read()) > -1)
            {
                out.write(r);
            }
        }
        catch(IOException ex)
        {
            getLogger().log(Level.WARNING, "Could not create configuration file: ", ex);
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch(Exception ex) {}
            }
            
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch(Exception ex) {}
            }
        }
    }
}

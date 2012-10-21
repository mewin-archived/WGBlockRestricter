/*
 * Copyright (C) 2012 mewin <mewin001@hotmail.de>
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

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.mewin.WGCustomFlags.flags.CustomSetFlag;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.EnumFlag;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author mewin <mewin001@hotmail.de>
 */
public class WGBlockRestricterPlugin extends JavaPlugin {
    public static final EnumFlag BLOCK_TYPE_FLAG = new EnumFlag("block-type", BlockMaterial.class);
    public static final CustomSetFlag ALLOW_BLOCK_FLAG = new CustomSetFlag("allow-blocks", BLOCK_TYPE_FLAG);
    public static final CustomSetFlag DENY_BLOCK_FLAG = new CustomSetFlag("deny-blocks", BLOCK_TYPE_FLAG);
    
    private BlockPlaceListener listener;
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
        
        listener = new BlockPlaceListener(this, wgPlugin);
        
        getServer().getPluginManager().registerEvents(listener, this);
        
        custPlugin.addCustomFlag(ALLOW_BLOCK_FLAG);
        custPlugin.addCustomFlag(DENY_BLOCK_FLAG);
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
}

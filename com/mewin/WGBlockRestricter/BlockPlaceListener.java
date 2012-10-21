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

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author mewin <mewin001@hotmail.de>
 */
public class BlockPlaceListener implements Listener {
    private WGBlockRestricterPlugin plugin;
    private WorldGuardPlugin wgPlugin;
    
    public BlockPlaceListener(WGBlockRestricterPlugin plugin, WorldGuardPlugin wgPlugin) {
        this.plugin = plugin;
        this.wgPlugin = wgPlugin;
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!e.getPlayer().isOp()
                && !Utils.blockAllowedAtLocation(wgPlugin, e.getBlockPlaced().getType(), e.getBlockPlaced().getLocation())) {
            e.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to place this block here.");
            e.setCancelled(true);
        }
    }
}

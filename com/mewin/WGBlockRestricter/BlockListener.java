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
import org.bukkit.Material;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

/**
 *
 * @author mewin <mewin001@hotmail.de>
 */
public class BlockListener implements Listener {
    private WGBlockRestricterPlugin plugin;
    private WorldGuardPlugin wgPlugin;
    
    public BlockListener(WGBlockRestricterPlugin plugin, WorldGuardPlugin wgPlugin) {
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
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        if (!e.getPlayer().isOp()
                && !Utils.blockAllowedAtLocation(wgPlugin, e.getBlock().getType(), e.getBlock().getLocation())) {
            e.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to break this block here.");
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onHangingPlace(HangingPlaceEvent e)
    {
        Material mat = Material.PAINTING;
        if (e.getEntity() instanceof ItemFrame)
        {
            mat = Material.ITEM_FRAME;
        }
        if (!e.getPlayer().isOp()
                && !Utils.blockAllowedAtLocation(wgPlugin, mat, e.getBlock().getRelative(e.getBlockFace()).getLocation()))
        {
            e.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to place this block here.");
            e.setCancelled(true);
        }
    }
    
    public void onHangingBreakByEntity(HangingBreakByEntityEvent e)
    {
        if (e.getRemover() instanceof Player)
        {
            Player player = (Player) e.getRemover();
            Material mat = Material.PAINTING;
            if (e.getEntity() instanceof ItemFrame)
            {
                mat = Material.ITEM_FRAME;
            }
            if (!player.isOp()
                    && !Utils.blockAllowedAtLocation(wgPlugin, mat, e.getEntity().getLocation()))
            {
                player.sendMessage(ChatColor.RED + "You are not allowed to place this block here.");
                e.setCancelled(true);
            }
        }
    }
}

package com.mewin.WGBlockRestricter;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author mewin
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

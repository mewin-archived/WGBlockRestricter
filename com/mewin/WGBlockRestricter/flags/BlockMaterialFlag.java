/*
 * Copyright (C) 2013 mewin<mewin001@hotmail.de>
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

package com.mewin.WGBlockRestricter.flags;

import com.mewin.WGBlockRestricter.BlockMaterial;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.EnumFlag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

/**
 *
 * @author mewin<mewin001@hotmail.de>
 */
public class BlockMaterialFlag extends EnumFlag
{
    public BlockMaterialFlag(String name)
    {
        super(name, BlockMaterial.class);
    }

    @Override
    public Enum parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat
    {
        try
        {
            int i = Integer.valueOf(input);
            Material mat = Material.getMaterial(i);
            
            if (mat == null)
            {
                throw new InvalidFlagFormat(input + " is not a valid material id.");
            }
            else
            {
                ArrayList<BlockMaterial> materials = BlockMaterial.ByMaterial.byMaterial.get(mat);

                if (materials == null || materials.size() < 1)
                {
                    throw new InvalidFlagFormat(mat.name() + " is not a valid block material.");
                }
                else
                {
                    return materials.get(0);
                }
            }
        }
        catch(NumberFormatException ex)
        {
            return super.parseInput(plugin, sender, input);
        }
    }
}
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

import com.mewin.WGBlockRestricter.Utils;
import com.mewin.WGCustomFlags.flags.CustomFlag;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

/**
 *
 * @author mewin<mewin001@hotmail.de>
 */
public class BlockMaterialFlag extends CustomFlag<Material>
{
    public BlockMaterialFlag(String name)
    {
        super(name);
    }

    @Override
    public Material parseInput(WorldGuardPlugin wgp, CommandSender cs, String input) throws InvalidFlagFormat
    {
        if (Utils.aliases.containsKey(input.trim().toLowerCase().replace(" ", "_")))
        {
            return Utils.aliases.get(input);
        }
        
        Material mat;
        try
        {
            int i = Integer.valueOf(input);
            mat = Material.getMaterial(i);
            
            if (mat == null)
            {
                throw new InvalidFlagFormat(input + " is not a valid material id.");
            }
        }
        catch(NumberFormatException ex)
        {
            mat = Material.getMaterial(input);
            if (mat == null)
            {
                throw new InvalidFlagFormat(input + " is not a valid material name.");
            }
        }
        if (!mat.isBlock())
        {
            throw new InvalidFlagFormat (mat.name() + " is not a block.");
        }
        else
        {
            return mat;
        }
    }

    @Override
    public Material unmarshal(Object o)
    {
        return loadFromDb((String) o);
    }

    @Override
    public Object marshal(Material t)
    {
        return saveToDb(t);
    }

    @Override
    public Material loadFromDb(String str)
    {
        return Material.getMaterial(str);
    }

    @Override
    public String saveToDb(Material o)
    {
        return o.name();
    }
}
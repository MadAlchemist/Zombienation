package com.madalchemist.zombienation;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.Nonnull;

public class CreativeTab extends ItemGroup
{
    public CreativeTab(String name)
    {
        super(name);
    }

    @Override
    @Nonnull
    public ItemStack makeIcon()
    {
        return new ItemStack(Items.ZOMBIE_HEAD);
    }

    @Override
    public boolean hasSearchBar()
    {
        return true;
    }
}
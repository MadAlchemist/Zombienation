package com.madalchemist.zombienation.utils;

import com.madalchemist.zombienation.init.ItemsRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class AntizombineRecipe implements IBrewingRecipe
{

    @Override
    public boolean isInput(ItemStack input)
    {
        return input.getItem() == Items.POTION && PotionUtils.getPotion(input) == Potions.WEAKNESS;
    }

    @Override
    public boolean isIngredient(ItemStack ingridient)
    {
        return (ingridient.getItem()==Items.GOLDEN_APPLE);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient)
    {
        return this.isInput(input) && this.isIngredient(ingredient)
                ? new ItemStack(ItemsRegistry.ANTIZOMBINE.get())
                : ItemStack.EMPTY;
    }

}
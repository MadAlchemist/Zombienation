package com.madalchemist.zombienation.potions;

import com.madalchemist.zombienation.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.ItemTags;
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
                ? new ItemStack(ModItems.ANTIZOMBINE.get())
                : ItemStack.EMPTY;
    }

}

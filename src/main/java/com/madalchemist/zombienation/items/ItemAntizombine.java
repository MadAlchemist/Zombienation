package com.madalchemist.zombienation.items;

import com.madalchemist.zombienation.client.ModCreativeModeTab;
import com.madalchemist.zombienation.init.PotionsRegistry;
import com.madalchemist.zombienation.utils.ConfigurationHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemAntizombine extends Item {
    public ItemAntizombine() {
        super(new Item.Properties()
                .food(new FoodProperties.Builder()
                        .alwaysEat()
                        .build())
                .tab(ModCreativeModeTab.ZOMBIENATION_TAB));


    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if(!ConfigurationHandler.INFECTION.hardcoreInfection.get()) {
            entityLiving.removeEffectNoUpdate(PotionsRegistry.POTION_ZOMBIE_VIRUS);
        }
        stack.shrink(1);
        return stack;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_77661_1_) {
        return UseAnim.DRINK;
    }
}
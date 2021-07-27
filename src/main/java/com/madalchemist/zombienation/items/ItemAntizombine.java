package com.madalchemist.zombienation.items;

import com.madalchemist.zombienation.CreativeTab;
import com.madalchemist.zombienation.PotionsRegistry;
import com.madalchemist.zombienation.Zombienation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class ItemAntizombine extends Item {
    public ItemAntizombine() {
        super(new Properties()
                .food(new Food.Builder()
                        .alwaysEat()
                        .build())
                .tab(Zombienation.CREATIVE_TAB));


    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        entityLiving.removeEffectNoUpdate(PotionsRegistry.POTION_ZOMBIE_VIRUS);
        stack.shrink(1);
        return stack;
    }

    @Override
    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.DRINK;
    }
}

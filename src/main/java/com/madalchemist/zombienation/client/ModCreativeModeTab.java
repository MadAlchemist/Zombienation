package com.madalchemist.zombienation.client;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModCreativeModeTab {
    public static final CreativeModeTab ZOMBIENATION_TAB = new CreativeModeTab("zombienation") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.ZOMBIE_HEAD);
        }
    };
}

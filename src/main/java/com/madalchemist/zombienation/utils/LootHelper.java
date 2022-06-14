package com.madalchemist.zombienation.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class LootHelper {
    public static void dropLoot(String ID, double chance, LivingEntity entity) {
        if(Math.random() >= chance) {
            if (ConfigurationHandler.LOOT.itemExists(ID)) {
                String id_parts[] = ID.split(":");
                ItemStack loot = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(id_parts[0], id_parts[1])), 1);
                ItemEntity itementity = new ItemEntity(entity.level,
                        entity.getX(),
                        entity.getY(),
                        entity.getZ(),
                        loot);
                entity.level.addFreshEntity(itementity);
            }
        }
    }
}

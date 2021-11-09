package com.madalchemist.zombienation.zombies;

import com.madalchemist.zombienation.ConfigHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class LootHelper {
    public static void dropLoot(String ID, double chance, LivingEntity entity) {
        if(Math.random() >= chance) {
            if (ConfigHandler.LOOT.itemExists(ID)) {
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

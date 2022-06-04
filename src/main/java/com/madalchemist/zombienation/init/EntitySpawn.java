package com.madalchemist.zombienation.init;

import com.madalchemist.zombienation.Zombienation;
import com.madalchemist.zombienation.utils.ConfigurationHandler;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Zombienation.MODID)
public class EntitySpawn {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addSpawn(BiomeLoadingEvent event) {
        if (event.getName() != null) {
            Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
            if (biome != null) {
                ResourceKey<Biome> resourceKey = ResourceKey.create(ForgeRegistries.Keys.BIOMES, event.getName());
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.RANDOM_ZOMBIE.get(), ConfigurationHandler.SPAWN.spawnWeight.get(), ConfigurationHandler.SPAWN.groupMin.get(), ConfigurationHandler.SPAWN.groupMax.get()));
            }
        }
    }
}
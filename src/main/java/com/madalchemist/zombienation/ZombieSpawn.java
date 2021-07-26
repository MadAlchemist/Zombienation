package com.madalchemist.zombienation;

import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = "zombienation")
public class ZombieSpawn {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addSpawn(BiomeLoadingEvent event) {
        if (event.getName() != null) {
            Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
            if (biome != null) {
                RegistryKey<Biome> biomeKey = RegistryKey.create(ForgeRegistries.Keys.BIOMES, event.getName());
                List<BiomeDictionary.Type> includeList = Arrays.asList(BiomeDictionaryHelper.toBiomeTypeArray(ConfigHandler.SPAWN.include.get()));
                List<BiomeDictionary.Type> excludeList = Arrays.asList(BiomeDictionaryHelper.toBiomeTypeArray(ConfigHandler.SPAWN.exclude.get()));
                if (!includeList.isEmpty()) {
                    Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biomeKey);
                    if (biomeTypes.stream().noneMatch(excludeList::contains) && biomeTypes.stream().anyMatch(includeList::contains)) {
                        int spawnWeightNormal = ConfigHandler.SPAWN.spawnWeightNormal.get();
                        int minGroupNormal = ConfigHandler.SPAWN.minGroupNormal.get();
                        int maxGroupNormal = ConfigHandler.SPAWN.maxGroupNormal.get();
                        int spawnWeightTough = ConfigHandler.SPAWN.spawnWeightTough.get();
                        int minGroupTough = ConfigHandler.SPAWN.minGroupTough.get();
                        int maxGroupTough = ConfigHandler.SPAWN.maxGroupTough.get();
                        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(ZombiesRegistry.ZOMBIE_1.get(), spawnWeightNormal, minGroupNormal, maxGroupNormal));
                        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(ZombiesRegistry.ZOMBIE_2.get(), spawnWeightNormal, minGroupNormal, maxGroupNormal));
                        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(ZombiesRegistry.ZOMBIE_3.get(), spawnWeightTough, minGroupTough, maxGroupTough));
                        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(ZombiesRegistry.ZOMBIE_4.get(), spawnWeightTough, minGroupTough, maxGroupTough));
                        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(ZombiesRegistry.ZOMBIE_5.get(), spawnWeightNormal, minGroupNormal, maxGroupNormal));
                        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(ZombiesRegistry.ZOMBIE_6.get(), spawnWeightNormal, minGroupNormal, maxGroupNormal));
                        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(ZombiesRegistry.ZOMBIE_7.get(), spawnWeightNormal, minGroupNormal, maxGroupNormal));
                        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(ZombiesRegistry.ZOMBIE_8.get(), spawnWeightNormal, minGroupNormal, maxGroupNormal));
                    }
                } else {
                    throw new IllegalArgumentException("Do not leave the BiomeDictionary type inclusion list empty. If you wish to disable spawning of an entity, set the weight to 0 instead.");
                }
            }
        }
    }
}
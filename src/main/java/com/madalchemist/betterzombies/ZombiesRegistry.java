package com.madalchemist.betterzombies;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.madalchemist.betterzombies.zombies.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = "betterzombies", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ZombiesRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_DEFERRED = DeferredRegister.create(ForgeRegistries.ENTITIES, "betterzombies");
    private static List<EntityType> entities = Lists.newArrayList();
    private static final List<Item> SPAWN_EGGS = Lists.newArrayList();

    public static final RegistryObject<EntityType<Zombie1>> ZOMBIE_1 = createEntity("zombie_1", Zombie1::new, 0.8F, 1.95F, 0x006600, 0x222222);
    public static final RegistryObject<EntityType<Zombie2>> ZOMBIE_2 = createEntity("zombie_2", Zombie2::new, 0.8F, 1.95F, 0x008800, 0x332211);
    public static final RegistryObject<EntityType<Zombie3>> ZOMBIE_3 = createEntity("zombie_3", Zombie3::new, 0.8F, 1.95F, 0x00AA00, 0x442200);
    public static final RegistryObject<EntityType<Zombie4>> ZOMBIE_4 = createEntity("zombie_4", Zombie4::new, 0.8F, 1.95F, 0x22DD22, 0x445555);
    public static final RegistryObject<EntityType<Zombie5>> ZOMBIE_5 = createEntity("zombie_5", Zombie5::new, 0.8F, 1.95F, 0xffff00, 0x009900);
    public static final RegistryObject<EntityType<Zombie6>> ZOMBIE_6 = createEntity("zombie_6", Zombie6::new, 0.8F, 1.95F, 0xff8888, 0x009900);
    public static final RegistryObject<EntityType<Zombie7>> ZOMBIE_7 = createEntity("zombie_7", Zombie7::new, 0.8F, 1.95F, 0xff0000, 0x0000ff);
    public static final RegistryObject<EntityType<Zombie8>> ZOMBIE_8 = createEntity("zombie_8", Zombie8::new, 0.8F, 1.95F, 0xff0000, 0x00ffff);

    private static <T extends MonsterEntity> RegistryObject<EntityType<T>> createEntity(String name, EntityType.IFactory<T> factory, float width, float height, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation("betterzombies", name);
        EntityType<T> entity = EntityType.Builder.of(factory, EntityClassification.MONSTER).sized(width, height).setTrackingRange(64).setUpdateInterval(1).build(location.toString());
        entities.add(entity);
        Item spawnEgg = new SpawnEggItem(entity, eggPrimary, eggSecondary, (new Item.Properties()).tab(ItemGroup.TAB_MISC));
        spawnEgg.setRegistryName(new ResourceLocation("betterzombies", name + "_spawn_egg"));
        SPAWN_EGGS.add(spawnEgg);

        return ENTITY_DEFERRED.register(name, () -> entity);
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ZOMBIE_1.get(), Zombie1.createAttributes().build());
        event.put(ZOMBIE_2.get(), Zombie2.createAttributes().build());
        event.put(ZOMBIE_3.get(), Zombie3.createAttributes().build());
        event.put(ZOMBIE_4.get(), Zombie4.createAttributes().build());
        event.put(ZOMBIE_5.get(), Zombie5.createAttributes().build());
        event.put(ZOMBIE_6.get(), Zombie6.createAttributes().build());
        event.put(ZOMBIE_7.get(), Zombie7.createAttributes().build());
        event.put(ZOMBIE_8.get(), Zombie8.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerZombies(RegistryEvent.Register<EntityType<?>> event) {
        for (EntityType entity : entities) {
            Preconditions.checkNotNull(entity.getRegistryName(), "registryName");
            event.getRegistry().register(entity);
            EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombieEntity::checkAnyLightMonsterSpawnRules);
        }
    }

    @SubscribeEvent
    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
        for (Item spawnEgg : SPAWN_EGGS) {
            Preconditions.checkNotNull(spawnEgg.getRegistryName(), "registryName");
            event.getRegistry().register(spawnEgg);
        }
    }
}

package com.madalchemist.zombienation.init;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.madalchemist.zombienation.Zombienation;
import com.madalchemist.zombienation.client.ModCreativeModeTab;
import com.madalchemist.zombienation.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.Level;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Zombienation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_DEFERRED = DeferredRegister.create(ForgeRegistries.ENTITIES, Zombienation.MODID);
    public static final DeferredRegister<Item> EGG_DEFERRED = DeferredRegister.create(ForgeRegistries.ITEMS, Zombienation.MODID);

    /* Register entities here */

    public static final RegistryObject<EntityType<Zombie1>> ZOMBIE1 =createMonster("zombie1", () -> Zombie1::new, 0.6f, 1.95f, 0x229922, 0xaaaaaa);
    public static final RegistryObject<EntityType<Zombie2>> ZOMBIE2 =createMonster("zombie2", () -> Zombie2::new, 0.6f, 1.95f, 0x229922, 0x226611);
    public static final RegistryObject<EntityType<Zombie3>> ZOMBIE3 =createMonster("zombie3", () -> Zombie3::new, 0.6f, 1.95f, 0x229922, 0x334455);
    public static final RegistryObject<EntityType<Zombie4>> ZOMBIE4 =createMonster("zombie4", () -> Zombie4::new, 0.6f, 1.95f, 0x229922, 0x785209);
    public static final RegistryObject<EntityType<Zombie5>> ZOMBIE5 =createMonster("zombie5", () -> Zombie5::new, 0.6f, 1.95f, 0x229922, 0xbaba40);
    public static final RegistryObject<EntityType<Zombie6>> ZOMBIE6 =createMonster("zombie6", () -> Zombie6::new, 0.6f, 1.95f, 0x229922, 0xdead00);
    public static final RegistryObject<EntityType<Zombie7>> ZOMBIE7 =createMonster("zombie7", () -> Zombie7::new, 0.6f, 1.95f, 0x229922, 0x000000);
    public static final RegistryObject<EntityType<Zombie8>> ZOMBIE8 =createMonster("zombie8", () -> Zombie8::new, 0.6f, 1.95f, 0x229922, 0x452684);
    public static final RegistryObject<EntityType<Zombie9>> ZOMBIE9 =createMonster("zombie9", () -> Zombie9::new, 0.6f, 1.95f, 0x229922, 0x657564);
    public static final RegistryObject<EntityType<RandomZombie>> RANDOM_ZOMBIE =createMonster("random_zombie", () -> RandomZombie::new, 0.6f, 1.95f, 0xffffff, 0x000000);
    public static final RegistryObject<EntityType<Chesthead>> CHESTHEAD =createMonster("chesthead", () -> Chesthead::new, 0.6f, 1.95f, 0xfa5401, 0x450823);
    public static final RegistryObject<EntityType<ZombieBear>> ZOMBIE_BEAR =createMonster("zombie_bear", () -> ZombieBear::new, 1.4f, 1.4f, 0x888888, 0x008800);
    public static final RegistryObject<EntityType<BrownBear>> BROWN_BEAR =createAnimal("brown_bear", () -> BrownBear::new, 1.4f, 1.4f, 0x4a2206, 0x999999);
    public static final RegistryObject<EntityType<Zolphin>> ZOLPHIN =createWaterMob("zombie_dolphin", () -> Zolphin::new,  0.9f, 0.6f, 0x99bb99, 0x99ff99);
    /* End register entites */

    private static <T extends Monster> RegistryObject<EntityType<T>> createMonster(String name, Supplier<EntityType.EntityFactory<T>> factory, float width, float height, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(Zombienation.MODID, name);
        RegistryObject<EntityType<T>> entityType = ENTITY_DEFERRED.register(name, () -> EntityType.Builder.of(factory.get(), MobCategory.MONSTER).sized(width, height).setTrackingRange(64).setUpdateInterval(1).build(location.toString()));
        EGG_DEFERRED.register(name + "_spawn_egg", () -> new ForgeSpawnEggItem(entityType, eggPrimary, eggSecondary, (new Item.Properties()).tab(ModCreativeModeTab.ZOMBIENATION_TAB)));
        return entityType;
    }

    private static <T extends Animal> RegistryObject<EntityType<T>> createAnimal(String name, Supplier<EntityType.EntityFactory<T>> factory, float width, float height, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(Zombienation.MODID, name);
        RegistryObject<EntityType<T>> entityType = ENTITY_DEFERRED.register(name, () -> EntityType.Builder.of(factory.get(), MobCategory.CREATURE).sized(width, height).setTrackingRange(64).setUpdateInterval(1).build(location.toString()));
        EGG_DEFERRED.register(name + "_spawn_egg", () -> new ForgeSpawnEggItem(entityType, eggPrimary, eggSecondary, (new Item.Properties()).tab(ModCreativeModeTab.ZOMBIENATION_TAB)));
        return entityType;
    }

    private static <T extends WaterAnimal> RegistryObject<EntityType<T>> createWaterMob(String name, Supplier<EntityType.EntityFactory<T>> factory, float width, float height, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(Zombienation.MODID, name);
        RegistryObject<EntityType<T>> entityType = ENTITY_DEFERRED.register(name, () -> EntityType.Builder.of(factory.get(), MobCategory.WATER_CREATURE).sized(width, height).setTrackingRange(64).setUpdateInterval(1).build(location.toString()));
        EGG_DEFERRED.register(name + "_spawn_egg", () -> new ForgeSpawnEggItem(entityType, eggPrimary, eggSecondary, (new Item.Properties()).tab(ModCreativeModeTab.ZOMBIENATION_TAB)));
        return entityType;
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        SpawnPlacements.register(ZOMBIE1.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zombie::checkMonsterSpawnRules);
        SpawnPlacements.register(ZOMBIE2.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zombie::checkMonsterSpawnRules);
        SpawnPlacements.register(ZOMBIE3.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zombie::checkMonsterSpawnRules);
        SpawnPlacements.register(ZOMBIE4.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zombie::checkMonsterSpawnRules);
        SpawnPlacements.register(ZOMBIE5.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zombie::checkMonsterSpawnRules);
        SpawnPlacements.register(ZOMBIE6.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zombie::checkMonsterSpawnRules);
        SpawnPlacements.register(ZOMBIE7.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zombie::checkMonsterSpawnRules);
        SpawnPlacements.register(ZOMBIE8.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zombie::checkMonsterSpawnRules);
        SpawnPlacements.register(ZOMBIE9.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zombie::checkMonsterSpawnRules);
        SpawnPlacements.register(RANDOM_ZOMBIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zombie::checkMonsterSpawnRules);
        SpawnPlacements.register(CHESTHEAD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zombie::checkMonsterSpawnRules);
        SpawnPlacements.register(ZOMBIE_BEAR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombieBear::checkMonsterSpawnRules);
        SpawnPlacements.register(BROWN_BEAR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BrownBear::checkAnimalSpawnRules);
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ZOMBIE1.get(), Zombie1.createAttributes().build());
        event.put(ZOMBIE2.get(), Zombie2.createAttributes().build());
        event.put(ZOMBIE3.get(), Zombie3.createAttributes().build());
        event.put(ZOMBIE4.get(), Zombie4.createAttributes().build());
        event.put(ZOMBIE5.get(), Zombie5.createAttributes().build());
        event.put(ZOMBIE6.get(), Zombie6.createAttributes().build());
        event.put(ZOMBIE7.get(), Zombie7.createAttributes().build());
        event.put(ZOMBIE8.get(), Zombie8.createAttributes().build());
        event.put(ZOMBIE9.get(), Zombie9.createAttributes().build());
        event.put(RANDOM_ZOMBIE.get(), RandomZombie.createAttributes().build());
        event.put(CHESTHEAD.get(), Chesthead.createAttributes().build());
        event.put(ZOMBIE_BEAR.get(), ZombieBear.createAttributes().build());
        event.put(BROWN_BEAR.get(), BrownBear.createAttributes().build());
        event.put(ZOLPHIN.get(), Zolphin.createAttributes().build());
    }

}


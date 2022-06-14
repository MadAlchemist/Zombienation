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
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Zombienation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_DEFERRED = DeferredRegister.create(ForgeRegistries.ENTITIES, Zombienation.MODID);
    private static final List<Item> SPAWN_EGGS = Lists.newArrayList();

    /* Register entities here */

    public static final RegistryObject<EntityType<Zombie1>> ZOMBIE1 =createEntity("zombie1", Zombie1::new, 0.6f, 1.95f, 0x229922, 0xaaaaaa);
    public static final RegistryObject<EntityType<Zombie2>> ZOMBIE2 =createEntity("zombie2", Zombie2::new, 0.6f, 1.95f, 0x229922, 0x226611);
    public static final RegistryObject<EntityType<Zombie3>> ZOMBIE3 =createEntity("zombie3", Zombie3::new, 0.6f, 1.95f, 0x229922, 0x334455);
    public static final RegistryObject<EntityType<Zombie4>> ZOMBIE4 =createEntity("zombie4", Zombie4::new, 0.6f, 1.95f, 0x229922, 0x785209);
    public static final RegistryObject<EntityType<Zombie5>> ZOMBIE5 =createEntity("zombie5", Zombie5::new, 0.6f, 1.95f, 0x229922, 0xbaba40);
    public static final RegistryObject<EntityType<Zombie6>> ZOMBIE6 =createEntity("zombie6", Zombie6::new, 0.6f, 1.95f, 0x229922, 0xdead00);
    public static final RegistryObject<EntityType<Zombie7>> ZOMBIE7 =createEntity("zombie7", Zombie7::new, 0.6f, 1.95f, 0x229922, 0x000000);
    public static final RegistryObject<EntityType<Zombie8>> ZOMBIE8 =createEntity("zombie8", Zombie8::new, 0.6f, 1.95f, 0x229922, 0x452684);
    public static final RegistryObject<EntityType<Zombie9>> ZOMBIE9 =createEntity("zombie9", Zombie9::new, 0.6f, 1.95f, 0x229922, 0x657564);
    public static final RegistryObject<EntityType<RandomZombie>> RANDOM_ZOMBIE =createEntity("random_zombie", RandomZombie::new, 0.6f, 1.95f, 0xffffff, 0x000000);
    public static final RegistryObject<EntityType<Chesthead>> CHESTHEAD =createEntity("chesthead", Chesthead::new, 0.6f, 1.95f, 0xfa5401, 0x450823);

    /* End register entites */

    private static <T extends Mob> RegistryObject<EntityType<T>> createEntity(String name, EntityType.EntityFactory<T> factory, float width, float height, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(Zombienation.MODID, name);
        EntityType<T> entity = EntityType.Builder.of(factory, MobCategory.MONSTER).sized(width, height).setTrackingRange(64).setUpdateInterval(1).build(location.toString());
        Item spawnEgg = new SpawnEggItem(entity, eggPrimary, eggSecondary, (new Item.Properties()).tab(ModCreativeModeTab.ZOMBIENATION_TAB));
        spawnEgg.setRegistryName(new ResourceLocation(Zombienation.MODID, name + "_spawn_egg"));
        SPAWN_EGGS.add(spawnEgg);
        return ENTITY_DEFERRED.register(name, () -> entity);
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
        event.put(CHESTHEAD.get(), RandomZombie.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
        for (Item spawnEgg : SPAWN_EGGS) {
            Preconditions.checkNotNull(spawnEgg.getRegistryName(), "registryName");
            event.getRegistry().register(spawnEgg);
        }
    }
}


package com.madalchemist.zombienation;

import com.madalchemist.zombienation.animals.BrownBearRenderer;
import com.madalchemist.zombienation.potions.AntizombineRecipe;
import com.madalchemist.zombienation.potions.PotionZombieVirus;
import com.madalchemist.zombienation.structures.ConfiguredStructures;
import com.madalchemist.zombienation.structures.Structures;
import com.madalchemist.zombienation.zombies.render.*;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("zombienation")
public class Zombienation
{
    public static final String MODID = "zombienation";
    public static final ItemGroup CREATIVE_TAB = new CreativeTab(MODID);

    public static <T extends IForgeRegistryEntry<T>> Consumer<RegistryEvent.Register<T>> getRegistrator(Consumer<Registrator<T>> consumer)
    {
        return event -> consumer.accept(new Registrator<>(event.getRegistry()));
    }

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public Zombienation() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);
        eventBus.addGenericListener(Effect.class, getRegistrator(this::onRegisterEffects));
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.spec);
        registerDeferredRegistries(eventBus);
        // For events that happen after initialization. This is probably going to be use a lot.
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);

        // The comments for BiomeLoadingEvent and StructureSpawnListGatherEvent says to do HIGH for additions.
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
    }

    public void setupCommon(final FMLCommonSetupEvent event) {
        BrewingRecipeRegistry.addRecipe(new AntizombineRecipe());

        /* Structure-related code */
        event.enqueueWork(() -> {
            Structures.setupStructures();
            ConfiguredStructures.registerConfiguredStructures();
        });
    }

    public void setupClient(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.ZOMBIE_1.get(), Zombie_1_Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.ZOMBIE_2.get(), Zombie_2_Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.ZOMBIE_3.get(), Zombie_3_Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.ZOMBIE_4.get(), Zombie_4_Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.ZOMBIE_5.get(), Zombie_5_Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.ZOMBIE_6.get(), Zombie_6_Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.ZOMBIE_7.get(), Zombie_7_Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.ZOMBIE_8.get(), Zombie_8_Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.ZOMBIE_9.get(), Zombie_9_Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.ZOMBIE_RANDOM.get(), ZombieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.CHESTHEAD.get(), ChestHeadRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.BROWN_BEAR.get(), BrownBearRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.ZOMBIE_BEAR.get(), ZombieBearRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.ZOLPHIN.get(), ZolphinRenderer::new);
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        ZombiesRegistry.ENTITY_DEFERRED.register(modBus);
        Structures.DEFERRED_REGISTRY_STRUCTURE.register(modBus);
        BlocksRegistry.init();
        ModItems.register(modBus);
    }

    public void onRegisterEffects(Registrator<Effect> reg)
    {
        reg.register(new ResourceLocation("zombienation", "zombie_virus"), new PotionZombieVirus(EffectType.HARMFUL, 0));
    }

    /* Mostly copied from tutorial */
    /**
     * This is the event you will use to add anything to any biome.
     * This includes spawns, changing the biome's looks, messing with its surfacebuilders,
     * adding carvers, spawning new features... etc
     *
     * Here, we will use this to add our structure to all biomes.
     */
    public void biomeModification(final BiomeLoadingEvent event) {
        /*
         * Add our structure to all biomes including other modded biomes.
         * You can skip or add only to certain biomes based on stuff like biome category,
         * temperature, scale, precipitation, mod id, etc. All kinds of options!
         *
         * You can even use the BiomeDictionary as well! To use BiomeDictionary, do
         * RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName()) to get the biome's
         * registrykey. Then that can be fed into the dictionary to get the biome's types.
         */
        if(event.getName().toString().equals("minecraft:desert") ||
           event.getName().toString().equals("minecraft:savanna")) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_CACTUS_FARM);
        }

        if(event.getName().toString().equals("minecraft:plains") ||
                event.getName().toString().equals("minecraft:sunflower_plains") ||
                event.getName().toString().equals("minecraft:meadow")
        ) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_CURSED_WELL);
        }
    }


    /**
     * Will go into the world's chunkgenerator and manually add our structure spacing.
     * If the spacing is not added, the structure doesn't spawn.
     *
     * Use this for dimension blacklists for your structure.
     * (Don't forget to attempt to remove your structure too from the map if you are blacklisting that dimension!)
     * (It might have your structure in it already.)
     *
     * Basically use this to make absolutely sure the chunkgenerator can or cannot spawn your structure.
     */
    private static Method GETCODEC_METHOD;
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            /*
             * Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
             * They will handle your structure spacing for your if you add to WorldGenRegistries.NOISE_GENERATOR_SETTINGS in your structure's registration.
             * This here is done with reflection as this tutorial is not about setting up and using Mixins.
             * If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.
             */
            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                Zombienation.LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            /*
             * Prevent spawning our structure in Vanilla's superflat world as
             * people seem to want their superflat worlds free of modded structures.
             * Also that vanilla superflat is really tricky and buggy to work with in my experience.
             */
            if(serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.dimension().equals(World.OVERWORLD)){
                return;
            }

            /*
             * putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
             * Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
             *
             * NOTE: if you add per-dimension spacing configs, you can't use putIfAbsent as WorldGenRegistries.NOISE_GENERATOR_SETTINGS in FMLCommonSetupEvent
             * already added your default structure spacing to some dimensions. You would need to override the spacing with .put(...)
             * And if you want to do dimension blacklisting, you need to remove the spacing entry entirely from the map below to prevent generation safely.
             */

            /* Cactus farms */
            Map<Structure<?>, StructureSeparationSettings> tempMapCactusFarm = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMapCactusFarm.putIfAbsent(Structures.CACTUS_FARM.get(), DimensionStructuresSettings.DEFAULTS.get(Structures.CACTUS_FARM.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMapCactusFarm;

            /* Cursed wells */
            Map<Structure<?>, StructureSeparationSettings> tempMapCursedWell = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMapCursedWell.putIfAbsent(Structures.CURSED_WELL.get(), DimensionStructuresSettings.DEFAULTS.get(Structures.CURSED_WELL.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMapCursedWell;
        }
    }
}

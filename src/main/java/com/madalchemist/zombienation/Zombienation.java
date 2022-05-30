package com.madalchemist.zombienation;

import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("zombienation")
public class Zombienation
{
    public static final String MODID = "zombienation";
    private static final Logger LOGGER = LogManager.getLogger();
    public static final CreativeModeTab TAB_ZOMBIENATION = new CreativeModeTab(10, "zombienation") {
        public ItemStack makeIcon() {
            return new ItemStack(Items.ZOMBIE_HEAD);
        }
    };
    public Zombienation() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
         // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM PREINIT");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call

    private void clientSetup(final FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public void onRenderRegistry(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntitiesRegistry.ZOMBIE_1.get(), ZombieRenderer::new);
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        EntitiesRegistry.ENTITY_DEFERRED.register(modBus);
        //Structures.DEFERRED_REGISTRY_STRUCTURE.register(modBus);
        //BlocksRegistry.init();
        //ModItems.register(modBus);
    }
                                 @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
         LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}

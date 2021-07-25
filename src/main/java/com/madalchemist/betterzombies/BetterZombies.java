package com.madalchemist.betterzombies;

import com.madalchemist.betterzombies.zombies.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("betterzombies")
public class BetterZombies
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public BetterZombies() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.spec);
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);
        registerDeferredRegistries(eventBus);
    }
    public void setupCommon(final FMLCommonSetupEvent event) {

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
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        ZombiesRegistry.ENTITY_DEFERRED.register(modBus);
    }
}

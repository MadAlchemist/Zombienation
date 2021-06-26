package com.madalchemist.betterzombies;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.madalchemist.betterzombies.zombies.BasicZombieRenderer;

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

        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);
        registerDeferredRegistries(eventBus);
    }
    public void setupCommon(final FMLCommonSetupEvent event) {

    }

    public void setupClient(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ZombiesRegistry.BASIC_ZOMBIE.get(), BasicZombieRenderer::new);
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        ZombiesRegistry.ENTITY_DEFERRED.register(modBus);
    }
}

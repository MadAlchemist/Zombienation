package com.madalchemist.zombienation;

import com.madalchemist.zombienation.client.ClientHandler;
import com.madalchemist.zombienation.init.EntityRegistry;
import com.madalchemist.zombienation.utils.ConfigurationHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value=Zombienation.MODID)
public class Zombienation {
    public static final String MODID = "zombienation";

    public Zombienation() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);
        registerDeferredRegistries(eventBus);
    }

    public void setupCommon(final FMLCommonSetupEvent event) {

    }

    public void setupClient(final FMLClientSetupEvent event) {
        ClientHandler.init();
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        EntityRegistry.ENTITY_DEFERRED.register(modBus);
        //SoundRegistry.SOUND_EVENT_DEFERRED.register(modBus);
    }
}
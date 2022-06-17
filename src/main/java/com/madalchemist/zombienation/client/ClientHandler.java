package com.madalchemist.zombienation.client;

import com.madalchemist.zombienation.Zombienation;
import com.madalchemist.zombienation.client.renderer.*;
import com.madalchemist.zombienation.init.EntityRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Zombienation.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientHandler {

    public static void init() {

    }

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.ZOMBIE1.get(), ZombieRenderer1::new);
        event.registerEntityRenderer(EntityRegistry.ZOMBIE2.get(), ZombieRenderer2::new);
        event.registerEntityRenderer(EntityRegistry.ZOMBIE3.get(), ZombieRenderer3::new);
        event.registerEntityRenderer(EntityRegistry.ZOMBIE4.get(), ZombieRenderer4::new);
        event.registerEntityRenderer(EntityRegistry.ZOMBIE5.get(), ZombieRenderer5::new);
        event.registerEntityRenderer(EntityRegistry.ZOMBIE6.get(), ZombieRenderer6::new);
        event.registerEntityRenderer(EntityRegistry.ZOMBIE7.get(), ZombieRenderer7::new);
        event.registerEntityRenderer(EntityRegistry.ZOMBIE8.get(), ZombieRenderer8::new);
        event.registerEntityRenderer(EntityRegistry.ZOMBIE9.get(), ZombieRenderer9::new);
        event.registerEntityRenderer(EntityRegistry.RANDOM_ZOMBIE.get(), ZombieRenderer::new);
        event.registerEntityRenderer(EntityRegistry.CHESTHEAD.get(), ChestheadRenderer::new);
        event.registerEntityRenderer(EntityRegistry.ZOMBIE_BEAR.get(), ZombieBearRenderer::new);
        event.registerEntityRenderer(EntityRegistry.BROWN_BEAR.get(), BrownBearRenderer::new);
        event.registerEntityRenderer(EntityRegistry.ZOLPHIN.get(), ZolphinRenderer::new);
    }
}
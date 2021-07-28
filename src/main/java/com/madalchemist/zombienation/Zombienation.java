package com.madalchemist.zombienation;

import com.madalchemist.zombienation.potions.AntizombineRecipe;
import com.madalchemist.zombienation.potions.PotionZombieVirus;
import com.madalchemist.zombienation.zombies.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("zombienation")
public class Zombienation
{
    public static final ItemGroup CREATIVE_TAB = new CreativeTab("zombienation");

    public static <T extends IForgeRegistryEntry<T>> Consumer<RegistryEvent.Register<T>> getRegistrator(Consumer<Registrator<T>> consumer)
    {
        return event -> consumer.accept(new Registrator<>(event.getRegistry()));
    }

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public Zombienation() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);
        eventBus.addGenericListener(Effect.class, getRegistrator(this::onRegisterEffects));
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.spec);
        registerDeferredRegistries(eventBus);
    }


    public void setupCommon(final FMLCommonSetupEvent event) {
        BrewingRecipeRegistry.addRecipe(new AntizombineRecipe());
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
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        ZombiesRegistry.ENTITY_DEFERRED.register(modBus);
        ModItems.register(modBus);
    }

    public void onRegisterEffects(Registrator<Effect> reg)
    {
        reg.register(new ResourceLocation("zombienation", "zombie_virus"), new PotionZombieVirus(EffectType.HARMFUL, 0));
    }
}

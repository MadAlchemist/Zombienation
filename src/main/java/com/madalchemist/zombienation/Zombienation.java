package com.madalchemist.zombienation;

import com.madalchemist.zombienation.client.ClientHandler;
import com.madalchemist.zombienation.init.EntityRegistry;
import com.madalchemist.zombienation.init.ItemsRegistry;
import com.madalchemist.zombienation.init.Registrator;
import com.madalchemist.zombienation.utils.AntizombineRecipe;
import com.madalchemist.zombienation.utils.ConfigurationHandler;
import com.madalchemist.zombienation.utils.PotionZombieVirus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

@Mod(value=Zombienation.MODID)
public class Zombienation {
    public static final String MODID = "zombienation";
    public static final Logger LOGGER = LogManager.getLogger();

    public static <T extends IForgeRegistryEntry<T>> Consumer<RegistryEvent.Register<T>> getRegistrator(Consumer<Registrator<T>> consumer)
    {
        return event -> consumer.accept(new Registrator<>(event.getRegistry()));
    }

    public Zombienation() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);
        eventBus.addGenericListener(MobEffect.class, getRegistrator(this::onRegisterEffects));
        registerDeferredRegistries(eventBus);
    }

    public void setupCommon(final FMLCommonSetupEvent event) {
        BrewingRecipeRegistry.addRecipe(new AntizombineRecipe());
    }

    public void setupClient(final FMLClientSetupEvent event) {
        ClientHandler.init();
    }

    public static void registerDeferredRegistries(IEventBus modBus) {

        EntityRegistry.ENTITY_DEFERRED.register(modBus);
        ItemsRegistry.ITEMS.register(modBus);
    }

    public void onRegisterEffects(Registrator<MobEffect> reg)
    {
        reg.register(new ResourceLocation("zombienation", "zombie_virus"), new PotionZombieVirus(MobEffectCategory.HARMFUL, 0));
    }

}
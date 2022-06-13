package com.madalchemist.zombienation.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = "zombienation")
public class SoundsRegistry {
    static ArrayList<SoundEvent> allItems = new ArrayList<SoundEvent>();

    public static SoundEvent ZOMBIE1_AMBIENT = register("zombie1_ambient");
    public static SoundEvent ZOMBIE2_AMBIENT = register("zombie2_ambient");
    public static SoundEvent ZOMBIE3_AMBIENT = register("zombie3_ambient");
    public static SoundEvent ZOMBIE4_AMBIENT = register("zombie4_ambient");
    public static SoundEvent ZOMBIE5_AMBIENT = register("zombie5_ambient");
    public static SoundEvent ZOMBIE6_AMBIENT = register("zombie6_ambient");
    public static SoundEvent ZOMBIE7_AMBIENT = register("zombie7_ambient");
    public static SoundEvent ZOMBIE8_AMBIENT = register("zombie8_ambient");


    public static SoundEvent[] getAllSounds()
    {
        return allItems.toArray(new SoundEvent[allItems.size()]);
    }

    private static ResourceLocation location(String name)
    {
        return new ResourceLocation("zombienation", name);
    }

    private static SoundEvent register(String name)
    {
        ResourceLocation soundLocation = location(name);
        SoundEvent ret = new SoundEvent(soundLocation).setRegistryName(name);
        allItems.add(ret);
        return ret;
    }

    @SubscribeEvent
    public static void onSoundsRegistry(final RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll
                (
                        getAllSounds()
                );
        //LOGGER.info("Sounds Registered");
    }
}
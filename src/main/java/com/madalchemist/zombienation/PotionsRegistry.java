package com.madalchemist.zombienation;

import com.madalchemist.zombienation.potions.PotionZombieVirus;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

public class PotionsRegistry
{
    @ObjectHolder("zombienation:zombie_virus")
    public static final PotionZombieVirus POTION_ZOMBIE_VIRUS = null;

    public static <T extends IForgeRegistryEntry<T>, U extends T> RegistryObject<U> makeRegistryObject(final ResourceLocation location, IForgeRegistry<T> registry)
    {
        return RegistryObject.of(location, registry);
    }

}
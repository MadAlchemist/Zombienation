package com.madalchemist.zombienation.utils;

import com.madalchemist.zombienation.Zombienation;
import com.madalchemist.zombienation.init.EntityRegistry;
import com.madalchemist.zombienation.init.PotionsRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Zombienation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InfectionHandler {
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        if(event.getSource().getEntity() instanceof Zombie) {
            if(! (event.getEntityLiving() instanceof Zombie)) {
                if(!(event.getEntityLiving().hasEffect(PotionsRegistry.POTION_ZOMBIE_VIRUS))) {
                    Random random = new Random();
                    if (random.nextDouble() < ConfigurationHandler.INFECTION.infectionChance.get()) {
                        event.getEntityLiving().addEffect(new MobEffectInstance(PotionsRegistry.POTION_ZOMBIE_VIRUS, ConfigurationHandler.INFECTION.infectionDuration.get(), 0, (false), (false)));
                    }
                }
            }
        }
        if(event.getEntityLiving().getType().equals(EntityRegistry.ZOMBIE5.get())) {
            if(ConfigurationHandler.GENERAL.hazmatZombiesImmuneToPotions.get()) {
                event.setCanceled(true);
            }
        }
    }

}

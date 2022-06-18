package com.madalchemist.zombienation.utils;

import com.madalchemist.zombienation.Zombienation;
import com.madalchemist.zombienation.entity.BrownBear;
import com.madalchemist.zombienation.entity.Zolphin;
import com.madalchemist.zombienation.entity.Zombie5;
import com.madalchemist.zombienation.entity.ZombieBear;
import com.madalchemist.zombienation.init.EntityRegistry;
import com.madalchemist.zombienation.init.PotionsRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Zombienation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InfectionHandler {

    public static final DamageSource ZOMBIFICATION = new DamageSource("zombification");
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Zombie || event.getSource().getEntity() instanceof Zolphin || event.getSource().getEntity() instanceof ZombieBear) {
            if (!(event.getEntityLiving() instanceof Zombie)) {
                if (!(event.getEntityLiving().hasEffect(PotionsRegistry.POTION_ZOMBIE_VIRUS))) {
                    Random random = new Random();
                    if (random.nextDouble() < ConfigurationHandler.INFECTION.infectionChance.get()) {
                        event.getEntityLiving().addEffect(new MobEffectInstance(PotionsRegistry.POTION_ZOMBIE_VIRUS, ConfigurationHandler.INFECTION.infectionDuration.get(), 0, (false), (false)));
                    }
                }
            }
        }
        if (event.getEntityLiving() instanceof Zombie5) {
            if (ConfigurationHandler.GENERAL.hazmatZombiesImmuneToPotions.get() && event.getSource().isMagic()) {
                event.setAmount(0.0f);
                event.setCanceled(true);
            }
        }

        if (event.getSource().getEntity() instanceof EnderMan) {
            //Then deal extra damage to undead
            if (event.getEntityLiving().getMobType() == MobType.UNDEAD) {
                event.getEntity().hurt(DamageSource.OUT_OF_WORLD, 10f);
            }
        }
    }

    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {
        if(event.getEntity() instanceof Creeper &&
                (event.getSource().getEntity() instanceof Zombie ||
                 event.getSource().getEntity() instanceof ZombieBear ||
                 event.getSource().getEntity() instanceof Zolphin)) {
                    Creeper creeper = (Creeper) event.getEntity();
                    creeper.addEffect(new MobEffectInstance(PotionsRegistry.POTION_ZOMBIE_VIRUS, ConfigurationHandler.INFECTION.infectionDuration.get(), (int) 0, true, (false)));
                    creeper.hurt(ZOMBIFICATION, 1.0f);
                    event.setCanceled(true);
                }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (!event.getSource().equals(ZOMBIFICATION)) {
            if (event.getEntityLiving().hasEffect(PotionsRegistry.POTION_ZOMBIE_VIRUS)) {
                if (event.getEntityLiving() instanceof Player) {
                    Zombie zombie = new Zombie(EntityType.ZOMBIE, event.getEntityLiving().level);
                    zombie.setCustomName(event.getEntityLiving().getCustomName());
                    zombie.setPos(event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ());
                    event.getEntityLiving().level.addFreshEntity(zombie);
                    zombie.removeAllEffects();
                }
                if (event.getEntityLiving() instanceof Villager) {
                    VillagerData data = ((Villager) (event.getEntityLiving())).getVillagerData();
                    ZombieVillager zombie = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, event.getEntityLiving().level);
                    zombie.setCustomName(event.getEntityLiving().getCustomName());
                    zombie.setVillagerData(data);
                    zombie.setPos(event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ());
                    event.getEntityLiving().level.addFreshEntity(zombie);
                    zombie.removeAllEffects();
                }
                if (event.getEntityLiving() instanceof BrownBear || event.getEntityLiving() instanceof PolarBear) {
                    ZombieBear zombie = new ZombieBear(EntityRegistry.ZOMBIE_BEAR.get(), event.getEntityLiving().level);
                    zombie.setCustomName(event.getEntityLiving().getCustomName());
                    zombie.setPos(event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ());
                    event.getEntityLiving().level.addFreshEntity(zombie);
                    zombie.removeAllEffects();
                }
                if (event.getEntityLiving() instanceof Dolphin) {
                    Zolphin zombie = new Zolphin(EntityRegistry.ZOLPHIN.get(), event.getEntityLiving().level);
                    zombie.setCustomName(event.getEntityLiving().getCustomName());
                    zombie.setPos(event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ());
                    event.getEntityLiving().level.addFreshEntity(zombie);
                    zombie.removeAllEffects();
                }

            }
        }
    }

    @SubscribeEvent
    public static void onExpire(PotionEvent.PotionExpiryEvent event) {
        if(event.getEntityLiving() instanceof Player && event.getPotionEffect().getEffect().getClass() == PotionZombieVirus.class) {
            Zombie zombie = new Zombie(EntityType.ZOMBIE, event.getEntityLiving().level);
            zombie.setCustomName(event.getEntityLiving().getCustomName());
            zombie.setPos(event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ());
            event.getEntityLiving().hurt(ZOMBIFICATION, Float.MAX_VALUE);
            event.getEntityLiving().level.addFreshEntity(zombie);
            zombie.removeAllEffects();
        }
        if(event.getEntityLiving() instanceof Villager && event.getPotionEffect().getEffect().getClass() == PotionZombieVirus.class) {
            VillagerData data = ((Villager)(event.getEntityLiving())).getVillagerData();
            ZombieVillager zombie = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, event.getEntityLiving().level);
            zombie.setCustomName(event.getEntityLiving().getCustomName());
            zombie.setVillagerData(data);
            zombie.setPos(event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ());
            event.getEntityLiving().hurt(ZOMBIFICATION, Float.MAX_VALUE);
            event.getEntityLiving().level.addFreshEntity(zombie);
            zombie.removeAllEffects();
        }

        if(event.getEntityLiving() instanceof Dolphin && event.getPotionEffect().getEffect().getClass() == PotionZombieVirus.class) {
            Zolphin zombie = new Zolphin(EntityRegistry.ZOLPHIN.get(), event.getEntityLiving().level);
            zombie.setCustomName(event.getEntityLiving().getCustomName());
            zombie.setPos(event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ());
            event.getEntityLiving().hurt(ZOMBIFICATION, Float.MAX_VALUE);
            event.getEntityLiving().level.addFreshEntity(zombie);
            zombie.removeAllEffects();
        }
    }
}

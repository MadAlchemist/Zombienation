package com.madalchemist.zombienation;

import com.madalchemist.zombienation.animals.BrownBearEntity;
import com.madalchemist.zombienation.potions.PotionZombieVirus;
import com.madalchemist.zombienation.zombies.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "zombienation")
public class EventsHandler {
    @SubscribeEvent
    public static void onJoin(EntityJoinWorldEvent event) {
        boolean isWeaponLootable = false;
        if(event.getEntity().getClass().getSuperclass() == ZombieEntity.class) {
            /* All modded zombies are adults */
            ((ZombieEntity)event.getEntity()).setBaby(false);
        }

        /* Is it a Zombie Miner? */
        if (event.getEntity().getClass() == Zombie3.class) {
            Zombie3 zombie = (Zombie3) event.getEntity();
            /* Zombie Miners are "tough" zombies, so add damage boost and health boost */
            zombie.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, (int) Integer.MAX_VALUE, (int) 3, (false), (false)));
            zombie.addEffect(new EffectInstance(Effects.HEALTH_BOOST, (int) Integer.MAX_VALUE, (int) 3, (false), (false)));
            /* Create helmet and pickaxe */
            ItemStack helmet = new ItemStack(Items.IRON_HELMET);
            //helmet.enchant(Enchantments.VANISHING_CURSE, 1);
            ItemStack pickaxe = new ItemStack(Items.IRON_PICKAXE);
            //pickaxe.enchant(Enchantments.VANISHING_CURSE, 5);

            /* Try to equip helmet */
            zombie.equipItemIfPossible(helmet);

            /* Try to equip pickaxe, if enabled by config */
            if(ConfigHandler.GENERAL.minersHavePickaxes.get()) {
                zombie.equipItemIfPossible(pickaxe);
            }
        }

        /* Is this a Zombie Warrior? */
        if (event.getEntity().getClass() == Zombie4.class) {
            Zombie4 zombie = (Zombie4) event.getEntity();
            /* Zombie Warriors are "tough" zombies, so apply damage boost and health boost */
            zombie.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, (int) Integer.MAX_VALUE, (int) 3, (false), (false)));
            zombie.addEffect(new EffectInstance(Effects.HEALTH_BOOST, (int) Integer.MAX_VALUE, (int) 3, (false), (false)));
            /* Try to equip sword, if enabled */
            if(ConfigHandler.GENERAL.warriorsHaveSwords.get()) {
                ItemStack sword = new ItemStack(Items.IRON_SWORD);
                sword.enchant(Enchantments.SHARPNESS, 5);
                sword.enchant(Enchantments.VANISHING_CURSE, 5);
                zombie.equipItemIfPossible(sword);
            }
        }

        /* Is this a Frozen Lumberjack? */
        if (event.getEntity() instanceof Zombie9) {
            Zombie9 zombie = (Zombie9) event.getEntity();
            /* Apply buffs and debuffs specific to Frozen Lumberjacks */
            zombie.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, (int) Integer.MAX_VALUE, (int) 3, (false), (false)));
            zombie.addEffect(new EffectInstance(Effects.HEALTH_BOOST, (int) Integer.MAX_VALUE, (int) 5, (false), (false)));
            zombie.addEffect(new EffectInstance(Effects.ABSORPTION, (int) Integer.MAX_VALUE, (int) 5, (false), (false)));
            zombie.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) Integer.MAX_VALUE, (int) 2, (false), (false)));
        }
    }

    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {
        /* Is this Hazmat Zombie? */
        if (event.getEntity() instanceof Zombie5) {
            /* Is damage source a potion? */
            if (event.getSource().isMagic()) {
                /* Does hazmat suit protect from potions? */
                if (ConfigHandler.GENERAL.hazmatSuitProtectsFromPotions.get()) {
                    event.setCanceled(true);
                }
            }
        }
        /* Is damage source a zombie ? */
        if (event.getSource().getEntity() instanceof ZombieEntity) {
            /* Is target infectable? */
            if (isInfectable(event.getEntity())) {
                double d = Math.random();
                if (d <= ConfigHandler.INFECTION.infectionChance.get()) {
                    /* Is entity already infected? */
                    if(!event.getEntityLiving().hasEffect(PotionsRegistry.POTION_ZOMBIE_VIRUS.getEffect())) {
                        event.getEntityLiving().addEffect(new EffectInstance(PotionsRegistry.POTION_ZOMBIE_VIRUS, ConfigHandler.INFECTION.infectionDuration.get() * 20, (int) 0, true, (false)));
                    }
                }
            }
            /* If target is bear or horse, zombies deal very little damage */
            if(event.getEntity() instanceof PolarBearEntity ||
               event.getEntity() instanceof BrownBearEntity ||
               event.getEntity() instanceof HorseEntity) {
                   LivingEntity entity = event.getEntityLiving();
                   ZombieEntity zombie = (ZombieEntity)event.getSource().getEntity();
                   event.setCanceled(true);
                   entity.hurt(DamageSource.GENERIC, 0.1f);
            }
        }

        /* Is damage source a zombie bear ? */
        if (event.getSource().getEntity() instanceof ZombieBear) {
            /* Is target infectable? */
            if (isInfectable(event.getEntity())) {
                double d = Math.random();
                if (d <= ConfigHandler.INFECTION.infectionChance.get()) {
                    /* Is entity already infected? */
                    if(!event.getEntityLiving().hasEffect(PotionsRegistry.POTION_ZOMBIE_VIRUS.getEffect())) {
                        event.getEntityLiving().addEffect(new EffectInstance(PotionsRegistry.POTION_ZOMBIE_VIRUS, ConfigHandler.INFECTION.infectionDuration.get() * 20, (int) 0, true, (false)));
                    }
                }
            }
        }
    }

    public static boolean isInfectable(Entity entity) {
        if(entity instanceof PlayerEntity ||
           entity instanceof VillagerEntity ||
           entity instanceof PolarBearEntity ||
           entity instanceof BrownBearEntity ||
           entity instanceof WolfEntity ||
           entity instanceof HorseEntity) {
            return true;
        } else {
            return false;
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        //If dies infected polar or brown bear, spawn zombie bear.
        if (event.getEntity() instanceof PolarBearEntity || event.getEntity() instanceof BrownBearEntity) {
            if(((PolarBearEntity) event.getEntity()).hasEffect(PotionsRegistry.POTION_ZOMBIE_VIRUS)) {
                ZombieBear zombear = new ZombieBear(ZombiesRegistry.ZOMBIE_BEAR.get(), event.getEntity().level);
                zombear.setPos(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                event.getEntity().level.addFreshEntity(zombear);
            }
        }

        //If dies zombie, zombie bear, or zombie horse, and it is killed by brown bear,
        //apply regeneration to that bear for 10 seconds
        if(event.getEntity() instanceof ZombieEntity || event.getEntity() instanceof ZombieBear || event.getEntity() instanceof ZombieHorseEntity) {
            if(event.getSource().getEntity() instanceof BrownBearEntity) {
                ((LivingEntity)event.getSource().getEntity()).addEffect(new EffectInstance(Effects.REGENERATION, 200, (int) 0, true, false));
            }
        }

        //If dies horse, spawn zombie horse
        if (event.getEntity() instanceof HorseEntity) {
            if(((HorseEntity) event.getEntity()).hasEffect(PotionsRegistry.POTION_ZOMBIE_VIRUS)) {
                ZombieHorseEntity zombie = new ZombieHorseEntity(EntityType.ZOMBIE_HORSE, event.getEntity().level);
                zombie.setPos(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                if (event.getEntity().hasCustomName()) {
                    zombie.setCustomName(event.getEntity().getCustomName());
                }
                event.getEntity().level.addFreshEntity(zombie);
            }
        }
    }

    @SubscribeEvent
    public static void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if(event.getEntityLiving() instanceof ZombieEntity) {
            if(event.getEntityLiving().isOnFire() && !event.getEntityLiving().isInLava()) {
                if(!ConfigHandler.GENERAL.burnAtDay.get()) {
                    event.getEntityLiving().clearFire();
                }
            }
        }
        if(event.getEntityLiving() instanceof ZombieVillagerEntity) {
            if(event.getEntityLiving().isOnFire() && !event.getEntityLiving().isInLava()) {
                if(!ConfigHandler.GENERAL.burnAtDay.get()) {
                    event.getEntityLiving().clearFire();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onZombieVirusExpired(PotionEvent.PotionExpiryEvent event) {
        if(event.getPotionEffect().getEffect() == PotionsRegistry.POTION_ZOMBIE_VIRUS) {
            event.getEntity().hurt(DamageSource.WITHER, Float.MAX_VALUE);
            if(ConfigHandler.INFECTION.infectionDeathZombification.get()){
                if(event.getEntity() instanceof PlayerEntity) {
                    if (!event.getEntity().level.isClientSide()) {
                        ZombieEntity zombie = new ZombieEntity(event.getEntity().level);
                        zombie.setPos(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                        zombie.setCustomName(((PlayerEntity)event.getEntity()).getName());
                        zombie.setCustomNameVisible(true);
                        zombie.setPersistenceRequired();
                        event.getEntity().level.addFreshEntity(zombie);
                    }
                }

                if(event.getEntity() instanceof VillagerEntity) {
                    ZombieVillagerEntity zombie = new ZombieVillagerEntity(EntityType.ZOMBIE_VILLAGER, event.getEntity().level);
                    if(event.getEntity().hasCustomName()) {
                        zombie.setCustomName(event.getEntity().getCustomName());
                    }
                    zombie.setBaby(((VillagerEntity)event.getEntity()).isBaby());
                    zombie.setVillagerData(((VillagerEntity)event.getEntity()).getVillagerData());
                    zombie.setPos(event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ());
                    event.getEntity().level.addFreshEntity(zombie);
                    event.getEntity().remove();
                }
            }
        }
    }
}
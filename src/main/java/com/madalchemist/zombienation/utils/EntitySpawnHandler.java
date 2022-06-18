package com.madalchemist.zombienation.utils;

import com.madalchemist.zombienation.Zombienation;
import com.madalchemist.zombienation.entity.*;
import com.madalchemist.zombienation.entity.ai.FeralNearestAttackableTargetGoal;
import com.madalchemist.zombienation.init.EntityRegistry;
import net.minecraft.server.TickTask;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;


import java.util.Random;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = Zombienation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntitySpawnHandler {

    @SubscribeEvent
    public static void onSpawn(EntityJoinWorldEvent event) {
        if(event.getEntity().getType().equals(EntityType.SKELETON) || event.getEntity().getType().equals(EntityType.STRAY)) {
            if(ConfigurationHandler.GENERAL.replaceSkeletonsWithRandomZombies.get() && event.getEntity().level.dimension() == Level.OVERWORLD) {
                RandomZombie zombie = new RandomZombie(EntityRegistry.RANDOM_ZOMBIE.get(), event.getEntity().level);
                zombie.setPos(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                event.setCanceled(true);
                BlockableEventLoop<Runnable> executor = (BlockableEventLoop<Runnable>) LogicalSidedProvider.WORKQUEUE.get(event.getWorld().isClientSide ? LogicalSide.CLIENT : LogicalSide.SERVER);
                executor.tell(new TickTask(0, () -> event.getWorld().addFreshEntity(zombie)));
            }
        }

        if(event.getEntity() instanceof Wolf) {
            Wolf wolf = (Wolf) event.getEntity();
            if (!wolf.isTame()) {
                Random random = new Random();
                if(random.nextInt(20) > 18) {
                    BrownBear bear = new BrownBear(EntityRegistry.BROWN_BEAR.get(), wolf.level);
                    bear.setPos(wolf.getX(), wolf.getY(), wolf.getZ());
                    BlockableEventLoop<Runnable> executor = (BlockableEventLoop<Runnable>) LogicalSidedProvider.WORKQUEUE.get(event.getWorld().isClientSide ? LogicalSide.CLIENT : LogicalSide.SERVER);
                    executor.tell(new TickTask(0, () -> event.getWorld().addFreshEntity(bear)));
                    event.setCanceled(true);
                }
            }
        }

        if(event.getEntity() instanceof Creeper) {
            Creeper creeper = (Creeper) event.getEntity();
            creeper.goalSelector.addGoal(3, new AvoidEntityGoal<>(creeper, Zombie.class, 6.0F, 1.0D, 1.2D));
        }

        if(event.getEntity() instanceof Zombie) {
            ((Zombie)event.getEntity()).targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(((Zombie)event.getEntity()), BrownBear.class, true));
            ((Zombie)event.getEntity()).targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(((Zombie)event.getEntity()), PolarBear.class, true));
            ((Zombie)event.getEntity()).targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(((Zombie)event.getEntity()), Horse.class, true));
            ((Zombie)event.getEntity()).targetSelector.addGoal(2, new FeralNearestAttackableTargetGoal<>(((Zombie)event.getEntity()), Mob.class, 0, false, false, FeralNearestAttackableTargetGoal.LIVING_ENTITY_SELECTOR));


            if(((Zombie) event.getEntity()).getActiveEffects().isEmpty()) {
                Random random = new Random();
                double tough = random.nextDouble();
                double brutal = random.nextDouble();
                double infernal = random.nextDouble();

                if (infernal < ConfigurationHandler.SPAWN.infernalChance.get()) {
                    ((Mob) event.getEntity()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 1, (false), (false)));
                }
                if (infernal < ConfigurationHandler.SPAWN.infernalChance.get() || brutal < ConfigurationHandler.SPAWN.brutalChance.get()) {
                    ((Mob) event.getEntity()).addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 1, (false), (false)));
                }
                if (infernal < ConfigurationHandler.SPAWN.infernalChance.get() || brutal < ConfigurationHandler.SPAWN.brutalChance.get() || tough < ConfigurationHandler.SPAWN.brutalChance.get()) {
                    ((Mob) event.getEntity()).addEffect(new MobEffectInstance(MobEffects.REGENERATION, Integer.MAX_VALUE, 1, (false), (false)));
                }
            }
        }

        if(event.getEntity() instanceof Zombie3) {
            if(ConfigurationHandler.GENERAL.minersHavePickaxes.get()) {
                ItemStack pickaxe = new ItemStack(Items.IRON_PICKAXE, 1);
                ((Zombie3) event.getEntity()).setItemInHand(InteractionHand.MAIN_HAND, pickaxe);
            }
            if(ConfigurationHandler.GENERAL.minersHaveHelmets.get()) {
                ItemStack helmet = new ItemStack(Items.IRON_HELMET, 1);
                ((Zombie3) event.getEntity()).setItemSlot(EquipmentSlot.HEAD, helmet);
            }
        }

        if(event.getEntity() instanceof Zombie4) {
            if(ConfigurationHandler.GENERAL.warriorsHaveSwords.get()) {
                ItemStack sword = new ItemStack(Items.IRON_SWORD, 1);
                ((Zombie4) event.getEntity()).setItemInHand(InteractionHand.MAIN_HAND, sword);
            }
            if(ConfigurationHandler.GENERAL.warriorsHaveHelmets.get()) {
                ItemStack helmet = new ItemStack(Items.IRON_HELMET, 1);
                ((Zombie4) event.getEntity()).setItemSlot(EquipmentSlot.HEAD, helmet);
            }
        }

        if(event.getEntity() instanceof Chesthead) {
                ItemStack chest = new ItemStack(Items.CHEST, 1);
                ((Chesthead) event.getEntity()).setItemSlot(EquipmentSlot.HEAD, chest);
                if(!((Chesthead) event.getEntity()).getActiveEffects().isEmpty()) {
                    ((Chesthead) event.getEntity()).removeAllEffects();
                }
                ((Chesthead)event.getEntity()).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, Integer.MAX_VALUE, 1, false, false));
                ((Chesthead)event.getEntity()).addEffect(new MobEffectInstance(MobEffects.ABSORPTION, Integer.MAX_VALUE, 2, false, false));
        }

        if(event.getEntity() instanceof Dolphin) {
            ((Dolphin) event.getEntity()).targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(((Dolphin) event.getEntity()), Drowned.class, 10, true, true, (Predicate<LivingEntity>) null));
        }

    }
}

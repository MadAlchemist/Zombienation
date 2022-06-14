package com.madalchemist.zombienation.utils;

import com.madalchemist.zombienation.Zombienation;
import com.madalchemist.zombienation.entity.Chesthead;
import com.madalchemist.zombienation.entity.RandomZombie;
import com.madalchemist.zombienation.entity.Zombie3;
import com.madalchemist.zombienation.entity.Zombie4;
import com.madalchemist.zombienation.init.EntityRegistry;
import net.minecraft.server.TickTask;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.LogicalSidedProvider;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Zombienation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntitySpawnHandler {

    @SubscribeEvent
    public static void onSpawn(EntityJoinWorldEvent event) {
        if(event.getEntity().getType().equals(EntityType.SKELETON) || event.getEntity().getType().equals(EntityType.STRAY)) {
            if(ConfigurationHandler.GENERAL.replaceSkeletonsWithRandomZombies.get() && event.getEntity().level.dimension() == Level.OVERWORLD) {
                RandomZombie zombie = new RandomZombie(EntityRegistry.RANDOM_ZOMBIE.get(), event.getEntity().level);
                zombie.setPos(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                event.setCanceled(true);
                BlockableEventLoop<Runnable> executor = LogicalSidedProvider.WORKQUEUE.get(event.getWorld().isClientSide ? LogicalSide.CLIENT : LogicalSide.SERVER);
                executor.tell(new TickTask(0, () -> event.getWorld().addFreshEntity(zombie)));
            }
        }

        if(event.getEntity() instanceof Zombie) {

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
    }
}

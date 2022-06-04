package com.madalchemist.zombienation.utils;

import com.madalchemist.zombienation.Zombienation;
import com.madalchemist.zombienation.entity.RandomZombie;
import com.madalchemist.zombienation.init.EntityRegistry;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.server.TickTask;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.LogicalSidedProvider;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Zombienation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntitySpawnHandler {
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
            Random random = new Random();
            double tough = random.nextDouble();
            double brutal = random.nextDouble();
            double infernal = random.nextDouble();

            if(infernal < ConfigurationHandler.SPAWN.infernalChance.get()) {
                ((Mob)event.getEntity()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, (int) Integer.MAX_VALUE, 1, (false), (false)));
            }
            if(infernal < ConfigurationHandler.SPAWN.infernalChance.get() || brutal < ConfigurationHandler.SPAWN.brutalChance.get()) {
                ((Mob)event.getEntity()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, (int) Integer.MAX_VALUE, 1, (false), (false)));
            }
        }
    }
}

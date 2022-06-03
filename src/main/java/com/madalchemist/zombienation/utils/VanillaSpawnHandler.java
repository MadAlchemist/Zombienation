package com.madalchemist.zombienation.utils;

import com.madalchemist.zombienation.Zombienation;
import com.madalchemist.zombienation.entity.RandomZombie;
import com.madalchemist.zombienation.init.EntityRegistry;
import net.minecraft.server.TickTask;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.LogicalSidedProvider;

@Mod.EventBusSubscriber(modid = Zombienation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VanillaSpawnHandler {
    public static void onSpawn(EntityJoinWorldEvent event) {
        if(event.getEntity().getType().equals(EntityType.SKELETON) || event.getEntity().getType().equals(EntityType.STRAY)) {
            if(ConfigurationHandler.GENERAL.replaceSkeletonsWithRandomZombies.get()) {
                RandomZombie zombie = new RandomZombie(EntityRegistry.RANDOM_ZOMBIE.get(), event.getEntity().level);
                zombie.setPos(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                event.setCanceled(true);
                BlockableEventLoop<Runnable> executor = LogicalSidedProvider.WORKQUEUE.get(event.getWorld().isClientSide ? LogicalSide.CLIENT : LogicalSide.SERVER);
                executor.tell(new TickTask(0, () -> event.getWorld().addFreshEntity(zombie)));
            }
        }

        if(event.getEntity().getType().equals(EntityType.ZOMBIE)) {
            if(ConfigurationHandler.GENERAL.replaceZombiesWithRandomZombies.get()) {
                RandomZombie zombie = new RandomZombie(EntityRegistry.RANDOM_ZOMBIE.get(), event.getEntity().level);
                zombie.setPos(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
                event.setCanceled(true);
                BlockableEventLoop<Runnable> executor = LogicalSidedProvider.WORKQUEUE.get(event.getWorld().isClientSide ? LogicalSide.CLIENT : LogicalSide.SERVER);
                executor.tell(new TickTask(0, () -> event.getWorld().addFreshEntity(zombie)));
            }
        }
    }
}

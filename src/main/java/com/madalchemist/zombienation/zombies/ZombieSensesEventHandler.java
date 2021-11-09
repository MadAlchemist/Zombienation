package com.madalchemist.zombienation.zombies;

/********************************************************************************************************/
/* This class contains event handlers for all events that may attract zombies. These events may include */
/* everything that changes light level or produces loud sounds - for example, placing or breaking       */
/* torches, blowing up TNT or creepers, lighting up fire, pouring or extinguishing lava, etc.           */
/********************************************************************************************************/

import com.madalchemist.zombienation.ConfigHandler;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;

import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = "zombienation")
public class ZombieSensesEventHandler {
    public static List<MonsterEntity> getMonsters(IWorld world, BlockPos pos, int radius) {
        BlockPos startBlock = new BlockPos(pos.getX()-radius,pos.getY()-32,pos.getZ()-radius);
        BlockPos endBlock = new BlockPos(pos.getX()+radius,pos.getY()+32,pos.getZ()+radius);
        List<MonsterEntity> zombies = world.getEntitiesOfClass(ZombieEntity.class, new AxisAlignedBB(startBlock, endBlock));
        List<MonsterEntity> bears = world.getEntitiesOfClass(ZombieBear.class, new AxisAlignedBB(startBlock, endBlock));
        return Stream.concat(zombies.stream(), bears.stream()).collect(Collectors.toList());
    }

    public static void attractZombies(IWorld world, BlockPos pos, int radius) {
        List<MonsterEntity> zombies = getMonsters(world,pos,radius);
        zombies.forEach(zombie -> {
            zombie.getNavigation().stop();
            zombie.getNavigation().moveTo(pos.getX(),pos.getY(),pos.getZ(), 1.0d);});
    }

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (ConfigHandler.SENSES.blockPlace.get().contains(event.getState().getBlock().getRegistryName().toString())) {
            attractZombies(event.getWorld(), event.getPos(), ConfigHandler.SENSES.blockPlaceNotifyRadius.get());
        }
    }

    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        if (ConfigHandler.SENSES.blockBreak.get().contains(event.getState().getBlock().getRegistryName().toString())) {
            attractZombies(event.getWorld(), event.getPos(), ConfigHandler.SENSES.blockBreakNotifyRadius.get());
        }
    }

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Detonate event) {
        attractZombies(event.getWorld(), new BlockPos(event.getExplosion().getPosition().x(),
                                                      event.getExplosion().getPosition().y(),
                                                      event.getExplosion().getPosition().z()),
                                                      ConfigHandler.SENSES.explosionNotifyRadius.get());
    }

    @SubscribeEvent
    public static void onAnvil(AnvilRepairEvent event) {
        attractZombies(event.getEntity().level,new BlockPos(event.getEntity().getX(),
                                                            event.getEntity().getY(),
                                                            event.getEntity().getZ()),
                                                            ConfigHandler.SENSES.anvilNotifyRadius.get());
    }

    @SubscribeEvent
    public static void onNoteblock(NoteBlockEvent.Play event) {
        attractZombies(event.getWorld(), event.getPos(), ConfigHandler.SENSES.noteblockNotifyRadius.get());
    }

}

package com.madalchemist.zombienation.entity.ai;

/********************************************************************************************************/
/* This class contains event handlers for all events that may attract zombies. These events may include */
/* everything that changes light level or produces loud sounds - for example, placing or breaking       */
/* torches, blowing up TNT or creepers, lighting up fire, pouring or extinguishing lava, etc.           */
/********************************************************************************************************/


import com.madalchemist.zombienation.entity.ZombieBear;
import com.madalchemist.zombienation.utils.ConfigurationHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.extensions.IForgeLevel;
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
    public static List<Monster> getMonsters(LevelAccessor world, BlockPos pos, int radius) {
        BlockPos startBlock = new BlockPos(pos.getX()-radius,pos.getY()-32,pos.getZ()-radius);
        BlockPos endBlock = new BlockPos(pos.getX()+radius,pos.getY()+32,pos.getZ()+radius);
        List<Zombie> zombies = world.getEntitiesOfClass(Zombie.class, new AABB(startBlock, endBlock));
        List<ZombieBear> bears = world.getEntitiesOfClass(ZombieBear.class, new AABB(startBlock, endBlock));
        return Stream.concat(zombies.stream(), bears.stream()).collect(Collectors.toList());
    }

    public static void attractZombies(LevelAccessor world, BlockPos pos, int radius) {
        List<Monster> zombies = getMonsters(world,pos,radius);
        zombies.forEach(zombie -> {
            zombie.getNavigation().stop();
            zombie.getNavigation().moveTo(pos.getX(),pos.getY(),pos.getZ(), 1.0d);});
    }

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (ConfigurationHandler.SENSES.blockPlace.get().contains(event.getState().getBlock().getRegistryName().toString())) {
            attractZombies(event.getWorld(), event.getPos(), ConfigurationHandler.SENSES.blockPlaceNotifyRadius.get());
        }
    }

    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        if (ConfigurationHandler.SENSES.blockBreak.get().contains(event.getState().getBlock().getRegistryName().toString())) {
            attractZombies(event.getWorld(), event.getPos(), ConfigurationHandler.SENSES.blockBreakNotifyRadius.get());
        }
    }

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Detonate event) {
        attractZombies(event.getWorld(), new BlockPos(event.getExplosion().getPosition().x(),
                        event.getExplosion().getPosition().y(),
                        event.getExplosion().getPosition().z()),
                ConfigurationHandler.SENSES.explosionNotifyRadius.get());
    }

    @SubscribeEvent
    public static void onAnvil(AnvilRepairEvent event) {
        attractZombies(event.getEntity().level,new BlockPos(event.getEntity().getX(),
                        event.getEntity().getY(),
                        event.getEntity().getZ()),
                ConfigurationHandler.SENSES.anvilNotifyRadius.get());
    }

    @SubscribeEvent
    public static void onNoteblock(NoteBlockEvent.Play event) {
        attractZombies(event.getWorld(), event.getPos(), ConfigurationHandler.SENSES.noteblockNotifyRadius.get());
    }

}
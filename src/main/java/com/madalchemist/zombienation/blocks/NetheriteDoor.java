package com.madalchemist.zombienation.blocks;

import com.madalchemist.zombienation.BlocksRegistry;
import com.madalchemist.zombienation.Zombienation;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.Level;

public class NetheriteDoor extends DoorBlock {
    public NetheriteDoor(Properties p) {
        super(p);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.IGNORE;
    }

    @Override
    public void neighborChanged(BlockState blockstate, World world, BlockPos blockpos1, Block block, BlockPos blockpos2, boolean unknown) {
        Zombienation.LOGGER.printf(Level.INFO, "Blockstate = %s\n", blockstate.getBlock().getRegistryName().toString());
        Zombienation.LOGGER.printf(Level.INFO, "Blockstate at blockpos1 - (0,1,0) = %s\n", world.getBlockState(blockpos1.below()).getBlock().getRegistryName().toString());
        Zombienation.LOGGER.printf(Level.INFO, "Blockstate at blockpos2 = %s\n", world.getBlockState(blockpos2).getBlock().getRegistryName().toString());
        if(world.getBlockState(blockpos1.below()).getBlock().is(Blocks.SLIME_BLOCK) || world.getBlockState(blockpos1.below(2)).getBlock().is(Blocks.SLIME_BLOCK) || world.getBlockState(blockpos1.below(3)).getBlock().is(Blocks.SLIME_BLOCK)) {

        } else { super.neighborChanged(blockstate,world,blockpos1,block,blockpos2,unknown); }
    }
}
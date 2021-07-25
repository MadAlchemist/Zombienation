package com.madalchemist.betterzombies.zombies;

import com.madalchemist.betterzombies.ConfigHandler;
import com.madalchemist.betterzombies.SoundsRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class Zombie8 extends ZombieEntity {

    public Zombie8(EntityType<? extends Zombie8> zombie, World world) {
        super(zombie, world);
    }

    @Override
    protected boolean isSunSensitive() {
        return ConfigHandler.GENERAL.burnAtDay.get();
    }

    protected SoundEvent getAmbientSound() {
        return SoundsRegistry.ZOMBIE8_AMBIENT;
    }
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.ZOMBIE_HURT;
    }
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }
    protected SoundEvent getStepSound() {
        return SoundEvents.ZOMBIE_STEP;
    }
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }
}
package com.madalchemist.zombienation.zombies;

import com.madalchemist.zombienation.ConfigHandler;
import com.madalchemist.zombienation.SoundsRegistry;
import com.madalchemist.zombienation.animals.BrownBearEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class Zombie2 extends ZombieEntity {

    public Zombie2(EntityType<? extends Zombie2> zombie, World world) {
        super(zombie, world);
    }

    @Override
    protected boolean isSunSensitive() {
        return ConfigHandler.GENERAL.burnAtDay.get();
    }

    protected SoundEvent getAmbientSound() {
        return SoundsRegistry.ZOMBIE2_AMBIENT;
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

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, BrownBearEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PolarBearEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, HorseEntity.class, true));
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent death) {
        if(death.getEntityLiving() instanceof Zombie1) {
            LootHelper.dropLoot(ConfigHandler.LOOT.zombie1_loot.get(),
                    ConfigHandler.LOOT.zombie1_drop_chance.get(),
                    death.getEntityLiving());
        }
    }
}
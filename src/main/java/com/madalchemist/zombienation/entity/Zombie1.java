package com.madalchemist.zombienation.entity;

import com.madalchemist.zombienation.init.SoundsRegistry;
import com.madalchemist.zombienation.utils.ConfigurationHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;

public class Zombie1 extends Zombie {
   public Zombie1(EntityType<? extends Zombie> zombie, Level level) {
      super(zombie, level);
   }

   @Override
   protected boolean isSunSensitive() {
      return ConfigurationHandler.GENERAL.burnAtDay.get();
   }

   protected SoundEvent getAmbientSound() {
      return SoundsRegistry.ZOMBIE1_AMBIENT;
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
      //this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, BrownBearEntity.class, true));
      //this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PolarBearEntity.class, true));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Horse.class, true));
      //this.targetSelector.addGoal(2, new FeralNearestAttackableTargetGoal<>(this, MobEntity.class, 0, false, false, FeralNearestAttackableTargetGoal.LIVING_ENTITY_SELECTOR));
   }

   public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
      return super.checkSpawnRules(world, reason);
   }

   /*
   @SubscribeEvent
   public static void onDeath(LivingDeathEvent death) {
      if(death.getEntityLiving() instanceof Zombie1) {
         LootHelper.dropLoot(ConfigHandler.LOOT.zombie1_loot.get(),
                 ConfigHandler.LOOT.zombie1_drop_chance.get(),
                 death.getEntityLiving());
      }
   }
   */
}

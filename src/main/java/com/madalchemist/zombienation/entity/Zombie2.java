package com.madalchemist.zombienation.entity;

import com.madalchemist.zombienation.entity.ai.FeralNearestAttackableTargetGoal;
import com.madalchemist.zombienation.init.SoundsRegistry;
import com.madalchemist.zombienation.utils.ConfigurationHandler;
import com.madalchemist.zombienation.utils.LootHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "zombienation")
public class Zombie2 extends Zombie {
   public Zombie2(EntityType<? extends Zombie> zombie, Level level) {
      super(zombie, level);
   }

   @Override
   protected boolean isSunSensitive() {
      return ConfigurationHandler.GENERAL.burnAtDay.get();
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
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, BrownBear.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PolarBear.class, true));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Horse.class, true));
      this.targetSelector.addGoal(2, new FeralNearestAttackableTargetGoal<>(this, Mob.class, 0, false, false, FeralNearestAttackableTargetGoal.LIVING_ENTITY_SELECTOR));
   }

   public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
      return super.checkSpawnRules(world, reason);
   }

   @SubscribeEvent
   public static void onDeath(LivingDeathEvent death) {
      if(death.getEntityLiving() instanceof Zombie2) {
         LootHelper.dropLoot(ConfigurationHandler.LOOT.zombie2_loot.get(),
                 ConfigurationHandler.LOOT.zombie2_drop_chance.get(),
                 death.getEntityLiving());
      }
   }
}

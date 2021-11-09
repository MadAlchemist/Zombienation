package com.madalchemist.zombienation.zombies;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ZolphinEntity extends DolphinEntity {
   public ZolphinEntity(EntityType<? extends ZolphinEntity> e, World w) {
      super(e, w);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(0, new BreatheAirGoal(this));
      this.goalSelector.addGoal(0, new FindWaterGoal(this));
      this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
      this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
      this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0F));
      this.goalSelector.addGoal(5, new DolphinJumpGoal(this, 10));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, (double)1.2F, true));
      this.goalSelector.addGoal(8, new FollowBoatGoal(this));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
      this.targetSelector.addGoal(2, new ModdedNearestAttackableTargetGoal<>(this, DolphinEntity.class, true));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, true));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SquidEntity.class, true));
      this.targetSelector.addGoal(2, new ModdedNearestAttackableTargetGoal<>(this, AbstractFishEntity.class, true));
   }
}
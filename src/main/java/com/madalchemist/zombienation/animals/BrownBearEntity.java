package com.madalchemist.zombienation.animals;

import com.madalchemist.zombienation.ConfigHandler;
import com.madalchemist.zombienation.ZombiesRegistry;
import com.madalchemist.zombienation.zombies.LootHelper;
import com.madalchemist.zombienation.zombies.Zombie1;
import com.madalchemist.zombienation.zombies.ZombieBear;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = "zombienation")
public class BrownBearEntity extends PolarBearEntity {
   private static final DataParameter<Boolean> DATA_STANDING_ID = EntityDataManager.defineId(BrownBearEntity.class, DataSerializers.BOOLEAN);
   private float clientSideStandAnimationO;
   private float clientSideStandAnimation;
   private int warningSoundTicks;
   private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
   private int remainingPersistentAngerTime;
   private UUID persistentAngerTarget;

   public BrownBearEntity(EntityType<? extends BrownBearEntity> p_i50249_1_, World p_i50249_2_) {
      super(p_i50249_1_, p_i50249_2_);
   }

   public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
      return ZombiesRegistry.BROWN_BEAR.get().create(p_241840_1_);
   }

   public boolean isFood(ItemStack p_70877_1_) {
      return false;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new SwimGoal(this));
      this.goalSelector.addGoal(1, new BrownBearEntity.MeleeAttackGoal());
      this.goalSelector.addGoal(1, new BrownBearEntity.PanicGoal());
      this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
      this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
      this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
      this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
      this.targetSelector.addGoal(1, new BrownBearEntity.HurtByTargetGoal());
      this.targetSelector.addGoal(2, new BrownBearEntity.AttackPlayerGoal());
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, FoxEntity.class, 10, true, true, (Predicate<LivingEntity>)null));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, 10, true, true, (Predicate<LivingEntity>)null));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ZombieBear.class, 10, true, true, (Predicate<LivingEntity>)null));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ZombieHorseEntity.class, 10, true, true, (Predicate<LivingEntity>)null));
      this.targetSelector.addGoal(5, new ResetAngerGoal<>(this, false));
   }

   public static AttributeModifierMap.MutableAttribute createAttributes() {
      return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_DAMAGE, 15.0D);
   }

   public static boolean checkBrownBearSpawnRules(EntityType<BrownBearEntity> p_223320_0_, IWorld p_223320_1_, SpawnReason p_223320_2_, BlockPos p_223320_3_, Random p_223320_4_) {
      return checkAnimalSpawnRules(p_223320_0_, p_223320_1_, p_223320_2_, p_223320_3_, p_223320_4_);
   }

   public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
      super.readAdditionalSaveData(p_70037_1_);
      if(!level.isClientSide) //FORGE: allow this entity to be read from nbt on client. (Fixes MC-189565)
      this.readPersistentAngerSaveData((ServerWorld)this.level, p_70037_1_);
   }

   public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
      super.addAdditionalSaveData(p_213281_1_);
      this.addPersistentAngerSaveData(p_213281_1_);
   }

   public void startPersistentAngerTimer() {
      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
   }

   public void setRemainingPersistentAngerTime(int p_230260_1_) {
      this.remainingPersistentAngerTime = p_230260_1_;
   }

   public int getRemainingPersistentAngerTime() {
      return this.remainingPersistentAngerTime;
   }

   public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {
      this.persistentAngerTarget = p_230259_1_;
   }

   public UUID getPersistentAngerTarget() {
      return this.persistentAngerTarget;
   }

   protected SoundEvent getAmbientSound() {
      return this.isBaby() ? SoundEvents.POLAR_BEAR_AMBIENT_BABY : SoundEvents.POLAR_BEAR_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
      return SoundEvents.POLAR_BEAR_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.POLAR_BEAR_DEATH;
   }

   protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
      this.playSound(SoundEvents.POLAR_BEAR_STEP, 0.15F, 1.0F);
   }

   protected void playWarningSound() {
      if (this.warningSoundTicks <= 0) {
         this.playSound(SoundEvents.POLAR_BEAR_WARNING, 1.0F, this.getVoicePitch());
         this.warningSoundTicks = 40;
      }

   }

   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_STANDING_ID, false);
   }

   public void tick() {
      super.tick();
      if (this.level.isClientSide) {
         if (this.clientSideStandAnimation != this.clientSideStandAnimationO) {
            this.refreshDimensions();
         }

         this.clientSideStandAnimationO = this.clientSideStandAnimation;
         if (this.isStanding()) {
            this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation + 1.0F, 0.0F, 6.0F);
         } else {
            this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation - 1.0F, 0.0F, 6.0F);
         }
      }

      if (this.warningSoundTicks > 0) {
         --this.warningSoundTicks;
      }

      if (!this.level.isClientSide) {
         this.updatePersistentAnger((ServerWorld)this.level, true);
      }

   }

   public EntitySize getDimensions(Pose p_213305_1_) {
      if (this.clientSideStandAnimation > 0.0F) {
         float f = this.clientSideStandAnimation / 6.0F;
         float f1 = 1.0F + f;
         return super.getDimensions(p_213305_1_).scale(1.0F, f1);
      } else {
         return super.getDimensions(p_213305_1_);
      }
   }

   public boolean doHurtTarget(Entity p_70652_1_) {
      boolean flag = p_70652_1_.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
      if (flag) {
         this.doEnchantDamageEffects(this, p_70652_1_);
      }

      return flag;
   }

   public boolean isStanding() {
      return this.entityData.get(DATA_STANDING_ID);
   }

   public void setStanding(boolean p_189794_1_) {
      this.entityData.set(DATA_STANDING_ID, p_189794_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public float getStandingAnimationScale(float p_189795_1_) {
      return MathHelper.lerp(p_189795_1_, this.clientSideStandAnimationO, this.clientSideStandAnimation) / 6.0F;
   }

   protected float getWaterSlowDown() {
      return 0.98F;
   }

   public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
      if (p_213386_4_ == null) {
         p_213386_4_ = new AgeableData(1.0F);
      }

      return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
   }

   class AttackPlayerGoal extends NearestAttackableTargetGoal<PlayerEntity> {
      public AttackPlayerGoal() {
         super(BrownBearEntity.this, PlayerEntity.class, 20, true, true, (Predicate<LivingEntity>)null);
      }

      public boolean canUse() {
         if (BrownBearEntity.this.isBaby()) {
            return false;
         } else {
            if (super.canUse()) {
               for(BrownBearEntity bear : BrownBearEntity.this.level.getEntitiesOfClass(BrownBearEntity.class, BrownBearEntity.this.getBoundingBox().inflate(8.0D, 4.0D, 8.0D))) {
                  if (bear.isBaby()) {
                     return true;
                  }
               }
            }

            return false;
         }
      }

      protected double getFollowDistance() {
         return super.getFollowDistance() * 0.5D;
      }
   }

   class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
      public HurtByTargetGoal() {
         super(BrownBearEntity.this);
      }

      public void start() {
         super.start();
         if (BrownBearEntity.this.isBaby()) {
            this.alertOthers();
            this.stop();
         }

      }

      protected void alertOther(MobEntity p_220793_1_, LivingEntity p_220793_2_) {
         if (p_220793_1_ instanceof BrownBearEntity && !p_220793_1_.isBaby()) {
            super.alertOther(p_220793_1_, p_220793_2_);
         }

      }
   }

   class MeleeAttackGoal extends net.minecraft.entity.ai.goal.MeleeAttackGoal {
      public MeleeAttackGoal() {
         super(BrownBearEntity.this, 1.25D, true);
      }

      protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
         double d0 = this.getAttackReachSqr(p_190102_1_);
         if (p_190102_2_ <= d0 && this.isTimeToAttack()) {
            this.resetAttackCooldown();
            this.mob.doHurtTarget(p_190102_1_);
            BrownBearEntity.this.setStanding(false);
         } else if (p_190102_2_ <= d0 * 2.0D) {
            if (this.isTimeToAttack()) {
               BrownBearEntity.this.setStanding(false);
               this.resetAttackCooldown();
            }

            if (this.getTicksUntilNextAttack() <= 10) {
               BrownBearEntity.this.setStanding(true);
               BrownBearEntity.this.playWarningSound();
            }
         } else {
            this.resetAttackCooldown();
            BrownBearEntity.this.setStanding(false);
         }

      }

      public void stop() {
         BrownBearEntity.this.setStanding(false);
         super.stop();
      }

      protected double getAttackReachSqr(LivingEntity p_179512_1_) {
         return (double)(4.0F + p_179512_1_.getBbWidth());
      }
   }

   class PanicGoal extends net.minecraft.entity.ai.goal.PanicGoal {
      public PanicGoal() {
         super(BrownBearEntity.this, 2.0D);
      }

      public boolean canUse() {
         return !BrownBearEntity.this.isBaby() && !BrownBearEntity.this.isOnFire() ? false : super.canUse();
      }
   }

   @SubscribeEvent
   public static void onDeath(LivingDeathEvent death) {
      if(death.getEntityLiving() instanceof Zombie1) {
         LootHelper.dropLoot(ConfigHandler.LOOT.brown_bear_loot.get(),
                 ConfigHandler.LOOT.brown_bear_drop_chance.get(),
                 death.getEntityLiving());
      }
   }
}

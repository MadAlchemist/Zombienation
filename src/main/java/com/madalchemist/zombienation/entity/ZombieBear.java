package com.madalchemist.zombienation.entity;

import com.madalchemist.zombienation.entity.ai.FeralNearestAttackableTargetGoal;
import com.madalchemist.zombienation.entity.ai.ModdedNearestAttackableTargetGoal;
import com.madalchemist.zombienation.utils.ConfigurationHandler;
import com.madalchemist.zombienation.utils.LootHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = "zombienation")
public class ZombieBear extends Monster {
    private static final EntityDataAccessor<Boolean> DATA_STANDING_ID = SynchedEntityData.defineId(ZombieBear.class, EntityDataSerializers.BOOLEAN);
    private float clientSideStandAnimationO;
    private float clientSideStandAnimation;
    private int warningSoundTicks;

    public ZombieBear(EntityType<? extends ZombieBear> p_i50249_1_, Level p_i50249_2_) {
        super(p_i50249_1_, p_i50249_2_);
    }

    protected void registerGoals() {
        super.registerGoals();
        //this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(1, new ZombieBear.ZombieBearMeleeAttackGoal());
        this.targetSelector.addGoal(2, new ModdedNearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new ModdedNearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new ModdedNearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new ModdedNearestAttackableTargetGoal<>(this, BrownBear.class, true));
        this.targetSelector.addGoal(3, new ModdedNearestAttackableTargetGoal<>(this, PolarBear.class, true));
        this.targetSelector.addGoal(3, new ModdedNearestAttackableTargetGoal<>(this, Horse.class, true));
        this.targetSelector.addGoal(5, new ModdedNearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
        this.goalSelector.addGoal(4, new FloatGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new ModdedNearestAttackableTargetGoal<>(this, Player.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(4, new ModdedNearestAttackableTargetGoal<>(this, Fox.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(2, new FeralNearestAttackableTargetGoal<>(this, Mob.class, 0, false, false, FeralNearestAttackableTargetGoal.LIVING_ENTITY_SELECTOR));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MOVEMENT_SPEED, 0.35D).add(Attributes.ATTACK_DAMAGE, 12.0D);
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
                this.clientSideStandAnimation = Mth.clamp(this.clientSideStandAnimation + 1.0F, 0.0F, 6.0F);
            } else {
                this.clientSideStandAnimation = Mth.clamp(this.clientSideStandAnimation - 1.0F, 0.0F, 6.0F);
            }
        }

        if (this.warningSoundTicks > 0) {
            --this.warningSoundTicks;
        }
    }

    public EntityDimensions getDimensions(Pose p_213305_1_) {
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
        return Mth.lerp(p_189795_1_, this.clientSideStandAnimationO, this.clientSideStandAnimation) / 6.0F;
    }

    protected float getWaterSlowDown() {
        return 0.98F;
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent death) {
        if(death.getEntityLiving() instanceof ZombieBear) {
            LootHelper.dropLoot(ConfigurationHandler.LOOT.zombie_bear_loot.get(),
                    ConfigurationHandler.LOOT.zombie_bear_drop_chance.get(),
                    death.getEntityLiving());
        }
    }

    class ZombieBearMeleeAttackGoal extends MeleeAttackGoal {
        public ZombieBearMeleeAttackGoal() {
            super(ZombieBear.this, 1.25D, true);
        }

        protected void checkAndPerformAttack(LivingEntity p_29589_, double p_29590_) {
            double d0 = this.getAttackReachSqr(p_29589_);
            if (p_29590_ <= d0 && this.isTimeToAttack()) {
                this.resetAttackCooldown();
                this.mob.doHurtTarget(p_29589_);
                ZombieBear.this.setStanding(false);
            } else if (p_29590_ <= d0 * 2.0D) {
                if (this.isTimeToAttack()) {
                    ZombieBear.this.setStanding(false);
                    this.resetAttackCooldown();
                }

                if (this.getTicksUntilNextAttack() <= 10) {
                    ZombieBear.this.setStanding(true);
                    ZombieBear.this.playWarningSound();
                }
            } else {
                this.resetAttackCooldown();
                ZombieBear.this.setStanding(false);
            }

        }

        public void stop() {
            ZombieBear.this.setStanding(false);
            super.stop();
        }

        protected double getAttackReachSqr(LivingEntity p_29587_) {
            return (double)(4.0F + p_29587_.getBbWidth());
        }
    }

    private void playWarningSound() {
        if (this.warningSoundTicks <= 0) {
            this.playSound(SoundEvents.POLAR_BEAR_WARNING, 1.0F, this.getVoicePitch());
            this.warningSoundTicks = 40;
        }
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }
}
package com.madalchemist.zombienation.entity;

import com.madalchemist.zombienation.entity.ai.FeralNearestAttackableTargetGoal;
import com.madalchemist.zombienation.entity.ai.ModdedNearestAttackableTargetGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FollowBoatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Zolphin extends Dolphin {
    public Zolphin(EntityType<? extends Zolphin> e, Level w) {
        super(e, w);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new ModdedNearestAttackableTargetGoal<>(this, Dolphin.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Squid.class, true));
        this.targetSelector.addGoal(2, new ModdedNearestAttackableTargetGoal<>(this, AbstractFish.class, true));
        this.targetSelector.addGoal(2, new ModdedNearestAttackableTargetGoal<>(this, PolarBear.class, true));
        this.targetSelector.addGoal(2, new ModdedNearestAttackableTargetGoal<>(this, BrownBear.class, true));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, (double)1.2F, true));
        this.goalSelector.addGoal(8, new FollowBoatGoal(this));
        //this.targetSelector.addGoal(2, new FeralNearestAttackableTargetGoal<>(this, LivingEntity.class, 0, false, false, FeralNearestAttackableTargetGoal.LIVING_ENTITY_SELECTOR));
    }
}
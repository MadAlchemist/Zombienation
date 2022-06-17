package com.madalchemist.zombienation.entity;

import com.madalchemist.zombienation.entity.ai.FeralNearestAttackableTargetGoal;
import com.madalchemist.zombienation.utils.ConfigurationHandler;
import com.madalchemist.zombienation.utils.LootHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.Predicate;

public class BrownBear extends PolarBear {

    public BrownBear(EntityType<? extends PolarBear> p_29519_, Level p_29520_) {
        super(p_29519_, p_29520_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie1.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie2.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie3.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie4.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie5.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie6.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie7.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie8.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie9.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Chesthead.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ZombieBear.class, 10, true, true, (Predicate<LivingEntity>)null));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ZombieHorse.class, 10, true, true, (Predicate<LivingEntity>)null));

    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent death) {
        if(death.getEntityLiving() instanceof BrownBear) {
            LootHelper.dropLoot(ConfigurationHandler.LOOT.brown_bear_loot.get(),
                    ConfigurationHandler.LOOT.brown_bear_drop_chance.get(),
                    death.getEntityLiving());
        }
    }
}

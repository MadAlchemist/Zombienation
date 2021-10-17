package com.madalchemist.zombienation.potions;

import com.madalchemist.zombienation.ConfigHandler;
import com.madalchemist.zombienation.ModItems;
import com.madalchemist.zombienation.PotionsRegistry;
import com.madalchemist.zombienation.animals.BrownBearEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;

import java.util.ArrayList;
import java.util.List;

public class PotionModdedOnFire extends Effect {
    public PotionModdedOnFire(EffectType type, int color) {
        super(type, color);
    }

    public boolean isReady(int duration, int amplifier) {
        return duration <= 1;
    }

    public void performEffect(LivingEntity entity, int amplifier) {

    }

    @Override
    public void applyEffectTick(LivingEntity entity, int p_76394_2_) {
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }
}

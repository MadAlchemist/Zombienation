package com.madalchemist.zombienation.utils;

import com.madalchemist.zombienation.entity.BrownBear;
import com.madalchemist.zombienation.init.ItemsRegistry;
import com.madalchemist.zombienation.init.PotionsRegistry;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PotionZombieVirus extends MobEffect {
    public PotionZombieVirus(MobEffectCategory type, int color) {
        super(type, color);
    }

    public boolean isReady(int duration, int amplifier) {
        return duration <= 1;
    }

    public void performEffect(LivingEntity entity, int amplifier) {

    }

    @Override
    public void applyEffectTick(LivingEntity entity, int p_76394_2_) {
        if (entity instanceof Player) {
            if (!entity.hasEffect(MobEffects.HUNGER) && Math.random() >= 0.125 && ConfigurationHandler.INFECTION.infectionHunger.get())
                entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 0, false, false));
            if (!entity.hasEffect(MobEffects.CONFUSION) && Math.random() <= 0.00172543 && ConfigurationHandler.INFECTION.infectionNausea.get())
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false, false));
            if (!entity.hasEffect(MobEffects.WEAKNESS) && Math.random() <= 0.333 && ConfigurationHandler.INFECTION.infectionWeakness.get())
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0, false, false));
        }
        // Most animals don't die from virus itself, but are very vulnerable due to poisoning.
        if(entity instanceof PolarBear || entity instanceof BrownBear || entity instanceof Horse || entity instanceof Wolf) {
            if (!entity.hasEffect(MobEffects.POISON) && Math.random() <= 0.000167819)
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 40, 0, false, false));
        }

        //Villagers  and animals can be healed by applying weakness and strength at same time
        //This also gives regeneration
        if(!(entity instanceof Player)) {
            if(entity.hasEffect(MobEffects.WEAKNESS) && entity.hasEffect(MobEffects.DAMAGE_BOOST)) {
                if(!entity.level.isClientSide()) {
                    entity.removeEffect(PotionsRegistry.POTION_ZOMBIE_VIRUS);
                    entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, false, false));
                    entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1200, 0, false, false));
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        List<ItemStack> cure=new ArrayList<ItemStack>();
        cure.add(new ItemStack(ItemsRegistry.ANTIZOMBINE.get()));
        return(cure);
    }
}
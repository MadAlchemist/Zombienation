package com.madalchemist.zombienation.potions;

import com.madalchemist.zombienation.ConfigHandler;
import com.madalchemist.zombienation.ModItems;
import com.madalchemist.zombienation.items.ItemAntizombine;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;

import java.util.ArrayList;
import java.util.List;

public class PotionZombieVirus extends Effect {
    public PotionZombieVirus(EffectType type, int color) {
        super(type, color);
    }

    public boolean isReady(int duration, int amplifier) {
        return duration <= 1;
    }

    public void performEffect(LivingEntity entity, int amplifier) {

    }

    @Override
    public void applyEffectTick(LivingEntity entity, int p_76394_2_) {
        if (entity instanceof PlayerEntity) {
            if (!entity.hasEffect(Effects.HUNGER) && Math.random() >= 0.125 && ConfigHandler.INFECTION.infectionHunger.get())
                entity.addEffect(new EffectInstance(Effects.HUNGER, 200, 0, false, false));
            if (!entity.hasEffect(Effects.CONFUSION) && Math.random() <= 0.00172543 && ConfigHandler.INFECTION.infectionNausea.get())
                entity.addEffect(new EffectInstance(Effects.CONFUSION, 200, 0, false, false));
            if (!entity.hasEffect(Effects.WEAKNESS) && Math.random() <= 0.333 && ConfigHandler.INFECTION.infectionWeakness.get())
                entity.addEffect(new EffectInstance(Effects.WEAKNESS, 200, 0, false, false));
        }
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        List<ItemStack> cure=new ArrayList<ItemStack>();
        cure.add(new ItemStack(ModItems.ANTIZOMBINE.get()));
        return(cure);
    }
}

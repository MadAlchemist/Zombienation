package com.madalchemist.betterzombies;

import com.madalchemist.betterzombies.zombies.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "betterzombies")
public class EventsHandler {
    @SubscribeEvent
    public static void onJoin(EntityJoinWorldEvent event) {
        boolean isWeaponLootable = false;
        if(event.getEntity().getClass().getSuperclass() == ZombieEntity.class) {
            /* All modded zombies are adults */
            ((ZombieEntity)event.getEntity()).setBaby(false);

            /* Do zombie loot must be enchanted with "Curse of Vanishing"? */
            if(Math.random() >= 0.95) {
                isWeaponLootable = true;
            } else {
                isWeaponLootable = false;
            }
        }

        /* Is it a Zombie Miner? */
        if (event.getEntity().getClass() == Zombie3.class) {
            Zombie3 zombie = (Zombie3) event.getEntity();
            /* Zombie Miners are "tough" zombies, so add damage boost and health boost */
            zombie.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, (int) Integer.MAX_VALUE, (int) 3, (false), (false)));
            zombie.addEffect(new EffectInstance(Effects.HEALTH_BOOST, (int) Integer.MAX_VALUE, (int) 3, (false), (false)));
            /* Create helmet and pickaxe */
            ItemStack helmet = new ItemStack(Items.IRON_HELMET);
            ItemStack pickaxe = new ItemStack(Items.IRON_PICKAXE);
            /* Enchant helmet and pickaxe with "Curse of Vanishing" if it is not lootable */
            if (!isWeaponLootable){
                helmet.enchant(Enchantments.VANISHING_CURSE,1);
                pickaxe.enchant(Enchantments.VANISHING_CURSE,1);
            }
            /* Try to equip helmet */
            zombie.equipItemIfPossible(new ItemStack(Items.IRON_HELMET));

            /* Try to equip pickaxe, if enabled by config */
            if(ConfigHandler.GENERAL.minersHavePickaxes.get()) {
                zombie.equipItemIfPossible(new ItemStack(Items.IRON_PICKAXE));
            }
        }

        /* Is this a Zombie Warrior? */
        if (event.getEntity().getClass() == Zombie4.class) {
            Zombie4 zombie = (Zombie4) event.getEntity();
            /* Zombie Warriors are "tough" zombies, so apply damage boost and health boost */
            zombie.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, (int) Integer.MAX_VALUE, (int) 3, (false), (false)));
            zombie.addEffect(new EffectInstance(Effects.HEALTH_BOOST, (int) Integer.MAX_VALUE, (int) 3, (false), (false)));
            /* Try to equip sword, if enabled */
            if(ConfigHandler.GENERAL.warriorsHaveSwords.get()) {
                ItemStack sword = new ItemStack(Items.IRON_SWORD);
                if (!isWeaponLootable) {
                    sword.enchant(Enchantments.VANISHING_CURSE, 1);
                }
                sword.enchant(Enchantments.SHARPNESS, 5);
                zombie.equipItemIfPossible(sword);
            }
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        /* Is this Hazmat Zombie? */
        if (event.getEntity() instanceof Zombie5) {
            /* Is damage source a potion? */
            if (event.getSource().isMagic()) {
                /* Does hazmat suit protect from potions? */
                if (ConfigHandler.GENERAL.hazmatSuitProtectsFromPotions.get()){
                    event.setCanceled(true);
                }
            }
        }
    }
}
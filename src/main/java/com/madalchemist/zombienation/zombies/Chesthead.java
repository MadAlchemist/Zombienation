package com.madalchemist.zombienation.zombies;

import com.madalchemist.zombienation.ConfigHandler;
import com.madalchemist.zombienation.SoundsRegistry;
import com.madalchemist.zombienation.animals.BrownBearEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "zombienation")
public class Chesthead extends ZombieEntity {

    public Chesthead(EntityType<? extends Chesthead> zombie, World world) {
        super(zombie, world);
    }

    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        if(!p_184601_1_.isFire() && !p_184601_1_.isMagic()) {
            return SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR;
        } else return SoundEvents.ZOMBIE_HURT;

    }
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR;
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
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, BrownBearEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PolarBearEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, HorseEntity.class, true));
    }

    @SubscribeEvent
    public static void onJoin(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof Chesthead) {
            ItemStack chest = new ItemStack(Blocks.CHEST,1);
            EquipmentSlotType slot  = EquipmentSlotType.HEAD;
            ((Chesthead) event.getEntity()).setItemSlot(slot, chest);
            ((Chesthead) event.getEntity()).addEffect(new EffectInstance(Effects.BLINDNESS,3,Integer.MAX_VALUE, false, false));
            ((Chesthead) event.getEntity()).addEffect(new EffectInstance(Effects.ABSORPTION,3,Integer.MAX_VALUE, false, false));
            ((Chesthead) event.getEntity()).addEffect(new EffectInstance(Effects.HEALTH_BOOST,2,Integer.MAX_VALUE, false, false));
            ((Chesthead) event.getEntity()).addEffect(new EffectInstance(Effects.DAMAGE_BOOST,2,Integer.MAX_VALUE, false, false));
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent death) {
        if(death.getEntity() instanceof Chesthead) {
            ItemEntity itementity = new ItemEntity(death.getEntityLiving().level,
                                                   death.getEntityLiving().getX(),
                                                   death.getEntityLiving().getY(),
                                                   death.getEntityLiving().getZ(),
                                                   getRandomLoot());
            death.getEntityLiving().level.addFreshEntity(itementity);
        }
    }

    private static ItemStack getRandomLoot() {
        int max_index =  ConfigHandler.GENERAL.chestheadLoot.get().size()-1;
        int index = (int)Math.round(Math.random()*max_index);
        String id = ConfigHandler.GENERAL.chestheadLoot.get().get(index);
        String id_parts[] = id.split(":");
        if(id_parts.length <= 1 || id_parts.length > 3) { return ItemStack.EMPTY; }
        return(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(id_parts[0], id_parts[1])),1));
    }

    public boolean checkSpawnRules(IServerWorld world, SpawnReason reason) {
       return super.checkSpawnRules(world, reason);
    }
}
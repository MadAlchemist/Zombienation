package com.madalchemist.zombienation.entity;

import com.madalchemist.zombienation.entity.ai.FeralNearestAttackableTargetGoal;
import com.madalchemist.zombienation.init.SoundsRegistry;
import com.madalchemist.zombienation.utils.ConfigurationHandler;
import com.madalchemist.zombienation.utils.LootHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = "zombienation")
public class Chesthead extends Zombie {
   public Chesthead(EntityType<? extends Zombie> zombie, Level level) {
      super(zombie, level);
   }

   @Override
   protected boolean isSunSensitive() {
      return ConfigurationHandler.GENERAL.burnAtDay.get();
   }

   protected SoundEvent getAmbientSound() {
      return SoundsRegistry.ZOMBIE1_AMBIENT;
   }
   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
      return SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR;
   }
   protected SoundEvent getDeathSound() {
      return SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR;
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
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, BrownBear.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PolarBear.class, true));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Horse.class, true));
      this.targetSelector.addGoal(1, new FeralNearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, true, (Predicate<LivingEntity>)null));
   }

   public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
      return super.checkSpawnRules(world, reason);
   }


   @SubscribeEvent
   public static void onDeath(LivingDeathEvent death) {
      if(death.getEntity() instanceof Chesthead) {
         death.getEntityLiving().setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
         ItemEntity itementity = new ItemEntity(death.getEntityLiving().level,
                 death.getEntityLiving().getX(),
                 death.getEntityLiving().getY(),
                 death.getEntityLiving().getZ(),
                 getRandomLoot());
         death.getEntityLiving().level.addFreshEntity(itementity);
      }
   }

   private static ItemStack getRandomLoot() {
      int max_index =  ConfigurationHandler.LOOT.chestheadLoot.get().size()-1;
      int index = (int)Math.round(Math.random()*max_index);
      String id = ConfigurationHandler.LOOT.chestheadLoot.get().get(index);
      String id_parts[] = id.split(":");
      if(id_parts.length <= 1 || id_parts.length > 3) { return ItemStack.EMPTY; }
      return(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(id_parts[0], id_parts[1])),1));
   }

   public boolean checkSpawnRules(ServerLevelAccessor world, MobSpawnType reason) {
      return super.checkSpawnRules(world, reason);
   }

}

package com.madalchemist.zombienation.entity;

import com.madalchemist.zombienation.Zombienation;
import com.madalchemist.zombienation.init.EntityRegistry;
import com.madalchemist.zombienation.utils.ConfigurationHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Zombienation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RandomZombie extends Zombie {
   public RandomZombie(EntityType<? extends Zombie> zombie, Level level) {
      super(zombie, level);
   }
   @Override
   protected boolean isSunSensitive() {
      //return ConfigHandler.GENERAL.burnAtDay.get();
      return false;
   }

   @SubscribeEvent
   public static void onSpawn(EntityJoinWorldEvent event) {
      if (event.getEntity() instanceof RandomZombie) {
         Random random = new Random();
         int variant = random.nextInt(11);
         switch(variant) {
            case 0:
               Zombie1 zombie1 = new Zombie1(EntityRegistry.ZOMBIE1.get(), event.getEntity().level);
               zombie1.setPos(new Vec3(event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ()));
               event.getEntity().level.addFreshEntity(zombie1);
               break;
            case 1:
               Zombie2 zombie2 = new Zombie2(EntityRegistry.ZOMBIE2.get(), event.getEntity().level);
               zombie2.setPos(new Vec3(event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ()));
               event.getEntity().level.addFreshEntity(zombie2);
               break;
            case 2:
               Zombie3 zombie3 = new Zombie3(EntityRegistry.ZOMBIE3.get(), event.getEntity().level);
               zombie3.setPos(new Vec3(event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ()));
               event.getEntity().level.addFreshEntity(zombie3);
               break;
            case 3:
               Zombie4 zombie4 = new Zombie4(EntityRegistry.ZOMBIE4.get(), event.getEntity().level);
               zombie4.setPos(new Vec3(event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ()));
               event.getEntity().level.addFreshEntity(zombie4);
               break;
            case 4:
               Zombie5 zombie5 = new Zombie5(EntityRegistry.ZOMBIE5.get(), event.getEntity().level);
               zombie5.setPos(new Vec3(event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ()));
               event.getEntity().level.addFreshEntity(zombie5);
               break;
            case 5:
               Zombie6 zombie6 = new Zombie6(EntityRegistry.ZOMBIE6.get(), event.getEntity().level);
               zombie6.setPos(new Vec3(event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ()));
               event.getEntity().level.addFreshEntity(zombie6);
               break;
            case 6:
               Zombie7 zombie7 = new Zombie7(EntityRegistry.ZOMBIE7.get(), event.getEntity().level);
               zombie7.setPos(new Vec3(event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ()));
               event.getEntity().level.addFreshEntity(zombie7);
               break;
            case 7:
               Zombie8 zombie8 = new Zombie8(EntityRegistry.ZOMBIE8.get(), event.getEntity().level);
               zombie8.setPos(new Vec3(event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ()));
               event.getEntity().level.addFreshEntity(zombie8);
               break;
            case 8:
               /*
               if(event.getEntity().level.getBiome(new BlockPos(event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ()))..equals(Biome.BiomeCategory.ICY)) {
                  Zombie9 zombie9 = new Zombie9(EntityRegistry.ZOMBIE9.get(), event.getEntity().level);
                  zombie9.setPos(new Vec3(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ()));
                  event.getEntity().level.addFreshEntity(zombie9);
               }
               break; */
            case 9:
               Zombie zombie = new Zombie(EntityType.ZOMBIE, event.getEntity().level);
               zombie.setPos(new Vec3(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ()));
               event.getEntity().level.addFreshEntity(zombie);
               break;
            case 10:
               ZombieVillager villager = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, event.getEntity().level);
               villager.setPos(new Vec3(event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ()));
               event.getEntity().level.addFreshEntity(villager);
               break;
         }
         event.setCanceled(true);
      }
   }
}

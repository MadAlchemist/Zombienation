package com.madalchemist.zombienation.zombies;

import com.madalchemist.zombienation.ConfigHandler;
import com.madalchemist.zombienation.SoundsRegistry;
import com.madalchemist.zombienation.ZombiesRegistry;
import com.madalchemist.zombienation.animals.BrownBearEntity;
import com.madalchemist.zombienation.zombies.ai.FeralNearestAttackableTargetGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.HuskEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

@Mod.EventBusSubscriber(modid = "zombienation")
public class RandomZombie extends ZombieEntity {

    public RandomZombie(EntityType<? extends RandomZombie> zombie, World world) {
        super(zombie, world);
    }

    @Override
    protected boolean isSunSensitive() {
        return ConfigHandler.GENERAL.burnAtDay.get();
    }


    public boolean checkSpawnRules(IServerWorld world, SpawnReason reason) {
       return super.checkSpawnRules(world, reason);
    }

    @Override
    public void tick() {
        Random rand = new Random();
        int z_type = rand.nextInt(11);

        switch(z_type) {
            case 0:
                ZombieEntity zombie = new ZombieEntity(EntityType.ZOMBIE, this.level);
                zombie.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(zombie);
                this.remove();
                break;

            case 1:
                Zombie1 zombie1 = new Zombie1(ZombiesRegistry.ZOMBIE_1.get(), this.level);
                zombie1.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(zombie1);
                this.remove();
                break;

            case 2:
                Zombie2 zombie2 = new Zombie2(ZombiesRegistry.ZOMBIE_2.get(), this.level);
                zombie2.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(zombie2);
                this.remove();
                break;

            case 3:
                Zombie3 zombie3 = new Zombie3(ZombiesRegistry.ZOMBIE_3.get(), this.level);
                zombie3.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(zombie3);
                this.remove();
                break;

            case 4:
                Zombie4 zombie4 = new Zombie4(ZombiesRegistry.ZOMBIE_4.get(), this.level);
                zombie4.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(zombie4);
                this.remove();
                break;

            case 5:
                Zombie5 zombie5 = new Zombie5(ZombiesRegistry.ZOMBIE_5.get(), this.level);
                zombie5.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(zombie5);
                this.remove();
                break;

            case 6:
                Zombie6 zombie6 = new Zombie6(ZombiesRegistry.ZOMBIE_6.get(), this.level);
                zombie6.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(zombie6);
                this.remove();
                break;

            case 7:
                Zombie7 zombie7 = new Zombie7(ZombiesRegistry.ZOMBIE_7.get(), this.level);
                zombie7.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(zombie7);
                this.remove();
                break;

            case 8:
                Zombie8 zombie8 = new Zombie8(ZombiesRegistry.ZOMBIE_8.get(), this.level);
                zombie8.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(zombie8);
                this.remove();
                break;

            case 9:
                RegistryKey<Biome> biomeKey = RegistryKey.create(ForgeRegistries.Keys.BIOMES, this.level.getBiome(new BlockPos(this.getX(),this.getY(),this.getZ())).getRegistryName());
                if(BiomeDictionary.hasType(biomeKey,BiomeDictionary.Type.SNOWY)) {

                /*
                    Zombie9 zombie9 = new Zombie9(ZombiesRegistry.ZOMBIE_9.get(), this.level);

                zombie9.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(zombie9);
                this.remove();
                */
                } else
                {
                    HuskEntity husk = new HuskEntity(EntityType.HUSK, this.level);
                    husk.setPos(this.getX(), this.getY(), this.getZ());
                    this.level.addFreshEntity(husk);
                    this.remove();
                    break;
                }
                break;

            case 10:
                ZombieVillagerEntity zombievillager = new ZombieVillagerEntity(EntityType.ZOMBIE_VILLAGER, this.level);
                zombievillager.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(zombievillager);
                this.remove();
                break;
        }
    }
}
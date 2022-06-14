package com.madalchemist.zombienation.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationHandler {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final Spawn SPAWN = new Spawn(BUILDER);
    public static final Infection INFECTION = new Infection(BUILDER);
    public static final Loot LOOT = new Loot(BUILDER);

    public static class General {
        public final ForgeConfigSpec.BooleanValue burnAtDay;
        public final ForgeConfigSpec.BooleanValue replaceSkeletonsWithRandomZombies;
        public final ForgeConfigSpec.BooleanValue hazmatZombiesImmuneToPotions;
        public final ForgeConfigSpec.BooleanValue minersHavePickaxes;
        public final ForgeConfigSpec.BooleanValue minersHaveHelmets;
        public final ForgeConfigSpec.BooleanValue warriorsHaveSwords;
        public final ForgeConfigSpec.BooleanValue warriorsHaveHelmets;

        General(ForgeConfigSpec.Builder builder) {

            builder.push("general");

            burnAtDay = builder
                    .comment("Should modded zombies burn at day? (default: false)")
                    .define("burnAtDay", false);

            replaceSkeletonsWithRandomZombies = builder
                    .comment("Should skeletons be replaced with random zombies in overworld? (default: false)")
                    .define("replaceSkeletons", false);

            hazmatZombiesImmuneToPotions = builder
                    .comment("Should hazmat suit protect zombies from splash potions? (default: true)")
                    .define("hazmatZombiesImmuneToPotions", true);

            minersHavePickaxes = builder
                    .comment("Should miner zombies have iron pickaxes? (default: true)")
                    .define("minersHavePickaxes", true);
            minersHaveHelmets = builder
                    .comment("Should miner zombies have iron helmets? (default: true)")
                    .define("minersHaveHelmets", true);
            warriorsHaveSwords = builder
                    .comment("Should warrior zombies have iron swords? (default: true)")
                    .define("warriorsHaveSwords", true);
            warriorsHaveHelmets = builder
                    .comment("Should warrior zombies have iron helmets? (default: true)")
                    .define("warriorsHaveHelmets", true);


            builder.pop();
        }
    }

    public static class Spawn {
        public final ForgeConfigSpec.IntValue spawnWeight;
        public final ForgeConfigSpec.IntValue groupMin;
        public final ForgeConfigSpec.IntValue groupMax;
        public final ForgeConfigSpec.IntValue chestheadSpawnWeight;
        public final ForgeConfigSpec.IntValue chestheadGroupMin;
        public final ForgeConfigSpec.IntValue chestheadGroupMax;

        public final ForgeConfigSpec.DoubleValue toughChance;
        public final ForgeConfigSpec.DoubleValue brutalChance;
        public final ForgeConfigSpec.DoubleValue infernalChance;
        Spawn(ForgeConfigSpec.Builder builder) {
            builder.push("spawn");

            spawnWeight = builder
                    .comment("Spawn weight of modded humanoid zombies (vanilla = 100)")
                    .defineInRange("spawnWeight", 100, 0, 1000);

            groupMin = builder
                    .comment("Minimum count in spawn group of humanoid zombies (vanilla=2)")
                    .defineInRange("groupMin", 2, 0, 16);

            groupMax = builder
                    .comment("Maximum count in spawn group of humanoid zombies (vanilla=4)")
                    .defineInRange("groupMax", 4, 0, 16);

            chestheadSpawnWeight = builder
                    .comment("Spawn weight of chestheads (Vanilla endermen = 100")
                    .defineInRange("chestheadSpawnWeight", 10, 0, 1000);
            chestheadGroupMin = builder
                    .comment("Minimum count in spawn group of chestheads (Vanilla endermen = 1)")
                    .defineInRange("chestheadSpawnGroupMin", 1, 0, 16);
            chestheadGroupMax = builder
                    .comment("Maximum count in spawn group of chestheads (Vanilla endermen = 4)")
                    .defineInRange("chestheadSpawnGroupMax", 4, 0, 16);

            toughChance = builder
                    .comment("Chance that new humanoid zombie will receive Regeneration II buff on spawn")
                    .defineInRange("toughChance", 0.025, 0, 1);

            brutalChance = builder
                    .comment("Chance that new humanoid zombie will receive Regeneration II and Strength II buffs on spawn")
                    .defineInRange("brutalChance", 0.025, 0, 1);

            infernalChance = builder
                    .comment("Chance that new humanoid zombie will receive Regeneration II, Strength II and Speed II buffs on spawn")
                    .defineInRange("infernalChance", 0.0025, 0, 1);

            builder.pop();
        }
    }

    public static class Infection {
        public final ForgeConfigSpec.DoubleValue infectionChance;
        public final ForgeConfigSpec.IntValue infectionDuration;
        public final ForgeConfigSpec.BooleanValue infectionHunger;
        public final ForgeConfigSpec.BooleanValue infectionNausea;
        public final ForgeConfigSpec.BooleanValue infectionWeakness;
        public final ForgeConfigSpec.BooleanValue hardcoreInfection;

        Infection(ForgeConfigSpec.Builder builder) {
            builder.push("infection");
                infectionChance = builder
                        .comment("Chance that you will be infected when hit by zombie")
                        .defineInRange("infectionChance", 0.1, 0.0, 1.0);

                infectionDuration = builder
                        .comment("How long (in ticks) you will suffer from infection without cure before you die")
                        .defineInRange("infectionDuration", 72000, 6000, Integer.MAX_VALUE);

                infectionHunger = builder
                        .comment("Does zombie virus cause hunger?")
                        .define("infectionHunger", true);

                infectionNausea = builder
                        .comment("Does zombie virus cause nausea?")
                        .define("infectionNausea", true);

                infectionWeakness = builder
                    .comment("Does zombie virus cause weakness?")
                    .define("infectionWeakness", true);

                hardcoreInfection = builder
                    .comment("Does zombie virus have no cure (infected = dead)?")
                    .define("hardcoreInfection", false);


            builder.pop();
        }
    }

    public static class Loot {
        public ForgeConfigSpec.ConfigValue<String> zombie1_loot;
        public ForgeConfigSpec.DoubleValue zombie1_drop_chance;
        public ForgeConfigSpec.ConfigValue<String> zombie2_loot;
        public ForgeConfigSpec.DoubleValue zombie2_drop_chance;
        public ForgeConfigSpec.ConfigValue<String> zombie3_loot;
        public ForgeConfigSpec.DoubleValue zombie3_drop_chance;
        public ForgeConfigSpec.ConfigValue<String> zombie4_loot;
        public ForgeConfigSpec.DoubleValue zombie4_drop_chance;
        public ForgeConfigSpec.ConfigValue<String> zombie5_loot;
        public ForgeConfigSpec.DoubleValue zombie5_drop_chance;
        public ForgeConfigSpec.ConfigValue<String> zombie6_loot;
        public ForgeConfigSpec.DoubleValue zombie6_drop_chance;
        public ForgeConfigSpec.ConfigValue<String> zombie7_loot;
        public ForgeConfigSpec.DoubleValue zombie7_drop_chance;
        public ForgeConfigSpec.ConfigValue<String> zombie8_loot;
        public ForgeConfigSpec.DoubleValue zombie8_drop_chance;
        public ForgeConfigSpec.ConfigValue<String> zombie9_loot;
        public ForgeConfigSpec.DoubleValue zombie9_drop_chance;
        //public ForgeConfigSpec.ConfigValue<String> zombie_bear_loot;
        //public ForgeConfigSpec.DoubleValue zombie_bear_drop_chance;
        //public ForgeConfigSpec.ConfigValue<String> brown_bear_loot;
        //public ForgeConfigSpec.DoubleValue brown_bear_drop_chance;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> chestheadLoot;

        Loot(ForgeConfigSpec.Builder builder) {
            builder.push("Loot");
            zombie1_loot = builder
                    .comment("Zombie in short pants loot:")
                    .define("zombie1_loot", "minecraft:carrot");
            zombie1_drop_chance = builder
                    .comment("Zombie in short pants loot drop chance:")
                    .defineInRange("zombie1_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie2_loot = builder
                    .comment("Husk with skin head loot:")
                    .define("zombie2_loot", "minecraft:acacia_leaves");
            zombie2_drop_chance = builder
                    .comment("Husk with skin head loot drop chance:")
                    .defineInRange("zombie2_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie3_loot = builder
                    .comment("Zombie miner loot:")
                    .define("zombie3_loot", "minecraft:minecraft:coal_ore");
            zombie3_drop_chance = builder
                    .comment("Zombie miner loot drop chance:")
                    .defineInRange("zombie3_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie4_loot = builder
                    .comment("Zombie warrior loot:")
                    .define("zombie4_loot", "minecraft:leather_boots");
            zombie4_drop_chance = builder
                    .comment("Zombie warrior loot drop chance:")
                    .defineInRange("zombie4_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie5_loot = builder
                    .comment("Hazmat zombie loot:")
                    .define("zombie5_loot", "minecraft:glass_bottle");
            zombie5_drop_chance = builder
                    .comment("Hazmat zombie loot drop chance:")
                    .defineInRange("zombie5_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie6_loot = builder
                    .comment("Zombie Alex loot:")
                    .define("zombie6_loot", "minecraft:pumpkin_pie");
            zombie6_drop_chance = builder
                    .comment("Zombie Alex loot drop chance:")
                    .defineInRange("zombie6_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie7_loot = builder
                    .comment("Husk girl loot:")
                    .define("zombie7_loot", "minecraft:paper");
            zombie7_drop_chance = builder
                    .comment("Husk girl loot drop chance:")
                    .defineInRange("zombie7_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie8_loot = builder
                    .comment("Zombie girl with flowers on head loot:")
                    .define("zombie8_loot", "minecraft:poppy");
            zombie8_drop_chance = builder
                    .comment("Zombie girl with flowers on head loot drop chance:")
                    .defineInRange("zombie8_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie9_loot = builder
                    .comment("Frozen lumberjack loot:")
                    .define("zombie9_loot", "minecraft:spruce_log");
            zombie9_drop_chance = builder
                    .comment("Frozen lumberjack loot drop chance:")
                    .defineInRange("zombie9_drop_chance", 0.5d, 0.0d, 1.0d);
            /*
            zombie_bear_loot = builder
                    .comment("Zombie bear loot:")
                    .define("zombie_bear_loot", "minecraft:bone_block");
            zombie_bear_drop_chance = builder
                    .comment("Zombie bear loot drop chance:")
                    .defineInRange("zombie_bear_drop_chance", 0.5d, 0.0d, 1.0d);

            brown_bear_loot = builder
                    .comment("Brown bear loot:")
                    .define("brown_bear_loot", "minecraft:leather");
            brown_bear_drop_chance = builder
                    .comment("Brown bear loot drop chance:")
                    .defineInRange("brown_bear_drop_chance", 0.5d, 0.0d, 1.0d);
            */
            ArrayList<String> loots = new ArrayList<String>(128);
            loots.add("minecraft:rotten_flesh");
            loots.add("minecraft:oak_planks");
            chestheadLoot = builder
                    .comment("Possible chesthead loot")
                    .defineList("chestheadLoot", loots, o -> itemExists(o.toString()));

            builder.pop();
        }

        public static boolean itemExists(String ID) {
            String id_parts[] = ID.split(":");
            if (id_parts.length <= 1 || id_parts.length > 3) {
                return false;
            }
            if (ForgeRegistries.ITEMS.getValue(new ResourceLocation(id_parts[0], id_parts[1])) == null) {
                return false;
            }
            return true;
        }
    }
    public static final ForgeConfigSpec spec = BUILDER.build();
}



package com.madalchemist.zombienation.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigurationHandler {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final Spawn SPAWN = new Spawn(BUILDER);
    public static final Infection INFECTION = new Infection(BUILDER);

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
                    .define("minersHavePickaxes", true);
            warriorsHaveSwords = builder
                    .comment("Should warrior zombies have iron swords? (default: true)")
                    .define("minersHavePickaxes", true);
            warriorsHaveHelmets = builder
                    .comment("Should warrior zombies have iron helmets? (default: true)")
                    .define("minersHavePickaxes", true);


            builder.pop();
        }
    }

    public static class Spawn {
        public final ForgeConfigSpec.IntValue spawnWeight;
        public final ForgeConfigSpec.IntValue groupMin;
        public final ForgeConfigSpec.IntValue groupMax;
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
            builder.push("spawn");
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
    public static final ForgeConfigSpec spec = BUILDER.build();
}



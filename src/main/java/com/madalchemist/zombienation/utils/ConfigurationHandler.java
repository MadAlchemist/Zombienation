package com.madalchemist.zombienation.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigurationHandler {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final Spawn SPAWN = new Spawn(BUILDER);

    public static class General {
        public final ForgeConfigSpec.BooleanValue burnAtDay;
        public final ForgeConfigSpec.BooleanValue replaceSkeletonsWithRandomZombies;

        General(ForgeConfigSpec.Builder builder) {

            builder.push("general");

            burnAtDay = builder
                    .comment("Should modded zombies burn at day? (default: false)")
                    .define("burnAtDay", false);

            replaceSkeletonsWithRandomZombies = builder
                    .comment("Should skeletons be replaced with random zombies in overworld? (default: false)")
                    .define("replaceSkeletons", false);

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
    public static final ForgeConfigSpec spec = BUILDER.build();
}



package com.madalchemist.zombienation.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigurationHandler {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);

    public static class General {
        public final ForgeConfigSpec.BooleanValue burnAtDay;
        public final ForgeConfigSpec.BooleanValue replaceZombiesWithRandomZombies;
        public final ForgeConfigSpec.BooleanValue replaceSkeletonsWithRandomZombies;

        General(ForgeConfigSpec.Builder builder) {
            builder.push("general");

            burnAtDay = builder
                    .comment("Should modded zombies burn at day? (default: false)")
                    .define("burnAtDay", false);

            replaceZombiesWithRandomZombies = builder
                    .comment("Should vanilla zombies be replaced with random zombies? (default: false)")
                    .define("replaceVanillaZombies", false);

            replaceSkeletonsWithRandomZombies = builder
                    .comment("Should skeletons be replaced with random zombies? (default: false)")
                    .define("replaceSkeletons", false);


            builder.pop();
        }

    }

    public static final ForgeConfigSpec spec = BUILDER.build();
}



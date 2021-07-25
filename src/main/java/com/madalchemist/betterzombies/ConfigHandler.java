package com.madalchemist.betterzombies;

import net.minecraft.world.GameRules;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class ConfigHandler {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final Spawn SPAWN = new Spawn(BUILDER);

    public static class General {
        public final ForgeConfigSpec.BooleanValue burnAtDay;
        public final ForgeConfigSpec.BooleanValue warriorsHaveSwords;
        public final ForgeConfigSpec.BooleanValue minersHavePickaxes;
        public final ForgeConfigSpec.BooleanValue hazmatSuitProtectsFromPotions;

        General(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            burnAtDay = builder
                    .comment("Do modded zombies burn at day?")
                    .define("burnAtDay", false);
            warriorsHaveSwords = builder
                    .comment("Do zombie warriors have enchanted iron swords?")
                    .define("warriorsHaveSwords", true);
            minersHavePickaxes = builder
                    .comment("Do zombie miners have iron pickaxes?")
                    .define("minersHavePickaxes", true);
            hazmatSuitProtectsFromPotions = builder
                    .comment("Are hazmat zombies immune to potions? (NOT WORKING YET)")
                    .define("hazmatSuitProtectsFromPotions", true);
            builder.pop();
        }


    }
    public static class Spawn {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> include;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> exclude;

        public final ForgeConfigSpec.IntValue spawnWeightNormal;
        public final ForgeConfigSpec.IntValue minGroupNormal;
        public final ForgeConfigSpec.IntValue maxGroupNormal;

        public final ForgeConfigSpec.IntValue spawnWeightTough;
        public final ForgeConfigSpec.IntValue minGroupTough;
        public final ForgeConfigSpec.IntValue maxGroupTough;

        //private int fix;

        Spawn(ForgeConfigSpec.Builder builder) {
            builder.push("Spawn");
            builder.comment("BiomeDictionary types to include");
            include = builder.defineList("include", Collections.singletonList(OVERWORLD.toString()), o -> BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(String.valueOf(o))));
            builder.comment("BiomeDictionary types to exclude (overrides \"include\")");
            exclude = builder.defineList("exclude", Arrays.asList(NETHER.toString(), END.toString()), o -> BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(String.valueOf(o))));
            //fix=100;
            spawnWeightNormal = builder
                    .comment("Normal zombie spawn weight (0-1000, vanilla zombies == 100")
                    .defineInRange("spawnWeightNormal", 100,0,1000);
            minGroupNormal = builder
                    .comment("Normal zombie min count in group (0-16, vanilla zombies == 2)")
                    .defineInRange("minGroupNormal", 2,0,16);
            maxGroupNormal = builder
                    .comment("Normal zombie max count in group (0-16, vanilla zombies == 4)")
                    .defineInRange("maxGroupNormal", 4, 0, 16);
            spawnWeightTough = builder
                    .comment("Tough zombie (warriors, miners) spawn weight (0-1000, vanilla zombies == 100")
                    .defineInRange("spawnWeightTough", 10,0,1000);
            minGroupTough = builder
                    .comment("Tough zombie (warriors, miners) min count in group (0-16, vanilla zombies == 2)")
                    .defineInRange("minGroupTough", 1, 0, 16);
            maxGroupTough = builder
                    .comment("Tough zombie (warriors, miners) max count in group (0-16, vanilla zombies == 4)")
                    .defineInRange("maxGroupTough", 2, 0, 16);
            builder.pop();
        }
    }
    public static final ForgeConfigSpec spec = BUILDER.build();
}

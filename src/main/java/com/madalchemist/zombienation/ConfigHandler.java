package com.madalchemist.zombienation;

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
    public static Infection INFECTION = new Infection(BUILDER);

    public static class General {
        public final ForgeConfigSpec.BooleanValue burnAtDay;
        public final ForgeConfigSpec.BooleanValue warriorsHaveSwords;
        public final ForgeConfigSpec.BooleanValue minersHavePickaxes;
        public final ForgeConfigSpec.BooleanValue hazmatSuitProtectsFromPotions;

        General(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            burnAtDay = builder
                    .comment("Do modded zombies burn at day (not affects vanilla zombies)?")
                    .define("burnAtDay", false);
            warriorsHaveSwords = builder
                    .comment("Do zombie warriors have enchanted iron swords?")
                    .define("warriorsHaveSwords", true);
            minersHavePickaxes = builder
                    .comment("Do zombie miners have iron pickaxes?")
                    .define("minersHavePickaxes", true);
            hazmatSuitProtectsFromPotions = builder
                    .comment("Are hazmat zombies immune to potions?")
                    .define("hazmatSuitProtectsFromPotions", true);

            builder.pop();
        }


    }

    public static class Infection {
        public final ForgeConfigSpec.DoubleValue infectionChance;
        public final ForgeConfigSpec.IntValue infectionDuration;
        public final ForgeConfigSpec.BooleanValue infectionHunger;
        public final ForgeConfigSpec.BooleanValue infectionNausea;
        public final ForgeConfigSpec.BooleanValue infectionWeakness;
        public final ForgeConfigSpec.BooleanValue infectionDeathZombification;


        Infection(ForgeConfigSpec.Builder builder) {
            builder.push("Infection");
            infectionChance = builder
                    .comment("Infection chance from being hit by zombie, min: 0.0, max: 1.0, default: 0.1")
                    .defineInRange("infectionChance", 0.1d, 0.0d, 1.0d);
            infectionDuration = builder
                    .comment("Infection duration in seconds before you die:")
                    .defineInRange("infectionDuration", 3600, 0, Integer.MAX_VALUE);
            infectionHunger = builder
                    .comment("Does infection cause hunger?")
                    .define("infectionHunger",true);
            infectionNausea = builder
                    .comment("Does infection cause nausea?")
                    .define("infectionNausea",true);
            infectionWeakness = builder
                    .comment("Does infection cause weakness?")
                    .define("infectionWeakness",true);
            infectionDeathZombification = builder
                    .comment("Spawn vanilla zombie when player dies from infection?")
                    .define("infectionDeathZombification",true);
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

        Spawn(ForgeConfigSpec.Builder builder) {
            builder.push("Spawn");
            builder.comment("BiomeDictionary types to include");
            include = builder.defineList("include", Collections.singletonList(OVERWORLD.toString()), o -> BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(String.valueOf(o))));
            builder.comment("BiomeDictionary types to exclude (overrides \"include\")");
            exclude = builder.defineList("exclude", Arrays.asList(NETHER.toString(), END.toString()), o -> BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(String.valueOf(o))));

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

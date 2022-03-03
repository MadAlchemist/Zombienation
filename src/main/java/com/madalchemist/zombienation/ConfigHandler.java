package com.madalchemist.zombienation;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class ConfigHandler {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final Spawn SPAWN = new Spawn(BUILDER);
    public static Infection INFECTION = new Infection(BUILDER);
    public static Senses SENSES = new Senses(BUILDER);
    public static Loot LOOT = new Loot(BUILDER);

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
        public ForgeConfigSpec.ConfigValue<String> zombie_bear_loot;
        public ForgeConfigSpec.DoubleValue zombie_bear_drop_chance;
        public ForgeConfigSpec.ConfigValue<String> brown_bear_loot;
        public ForgeConfigSpec.DoubleValue brown_bear_drop_chance;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> chestheadLoot;

        Loot(ForgeConfigSpec.Builder builder) {
            builder.push("Loot");
            zombie1_loot = builder
                    .comment("Zombie in short pants loot:")
                    .define("zombie1_loot", "minecraft:rotten_flesh");
            zombie1_drop_chance = builder
                    .comment("Zombie in short pants loot drop chance:")
                    .defineInRange("zombie1_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie2_loot = builder
                    .comment("Husk with skin head loot:")
                    .define("zombie2_loot", "minecraft:rotten_flesh");
            zombie2_drop_chance = builder
                    .comment("Husk with skin head loot drop chance:")
                    .defineInRange("zombie2_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie3_loot = builder
                    .comment("Zombie miner loot:")
                    .define("zombie3_loot", "minecraft:rotten_flesh");
            zombie3_drop_chance = builder
                    .comment("Zombie miner loot drop chance:")
                    .defineInRange("zombie3_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie4_loot = builder
                    .comment("Zombie warrior loot:")
                    .define("zombie4_loot", "minecraft:rotten_flesh");
            zombie4_drop_chance = builder
                    .comment("Zombie warrior loot drop chance:")
                    .defineInRange("zombie4_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie5_loot = builder
                    .comment("Hazmat zombie loot:")
                    .define("zombie5_loot", "minecraft:rotten_flesh");
            zombie5_drop_chance = builder
                    .comment("Hazmat zombie loot drop chance:")
                    .defineInRange("zombie5_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie6_loot = builder
                    .comment("Zombie Alex loot:")
                    .define("zombie6_loot", "minecraft:rotten_flesh");
            zombie6_drop_chance = builder
                    .comment("Zombie Alex loot drop chance:")
                    .defineInRange("zombie6_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie7_loot = builder
                    .comment("Husk girl loot:")
                    .define("zombie7_loot", "minecraft:rotten_flesh");
            zombie7_drop_chance = builder
                    .comment("Husk girl loot drop chance:")
                    .defineInRange("zombie7_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie8_loot = builder
                    .comment("Zombie girl with flowers on head loot:")
                    .define("zombie8_loot", "minecraft:rotten_flesh");
            zombie8_drop_chance = builder
                    .comment("Zombie girl with flowers on head loot drop chance:")
                    .defineInRange("zombie8_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie9_loot = builder
                    .comment("Frozen lumberjack loot:")
                    .define("zombie9_loot", "minecraft:rotten_flesh");
            zombie9_drop_chance = builder
                    .comment("Frozen lumberjack loot drop chance:")
                    .defineInRange("zombie9_drop_chance", 0.5d, 0.0d, 1.0d);

            zombie_bear_loot = builder
                    .comment("Zombie bear loot:")
                    .define("zombie_bear_loot", "minecraft:rotten_flesh");
            zombie_bear_drop_chance = builder
                    .comment("Zombie bear loot drop chance:")
                    .defineInRange("zombie_bear_drop_chance", 0.5d, 0.0d, 1.0d);

            brown_bear_loot = builder
                    .comment("Brown bear loot:")
                    .define("brown_bear_loot", "minecraft:leather");
            brown_bear_drop_chance = builder
                    .comment("Brown bear loot drop chance:")
                    .defineInRange("brown_bear_drop_chance", 0.5d, 0.0d, 1.0d);

            chestheadLoot = builder
                    .comment("Possible chesthead loot")
                    .defineList("chestheadLoot", new ArrayList<String>(), o -> itemExists(o.toString()));


            builder.pop();
        }

        public static boolean itemExists(String ID) {
            String id_parts[] = ID.split(":");
            if(id_parts.length <= 1 || id_parts.length > 3) { return false; }
            if(ForgeRegistries.ITEMS.getValue(new ResourceLocation(id_parts[0], id_parts[1])) == null) {return false;}
            return true;
        }
    }

    public static class General {
        public final ForgeConfigSpec.BooleanValue burnAtDay;
        public final ForgeConfigSpec.BooleanValue warriorsHaveSwords;
        public final ForgeConfigSpec.BooleanValue minersHavePickaxes;
        public final ForgeConfigSpec.BooleanValue hazmatSuitProtectsFromPotions;
        public final ForgeConfigSpec.BooleanValue feralMode;
        public final ForgeConfigSpec.BooleanValue creeperProtection;

        General(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            burnAtDay = builder
                    .comment("Do zombies burn at day?")
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
            feralMode = builder
                    .comment("Should zombies attack everything except undead?")
                    .define("feralMode", false);
            creeperProtection = builder
                    .comment("Prevent creepers from blowing up zombies? Useful when feralMode is on.")
                    .define("creeperProtection", false);
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
        public final ForgeConfigSpec.BooleanValue hardcoreInfection;

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
            hardcoreInfection = builder
                    .comment("Enable hardcore infection (disables cure for players, infected = dead)?")
                    .define("hardcoreInfection", false);

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

        public final ForgeConfigSpec.IntValue spawnWeightFrozenLumberjack;
        public final ForgeConfigSpec.IntValue minGroupFrozenLumberjack;
        public final ForgeConfigSpec.IntValue maxGroupFrozenLumberjack;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> frozenLumberjackBiomes;

        public final ForgeConfigSpec.ConfigValue<List<? extends String>> bearBiomes;
        public final ForgeConfigSpec.IntValue spawnWeightBrownBear;
        public final ForgeConfigSpec.IntValue minGroupBrownBear;
        public final ForgeConfigSpec.IntValue maxGroupBrownBear;
        public final ForgeConfigSpec.IntValue spawnWeightZombieBear;
        public final ForgeConfigSpec.IntValue minGroupZombieBear;
        public final ForgeConfigSpec.IntValue maxGroupZombieBear;


        Spawn(ForgeConfigSpec.Builder builder) {
            builder.push("Spawn");

            include = builder
                    .comment("BiomeDictionary types to include")
                    .defineList("include", Collections.singletonList(OVERWORLD.toString()), o -> BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(String.valueOf(o))));

            exclude = builder
                    .comment("BiomeDictionary types to exclude (overrides \"include\")")
                    .defineList("exclude", Arrays.asList(NETHER.toString(), END.toString()), o -> BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(String.valueOf(o))));

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

            frozenLumberjackBiomes = builder
                    .comment("Biomes where frozen lumberjacks will spawn:")
                    .defineList("frozenLumberjackBiomes", Collections.singletonList(SNOWY.toString()), o -> BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(String.valueOf(o))));
            spawnWeightFrozenLumberjack = builder
                    .comment("Frozen lumberjack spawn weight, 0-1000, vanilla zombies == 100")
                    .defineInRange("spawnWeightFrozenLumberjack", 100, 0, 1000);
            minGroupFrozenLumberjack = builder
                    .comment("Frozen lumberjacks min count in group, 0-16, vanilla zombies == 2")
                    .defineInRange("minGroupFrozenLumberjacks", 2, 0, 16);
            maxGroupFrozenLumberjack = builder
                    .comment("Frozen lumberjacks max count in group, 0-16, vanilla zombies == 4")
                    .defineInRange("maxGroupFrozenLumberjacks", 4, 0, 16);
            bearBiomes = builder
                    .comment("Biomes where brown bears and zombie bears will spawn:")
                    .defineList("bearBiomes", Collections.singletonList(FOREST.toString()), o -> BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(String.valueOf(o))));
            spawnWeightBrownBear = builder
                    .comment("Brown bear spawn weight:")
                    .defineInRange("spawnWeightBrownBear", 10, 0, 1000);
            minGroupBrownBear = builder
                    .comment("Brown bear min count in group, 0-16:")
                    .defineInRange("minGroupBrownBear", 1, 0, 16);
            maxGroupBrownBear = builder
                    .comment("Brown bear max count in group, 0-16:")
                    .defineInRange("maxGroupBrownBear", 3, 0, 16);
            spawnWeightZombieBear = builder
                    .comment("Zombie bear spawn weight:")
                    .defineInRange("spawnWeightZombieBear", 10, 0, 1000);
            minGroupZombieBear = builder
                    .comment("Zombie bear min count in group, 0-16:")
                    .defineInRange("minGroupZombieBear", 1, 0, 16);
            maxGroupZombieBear = builder
                    .comment("Zombie bear max count in group, 0-16:")
                    .defineInRange("maxGroupZombieBear", 3, 0, 16);
            builder.pop();
        }
    }

    public static class Senses {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> blockBreak;
        public final ForgeConfigSpec.IntValue blockBreakNotifyRadius;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> blockPlace;
        public final ForgeConfigSpec.IntValue blockPlaceNotifyRadius;
        public final ForgeConfigSpec.IntValue explosionNotifyRadius;
        public final ForgeConfigSpec.IntValue anvilNotifyRadius;
        public final ForgeConfigSpec.IntValue noteblockNotifyRadius;
        //public final ForgeConfigSpec.IntValue minecartNotifyRadius;

        public static boolean blockExists(String ID) {
            String id_parts[] = ID.split(":");
            if(id_parts.length <= 1 || id_parts.length > 3) { return false; }
            if(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(id_parts[0], id_parts[1])) == null) {return false;}
            return true;
        }


        Senses(ForgeConfigSpec.Builder builder) {
            builder.push("Senses");
            System.out.println("Creating lists...");
            ArrayList<String> blocks_break = new ArrayList<String>();
            ArrayList<String> blocks_place = new ArrayList<String>();
            System.out.println("Populating lists...");
            blocks_break.add("minecraft:glass");
            blocks_place.add("minecraft:torch");

            System.out.println("Setting up \"blockBreak\"...");
            blockBreak = builder
                    .comment("Zombies will be attracted to breaking these blocks:")
                    .defineList("blockBreak", blocks_break, o -> blockExists(o.toString()));
            System.out.println("Done!");
            blockBreakNotifyRadius = builder
                    .comment("Radius of area around the player where zombies will be notified about breaking blocks:")
                    .defineInRange("blockBreakNotifyRadius", 64, 0, Integer.MAX_VALUE);
            blockPlace = builder
                    .comment("Zombies will be attracted to placing these blocks:")
                    .defineList("blockPlace", blocks_place, o -> blockExists(o.toString()));
            blockPlaceNotifyRadius = builder
                    .comment("Radius of area around the player where zombies will be notified about placing blocks:")
                    .defineInRange("blockPlaceNotifyRadius", 64, 0, Integer.MAX_VALUE);
            explosionNotifyRadius = builder
                    .comment("Radius of area around the player where zombies will be notified about explosions:")
                    .defineInRange("explosionNotifyRadius", 128, 0, Integer.MAX_VALUE);
            anvilNotifyRadius = builder
                    .comment("Radius of area where zombies will be notified about using anvil:")
                    .defineInRange("anvilNotifyRadius", 64, 0, Integer.MAX_VALUE);
            noteblockNotifyRadius = builder
                    .comment("Radius of area where zombies will be notified about noteblock activation:")
                    .defineInRange("noteblockNotifyRadius", 64, 0, Integer.MAX_VALUE);
            /*
            minecartNotifyRadius = builder
                    .comment("Radius of area where zombies will be notified about moving minecart:")
                    .defineInRange("minecartNotifyRadius", 64, 0, Integer.MAX_VALUE);
            */
            builder.pop();
        }
    }
    public static final ForgeConfigSpec spec = BUILDER.build();
}

package com.madalchemist.zombienation;

import com.madalchemist.zombienation.blocks.NetheriteDoor;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlocksRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Zombienation.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Zombienation.MODID);

    public static void init() {
        // attach DeferredRegisters to the event bus
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // register blocks
   public static final RegistryObject<Block> NETHERITE_DOOR = BLOCKS.register("netherite_door", () ->
       new NetheriteDoor(AbstractBlock.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(16000.0F,13.0f).sound(SoundType.NETHERITE_BLOCK).noOcclusion()));

   public static final RegistryObject<Block> COMPOSITE_BRICKS = BLOCKS.register("composite_bricks", () ->
       new Block(AbstractBlock.Properties.of(Material.HEAVY_METAL, MaterialColor.STONE).requiresCorrectToolForDrops().strength(16000.0F,14.0f).sound(SoundType.NETHERITE_BLOCK)));
   //register items
   public static final RegistryObject<Item> NETHERITE_DOOR_ITEM = ITEMS.register("netherite_door", () ->
       new TallBlockItem(NETHERITE_DOOR.get(), (new Item.Properties()).tab(Zombienation.CREATIVE_TAB)));

   public static final RegistryObject<Item> COMPOSITE_BRICKS_ITEM = ITEMS.register("composite_bricks", () ->
       new BlockItem(COMPOSITE_BRICKS.get(), (new Item.Properties()).tab(Zombienation.CREATIVE_TAB)));
}


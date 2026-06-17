package io.siuolplex.wood_you_dye.registry;

import io.gremstudio.gremlib.block.util.BlockSetTypeUtil;
import io.gremstudio.gremlib.block.util.WoodTypeUtil;
import io.gremstudio.gremlib.util.WoodSetInfo;
import io.siuolplex.wood_you_dye.AnotherWoodSet;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;
import java.util.List;

public class WoodYouDyeWoodSets {
    public static List<AnotherWoodSet> WOODSETS = new ArrayList<>();

    public static AnotherWoodSet RED_DYED_WOOD = addStandardWoodset("red_dyed", MapColor.COLOR_RED, MapColor.TERRACOTTA_RED, "red");
    public static AnotherWoodSet PINK_DYED_WOOD = addStandardWoodset("pink_dyed", MapColor.COLOR_PINK, MapColor.TERRACOTTA_PINK, "pink");
    public static AnotherWoodSet ORANGE_DYED_WOOD = addStandardWoodset("orange_dyed", MapColor.COLOR_ORANGE, MapColor.TERRACOTTA_ORANGE, "orange");
    public static AnotherWoodSet BROWN_DYED_WOOD = addStandardWoodset("brown_dyed", MapColor.COLOR_BROWN, MapColor.TERRACOTTA_BROWN, "brown");
    public static AnotherWoodSet YELLOW_DYED_WOOD = addStandardWoodset("yellow_dyed", MapColor.COLOR_YELLOW, MapColor.TERRACOTTA_YELLOW, "yellow");
    public static AnotherWoodSet LIME_DYED_WOOD = addStandardWoodset("lime_dyed", MapColor.COLOR_LIGHT_GREEN, MapColor.TERRACOTTA_LIGHT_GREEN, "lime");
    public static AnotherWoodSet GREEN_DYED_WOOD = addStandardWoodset("green_dyed", MapColor.COLOR_GREEN, MapColor.COLOR_GREEN, "green");
    public static AnotherWoodSet CYAN_DYED_WOOD = addStandardWoodset("cyan_dyed", MapColor.COLOR_CYAN, MapColor.COLOR_CYAN, "cyan");
    public static AnotherWoodSet BLUE_DYED_WOOD = addStandardWoodset("blue_dyed", MapColor.COLOR_BLUE, MapColor.COLOR_BLUE, "blue");
    public static AnotherWoodSet LIGHT_BLUE_DYED_WOOD = addStandardWoodset("light_blue_dyed", MapColor.COLOR_LIGHT_BLUE, MapColor.TERRACOTTA_LIGHT_BLUE, "light_blue");
    public static AnotherWoodSet PURPLE_DYED_WOOD = addStandardWoodset("purple_dyed", MapColor.COLOR_PURPLE, MapColor.COLOR_PURPLE, "purple");
    public static AnotherWoodSet MAGENTA_DYED_WOOD = addStandardWoodset("magenta_dyed", MapColor.COLOR_MAGENTA, MapColor.COLOR_MAGENTA, "magenta");
    public static AnotherWoodSet WHITE_DYED_WOOD = addStandardWoodset("white_dyed", MapColor.WOOL, MapColor.TERRACOTTA_WHITE, "white");
    public static AnotherWoodSet LIGHT_GRAY_DYED_WOOD = addStandardWoodset("light_gray_dyed", MapColor.COLOR_LIGHT_GRAY, MapColor.TERRACOTTA_LIGHT_GRAY, "light_gray");
    public static AnotherWoodSet GRAY_DYED_WOOD = addStandardWoodset("gray_dyed", MapColor.COLOR_GRAY, MapColor.TERRACOTTA_GRAY, "gray");
    public static AnotherWoodSet BLACK_DYED_WOOD = addStandardWoodset("black_dyed", MapColor.COLOR_BLACK, MapColor.COLOR_BLACK, "black");

    public static AnotherWoodSet NETHER_RED_DYED_WOOD = addNetherWoodset("red_dyed_nether", MapColor.COLOR_RED, MapColor.TERRACOTTA_RED, "red");
    public static AnotherWoodSet NETHER_PINK_DYED_WOOD = addNetherWoodset("pink_dyed_nether", MapColor.COLOR_PINK, MapColor.TERRACOTTA_PINK, "pink");
    public static AnotherWoodSet NETHER_ORANGE_DYED_WOOD = addNetherWoodset("orange_dyed_nether", MapColor.COLOR_ORANGE, MapColor.TERRACOTTA_ORANGE, "orange");
    public static AnotherWoodSet NETHER_BROWN_DYED_WOOD = addNetherWoodset("brown_dyed_nether", MapColor.COLOR_BROWN, MapColor.TERRACOTTA_BROWN, "brown");
    public static AnotherWoodSet NETHER_YELLOW_DYED_WOOD = addNetherWoodset("yellow_dyed_nether", MapColor.COLOR_YELLOW, MapColor.TERRACOTTA_YELLOW, "yellow");
    public static AnotherWoodSet NETHER_LIME_DYED_WOOD = addNetherWoodset("lime_dyed_nether", MapColor.COLOR_LIGHT_GREEN, MapColor.TERRACOTTA_LIGHT_GREEN, "lime");
    public static AnotherWoodSet NETHER_GREEN_DYED_WOOD = addNetherWoodset("green_dyed_nether", MapColor.COLOR_GREEN, MapColor.COLOR_GREEN, "green");
    public static AnotherWoodSet NETHER_CYAN_DYED_WOOD = addNetherWoodset("cyan_dyed_nether", MapColor.COLOR_CYAN, MapColor.COLOR_CYAN, "cyan");
    public static AnotherWoodSet NETHER_BLUE_DYED_WOOD = addNetherWoodset("blue_dyed_nether", MapColor.COLOR_BLUE, MapColor.COLOR_BLUE, "blue");
    public static AnotherWoodSet NETHER_LIGHT_BLUE_DYED_WOOD = addNetherWoodset("light_blue_dyed_nether", MapColor.COLOR_LIGHT_BLUE, MapColor.TERRACOTTA_LIGHT_BLUE, "light_blue");
    public static AnotherWoodSet NETHER_PURPLE_DYED_WOOD = addNetherWoodset("purple_dyed_nether", MapColor.COLOR_PURPLE, MapColor.COLOR_PURPLE, "purple");
    public static AnotherWoodSet NETHER_MAGENTA_DYED_WOOD = addNetherWoodset("magenta_dyed_nether", MapColor.COLOR_MAGENTA, MapColor.COLOR_MAGENTA, "magenta");
    public static AnotherWoodSet NETHER_WHITE_DYED_WOOD = addNetherWoodset("white_dyed_nether", MapColor.WOOL, MapColor.TERRACOTTA_WHITE, "white");
    public static AnotherWoodSet NETHER_LIGHT_GRAY_DYED_WOOD = addNetherWoodset("light_gray_dyed_nether", MapColor.COLOR_LIGHT_GRAY, MapColor.TERRACOTTA_LIGHT_GRAY, "light_gray");
    public static AnotherWoodSet NETHER_GRAY_DYED_WOOD = addNetherWoodset("gray_dyed_nether", MapColor.COLOR_GRAY, MapColor.TERRACOTTA_GRAY, "gray");
    public static AnotherWoodSet NETHER_BLACK_DYED_WOOD = addNetherWoodset("black_dyed_nether", MapColor.COLOR_BLACK, MapColor.COLOR_BLACK, "black");

    public static AnotherWoodSet BAMBOO_RED_DYED_WOOD = addBambooWoodset("red_dyed_bamboo", MapColor.COLOR_RED, MapColor.TERRACOTTA_RED, "red");
    public static AnotherWoodSet BAMBOO_PINK_DYED_WOOD = addBambooWoodset("pink_dyed_bamboo", MapColor.COLOR_PINK, MapColor.TERRACOTTA_PINK, "pink");
    public static AnotherWoodSet BAMBOO_ORANGE_DYED_WOOD = addBambooWoodset("orange_dyed_bamboo", MapColor.COLOR_ORANGE, MapColor.TERRACOTTA_ORANGE, "orange");
    public static AnotherWoodSet BAMBOO_BROWN_DYED_WOOD = addBambooWoodset("brown_dyed_bamboo", MapColor.COLOR_BROWN, MapColor.TERRACOTTA_BROWN, "brown");
    public static AnotherWoodSet BAMBOO_YELLOW_DYED_WOOD = addBambooWoodset("yellow_dyed_bamboo", MapColor.COLOR_YELLOW, MapColor.TERRACOTTA_YELLOW, "yellow");
    public static AnotherWoodSet BAMBOO_LIME_DYED_WOOD = addBambooWoodset("lime_dyed_bamboo", MapColor.COLOR_LIGHT_GREEN, MapColor.TERRACOTTA_LIGHT_GREEN, "lime");
    public static AnotherWoodSet BAMBOO_GREEN_DYED_WOOD = addBambooWoodset("green_dyed_bamboo", MapColor.COLOR_GREEN, MapColor.COLOR_GREEN, "green");
    public static AnotherWoodSet BAMBOO_CYAN_DYED_WOOD = addBambooWoodset("cyan_dyed_bamboo", MapColor.COLOR_CYAN, MapColor.COLOR_CYAN, "cyan");
    public static AnotherWoodSet BAMBOO_BLUE_DYED_WOOD = addBambooWoodset("blue_dyed_bamboo", MapColor.COLOR_BLUE, MapColor.COLOR_BLUE, "blue");
    public static AnotherWoodSet BAMBOO_LIGHT_BLUE_DYED_WOOD = addBambooWoodset("light_blue_dyed_bamboo", MapColor.COLOR_LIGHT_BLUE, MapColor.TERRACOTTA_LIGHT_BLUE, "light_blue");
    public static AnotherWoodSet BAMBOO_PURPLE_DYED_WOOD = addBambooWoodset("purple_dyed_bamboo", MapColor.COLOR_PURPLE, MapColor.COLOR_PURPLE, "purple");
    public static AnotherWoodSet BAMBOO_MAGENTA_DYED_WOOD = addBambooWoodset("magenta_dyed_bamboo", MapColor.COLOR_MAGENTA, MapColor.COLOR_MAGENTA, "magenta");
    public static AnotherWoodSet BAMBOO_WHITE_DYED_WOOD = addBambooWoodset("white_dyed_bamboo", MapColor.WOOL, MapColor.TERRACOTTA_WHITE, "white");
    public static AnotherWoodSet BAMBOO_LIGHT_GRAY_DYED_WOOD = addBambooWoodset("light_gray_dyed_bamboo", MapColor.COLOR_LIGHT_GRAY, MapColor.TERRACOTTA_LIGHT_GRAY, "light_gray");
    public static AnotherWoodSet BAMBOO_GRAY_DYED_WOOD = addBambooWoodset("gray_dyed_bamboo", MapColor.COLOR_GRAY, MapColor.TERRACOTTA_GRAY, "gray");
    public static AnotherWoodSet BAMBOO_BLACK_DYED_WOOD = addBambooWoodset("black_dyed_bamboo", MapColor.COLOR_BLACK, MapColor.COLOR_BLACK, "black");


    public static AnotherWoodSet addWoodSet(AnotherWoodSet set) {;
        WOODSETS.add(set);
        return set;
    }

    public static AnotherWoodSet addStandardWoodset(String setName, MapColor mainColor, MapColor secondaryColor, String permutationName) {
        BlockSetType blockSetType = BlockSetTypeUtil.ofWood("standard/" + permutationName);
        WoodType woodType = WoodTypeUtil.of("standard/" + permutationName, blockSetType);

        return addWoodSet(new AnotherWoodSet.Builder(setName)
                .setBlockSetType(blockSetType)
                .setWoodType(woodType)
                .setWoodDetail(WoodSetInfo.OVERWORLD)
                .setLogProperties(() -> logProperties(mainColor, secondaryColor, woodType))
                .setPlankProperties(() -> BlockBehaviour.Properties.of().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0f).ignitedByLava().mapColor(mainColor))
                .setItemProperties(Item.Properties::new)
                .setVariantName("standard")
                .setPermutationName(permutationName).build());
    }

    public static AnotherWoodSet addNetherWoodset(String setName, MapColor mainColor, MapColor secondaryColor, String permutationName) {
        BlockSetType blockSetType = BlockSetTypeUtil.ofWood("nether/" + permutationName);
        WoodType woodType = WoodTypeUtil.of("nether/" + permutationName, blockSetType);

        return addWoodSet(new AnotherWoodSet.Builder(setName)
                .setBlockSetType(blockSetType)
                .setWoodType(woodType)
                .setWoodDetail(WoodSetInfo.NETHER)
                .setLogProperties(() -> logProperties(mainColor, secondaryColor, woodType))
                .setPlankProperties(() -> BlockBehaviour.Properties.of().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0f).mapColor(mainColor))
                .setItemProperties(() -> new Item.Properties().fireResistant())
                .setVariantName("nether")
                .setPermutationName(permutationName).build());
    }

    public static AnotherWoodSet addBambooWoodset(String setName, MapColor mainColor, MapColor secondaryColor, String permutationName) {
        BlockSetType blockSetType = BlockSetTypeUtil.ofWood("bamboo/" + permutationName);
        WoodType woodType = WoodTypeUtil.of("bamboo/" + permutationName, blockSetType);

        return addWoodSet(new AnotherWoodSet.Builder(setName)
                .setBlockSetType(blockSetType)
                .setWoodType(woodType)
                .setWoodDetail(WoodSetInfo.BAMBOO)
                .setLogProperties(() -> logProperties(mainColor, secondaryColor, woodType))
                .setPlankProperties(() -> BlockBehaviour.Properties.of().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0f).ignitedByLava().mapColor(mainColor))
                .setItemProperties(Item.Properties::new)
                .setVariantName("bamboo")
                .setPermutationName(permutationName).build());
    }

    private static BlockBehaviour.Properties logProperties(MapColor topColor, MapColor sideColor, WoodType type) {
        return BlockBehaviour.Properties.of()
                .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(type.soundType())
                .ignitedByLava();
    }

    public static void register(Registry<?> registry) {
        if (registry.key().equals(Registries.BLOCK)) {
            WOODSETS.forEach(set -> set.BLOCKS.setRegister());
        } else if (registry.key().equals(Registries.ITEM)) {
            WOODSETS.forEach(set -> set.ITEMS.setRegister());
        } else if (registry.key().equals(Registries.ENTITY_TYPE)) {
            WOODSETS.forEach(set -> set.ENTITIES.setRegister());
        } else if (registry.equals(BuiltInRegistries.BLOCK_ENTITY_TYPE)) {
            WOODSETS.forEach(set -> set.BLOCK_ENTITIES.setRegister());
        }
    }
}

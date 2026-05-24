package io.siuolplex.wood_you_dye.registry;

import io.siuolplex.wood_you_dye.block.*;
import io.siuolplex.wood_you_dye.block.sign.WoodYouDyeHangingSignBlock;
import io.siuolplex.wood_you_dye.block.sign.WoodYouDyeHangingWallSignBlock;
import io.siuolplex.wood_you_dye.block.sign.WoodYouDyeSignBlock;
import io.siuolplex.wood_you_dye.block.sign.WoodYouDyeWallSignBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.LinkedHashSet;
import java.util.Set;

import static io.siuolplex.wood_you_dye.registry.WoodYouDyeSignTypes.*;

public class WoodYouDyeBlocks {
    public static Set<Block> blockHolder = new LinkedHashSet<>();
    public static final Block RED_PLANKS = register("red_planks", new Block(commonSettings().mapColor(MapColor.COLOR_RED)));
    public static final Block RED_PLANK_SLAB = register("red_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_RED)));
    public static final Block RED_PLANK_STAIRS = register("red_plank_stairs", new WoodYouDyeStairBlock(RED_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_RED)));
    public static final Block RED_PLANK_FENCE = register("red_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_RED)));
    public static final Block RED_PLANK_FENCE_GATE = register("red_plank_fence_gate", new WoodYouDyeFenceGateBlock(RED_WOOD, commonSettings().mapColor(MapColor.COLOR_RED)));
    public static final Block RED_PLANK_PRESSURE_PLATE = register("red_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_RED)));
    public static final Block RED_PLANK_BUTTON = register("red_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_RED)));
    public static final Block RED_PLANK_DOOR = register("red_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_RED)));
    public static final Block RED_PLANK_TRAPDOOR = register("red_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_RED)));
    public static final Block RED_PLANK_SIGN = register("red_plank_sign", new WoodYouDyeSignBlock(RED_WOOD, commonSettings().mapColor(MapColor.COLOR_RED)));
    public static final Block RED_PLANK_WALL_SIGN = register("red_plank_wall_sign", new WoodYouDyeWallSignBlock(RED_WOOD, commonSettings().mapColor(MapColor.COLOR_RED)));
    public static final Block RED_PLANK_HANGING_SIGN = register("red_plank_hanging_sign", new WoodYouDyeHangingSignBlock(RED_WOOD, commonSettings().mapColor(MapColor.COLOR_RED)));
    public static final Block RED_PLANK_WALL_HANGING_SIGN = register("red_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(RED_WOOD, commonSettings().mapColor(MapColor.COLOR_RED)));

    public static final Block ORANGE_PLANKS = register("orange_planks", new Block(commonSettings().mapColor(MapColor.COLOR_ORANGE)));
    public static final Block ORANGE_PLANK_SLAB = register("orange_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_ORANGE)));
    public static final Block ORANGE_PLANK_STAIRS = register("orange_plank_stairs", new WoodYouDyeStairBlock(ORANGE_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_ORANGE)));
    public static final Block ORANGE_PLANK_FENCE = register("orange_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_ORANGE)));
    public static final Block ORANGE_PLANK_FENCE_GATE = register("orange_plank_fence_gate", new WoodYouDyeFenceGateBlock(ORANGE_WOOD, commonSettings().mapColor(MapColor.COLOR_ORANGE)));
    public static final Block ORANGE_PLANK_PRESSURE_PLATE = register("orange_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_ORANGE)));
    public static final Block ORANGE_PLANK_BUTTON = register("orange_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_ORANGE)));
    public static final Block ORANGE_PLANK_DOOR = register("orange_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_ORANGE)));
    public static final Block ORANGE_PLANK_TRAPDOOR = register("orange_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_ORANGE)));
    public static final Block ORANGE_PLANK_SIGN = register("orange_plank_sign", new WoodYouDyeSignBlock(ORANGE_WOOD, commonSettings().mapColor(MapColor.COLOR_ORANGE)));
    public static final Block ORANGE_PLANK_WALL_SIGN = register("orange_plank_wall_sign", new WoodYouDyeWallSignBlock(ORANGE_WOOD, commonSettings().mapColor(MapColor.COLOR_ORANGE)));
    public static final Block ORANGE_PLANK_HANGING_SIGN = register("orange_plank_hanging_sign", new WoodYouDyeHangingSignBlock(ORANGE_WOOD, commonSettings().mapColor(MapColor.COLOR_ORANGE)));
    public static final Block ORANGE_PLANK_WALL_HANGING_SIGN = register("orange_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(ORANGE_WOOD, commonSettings().mapColor(MapColor.COLOR_ORANGE)));

    public static final Block YELLOW_PLANKS = register("yellow_planks", new Block(commonSettings().mapColor(MapColor.COLOR_YELLOW)));
    public static final Block YELLOW_PLANK_SLAB = register("yellow_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_YELLOW)));
    public static final Block YELLOW_PLANK_STAIRS = register("yellow_plank_stairs", new WoodYouDyeStairBlock(YELLOW_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_YELLOW)));
    public static final Block YELLOW_PLANK_FENCE = register("yellow_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_YELLOW)));
    public static final Block YELLOW_PLANK_FENCE_GATE = register("yellow_plank_fence_gate", new WoodYouDyeFenceGateBlock(YELLOW_WOOD, commonSettings().mapColor(MapColor.COLOR_YELLOW)));
    public static final Block YELLOW_PLANK_PRESSURE_PLATE = register("yellow_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_YELLOW)));
    public static final Block YELLOW_PLANK_BUTTON = register("yellow_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_YELLOW)));
    public static final Block YELLOW_PLANK_DOOR = register("yellow_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_YELLOW)));
    public static final Block YELLOW_PLANK_TRAPDOOR = register("yellow_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_YELLOW)));
    public static final Block YELLOW_PLANK_SIGN = register("yellow_plank_sign", new WoodYouDyeSignBlock(YELLOW_WOOD, commonSettings().mapColor(MapColor.COLOR_YELLOW)));
    public static final Block YELLOW_PLANK_WALL_SIGN = register("yellow_plank_wall_sign", new WoodYouDyeWallSignBlock(YELLOW_WOOD, commonSettings().mapColor(MapColor.COLOR_YELLOW)));
    public static final Block YELLOW_PLANK_HANGING_SIGN = register("yellow_plank_hanging_sign", new WoodYouDyeHangingSignBlock(YELLOW_WOOD, commonSettings().mapColor(MapColor.COLOR_YELLOW)));
    public static final Block YELLOW_PLANK_WALL_HANGING_SIGN = register("yellow_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(YELLOW_WOOD, commonSettings().mapColor(MapColor.COLOR_YELLOW)));

    public static final Block LIME_PLANKS = register("lime_planks", new Block(commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Block LIME_PLANK_SLAB = register("lime_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Block LIME_PLANK_STAIRS = register("lime_plank_stairs", new WoodYouDyeStairBlock(LIME_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Block LIME_PLANK_FENCE = register("lime_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Block LIME_PLANK_FENCE_GATE = register("lime_plank_fence_gate", new WoodYouDyeFenceGateBlock(LIME_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Block LIME_PLANK_PRESSURE_PLATE = register("lime_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Block LIME_PLANK_BUTTON = register("lime_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Block LIME_PLANK_DOOR = register("lime_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Block LIME_PLANK_TRAPDOOR = register("lime_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Block LIME_PLANK_SIGN = register("lime_plank_sign", new WoodYouDyeSignBlock(LIME_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Block LIME_PLANK_WALL_SIGN = register("lime_plank_wall_sign", new WoodYouDyeWallSignBlock(LIME_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Block LIME_PLANK_HANGING_SIGN = register("lime_plank_hanging_sign", new WoodYouDyeHangingSignBlock(LIME_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Block LIME_PLANK_WALL_HANGING_SIGN = register("lime_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(LIME_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GREEN)));

    public static final Block GREEN_PLANKS = register("green_planks", new Block(commonSettings().mapColor(MapColor.COLOR_GREEN)));
    public static final Block GREEN_PLANK_SLAB = register("green_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_GREEN)));
    public static final Block GREEN_PLANK_STAIRS = register("green_plank_stairs", new WoodYouDyeStairBlock(GREEN_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_GREEN)));
    public static final Block GREEN_PLANK_FENCE = register("green_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_GREEN)));
    public static final Block GREEN_PLANK_FENCE_GATE = register("green_plank_fence_gate", new WoodYouDyeFenceGateBlock(GREEN_WOOD, commonSettings().mapColor(MapColor.COLOR_GREEN)));
    public static final Block GREEN_PLANK_PRESSURE_PLATE = register("green_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_GREEN)));
    public static final Block GREEN_PLANK_BUTTON = register("green_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_GREEN)));
    public static final Block GREEN_PLANK_DOOR = register("green_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_GREEN)));
    public static final Block GREEN_PLANK_TRAPDOOR = register("green_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_GREEN)));
    public static final Block GREEN_PLANK_SIGN = register("green_plank_sign", new WoodYouDyeSignBlock(GREEN_WOOD, commonSettings().mapColor(MapColor.COLOR_GREEN)));
    public static final Block GREEN_PLANK_WALL_SIGN = register("green_plank_wall_sign", new WoodYouDyeWallSignBlock(GREEN_WOOD, commonSettings().mapColor(MapColor.COLOR_GREEN)));
    public static final Block GREEN_PLANK_HANGING_SIGN = register("green_plank_hanging_sign", new WoodYouDyeHangingSignBlock(GREEN_WOOD, commonSettings().mapColor(MapColor.COLOR_GREEN)));
    public static final Block GREEN_PLANK_WALL_HANGING_SIGN = register("green_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(GREEN_WOOD, commonSettings().mapColor(MapColor.COLOR_GREEN)));

    public static final Block BLUE_PLANKS = register("blue_planks", new Block(commonSettings().mapColor(MapColor.COLOR_BLUE)));
    public static final Block BLUE_PLANK_SLAB = register("blue_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_BLUE)));
    public static final Block BLUE_PLANK_STAIRS = register("blue_plank_stairs", new WoodYouDyeStairBlock(BLUE_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_BLUE)));
    public static final Block BLUE_PLANK_FENCE = register("blue_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_BLUE)));
    public static final Block BLUE_PLANK_FENCE_GATE = register("blue_plank_fence_gate", new WoodYouDyeFenceGateBlock(BLUE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLUE)));
    public static final Block BLUE_PLANK_PRESSURE_PLATE = register("blue_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLUE)));
    public static final Block BLUE_PLANK_BUTTON = register("blue_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLUE)));
    public static final Block BLUE_PLANK_DOOR = register("blue_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLUE)));
    public static final Block BLUE_PLANK_TRAPDOOR = register("blue_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLUE)));
    public static final Block BLUE_PLANK_SIGN = register("blue_plank_sign", new WoodYouDyeSignBlock(BLUE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLUE)));
    public static final Block BLUE_PLANK_WALL_SIGN = register("blue_plank_wall_sign", new WoodYouDyeWallSignBlock(BLUE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLUE)));
    public static final Block BLUE_PLANK_HANGING_SIGN = register("blue_plank_hanging_sign", new WoodYouDyeHangingSignBlock(BLUE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLUE)));
    public static final Block BLUE_PLANK_WALL_HANGING_SIGN = register("blue_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(BLUE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLUE)));

    public static final Block CYAN_PLANKS = register("cyan_planks", new Block(commonSettings().mapColor(MapColor.COLOR_CYAN)));
    public static final Block CYAN_PLANK_SLAB = register("cyan_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_CYAN)));
    public static final Block CYAN_PLANK_STAIRS = register("cyan_plank_stairs", new WoodYouDyeStairBlock(CYAN_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_CYAN)));
    public static final Block CYAN_PLANK_FENCE = register("cyan_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_CYAN)));
    public static final Block CYAN_PLANK_FENCE_GATE = register("cyan_plank_fence_gate", new WoodYouDyeFenceGateBlock(CYAN_WOOD, commonSettings().mapColor(MapColor.COLOR_CYAN)));
    public static final Block CYAN_PLANK_PRESSURE_PLATE = register("cyan_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_CYAN)));
    public static final Block CYAN_PLANK_BUTTON = register("cyan_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_CYAN)));
    public static final Block CYAN_PLANK_DOOR = register("cyan_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_CYAN)));
    public static final Block CYAN_PLANK_TRAPDOOR = register("cyan_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_CYAN)));
    public static final Block CYAN_PLANK_SIGN = register("cyan_plank_sign", new WoodYouDyeSignBlock(CYAN_WOOD, commonSettings().mapColor(MapColor.COLOR_CYAN)));
    public static final Block CYAN_PLANK_WALL_SIGN = register("cyan_plank_wall_sign", new WoodYouDyeWallSignBlock(CYAN_WOOD, commonSettings().mapColor(MapColor.COLOR_CYAN)));
    public static final Block CYAN_PLANK_HANGING_SIGN = register("cyan_plank_hanging_sign", new WoodYouDyeHangingSignBlock(CYAN_WOOD, commonSettings().mapColor(MapColor.COLOR_CYAN)));
    public static final Block CYAN_PLANK_WALL_HANGING_SIGN = register("cyan_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(CYAN_WOOD, commonSettings().mapColor(MapColor.COLOR_CYAN)));

    public static final Block LIGHT_BLUE_PLANKS = register("light_blue_planks", new Block(commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Block LIGHT_BLUE_PLANK_SLAB = register("light_blue_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Block LIGHT_BLUE_PLANK_STAIRS = register("light_blue_plank_stairs", new WoodYouDyeStairBlock(LIGHT_BLUE_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Block LIGHT_BLUE_PLANK_FENCE = register("light_blue_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Block LIGHT_BLUE_PLANK_FENCE_GATE = register("light_blue_plank_fence_gate", new WoodYouDyeFenceGateBlock(LIGHT_BLUE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Block LIGHT_BLUE_PLANK_PRESSURE_PLATE = register("light_blue_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Block LIGHT_BLUE_PLANK_BUTTON = register("light_blue_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Block LIGHT_BLUE_PLANK_DOOR = register("light_blue_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Block LIGHT_BLUE_PLANK_TRAPDOOR = register("light_blue_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Block LIGHT_BLUE_PLANK_SIGN = register("light_blue_plank_sign", new WoodYouDyeSignBlock(LIGHT_BLUE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Block LIGHT_BLUE_PLANK_WALL_SIGN = register("light_blue_plank_wall_sign", new WoodYouDyeWallSignBlock(LIGHT_BLUE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Block LIGHT_BLUE_PLANK_HANGING_SIGN = register("light_blue_plank_hanging_sign", new WoodYouDyeHangingSignBlock(LIGHT_BLUE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Block LIGHT_BLUE_PLANK_WALL_HANGING_SIGN = register("light_blue_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(LIGHT_BLUE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_BLUE)));

    public static final Block PURPLE_PLANKS = register("purple_planks", new Block(commonSettings().mapColor(MapColor.COLOR_PURPLE)));
    public static final Block PURPLE_PLANK_SLAB = register("purple_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_PURPLE)));
    public static final Block PURPLE_PLANK_STAIRS = register("purple_plank_stairs", new WoodYouDyeStairBlock(PURPLE_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_PURPLE)));
    public static final Block PURPLE_PLANK_FENCE = register("purple_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_PURPLE)));
    public static final Block PURPLE_PLANK_FENCE_GATE = register("purple_plank_fence_gate", new WoodYouDyeFenceGateBlock(PURPLE_WOOD, commonSettings().mapColor(MapColor.COLOR_PURPLE)));
    public static final Block PURPLE_PLANK_PRESSURE_PLATE = register("purple_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_PURPLE)));
    public static final Block PURPLE_PLANK_BUTTON = register("purple_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_PURPLE)));
    public static final Block PURPLE_PLANK_DOOR = register("purple_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_PURPLE)));
    public static final Block PURPLE_PLANK_TRAPDOOR = register("purple_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_PURPLE)));
    public static final Block PURPLE_PLANK_SIGN = register("purple_plank_sign", new WoodYouDyeSignBlock(PURPLE_WOOD, commonSettings().mapColor(MapColor.COLOR_PURPLE)));
    public static final Block PURPLE_PLANK_WALL_SIGN = register("purple_plank_wall_sign", new WoodYouDyeWallSignBlock(PURPLE_WOOD, commonSettings().mapColor(MapColor.COLOR_PURPLE)));
    public static final Block PURPLE_PLANK_HANGING_SIGN = register("purple_plank_hanging_sign", new WoodYouDyeHangingSignBlock(PURPLE_WOOD, commonSettings().mapColor(MapColor.COLOR_PURPLE)));
    public static final Block PURPLE_PLANK_WALL_HANGING_SIGN = register("purple_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(PURPLE_WOOD, commonSettings().mapColor(MapColor.COLOR_PURPLE)));

    public static final Block PINK_PLANKS = register("pink_planks", new Block(commonSettings().mapColor(MapColor.COLOR_PINK)));
    public static final Block PINK_PLANK_SLAB = register("pink_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_PINK)));
    public static final Block PINK_PLANK_STAIRS = register("pink_plank_stairs", new WoodYouDyeStairBlock(PINK_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_PINK)));
    public static final Block PINK_PLANK_FENCE = register("pink_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_PINK)));
    public static final Block PINK_PLANK_FENCE_GATE = register("pink_plank_fence_gate", new WoodYouDyeFenceGateBlock(PINK_WOOD, commonSettings().mapColor(MapColor.COLOR_PINK)));
    public static final Block PINK_PLANK_PRESSURE_PLATE = register("pink_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_PINK)));
    public static final Block PINK_PLANK_BUTTON = register("pink_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_PINK)));
    public static final Block PINK_PLANK_DOOR = register("pink_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_PINK)));
    public static final Block PINK_PLANK_TRAPDOOR = register("pink_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_PINK)));
    public static final Block PINK_PLANK_SIGN = register("pink_plank_sign", new WoodYouDyeSignBlock(PINK_WOOD, commonSettings().mapColor(MapColor.COLOR_PINK)));
    public static final Block PINK_PLANK_WALL_SIGN = register("pink_plank_wall_sign", new WoodYouDyeWallSignBlock(PINK_WOOD,commonSettings().mapColor(MapColor.COLOR_PINK)));
    public static final Block PINK_PLANK_HANGING_SIGN = register("pink_plank_hanging_sign", new WoodYouDyeHangingSignBlock(PINK_WOOD, commonSettings().mapColor(MapColor.COLOR_PINK)));
    public static final Block PINK_PLANK_WALL_HANGING_SIGN = register("pink_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(PINK_WOOD,commonSettings().mapColor(MapColor.COLOR_PINK)));

    public static final Block MAGENTA_PLANKS = register("magenta_planks", new Block(commonSettings().mapColor(MapColor.COLOR_MAGENTA)));
    public static final Block MAGENTA_PLANK_SLAB = register("magenta_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_MAGENTA)));
    public static final Block MAGENTA_PLANK_STAIRS = register("magenta_plank_stairs", new WoodYouDyeStairBlock(MAGENTA_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_MAGENTA)));
    public static final Block MAGENTA_PLANK_FENCE = register("magenta_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_MAGENTA)));
    public static final Block MAGENTA_PLANK_FENCE_GATE = register("magenta_plank_fence_gate", new WoodYouDyeFenceGateBlock(MAGENTA_WOOD, commonSettings().mapColor(MapColor.COLOR_MAGENTA)));
    public static final Block MAGENTA_PLANK_PRESSURE_PLATE = register("magenta_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_MAGENTA)));
    public static final Block MAGENTA_PLANK_BUTTON = register("magenta_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_MAGENTA)));
    public static final Block MAGENTA_PLANK_DOOR = register("magenta_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_MAGENTA)));
    public static final Block MAGENTA_PLANK_TRAPDOOR = register("magenta_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_MAGENTA)));
    public static final Block MAGENTA_PLANK_SIGN = register("magenta_plank_sign", new WoodYouDyeSignBlock(MAGENTA_WOOD, commonSettings().mapColor(MapColor.COLOR_MAGENTA)));
    public static final Block MAGENTA_PLANK_WALL_SIGN = register("magenta_plank_wall_sign", new WoodYouDyeWallSignBlock(MAGENTA_WOOD, commonSettings().mapColor(MapColor.COLOR_MAGENTA)));
    public static final Block MAGENTA_PLANK_HANGING_SIGN = register("magenta_plank_hanging_sign", new WoodYouDyeHangingSignBlock(MAGENTA_WOOD, commonSettings().mapColor(MapColor.COLOR_MAGENTA)));
    public static final Block MAGENTA_PLANK_WALL_HANGING_SIGN = register("magenta_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(MAGENTA_WOOD, commonSettings().mapColor(MapColor.COLOR_MAGENTA)));

    public static final Block BROWN_PLANKS = register("brown_planks", new Block(commonSettings().mapColor(MapColor.COLOR_BROWN)));
    public static final Block BROWN_PLANK_SLAB = register("brown_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_BROWN)));
    public static final Block BROWN_PLANK_STAIRS = register("brown_plank_stairs", new WoodYouDyeStairBlock(BROWN_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_BROWN)));
    public static final Block BROWN_PLANK_FENCE = register("brown_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_BROWN)));
    public static final Block BROWN_PLANK_FENCE_GATE = register("brown_plank_fence_gate", new WoodYouDyeFenceGateBlock(BROWN_WOOD, commonSettings().mapColor(MapColor.COLOR_BROWN)));
    public static final Block BROWN_PLANK_PRESSURE_PLATE = register("brown_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_BROWN)));
    public static final Block BROWN_PLANK_BUTTON = register("brown_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_BROWN)));
    public static final Block BROWN_PLANK_DOOR = register("brown_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_BROWN)));
    public static final Block BROWN_PLANK_TRAPDOOR = register("brown_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_BROWN)));
    public static final Block BROWN_PLANK_SIGN = register("brown_plank_sign", new WoodYouDyeSignBlock(BROWN_WOOD, commonSettings().mapColor(MapColor.COLOR_BROWN)));
    public static final Block BROWN_PLANK_WALL_SIGN = register("brown_plank_wall_sign", new WoodYouDyeWallSignBlock(BROWN_WOOD, commonSettings().mapColor(MapColor.COLOR_BROWN)));
    public static final Block BROWN_PLANK_HANGING_SIGN = register("brown_plank_hanging_sign", new WoodYouDyeHangingSignBlock(BROWN_WOOD, commonSettings().mapColor(MapColor.COLOR_BROWN)));
    public static final Block BROWN_PLANK_WALL_HANGING_SIGN = register("brown_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(BROWN_WOOD, commonSettings().mapColor(MapColor.COLOR_BROWN)));

    public static final Block WHITE_PLANKS = register("white_planks", new Block(commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Block WHITE_PLANK_SLAB = register("white_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Block WHITE_PLANK_STAIRS = register("white_plank_stairs", new WoodYouDyeStairBlock(WHITE_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Block WHITE_PLANK_FENCE = register("white_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Block WHITE_PLANK_FENCE_GATE = register("white_plank_fence_gate", new WoodYouDyeFenceGateBlock(WHITE_WOOD, commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Block WHITE_PLANK_PRESSURE_PLATE = register("white_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Block WHITE_PLANK_BUTTON = register("white_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Block WHITE_PLANK_DOOR = register("white_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Block WHITE_PLANK_TRAPDOOR = register("white_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Block WHITE_PLANK_SIGN = register("white_plank_sign", new WoodYouDyeSignBlock(WHITE_WOOD, commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Block WHITE_PLANK_WALL_SIGN = register("white_plank_wall_sign", new WoodYouDyeWallSignBlock(WHITE_WOOD, commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Block WHITE_PLANK_HANGING_SIGN = register("white_plank_hanging_sign", new WoodYouDyeHangingSignBlock(WHITE_WOOD, commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Block WHITE_PLANK_WALL_HANGING_SIGN = register("white_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(WHITE_WOOD, commonSettings().mapColor(MapColor.TERRACOTTA_WHITE)));

    public static final Block LIGHT_GRAY_PLANKS = register("light_gray_planks", new Block(commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Block LIGHT_GRAY_PLANK_SLAB = register("light_gray_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Block LIGHT_GRAY_PLANK_STAIRS = register("light_gray_plank_stairs", new WoodYouDyeStairBlock(LIGHT_GRAY_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Block LIGHT_GRAY_PLANK_FENCE = register("light_gray_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Block LIGHT_GRAY_PLANK_FENCE_GATE = register("light_gray_plank_fence_gate", new WoodYouDyeFenceGateBlock(LIGHT_GRAY_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Block LIGHT_GRAY_PLANK_PRESSURE_PLATE = register("light_gray_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Block LIGHT_GRAY_PLANK_BUTTON = register("light_gray_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Block LIGHT_GRAY_PLANK_DOOR = register("light_gray_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Block LIGHT_GRAY_PLANK_TRAPDOOR = register("light_gray_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Block LIGHT_GRAY_PLANK_SIGN = register("light_gray_plank_sign", new WoodYouDyeSignBlock(LIGHT_GRAY_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Block LIGHT_GRAY_PLANK_WALL_SIGN = register("light_gray_plank_wall_sign", new WoodYouDyeWallSignBlock(LIGHT_GRAY_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Block LIGHT_GRAY_PLANK_HANGING_SIGN = register("light_gray_plank_hanging_sign", new WoodYouDyeHangingSignBlock(LIGHT_GRAY_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Block LIGHT_GRAY_PLANK_WALL_HANGING_SIGN = register("light_gray_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(LIGHT_GRAY_WOOD, commonSettings().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final Block GRAY_PLANKS = register("gray_planks", new Block(commonSettings().mapColor(MapColor.COLOR_GRAY)));
    public static final Block GRAY_PLANK_SLAB = register("gray_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_GRAY)));
    public static final Block GRAY_PLANK_STAIRS = register("gray_plank_stairs", new WoodYouDyeStairBlock(GRAY_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_GRAY)));
    public static final Block GRAY_PLANK_FENCE = register("gray_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_GRAY)));
    public static final Block GRAY_PLANK_FENCE_GATE = register("gray_plank_fence_gate", new WoodYouDyeFenceGateBlock(GRAY_WOOD, commonSettings().mapColor(MapColor.COLOR_GRAY)));
    public static final Block GRAY_PLANK_PRESSURE_PLATE = register("gray_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_GRAY)));
    public static final Block GRAY_PLANK_BUTTON = register("gray_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_GRAY)));
    public static final Block GRAY_PLANK_DOOR = register("gray_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_GRAY)));
    public static final Block GRAY_PLANK_TRAPDOOR = register("gray_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_GRAY)));
    public static final Block GRAY_PLANK_SIGN = register("gray_plank_sign", new WoodYouDyeSignBlock(GRAY_WOOD, commonSettings().mapColor(MapColor.COLOR_GRAY)));
    public static final Block GRAY_PLANK_WALL_SIGN = register("gray_plank_wall_sign", new WoodYouDyeWallSignBlock(GRAY_WOOD, commonSettings().mapColor(MapColor.COLOR_GRAY)));
    public static final Block GRAY_PLANK_HANGING_SIGN = register("gray_plank_hanging_sign", new WoodYouDyeHangingSignBlock(GRAY_WOOD, commonSettings().mapColor(MapColor.COLOR_GRAY)));
    public static final Block GRAY_PLANK_WALL_HANGING_SIGN = register("gray_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(GRAY_WOOD,commonSettings().mapColor(MapColor.COLOR_GRAY)));

    public static final Block BLACK_PLANKS = register("black_planks", new Block(commonSettings().mapColor(MapColor.COLOR_BLACK)));
    public static final Block BLACK_PLANK_SLAB = register("black_plank_slab", new SlabBlock(commonSettings().mapColor(MapColor.COLOR_BLACK)));
    public static final Block BLACK_PLANK_STAIRS = register("black_plank_stairs", new WoodYouDyeStairBlock(BLACK_PLANKS.defaultBlockState(), commonSettings().mapColor(MapColor.COLOR_BLACK)));
    public static final Block BLACK_PLANK_FENCE = register("black_plank_fence", new FenceBlock(commonSettings().mapColor(MapColor.COLOR_BLACK)));
    public static final Block BLACK_PLANK_FENCE_GATE = register("black_plank_fence_gate", new WoodYouDyeFenceGateBlock(BLACK_WOOD,commonSettings().mapColor(MapColor.COLOR_BLACK)));
    public static final Block BLACK_PLANK_PRESSURE_PLATE = register("black_plank_pressure_plate", new WoodYouDyePressurePlateBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLACK)));
    public static final Block BLACK_PLANK_BUTTON = register("black_plank_button", new WoodYouDyeWoodenButtonBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLACK)));
    public static final Block BLACK_PLANK_DOOR = register("black_plank_door", new WoodYouDyeDoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLACK)));
    public static final Block BLACK_PLANK_TRAPDOOR = register("black_plank_trapdoor", new WoodYouDyeTrapdoorBlock(WOOD_YOU_DYE_WOOD, commonSettings().mapColor(MapColor.COLOR_BLACK)));
    public static final Block BLACK_PLANK_SIGN = register("black_plank_sign", new WoodYouDyeSignBlock(BLACK_WOOD, commonSettings().mapColor(MapColor.COLOR_BLACK)));
    public static final Block BLACK_PLANK_WALL_SIGN = register("black_plank_wall_sign", new WoodYouDyeWallSignBlock(BLACK_WOOD, commonSettings().mapColor(MapColor.COLOR_BLACK)));
    public static final Block BLACK_PLANK_HANGING_SIGN = register("black_plank_hanging_sign", new WoodYouDyeHangingSignBlock(BLACK_WOOD, commonSettings().mapColor(MapColor.COLOR_BLACK)));
    public static final Block BLACK_PLANK_WALL_HANGING_SIGN = register("black_plank_wall_hanging_sign", new WoodYouDyeHangingWallSignBlock(BLACK_WOOD, commonSettings().mapColor(MapColor.COLOR_BLACK)));

    public static String dumpedIds = "Dumped IDs:\n";

    public static Block register(String id, Block block) {
        blockHolder.add(block);
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", id), block);
    }

    public static void init() {}

    public static BlockBehaviour.Properties commonSettings() {
        return BlockBehaviour.Properties.of().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0f).ignitedByLava();
    }
}

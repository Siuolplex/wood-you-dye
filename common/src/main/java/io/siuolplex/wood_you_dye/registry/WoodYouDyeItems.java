package io.siuolplex.wood_you_dye.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.LinkedHashSet;
import java.util.Set;

public class WoodYouDyeItems {
    public static Set<Item> itemGroupHolder = new LinkedHashSet<>();
    /*public static final Item RED_PLANKS = register("red_planks", new BlockItem(WoodYouDyeBlocks.RED_PLANKS, sharedItemSettings()));
    public static final Item RED_PLANK_SLAB = register("red_plank_slab", new BlockItem(WoodYouDyeBlocks.RED_PLANK_SLAB, sharedItemSettings()));
    public static final Item RED_PLANK_STAIRS = register("red_plank_stairs", new BlockItem(WoodYouDyeBlocks.RED_PLANK_STAIRS, sharedItemSettings()));
    public static final Item RED_PLANK_FENCE = register("red_plank_fence", new BlockItem(WoodYouDyeBlocks.RED_PLANK_FENCE, sharedItemSettings()));
    public static final Item RED_PLANK_FENCE_GATE = register("red_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.RED_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item RED_PLANK_BUTTON = register("red_plank_button", new BlockItem(WoodYouDyeBlocks.RED_PLANK_BUTTON, sharedItemSettings()));
    public static final Item RED_PLANK_PRESSURE_PLATE = register("red_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.RED_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item RED_PLANK_DOOR = register("red_plank_door", new BlockItem(WoodYouDyeBlocks.RED_PLANK_DOOR, sharedItemSettings()));
    public static final Item RED_PLANK_TRAPDOOR = register("red_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.RED_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item RED_PLANK_SIGN = register("red_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.RED_PLANK_SIGN, WoodYouDyeBlocks.RED_PLANK_WALL_SIGN));
    public static final Item RED_PLANK_HANGING_SIGN = register("red_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.RED_PLANK_HANGING_SIGN, WoodYouDyeBlocks.RED_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item ORANGE_PLANKS = register("orange_planks", new BlockItem(WoodYouDyeBlocks.ORANGE_PLANKS, sharedItemSettings()));
    public static final Item ORANGE_PLANK_SLAB = register("orange_plank_slab", new BlockItem(WoodYouDyeBlocks.ORANGE_PLANK_SLAB, sharedItemSettings()));
    public static final Item ORANGE_PLANK_STAIRS = register("orange_plank_stairs", new BlockItem(WoodYouDyeBlocks.ORANGE_PLANK_STAIRS, sharedItemSettings()));
    public static final Item ORANGE_PLANK_FENCE = register("orange_plank_fence", new BlockItem(WoodYouDyeBlocks.ORANGE_PLANK_FENCE, sharedItemSettings()));
    public static final Item ORANGE_PLANK_FENCE_GATE = register("orange_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.ORANGE_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item ORANGE_PLANK_BUTTON = register("orange_plank_button", new BlockItem(WoodYouDyeBlocks.ORANGE_PLANK_BUTTON, sharedItemSettings()));
    public static final Item ORANGE_PLANK_PRESSURE_PLATE = register("orange_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.ORANGE_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item ORANGE_PLANK_DOOR = register("orange_plank_door", new BlockItem(WoodYouDyeBlocks.ORANGE_PLANK_DOOR, sharedItemSettings()));
    public static final Item ORANGE_PLANK_TRAPDOOR = register("orange_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.ORANGE_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item ORANGE_PLANK_SIGN = register("orange_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.ORANGE_PLANK_SIGN, WoodYouDyeBlocks.ORANGE_PLANK_WALL_SIGN));
    public static final Item ORANGE_PLANK_HANGING_SIGN = register("orange_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.ORANGE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.ORANGE_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item YELLOW_PLANKS = register("yellow_planks", new BlockItem(WoodYouDyeBlocks.YELLOW_PLANKS, sharedItemSettings()));
    public static final Item YELLOW_PLANK_SLAB = register("yellow_plank_slab", new BlockItem(WoodYouDyeBlocks.YELLOW_PLANK_SLAB, sharedItemSettings()));
    public static final Item YELLOW_PLANK_STAIRS = register("yellow_plank_stairs", new BlockItem(WoodYouDyeBlocks.YELLOW_PLANK_STAIRS, sharedItemSettings()));
    public static final Item YELLOW_PLANK_FENCE = register("yellow_plank_fence", new BlockItem(WoodYouDyeBlocks.YELLOW_PLANK_FENCE, sharedItemSettings()));
    public static final Item YELLOW_PLANK_FENCE_GATE = register("yellow_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.YELLOW_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item YELLOW_PLANK_BUTTON = register("yellow_plank_button", new BlockItem(WoodYouDyeBlocks.YELLOW_PLANK_BUTTON, sharedItemSettings()));
    public static final Item YELLOW_PLANK_PRESSURE_PLATE = register("yellow_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.YELLOW_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item YELLOW_PLANK_DOOR = register("yellow_plank_door", new BlockItem(WoodYouDyeBlocks.YELLOW_PLANK_DOOR, sharedItemSettings()));
    public static final Item YELLOW_PLANK_TRAPDOOR = register("yellow_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.YELLOW_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item YELLOW_PLANK_SIGN = register("yellow_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.YELLOW_PLANK_SIGN, WoodYouDyeBlocks.YELLOW_PLANK_WALL_SIGN));
    public static final Item YELLOW_PLANK_HANGING_SIGN = register("yellow_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.YELLOW_PLANK_HANGING_SIGN, WoodYouDyeBlocks.YELLOW_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item LIME_PLANKS = register("lime_planks", new BlockItem(WoodYouDyeBlocks.LIME_PLANKS, sharedItemSettings()));
    public static final Item LIME_PLANK_SLAB = register("lime_plank_slab", new BlockItem(WoodYouDyeBlocks.LIME_PLANK_SLAB, sharedItemSettings()));
    public static final Item LIME_PLANK_STAIRS = register("lime_plank_stairs", new BlockItem(WoodYouDyeBlocks.LIME_PLANK_STAIRS, sharedItemSettings()));
    public static final Item LIME_PLANK_FENCE = register("lime_plank_fence", new BlockItem(WoodYouDyeBlocks.LIME_PLANK_FENCE, sharedItemSettings()));
    public static final Item LIME_PLANK_FENCE_GATE = register("lime_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.LIME_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item LIME_PLANK_BUTTON = register("lime_plank_button", new BlockItem(WoodYouDyeBlocks.LIME_PLANK_BUTTON, sharedItemSettings()));
    public static final Item LIME_PLANK_PRESSURE_PLATE = register("lime_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.LIME_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item LIME_PLANK_DOOR = register("lime_plank_door", new BlockItem(WoodYouDyeBlocks.LIME_PLANK_DOOR, sharedItemSettings()));
    public static final Item LIME_PLANK_TRAPDOOR = register("lime_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.LIME_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item LIME_PLANK_SIGN = register("lime_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.LIME_PLANK_SIGN, WoodYouDyeBlocks.LIME_PLANK_WALL_SIGN));
    public static final Item LIME_PLANK_HANGING_SIGN = register("lime_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.LIME_PLANK_HANGING_SIGN, WoodYouDyeBlocks.LIME_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item GREEN_PLANKS = register("green_planks", new BlockItem(WoodYouDyeBlocks.GREEN_PLANKS, sharedItemSettings()));
    public static final Item GREEN_PLANK_SLAB = register("green_plank_slab", new BlockItem(WoodYouDyeBlocks.GREEN_PLANK_SLAB, sharedItemSettings()));
    public static final Item GREEN_PLANK_STAIRS = register("green_plank_stairs", new BlockItem(WoodYouDyeBlocks.GREEN_PLANK_STAIRS, sharedItemSettings()));
    public static final Item GREEN_PLANK_FENCE = register("green_plank_fence", new BlockItem(WoodYouDyeBlocks.GREEN_PLANK_FENCE, sharedItemSettings()));
    public static final Item GREEN_PLANK_FENCE_GATE = register("green_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.GREEN_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item GREEN_PLANK_BUTTON = register("green_plank_button", new BlockItem(WoodYouDyeBlocks.GREEN_PLANK_BUTTON, sharedItemSettings()));
    public static final Item GREEN_PLANK_PRESSURE_PLATE = register("green_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.GREEN_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item GREEN_PLANK_DOOR = register("green_plank_door", new BlockItem(WoodYouDyeBlocks.GREEN_PLANK_DOOR, sharedItemSettings()));
    public static final Item GREEN_PLANK_TRAPDOOR = register("green_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.GREEN_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item GREEN_PLANK_SIGN = register("green_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.GREEN_PLANK_SIGN, WoodYouDyeBlocks.GREEN_PLANK_WALL_SIGN));
    public static final Item GREEN_PLANK_HANGING_SIGN = register("green_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.GREEN_PLANK_HANGING_SIGN, WoodYouDyeBlocks.GREEN_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item BLUE_PLANKS = register("blue_planks", new BlockItem(WoodYouDyeBlocks.BLUE_PLANKS, sharedItemSettings()));
    public static final Item BLUE_PLANK_SLAB = register("blue_plank_slab", new BlockItem(WoodYouDyeBlocks.BLUE_PLANK_SLAB, sharedItemSettings()));
    public static final Item BLUE_PLANK_STAIRS = register("blue_plank_stairs", new BlockItem(WoodYouDyeBlocks.BLUE_PLANK_STAIRS, sharedItemSettings()));
    public static final Item BLUE_PLANK_FENCE = register("blue_plank_fence", new BlockItem(WoodYouDyeBlocks.BLUE_PLANK_FENCE, sharedItemSettings()));
    public static final Item BLUE_PLANK_FENCE_GATE = register("blue_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.BLUE_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item BLUE_PLANK_BUTTON = register("blue_plank_button", new BlockItem(WoodYouDyeBlocks.BLUE_PLANK_BUTTON, sharedItemSettings()));
    public static final Item BLUE_PLANK_PRESSURE_PLATE = register("blue_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.BLUE_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item BLUE_PLANK_DOOR = register("blue_plank_door", new BlockItem(WoodYouDyeBlocks.BLUE_PLANK_DOOR, sharedItemSettings()));
    public static final Item BLUE_PLANK_TRAPDOOR = register("blue_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.BLUE_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item BLUE_PLANK_SIGN = register("blue_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.BLUE_PLANK_SIGN, WoodYouDyeBlocks.BLUE_PLANK_WALL_SIGN));
    public static final Item BLUE_PLANK_HANGING_SIGN = register("blue_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.BLUE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.BLUE_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item CYAN_PLANKS = register("cyan_planks", new BlockItem(WoodYouDyeBlocks.CYAN_PLANKS, sharedItemSettings()));
    public static final Item CYAN_PLANK_SLAB = register("cyan_plank_slab", new BlockItem(WoodYouDyeBlocks.CYAN_PLANK_SLAB, sharedItemSettings()));
    public static final Item CYAN_PLANK_STAIRS = register("cyan_plank_stairs", new BlockItem(WoodYouDyeBlocks.CYAN_PLANK_STAIRS, sharedItemSettings()));
    public static final Item CYAN_PLANK_FENCE = register("cyan_plank_fence", new BlockItem(WoodYouDyeBlocks.CYAN_PLANK_FENCE, sharedItemSettings()));
    public static final Item CYAN_PLANK_FENCE_GATE = register("cyan_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.CYAN_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item CYAN_PLANK_BUTTON = register("cyan_plank_button", new BlockItem(WoodYouDyeBlocks.CYAN_PLANK_BUTTON, sharedItemSettings()));
    public static final Item CYAN_PLANK_PRESSURE_PLATE = register("cyan_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.CYAN_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item CYAN_PLANK_DOOR = register("cyan_plank_door", new BlockItem(WoodYouDyeBlocks.CYAN_PLANK_DOOR, sharedItemSettings()));
    public static final Item CYAN_PLANK_TRAPDOOR = register("cyan_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.CYAN_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item CYAN_PLANK_SIGN = register("cyan_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.CYAN_PLANK_SIGN, WoodYouDyeBlocks.CYAN_PLANK_WALL_SIGN));
    public static final Item CYAN_PLANK_HANGING_SIGN = register("cyan_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.CYAN_PLANK_HANGING_SIGN, WoodYouDyeBlocks.CYAN_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item LIGHT_BLUE_PLANKS = register("light_blue_planks", new BlockItem(WoodYouDyeBlocks.LIGHT_BLUE_PLANKS, sharedItemSettings()));
    public static final Item LIGHT_BLUE_PLANK_SLAB = register("light_blue_plank_slab", new BlockItem(WoodYouDyeBlocks.LIGHT_BLUE_PLANK_SLAB, sharedItemSettings()));
    public static final Item LIGHT_BLUE_PLANK_STAIRS = register("light_blue_plank_stairs", new BlockItem(WoodYouDyeBlocks.LIGHT_BLUE_PLANK_STAIRS, sharedItemSettings()));
    public static final Item LIGHT_BLUE_PLANK_FENCE = register("light_blue_plank_fence", new BlockItem(WoodYouDyeBlocks.LIGHT_BLUE_PLANK_FENCE, sharedItemSettings()));
    public static final Item LIGHT_BLUE_PLANK_FENCE_GATE = register("light_blue_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.LIGHT_BLUE_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item LIGHT_BLUE_PLANK_BUTTON = register("light_blue_plank_button", new BlockItem(WoodYouDyeBlocks.LIGHT_BLUE_PLANK_BUTTON, sharedItemSettings()));
    public static final Item LIGHT_BLUE_PLANK_PRESSURE_PLATE = register("light_blue_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.LIGHT_BLUE_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item LIGHT_BLUE_PLANK_DOOR = register("light_blue_plank_door", new BlockItem(WoodYouDyeBlocks.LIGHT_BLUE_PLANK_DOOR, sharedItemSettings()));
    public static final Item LIGHT_BLUE_PLANK_TRAPDOOR = register("light_blue_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.LIGHT_BLUE_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item LIGHT_BLUE_PLANK_SIGN = register("light_blue_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.LIGHT_BLUE_PLANK_SIGN, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_WALL_SIGN));
    public static final Item LIGHT_BLUE_PLANK_HANGING_SIGN = register("light_blue_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.LIGHT_BLUE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item PURPLE_PLANKS = register("purple_planks", new BlockItem(WoodYouDyeBlocks.PURPLE_PLANKS, sharedItemSettings()));
    public static final Item PURPLE_PLANK_SLAB = register("purple_plank_slab", new BlockItem(WoodYouDyeBlocks.PURPLE_PLANK_SLAB, sharedItemSettings()));
    public static final Item PURPLE_PLANK_STAIRS = register("purple_plank_stairs", new BlockItem(WoodYouDyeBlocks.PURPLE_PLANK_STAIRS, sharedItemSettings()));
    public static final Item PURPLE_PLANK_FENCE = register("purple_plank_fence", new BlockItem(WoodYouDyeBlocks.PURPLE_PLANK_FENCE, sharedItemSettings()));
    public static final Item PURPLE_PLANK_FENCE_GATE = register("purple_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.PURPLE_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item PURPLE_PLANK_BUTTON = register("purple_plank_button", new BlockItem(WoodYouDyeBlocks.PURPLE_PLANK_BUTTON, sharedItemSettings()));
    public static final Item PURPLE_PLANK_PRESSURE_PLATE = register("purple_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.PURPLE_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item PURPLE_PLANK_DOOR = register("purple_plank_door", new BlockItem(WoodYouDyeBlocks.PURPLE_PLANK_DOOR, sharedItemSettings()));
    public static final Item PURPLE_PLANK_TRAPDOOR = register("purple_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.PURPLE_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item PURPLE_PLANK_SIGN = register("purple_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.PURPLE_PLANK_SIGN, WoodYouDyeBlocks.PURPLE_PLANK_WALL_SIGN));
    public static final Item PURPLE_PLANK_HANGING_SIGN = register("purple_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.PURPLE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.PURPLE_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item PINK_PLANKS = register("pink_planks", new BlockItem(WoodYouDyeBlocks.PINK_PLANKS, sharedItemSettings()));
    public static final Item PINK_PLANK_SLAB = register("pink_plank_slab", new BlockItem(WoodYouDyeBlocks.PINK_PLANK_SLAB, sharedItemSettings()));
    public static final Item PINK_PLANK_STAIRS = register("pink_plank_stairs", new BlockItem(WoodYouDyeBlocks.PINK_PLANK_STAIRS, sharedItemSettings()));
    public static final Item PINK_PLANK_FENCE = register("pink_plank_fence", new BlockItem(WoodYouDyeBlocks.PINK_PLANK_FENCE, sharedItemSettings()));
    public static final Item PINK_PLANK_FENCE_GATE = register("pink_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.PINK_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item PINK_PLANK_BUTTON = register("pink_plank_button", new BlockItem(WoodYouDyeBlocks.PINK_PLANK_BUTTON, sharedItemSettings()));
    public static final Item PINK_PLANK_PRESSURE_PLATE = register("pink_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.PINK_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item PINK_PLANK_DOOR = register("pink_plank_door", new BlockItem(WoodYouDyeBlocks.PINK_PLANK_DOOR, sharedItemSettings()));
    public static final Item PINK_PLANK_TRAPDOOR = register("pink_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.PINK_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item PINK_PLANK_SIGN = register("pink_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.PINK_PLANK_SIGN, WoodYouDyeBlocks.PINK_PLANK_WALL_SIGN));
    public static final Item PINK_PLANK_HANGING_SIGN = register("pink_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.PINK_PLANK_HANGING_SIGN, WoodYouDyeBlocks.PINK_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item MAGENTA_PLANKS = register("magenta_planks", new BlockItem(WoodYouDyeBlocks.MAGENTA_PLANKS, sharedItemSettings()));
    public static final Item MAGENTA_PLANK_SLAB = register("magenta_plank_slab", new BlockItem(WoodYouDyeBlocks.MAGENTA_PLANK_SLAB, sharedItemSettings()));
    public static final Item MAGENTA_PLANK_STAIRS = register("magenta_plank_stairs", new BlockItem(WoodYouDyeBlocks.MAGENTA_PLANK_STAIRS, sharedItemSettings()));
    public static final Item MAGENTA_PLANK_FENCE = register("magenta_plank_fence", new BlockItem(WoodYouDyeBlocks.MAGENTA_PLANK_FENCE, sharedItemSettings()));
    public static final Item MAGENTA_PLANK_FENCE_GATE = register("magenta_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.MAGENTA_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item MAGENTA_PLANK_BUTTON = register("magenta_plank_button", new BlockItem(WoodYouDyeBlocks.MAGENTA_PLANK_BUTTON, sharedItemSettings()));
    public static final Item MAGENTA_PLANK_PRESSURE_PLATE = register("magenta_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.MAGENTA_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item MAGENTA_PLANK_DOOR = register("magenta_plank_door", new BlockItem(WoodYouDyeBlocks.MAGENTA_PLANK_DOOR, sharedItemSettings()));
    public static final Item MAGENTA_PLANK_TRAPDOOR = register("magenta_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.MAGENTA_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item MAGENTA_PLANK_SIGN = register("magenta_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.MAGENTA_PLANK_SIGN, WoodYouDyeBlocks.MAGENTA_PLANK_WALL_SIGN));
    public static final Item MAGENTA_PLANK_HANGING_SIGN = register("magenta_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.MAGENTA_PLANK_HANGING_SIGN, WoodYouDyeBlocks.MAGENTA_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item BROWN_PLANKS = register("brown_planks", new BlockItem(WoodYouDyeBlocks.BROWN_PLANKS, sharedItemSettings()));
    public static final Item BROWN_PLANK_SLAB = register("brown_plank_slab", new BlockItem(WoodYouDyeBlocks.BROWN_PLANK_SLAB, sharedItemSettings()));
    public static final Item BROWN_PLANK_STAIRS = register("brown_plank_stairs", new BlockItem(WoodYouDyeBlocks.BROWN_PLANK_STAIRS, sharedItemSettings()));
    public static final Item BROWN_PLANK_FENCE = register("brown_plank_fence", new BlockItem(WoodYouDyeBlocks.BROWN_PLANK_FENCE, sharedItemSettings()));
    public static final Item BROWN_PLANK_FENCE_GATE = register("brown_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.BROWN_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item BROWN_PLANK_BUTTON = register("brown_plank_button", new BlockItem(WoodYouDyeBlocks.BROWN_PLANK_BUTTON, sharedItemSettings()));
    public static final Item BROWN_PLANK_PRESSURE_PLATE = register("brown_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.BROWN_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item BROWN_PLANK_DOOR = register("brown_plank_door", new BlockItem(WoodYouDyeBlocks.BROWN_PLANK_DOOR, sharedItemSettings()));
    public static final Item BROWN_PLANK_TRAPDOOR = register("brown_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.BROWN_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item BROWN_PLANK_SIGN = register("brown_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.BROWN_PLANK_SIGN, WoodYouDyeBlocks.BROWN_PLANK_WALL_SIGN));
    public static final Item BROWN_PLANK_HANGING_SIGN = register("brown_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.BROWN_PLANK_HANGING_SIGN, WoodYouDyeBlocks.BROWN_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item WHITE_PLANKS = register("white_planks", new BlockItem(WoodYouDyeBlocks.WHITE_PLANKS, sharedItemSettings()));
    public static final Item WHITE_PLANK_SLAB = register("white_plank_slab", new BlockItem(WoodYouDyeBlocks.WHITE_PLANK_SLAB, sharedItemSettings()));
    public static final Item WHITE_PLANK_STAIRS = register("white_plank_stairs", new BlockItem(WoodYouDyeBlocks.WHITE_PLANK_STAIRS, sharedItemSettings()));
    public static final Item WHITE_PLANK_FENCE = register("white_plank_fence", new BlockItem(WoodYouDyeBlocks.WHITE_PLANK_FENCE, sharedItemSettings()));
    public static final Item WHITE_PLANK_FENCE_GATE = register("white_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.WHITE_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item WHITE_PLANK_BUTTON = register("white_plank_button", new BlockItem(WoodYouDyeBlocks.WHITE_PLANK_BUTTON, sharedItemSettings()));
    public static final Item WHITE_PLANK_PRESSURE_PLATE = register("white_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.WHITE_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item WHITE_PLANK_DOOR = register("white_plank_door", new BlockItem(WoodYouDyeBlocks.WHITE_PLANK_DOOR, sharedItemSettings()));
    public static final Item WHITE_PLANK_TRAPDOOR = register("white_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.WHITE_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item WHITE_PLANK_SIGN = register("white_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.WHITE_PLANK_SIGN, WoodYouDyeBlocks.WHITE_PLANK_WALL_SIGN));
    public static final Item WHITE_PLANK_HANGING_SIGN = register("white_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.WHITE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.WHITE_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item LIGHT_GRAY_PLANKS = register("light_gray_planks", new BlockItem(WoodYouDyeBlocks.LIGHT_GRAY_PLANKS, sharedItemSettings()));
    public static final Item LIGHT_GRAY_PLANK_SLAB = register("light_gray_plank_slab", new BlockItem(WoodYouDyeBlocks.LIGHT_GRAY_PLANK_SLAB, sharedItemSettings()));
    public static final Item LIGHT_GRAY_PLANK_STAIRS = register("light_gray_plank_stairs", new BlockItem(WoodYouDyeBlocks.LIGHT_GRAY_PLANK_STAIRS, sharedItemSettings()));
    public static final Item LIGHT_GRAY_PLANK_FENCE = register("light_gray_plank_fence", new BlockItem(WoodYouDyeBlocks.LIGHT_GRAY_PLANK_FENCE, sharedItemSettings()));
    public static final Item LIGHT_GRAY_PLANK_FENCE_GATE = register("light_gray_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.LIGHT_GRAY_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item LIGHT_GRAY_PLANK_BUTTON = register("light_gray_plank_button", new BlockItem(WoodYouDyeBlocks.LIGHT_GRAY_PLANK_BUTTON, sharedItemSettings()));
    public static final Item LIGHT_GRAY_PLANK_PRESSURE_PLATE = register("light_gray_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.LIGHT_GRAY_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item LIGHT_GRAY_PLANK_DOOR = register("light_gray_plank_door", new BlockItem(WoodYouDyeBlocks.LIGHT_GRAY_PLANK_DOOR, sharedItemSettings()));
    public static final Item LIGHT_GRAY_PLANK_TRAPDOOR = register("light_gray_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.LIGHT_GRAY_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item LIGHT_GRAY_PLANK_SIGN = register("light_gray_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.LIGHT_GRAY_PLANK_SIGN, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_WALL_SIGN));
    public static final Item LIGHT_GRAY_PLANK_HANGING_SIGN = register("light_gray_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.LIGHT_GRAY_PLANK_HANGING_SIGN, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item GRAY_PLANKS = register("gray_planks", new BlockItem(WoodYouDyeBlocks.GRAY_PLANKS, sharedItemSettings()));
    public static final Item GRAY_PLANK_SLAB = register("gray_plank_slab", new BlockItem(WoodYouDyeBlocks.GRAY_PLANK_SLAB, sharedItemSettings()));
    public static final Item GRAY_PLANK_STAIRS = register("gray_plank_stairs", new BlockItem(WoodYouDyeBlocks.GRAY_PLANK_STAIRS, sharedItemSettings()));
    public static final Item GRAY_PLANK_FENCE = register("gray_plank_fence", new BlockItem(WoodYouDyeBlocks.GRAY_PLANK_FENCE, sharedItemSettings()));
    public static final Item GRAY_PLANK_FENCE_GATE = register("gray_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.GRAY_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item GRAY_PLANK_BUTTON = register("gray_plank_button", new BlockItem(WoodYouDyeBlocks.GRAY_PLANK_BUTTON, sharedItemSettings()));
    public static final Item GRAY_PLANK_PRESSURE_PLATE = register("gray_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.GRAY_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item GRAY_PLANK_DOOR = register("gray_plank_door", new BlockItem(WoodYouDyeBlocks.GRAY_PLANK_DOOR, sharedItemSettings()));
    public static final Item GRAY_PLANK_TRAPDOOR = register("gray_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.GRAY_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item GRAY_PLANK_SIGN = register("gray_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.GRAY_PLANK_SIGN, WoodYouDyeBlocks.GRAY_PLANK_WALL_SIGN));
    public static final Item GRAY_PLANK_HANGING_SIGN = register("gray_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.GRAY_PLANK_HANGING_SIGN, WoodYouDyeBlocks.GRAY_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));

    public static final Item BLACK_PLANKS = register("black_planks", new BlockItem(WoodYouDyeBlocks.BLACK_PLANKS, sharedItemSettings()));
    public static final Item BLACK_PLANK_SLAB = register("black_plank_slab", new BlockItem(WoodYouDyeBlocks.BLACK_PLANK_SLAB, sharedItemSettings()));
    public static final Item BLACK_PLANK_STAIRS = register("black_plank_stairs", new BlockItem(WoodYouDyeBlocks.BLACK_PLANK_STAIRS, sharedItemSettings()));
    public static final Item BLACK_PLANK_FENCE = register("black_plank_fence", new BlockItem(WoodYouDyeBlocks.BLACK_PLANK_FENCE, sharedItemSettings()));
    public static final Item BLACK_PLANK_FENCE_GATE = register("black_plank_fence_gate", new BlockItem(WoodYouDyeBlocks.BLACK_PLANK_FENCE_GATE, sharedItemSettings()));
    public static final Item BLACK_PLANK_BUTTON = register("black_plank_button", new BlockItem(WoodYouDyeBlocks.BLACK_PLANK_BUTTON, sharedItemSettings()));
    public static final Item BLACK_PLANK_PRESSURE_PLATE = register("black_plank_pressure_plate", new BlockItem(WoodYouDyeBlocks.BLACK_PLANK_PRESSURE_PLATE, sharedItemSettings()));
    public static final Item BLACK_PLANK_DOOR = register("black_plank_door", new BlockItem(WoodYouDyeBlocks.BLACK_PLANK_DOOR, sharedItemSettings()));
    public static final Item BLACK_PLANK_TRAPDOOR = register("black_plank_trapdoor", new BlockItem(WoodYouDyeBlocks.BLACK_PLANK_TRAPDOOR, sharedItemSettings()));
    public static final Item BLACK_PLANK_SIGN = register("black_plank_sign", new SignItem(sharedItemSettings(), WoodYouDyeBlocks.BLACK_PLANK_SIGN, WoodYouDyeBlocks.BLACK_PLANK_WALL_SIGN));
    public static final Item BLACK_PLANK_HANGING_SIGN = register("black_plank_hanging_sign", new HangingSignItem(WoodYouDyeBlocks.BLACK_PLANK_HANGING_SIGN, WoodYouDyeBlocks.BLACK_PLANK_WALL_HANGING_SIGN, sharedItemSettings()));
     */
    public static Item.Properties sharedItemSettings() {
        return new Item.Properties();
    }

    public static Item register(String id, Item item) {
        itemGroupHolder.add(item);
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", id), item);
    }

    public static void init() {}
}

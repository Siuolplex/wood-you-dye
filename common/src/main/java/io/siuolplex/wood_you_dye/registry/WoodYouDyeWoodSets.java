package io.siuolplex.wood_you_dye.registry;

import io.siuolplex.gremlib.block.util.BlockSetTypeUtil;
import io.siuolplex.gremlib.block.util.WoodTypeUtil;
import io.siuolplex.gremlib.util.WoodSetInfo;
import io.siuolplex.wood_you_dye.AnotherWoodSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.ArrayList;
import java.util.List;

public class WoodYouDyeWoodSets {
    public static List<AnotherWoodSet> WOODSETS = new ArrayList<>();

    public static BlockSetType STANDARD_BLOCK_SET_TYPE = BlockSetTypeUtil.ofWood("standard");
    public static WoodType STANDARD_WOOD_TYPE = WoodTypeUtil.of("standard", STANDARD_BLOCK_SET_TYPE);
    public static AnotherWoodSet RED_DYED_WOOD = addWoodSet(new AnotherWoodSet.Builder("red_dyed")
            .setBlockSetType(STANDARD_BLOCK_SET_TYPE)
            .setWoodType(STANDARD_WOOD_TYPE)
            .setWoodDetail(WoodSetInfo.OVERWORLD)
            .setLogProperties(BlockBehaviour.Properties.of().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0f).ignitedByLava().mapColor(DyeColor.RED))
            .setPlankProperties(BlockBehaviour.Properties.of().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0f).ignitedByLava().mapColor(DyeColor.RED))
            .setItemProperties(new Item.Properties())
            .setVariantName("standard")
            .setPermutationName("red").build());

    public static AnotherWoodSet BLUE_DYED_WOOD = addWoodSet(new AnotherWoodSet.Builder("blue_dyed")
            .setBlockSetType(STANDARD_BLOCK_SET_TYPE)
            .setWoodType(STANDARD_WOOD_TYPE)
            .setWoodDetail(WoodSetInfo.OVERWORLD)
            .setLogProperties(BlockBehaviour.Properties.of().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0f).ignitedByLava().mapColor(DyeColor.BLUE))
            .setPlankProperties(BlockBehaviour.Properties.of().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0f).ignitedByLava().mapColor(DyeColor.BLUE))
            .setItemProperties(new Item.Properties())
            .setVariantName("standard")
            .setPermutationName("blue").build());

    public static AnotherWoodSet addWoodSet(AnotherWoodSet set) {;
        WOODSETS.add(set);
        return set;
    }

    public static void register(Registry<?> registry) {
        if (registry.key().equals(Registries.BLOCK)) {
            RED_DYED_WOOD.BLOCKS.setRegister();
        } else if (registry.key().equals(Registries.ITEM)) {
            RED_DYED_WOOD.ITEMS.setRegister();
        } else if (registry.key().equals(Registries.ENTITY_TYPE)) {
            RED_DYED_WOOD.ENTITIES.setRegister();
        }
    }
}

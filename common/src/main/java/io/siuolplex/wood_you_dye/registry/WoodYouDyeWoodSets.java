package io.siuolplex.wood_you_dye.registry;

import io.siuolplex.gremlib.block.util.BlockSetTypeUtil;
import io.siuolplex.gremlib.block.util.WoodTypeUtil;
import io.siuolplex.wood_you_dye.PrefixedWoodSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.ArrayList;
import java.util.List;

public class WoodYouDyeWoodSets {
    public static List<PrefixedWoodSet> WOODSETS = new ArrayList<>();

    public static BlockSetType STANDARD_BLOCK_SET_TYPE = BlockSetTypeUtil.ofWood("standard");
    public static WoodType STANDARD_WOOD_TYPE = WoodTypeUtil.of("standard", STANDARD_BLOCK_SET_TYPE);
    public static PrefixedWoodSet RED_DYED_WOOD = addWoodSet(new PrefixedWoodSet("wood_you_dye", "dyed_red", "standard",
            () -> BlockBehaviour.Properties.of().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0f).ignitedByLava().mapColor(DyeColor.RED),
            () -> BlockBehaviour.Properties.of().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0f).ignitedByLava().mapColor(DyeColor.RED),
            STANDARD_BLOCK_SET_TYPE,
            STANDARD_WOOD_TYPE
    ));

    public static PrefixedWoodSet addWoodSet(PrefixedWoodSet woodSet) {
        WOODSETS.add(woodSet);
        return woodSet;
    }

    public static void register(Registry<?> registry) {
        if (registry.key().equals(Registries.BLOCK)) {
            RED_DYED_WOOD.registerBlocks();
        }

        else if (registry.key().equals(Registries.ITEM)) {
            RED_DYED_WOOD.registerItems();
        }
    }
}

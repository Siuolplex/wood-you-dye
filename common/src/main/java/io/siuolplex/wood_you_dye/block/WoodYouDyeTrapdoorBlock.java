package io.siuolplex.wood_you_dye.block;

import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class WoodYouDyeTrapdoorBlock extends TrapDoorBlock {
    public WoodYouDyeTrapdoorBlock(BlockSetType type, BlockBehaviour.Properties settings) {
        super(settings.noOcclusion(), type);
    }
}

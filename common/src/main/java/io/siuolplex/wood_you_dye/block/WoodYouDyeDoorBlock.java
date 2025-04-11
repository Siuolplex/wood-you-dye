package io.siuolplex.wood_you_dye.block;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class WoodYouDyeDoorBlock extends DoorBlock {
    public WoodYouDyeDoorBlock(BlockSetType type, BlockBehaviour.Properties settings) {
        super(settings.noOcclusion(), type);
    }
}

package io.siuolplex.wood_you_dye.block;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class WoodYouDyePressurePlateBlock extends PressurePlateBlock {
    public WoodYouDyePressurePlateBlock(BlockSetType type, BlockBehaviour.Properties settings) {
        super(Sensitivity.EVERYTHING, settings.noCollission(), type);
    }
}

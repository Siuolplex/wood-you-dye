package io.siuolplex.wood_you_dye.block;

import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class WoodYouDyeFenceGateBlock extends FenceGateBlock {
    public WoodYouDyeFenceGateBlock(WoodType type, Properties settings) {
        super(settings, type);
    }
}

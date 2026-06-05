package io.siuolplex.wood_you_dye.block;

import io.siuolplex.gremlib.block.sign.GremWallSignBlock;
import io.siuolplex.gremlib.client.UsesPalettes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.WoodType;

public class DyedWallSignBlock extends GremWallSignBlock implements UsesPalettes {
    public DyedWallSignBlock(WoodType type, Properties settings, Identifier texture) {
        super(type, settings, texture);
    }
}

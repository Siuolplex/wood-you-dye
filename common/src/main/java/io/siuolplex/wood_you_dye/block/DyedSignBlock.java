package io.siuolplex.wood_you_dye.block;

import io.gremstudio.gremlib.block.sign.GremSignBlock;
import io.gremstudio.gremlib.client.UsesPalettes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.WoodType;

public class DyedSignBlock extends GremSignBlock implements UsesPalettes {
    public DyedSignBlock(WoodType type, Properties settings, Identifier texture) {
        super(type, settings, texture);
    }
}

package io.siuolplex.wood_you_dye.block;

import io.siuolplex.gremlib.block.sign.GremCeilingHangingSignBlock;
import io.siuolplex.gremlib.client.UsesPalettes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.WoodType;

public class DyedCeilingHangingSignBlock extends GremCeilingHangingSignBlock implements UsesPalettes {
    public DyedCeilingHangingSignBlock(WoodType type, Properties settings, Identifier texture, Identifier guiTexture) {
        super(type, settings, texture, guiTexture);
    }
}

package io.siuolplex.wood_you_dye.block;

import io.gremstudio.gremlib.block.sign.GremWallSignBlock;
import io.gremstudio.gremlib.client.UsesPalettes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

public class DyedWallSignBlock extends GremWallSignBlock implements UsesPalettes {
    public DyedWallSignBlock(WoodType type, Properties settings, ResourceLocation texture) {
        super(type, settings, texture);
    }
}

package io.siuolplex.wood_you_dye.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.item.DyeColor;

public class WoodYouDyeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (DyeColor color : DyeColor.values()) {
            //BlockRenderLayerMap.INSTANCE.putBlock(BuiltInRegistries.BLOCK.get(Identifier.fromNamespaceAndPath("wood_you_dye", color.toString().toLowerCase() + "_plank_door")), RenderType.translucent());
            //BlockRenderLayerMap.INSTANCE.putBlock(BuiltInRegistries.BLOCK.get(Identifier.fromNamespaceAndPath("wood_you_dye", color.toString().toLowerCase() + "_plank_trapdoor")), RenderType.translucent());
        }
    }
}

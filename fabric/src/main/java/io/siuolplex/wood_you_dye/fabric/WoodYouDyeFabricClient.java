package io.siuolplex.wood_you_dye.fabric;

import io.siuolplex.wood_you_dye.AnotherWoodSet;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeWoodSets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class WoodYouDyeFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (AnotherWoodSet set : WoodYouDyeWoodSets.WOODSETS) {
            BlockRenderLayerMap.INSTANCE.putBlock(set.BLOCKS.PLANK_DOOR, RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(set.BLOCKS.PLANK_TRAPDOOR, RenderType.cutout());
        }
    }
}

package io.siuolplex.wood_you_dye.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class WoodYouDyeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (DyeColor color : DyeColor.values()) {
            BlockRenderLayerMap.INSTANCE.putBlock(BuiltInRegistries.BLOCK.get(ResourceLocation.tryBuild("wood_you_dye", color.toString().toLowerCase() + "_plank_door")), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(BuiltInRegistries.BLOCK.get(ResourceLocation.tryBuild("wood_you_dye", color.toString().toLowerCase() + "_plank_trapdoor")), RenderType.translucent());
        }
    }
}

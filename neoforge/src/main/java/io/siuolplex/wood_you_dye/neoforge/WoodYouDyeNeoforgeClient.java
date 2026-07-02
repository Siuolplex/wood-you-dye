package io.siuolplex.wood_you_dye.neoforge;

import io.siuolplex.wood_you_dye.AnotherWoodSet;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeWoodSets;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = "wood_you_dye", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class WoodYouDyeNeoforgeClient {
    @SubscribeEvent
    public static void onInitializeClient(final FMLClientSetupEvent event) {
        for (AnotherWoodSet set : WoodYouDyeWoodSets.WOODSETS) {
            ItemBlockRenderTypes.setRenderLayer(set.BLOCKS.PLANK_DOOR, RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(set.BLOCKS.PLANK_TRAPDOOR, RenderType.cutout());
        }
    }
}

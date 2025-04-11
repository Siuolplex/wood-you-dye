package io.siuolplex.wood_you_dye.forge;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = WoodYouDye.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class WoodYouDyeClient {
    @SubscribeEvent
    public static void onInitializeClient(final FMLClientSetupEvent event) {
        for (DyeColor color : DyeColor.values()) {
            ItemBlockRenderTypes.setRenderLayer(BuiltInRegistries.BLOCK.get(new ResourceLocation("wood_you_dye", color.toString().toLowerCase() + "_plank_door")), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BuiltInRegistries.BLOCK.get(new ResourceLocation("wood_you_dye", color.toString().toLowerCase() + "_plank_trapdoor")), RenderType.cutout());
        }
    }
}

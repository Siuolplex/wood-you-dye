package io.siuolplex.wood_you_dye.forge;

import io.siuolplex.wood_you_dye.registry.WoodYouDyeBlocks;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeItems;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = "wood_you_dye", bus = Mod.EventBusSubscriber.Bus.MOD)
public class WoodYouDyeForgeRegistration {
    @SubscribeEvent
    public static void registrationTime(RegisterEvent event) {
        if (event.getForgeRegistry() != null) {
            if (event.getForgeRegistry().equals(ForgeRegistries.BLOCKS)) {
                WoodYouDyeBlocks.init();
            } else if (event.getForgeRegistry().equals(ForgeRegistries.ITEMS)) {
                WoodYouDyeItems.init();
            }
        }
    }
}

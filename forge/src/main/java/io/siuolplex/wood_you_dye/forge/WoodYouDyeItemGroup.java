package io.siuolplex.wood_you_dye.forge;

import io.siuolplex.wood_you_dye.registry.WoodYouDyeItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "wood_you_dye", bus = Mod.EventBusSubscriber.Bus.MOD)
public class WoodYouDyeItemGroup {
    @SubscribeEvent
    public static void addToItemGroup(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            for (Item item : WoodYouDyeItems.itemGroupHolder) {
                event.accept(item.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }
}

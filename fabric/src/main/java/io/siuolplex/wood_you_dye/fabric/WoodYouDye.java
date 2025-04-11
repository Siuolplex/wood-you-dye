package io.siuolplex.wood_you_dye.fabric;

import io.siuolplex.wood_you_dye.WoodYouDyeMain;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeItems;
import io.siuolplex.wood_you_dye.util.Loader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class WoodYouDye implements ModInitializer {
    public static final Loader MODLOADER = new WYDFabricLoader();

    @Override
    public void onInitialize() {
        WoodYouDyeMain.init(MODLOADER);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(content -> {
            for (Item item : WoodYouDyeItems.itemGroupHolder) {
                content.addBefore(Items.STONE.getDefaultInstance(), item.getDefaultInstance());
            }
        });

        ResourceManagerHelper.registerBuiltinResourcePack(
                ResourceLocation.tryBuild("wood_you_dye", "programmer_art"),
                FabricLoader.getInstance().getModContainer("wood_you_dye").get(),
                Component.literal("Wood you Dye Programmer Art"),
                ResourcePackActivationType.NORMAL);
    }

    public static class WYDFabricLoader implements Loader {
        @Override
        public String getName() {
            return "fabric";
        }

    }
}

package io.siuolplex.wood_you_dye.fabric;

import io.gremstudio.gremlib.fabric.initializers.GremModInitializer;
import io.siuolplex.wood_you_dye.WoodYouDye;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class WoodYouDyeFabric implements GremModInitializer {
    @Override
    public void onGremModInitalization() {
        new WoodYouDye();

        ResourceManagerHelper.registerBuiltinResourcePack(
                ResourceLocation.fromNamespaceAndPath("wood_you_dye", "programmer_art"),
                FabricLoader.getInstance().getModContainer("wood_you_dye").get(),
                Component.literal("Wood you Dye Programmer Art"),
                ResourcePackActivationType.NORMAL);
    }
}


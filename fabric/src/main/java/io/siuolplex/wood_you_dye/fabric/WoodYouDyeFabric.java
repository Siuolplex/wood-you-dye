package io.siuolplex.wood_you_dye.fabric;

import io.gremstudio.gremlib.fabric.initializers.GremModInitializer;
import io.siuolplex.wood_you_dye.WoodYouDye;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class WoodYouDyeFabric implements GremModInitializer {
    @Override
    public void onGremModInitalization() {
        new WoodYouDye();

        ResourceLoader.registerBuiltinPack(
                Identifier.fromNamespaceAndPath("wood_you_dye", "programmer_art"),
                FabricLoader.getInstance().getModContainer("wood_you_dye").get(),
                Component.literal("Wood you Dye Programmer Art"),
                PackActivationType.NORMAL);


    }
}


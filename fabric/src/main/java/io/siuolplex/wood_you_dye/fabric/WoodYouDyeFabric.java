package io.siuolplex.wood_you_dye.fabric;

import io.siuolplex.gremlib.fabric.initializers.GremModInitializer;
import io.siuolplex.wood_you_dye.WoodYouDye;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeItems;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class WoodYouDyeFabric implements GremModInitializer {
    @Override
    public void onGremModInitalization() {
        new WoodYouDye();
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(content -> {
            List<ItemStack> stacks = new ArrayList<>();
            WoodYouDyeItems.itemGroupHolder.forEach(item -> stacks.add(item.getDefaultInstance()));
            content.insertBefore(Items.STONE.getDefaultInstance(), stacks);
        });

        ResourceLoader.registerBuiltinPack(
                Identifier.fromNamespaceAndPath("wood_you_dye", "programmer_art"),
                FabricLoader.getInstance().getModContainer("wood_you_dye").get(),
                Component.literal("Wood you Dye Programmer Art"),
                PackActivationType.NORMAL);


    }
}


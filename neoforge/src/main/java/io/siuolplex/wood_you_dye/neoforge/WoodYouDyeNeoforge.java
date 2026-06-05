package io.siuolplex.wood_you_dye.neoforge;

import io.siuolplex.gremlib.neoforge.initializers.GremModInitalizationEvent;
import io.siuolplex.wood_you_dye.WoodYouDye;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@Mod(WoodYouDyeNeoforge.ID)
public class WoodYouDyeNeoforge {
    public static final String ID = "wood_you_dye";

    WoodYouDye woodYouDye;

    final ModLoadingContext modLoadingContext = ModLoadingContext.get();
    final IEventBus modEventBus = modLoadingContext.getActiveContainer().getEventBus();

    public WoodYouDyeNeoforge() {
        modEventBus.register(this);
    }

    @SubscribeEvent
    public void addProgArtPack(AddPackFindersEvent event) {
        event.addPackFinders(
                Identifier.fromNamespaceAndPath("wood_you_dye", "resourcepacks/programmer_art"),
                PackType.CLIENT_RESOURCES,
                Component.literal("Wood You Dye Programmer Art"),
                PackSource.DEFAULT,
                false,
                Pack.Position.TOP);
    }


    @SubscribeEvent
    public void onGremModInitalization(GremModInitalizationEvent event) {
        woodYouDye = new WoodYouDye();
    }
}

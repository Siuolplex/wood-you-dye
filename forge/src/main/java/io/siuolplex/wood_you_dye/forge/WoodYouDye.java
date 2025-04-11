package io.siuolplex.wood_you_dye.forge;

import io.siuolplex.wood_you_dye.WoodYouDyeMain;
import io.siuolplex.wood_you_dye.util.Loader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;

@Mod(WoodYouDye.ID)
public class WoodYouDye {
    public static final String ID = "wood_you_dye";

    public static final Loader MODLOADER = new ForgeLoader();

    final ModLoadingContext modLoadingContext = new ModLoadingContext();
    final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public WoodYouDye() {
        WoodYouDyeMain.init(MODLOADER);
        modEventBus.register(this);
    }

    public static final class ForgeLoader implements Loader {
        @Override
        public String getName() {
            return "neoforge";
        }
    }

    // Based on what C+ does, which is based on what Create does. Find C+'s stuff here: https://github.com/ConsistencyPlus/ConsistencyPlus/blob/1.20/forge/src/main/java/io/github/consistencyplus/consistency_plus/forge/ConsistencyPlusClient.java
    @SubscribeEvent
    public void addProgArtPack(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES)
            return;
        IModFileInfo mod = ModList.get().getModFileById(WoodYouDye.ID);
        IModFile file = mod.getFile();
        event.addRepositorySource((packProvider) ->
                packProvider.accept(Pack.create(
                        new ResourceLocation("wood_you_dye", "resourcepacks/programmer_art").toString(),
                        Component.literal("Wood You Dye Programmer Art"),
                        false,
                        (str) -> new ModFilePackResources(
                                "Wood You Dye Programmer Art",
                                file,
                                "resourcepacks/programmer_art"
                        ),
                        new Pack.Info(Component.translatable(new ResourceLocation("wood_you_dye", "resourcepacks/programmer_art").toLanguageKey()), 15, FeatureFlagSet.of()),
                        PackType.CLIENT_RESOURCES,
                        Pack.Position.TOP,
                        false,
                        PackSource.BUILT_IN
                ))
        );

    }
}

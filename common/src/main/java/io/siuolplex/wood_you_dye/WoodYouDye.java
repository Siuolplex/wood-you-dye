package io.siuolplex.wood_you_dye;

import io.gremstudio.gremlib.mod.GremMod;
import io.gremstudio.gremlib.mod.HasRegistration;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeWoodSets;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WoodYouDye extends GremMod implements HasRegistration {
    public static WoodYouDye INSTANCE = null;
    private final Logger LOGGER = LoggerFactory.getLogger("Wood you Dye");
    Map<ResourceKey<?>, Consumer<Registry<?>>> registryMap = new HashMap<>();

    public WoodYouDye() {
        super();

        if (INSTANCE != null) {
            throw new GremMod.GremModReinitError("Can't run a GremMod twice over!");
        }

        INSTANCE = this;
    }

    @Override
    public String getModID() {
        return "wood_you_dye";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public Map<ResourceKey<?>, Consumer<Registry<?>>> getOrMapRegistries() {
        if (registryMap.isEmpty()) {
            registryMap.put(Registries.BLOCK, WoodYouDyeWoodSets::register);
            registryMap.put(Registries.ENTITY_TYPE, WoodYouDyeWoodSets::register);
            registryMap.put(Registries.ITEM, WoodYouDyeWoodSets::register);
            registryMap.put(Registries.BLOCK_ENTITY_TYPE, WoodYouDyeWoodSets::register);
        }

        return registryMap;
    }
}

package io.siuolplex.wood_you_dye.client;

import io.siuolplex.gremlib.client.util.LayerDefinitionRegistry;
import io.siuolplex.gremlib.client.util.ModelLayersUtil;
import io.siuolplex.gremlib.mixin.client.EntityRenderersInvoker;
import io.siuolplex.gremlib.util.WoodSetInfo;
import io.siuolplex.wood_you_dye.AnotherWoodSet;
import io.siuolplex.wood_you_dye.WoodYouDye;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.model.object.boat.RaftModel;

public class ClientWoodSet {
    AnotherWoodSet woodSet;
    ModelLayerLocation boatLayer;
    ModelLayerLocation chestBoatLayer;

    public ClientWoodSet(AnotherWoodSet set) {
        this.woodSet = set;

        if (set.getDetail().getBoatInfo().getFirst()) {
            this.boatLayer = ModelLayersUtil.register(WoodYouDye.INSTANCE.createId(set.getVariantName() + "/" + set.getSetName() + "_" + set.getDetail().getBoatInfo().getSecond().name));
            this.chestBoatLayer = ModelLayersUtil.register(WoodYouDye.INSTANCE.createId(set.getVariantName() + "/" + set.getSetName() + "_chest_" + set.getDetail().getBoatInfo().getSecond().name));

            if (set.getDetail().getBoatInfo().getSecond().equals(WoodSetInfo.BoatType.BOAT)) {
                LayerDefinitionRegistry.addLayer(boatLayer, BoatModel.createBoatModel());
                LayerDefinitionRegistry.addLayer(chestBoatLayer, BoatModel.createChestBoatModel());

            } else {
                LayerDefinitionRegistry.addLayer(boatLayer, RaftModel.createRaftModel());
                LayerDefinitionRegistry.addLayer(chestBoatLayer, RaftModel.createChestRaftModel());
            }
        }
    }

    public void registerRenderers() {
        if (woodSet.getDetail().getBoatInfo().getFirst()) {
            if (woodSet.getDetail().getBoatInfo().getSecond().equals(WoodSetInfo.BoatType.BOAT)) {
                EntityRenderersInvoker.invokeRegister(woodSet.ENTITIES.PLANK_BOAT, context -> new DyedBoatRenderer(context, boatLayer, woodSet, false));
                EntityRenderersInvoker.invokeRegister(woodSet.ENTITIES.PLANK_CHEST_BOAT, context -> new DyedBoatRenderer(context, chestBoatLayer, woodSet, true));
            } else {
                EntityRenderersInvoker.invokeRegister(woodSet.ENTITIES.PLANK_BOAT, context -> new DyedRaftRenderer(context, boatLayer, woodSet, false));
                EntityRenderersInvoker.invokeRegister(woodSet.ENTITIES.PLANK_CHEST_BOAT, context -> new DyedRaftRenderer(context, chestBoatLayer, woodSet, true));
            }
        }
    }

}

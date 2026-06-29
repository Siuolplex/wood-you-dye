package io.siuolplex.wood_you_dye.client;

import io.gremstudio.gremlib.block.sign.GremHangingSign;
import io.gremstudio.gremlib.block.sign.GremSign;
import io.gremstudio.gremlib.client.util.LayerDefinitionRegistry;
import io.gremstudio.gremlib.client.util.ModelLayersUtil;
import io.gremstudio.gremlib.client.util.SignHelper;
import io.gremstudio.gremlib.mixin.client.EntityRenderersInvoker;
import io.gremstudio.gremlib.util.WoodSetInfo;
import io.siuolplex.wood_you_dye.AnotherWoodSet;
import io.siuolplex.wood_you_dye.WoodYouDye;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.model.object.boat.RaftModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ClientWoodSet {
    AnotherWoodSet woodSet;
    ModelLayerLocation boatLayer;
    ModelLayerLocation chestBoatLayer;
    List<Supplier<?>> suppliers = new ArrayList<>();

    public ClientWoodSet(AnotherWoodSet set) {
        this.woodSet = set;

        if (set.getDetail().hasBoat()) {
            this.boatLayer = ModelLayersUtil.register(WoodYouDye.INSTANCE.createId(set.getVariantName() + "/" + set.getSetName() + "_" + set.getDetail().getBoat().name));
            this.chestBoatLayer = ModelLayersUtil.register(WoodYouDye.INSTANCE.createId(set.getVariantName() + "/" + set.getSetName() + "_chest_" + set.getDetail().getBoat().name));

            if (set.getDetail().getBoat().equals(WoodSetInfo.BoatType.BOAT)) {
                LayerDefinitionRegistry.addLayer(boatLayer, BoatModel.createBoatModel());
                LayerDefinitionRegistry.addLayer(chestBoatLayer, BoatModel.createChestBoatModel());

            } else {
                LayerDefinitionRegistry.addLayer(boatLayer, RaftModel.createRaftModel());
                LayerDefinitionRegistry.addLayer(chestBoatLayer, RaftModel.createChestRaftModel());
            }
        }
    }

    public void registerRenderers() {
        if (woodSet.getDetail().hasBoat()) {
            if (woodSet.getDetail().getBoat().equals(WoodSetInfo.BoatType.BOAT)) {
                EntityRenderersInvoker.invokeRegister(woodSet.ENTITIES.PLANK_BOAT, context -> new DyedBoatRenderer(context, boatLayer, woodSet, false));
                EntityRenderersInvoker.invokeRegister(woodSet.ENTITIES.PLANK_CHEST_BOAT, context -> new DyedBoatRenderer(context, chestBoatLayer, woodSet, true));
            } else {
                EntityRenderersInvoker.invokeRegister(woodSet.ENTITIES.PLANK_BOAT, context -> new DyedRaftRenderer(context, boatLayer, woodSet, false));
                EntityRenderersInvoker.invokeRegister(woodSet.ENTITIES.PLANK_CHEST_BOAT, context -> new DyedRaftRenderer(context, chestBoatLayer, woodSet, true));
            }
        }

        SignHelper.addReplacement(woodSet.getWoodType(), (GremSign)woodSet.BLOCKS.PLANK_SIGN, (GremHangingSign)woodSet.BLOCKS.PLANK_HANGING_SIGN);
    }

}

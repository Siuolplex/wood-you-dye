package io.siuolplex.wood_you_dye.client;

import io.siuolplex.wood_you_dye.AnotherWoodSet;
import io.siuolplex.wood_you_dye.WoodYouDye;
import io.siuolplex.wood_you_dye.mixin.client.AbstractBoatRendererAccessor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RaftRenderer;

public class DyedRaftRenderer extends RaftRenderer implements DyedVessel {
    AnotherWoodSet set;

    public DyedRaftRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelId, AnotherWoodSet set, boolean chest) {
        super(context, modelId);
        this.set = set;
        ((AbstractBoatRendererAccessor)this).wyd$setTexture(WoodYouDye.INSTANCE.createId("textures/dyed_wood/entity/" + set.getVariantName() + "/" + ((chest) ? "chest_" : "") + "boat_" + set.getPermutationName()));
    }
}

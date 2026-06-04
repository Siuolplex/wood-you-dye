package io.siuolplex.wood_you_dye.client;

import io.siuolplex.wood_you_dye.AnotherWoodSet;
import io.siuolplex.wood_you_dye.WoodYouDye;
import io.siuolplex.wood_you_dye.mixin.client.AbstractBoatRendererAccessor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DyedBoatRenderer extends BoatRenderer implements DyedVessel {
    AnotherWoodSet set;

    public DyedBoatRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelId, AnotherWoodSet set) {
        super(context, modelId);
        this.set = set;
        ((AbstractBoatRendererAccessor)this).wyd$setTexture(WoodYouDye.INSTANCE.createId("dyed_wood/entity/" + set.getVariantName() + "/boat_" + set.getPermutationName()));
    }
}

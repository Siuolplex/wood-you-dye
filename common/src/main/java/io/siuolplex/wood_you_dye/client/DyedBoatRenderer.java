package io.siuolplex.wood_you_dye.client;

import io.siuolplex.gremlib.client.UsesPalettes;
import io.siuolplex.gremlib.mixin.client.AbstractBoatRendererAccessor;
import io.siuolplex.wood_you_dye.AnotherWoodSet;
import io.siuolplex.wood_you_dye.WoodYouDye;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DyedBoatRenderer extends BoatRenderer implements UsesPalettes {
    AnotherWoodSet set;

    public DyedBoatRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelId, AnotherWoodSet set, boolean chest) {
        super(context, modelId);
        this.set = set;
        ((AbstractBoatRendererAccessor)this).gremlib$setTexture(WoodYouDye.INSTANCE.createId("dyed_wood/entity/" + set.getVariantName() + "/" + ((chest) ? "chest_" : "") + "boat_" + set.getPermutationName()));
    }
}

package io.siuolplex.wood_you_dye.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import io.siuolplex.wood_you_dye.client.DyedBoatRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BoatRenderer.class)
public abstract class BoatRendererMixin extends EntityRenderer<Boat> {
    protected BoatRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/vehicle/Boat;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    public void wood_you_dye$skipRenderForDyed(Boat boat, float f, float g, PoseStack poseStack, MultiBufferSource bufferSource, int i, CallbackInfo ci) {
        if (((Object)this) instanceof DyedBoatRenderer) {
            super.render(boat, f, g, poseStack, bufferSource, i);
            ci.cancel();
        }
    }
}

package io.siuolplex.wood_you_dye.mixin.client;

import net.minecraft.client.renderer.texture.Stitcher;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Stitcher.class)
public class StitcherMixin {
    @Mutable
    @Shadow
    @Final
    private int padding;

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/texture/Stitcher;padding:I"))
    public void nuhuh(Stitcher instance, int value) {
        this.padding = 0;
    }
}

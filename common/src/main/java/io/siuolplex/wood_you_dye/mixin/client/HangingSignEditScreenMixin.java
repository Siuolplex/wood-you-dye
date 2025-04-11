package io.siuolplex.wood_you_dye.mixin.client;

import io.siuolplex.wood_you_dye.block.sign.WoodYouDyeHangingSign;
import net.minecraft.client.gui.screens.inventory.HangingSignEditScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


// Code is based on Terraform
// Found here: https://github.com/TerraformersMC/Terraform/blob/1.19.4/terraform-wood-api-v1/src/main/java/com/terraformersmc/terraform/sign/mixin/MixinHangingSignEditScreen.java
@Mixin(HangingSignEditScreen.class)
public class HangingSignEditScreenMixin {

    @Mutable
    @Shadow private ResourceLocation texture;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void woodYouDye$initSignTextureId(SignBlockEntity signBlockEntity, boolean bl, boolean bl2, CallbackInfo ci) {
        if (signBlockEntity.getBlockState().getBlock() instanceof WoodYouDyeHangingSign sign) {
            ResourceLocation guiTexture = sign.getGuiTexture();
            this.texture = new ResourceLocation(guiTexture.getNamespace(), guiTexture.getPath() + ".png");
        }
    }
}

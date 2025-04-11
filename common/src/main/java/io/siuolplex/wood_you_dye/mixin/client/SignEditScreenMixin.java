package io.siuolplex.wood_you_dye.mixin.client;

import io.siuolplex.wood_you_dye.block.sign.WoodYouDyeSign;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// Code is based on Terraform
// Found here: https://github.com/TerraformersMC/Terraform/blob/1.19.4/terraform-wood-api-v1/src/main/java/com/terraformersmc/terraform/sign/mixin/MixinSignEditScreen.java
@Mixin(SignEditScreen.class)
public class SignEditScreenMixin{
    @ModifyVariable(method = "renderSignBackground", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/Sheets;getSignMaterial(Lnet/minecraft/world/level/block/state/properties/WoodType;)Lnet/minecraft/client/resources/model/Material;"))
    private Material getSignTextureId(Material material, GuiGraphics context, BlockState state) {
        if (state.getBlock() instanceof WoodYouDyeSign sign) {
            return new Material(Sheets.SIGN_SHEET, sign.getTexture());
        }
        return material;
    }
}
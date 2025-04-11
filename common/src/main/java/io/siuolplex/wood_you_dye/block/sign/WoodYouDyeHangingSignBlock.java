package io.siuolplex.wood_you_dye.block.sign;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;

// Code is based on Terraform
// Original found here: https://github.com/TerraformersMC/Terraform/blob/1.19.4/terraform-wood-api-v1/src/main/java/com/terraformersmc/terraform/sign/block/TerraformSignBlock.java
public class WoodYouDyeHangingSignBlock extends CeilingHangingSignBlock implements WoodYouDyeHangingSign {
    private final ResourceLocation guiTexture;
    private final ResourceLocation texture;

    public WoodYouDyeHangingSignBlock(WoodType type, BlockBehaviour.Properties settings) {
        super(settings, type);
        this.texture = new ResourceLocation("wood_you_dye", "entity/signs/hanging/" + type.name());
        this.guiTexture = new ResourceLocation("wood_you_dye", "textures/gui/hanging_sign/" + type.name());
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return guiTexture;
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }
}

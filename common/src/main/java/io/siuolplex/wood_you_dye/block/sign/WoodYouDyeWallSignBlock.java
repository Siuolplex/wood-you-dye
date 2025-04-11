package io.siuolplex.wood_you_dye.block.sign;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;

// Code is based on Terraform
// Original found here: https://github.com/TerraformersMC/Terraform/blob/1.19.4/terraform-wood-api-v1/src/main/java/com/terraformersmc/terraform/sign/block/TerraformSignBlock.java
public class WoodYouDyeWallSignBlock extends WallSignBlock implements WoodYouDyeSign {
    private final ResourceLocation texture;

    public WoodYouDyeWallSignBlock(WoodType type, BlockBehaviour.Properties settings) {
        super(settings.noOcclusion().noCollission(), type);
        this.texture = new ResourceLocation("wood_you_dye", "entity/signs/" + type.name());;
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }
}
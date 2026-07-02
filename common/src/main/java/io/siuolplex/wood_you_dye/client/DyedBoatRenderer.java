package io.siuolplex.wood_you_dye.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.gremstudio.gremlib.Gremlib;
import io.gremstudio.gremlib.client.UsesPalettes;
import io.gremstudio.gremlib.util.WoodSetInfo;
import io.siuolplex.wood_you_dye.AnotherWoodSet;
import io.siuolplex.wood_you_dye.WoodYouDye;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import org.joml.Quaternionf;

public class DyedBoatRenderer extends BoatRenderer implements UsesPalettes {
    AnotherWoodSet set;
    ResourceLocation texture;
    ListModel<Boat> model;
    ModelLayerLocation location;

    public DyedBoatRenderer(EntityRendererProvider.Context context, AnotherWoodSet set, ModelLayerLocation location, boolean chest) {
        super(context, chest);
        this.set = set;
        this.location = location;
        this.texture = WoodYouDye.INSTANCE.createId("dyed_wood/entity/" + set.getVariantName() + "/" + ((chest) ? "chest_" : "") + (set.getDetail().getBoat().equals(WoodSetInfo.BoatType.BOAT) ? "boat_" : "raft_") + set.getPermutationName());
        this.model = createDyedBoatModel(context, chest);
        //((AbstractBoatRendererAccessor)this).gremlib$setTexture(WoodYouDye.INSTANCE.createId("dyed_wood/entity/" + set.getVariantName() + "/" + ((chest) ? "chest_" : "") + "boat_" + set.getPermutationName()));
    }

    @Override
    public void render(Boat boat, float angle, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLightCoords) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - angle));
        float hurt = (float)boat.getHurtTime() - partialTick;
        float hurtAngle = boat.getDamage() - partialTick;
        if (hurtAngle < 0.0F) {
            hurtAngle = 0.0F;
        }

        if (hurt > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(hurt) * hurt * hurtAngle / 10.0F * (float)boat.getHurtDir()));
        }

        float bubbleAngle = boat.getBubbleAngle(partialTick);
        if (!Mth.equal(bubbleAngle, 0.0F)) {
            poseStack.mulPose((new Quaternionf()).setAngleAxis(boat.getBubbleAngle(partialTick) * ((float)Math.PI / 180F), 1.0F, 0.0F, 1.0F));
        }

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(Gremlib.INSTANCE.createId("textures/atlas/boats.png")).apply(getTextureLocation());
        ListModel<Boat> listmodel = model;
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        listmodel.setupAnim(boat, partialTick, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entitySolid(Gremlib.INSTANCE.createId("textures/atlas/boats.png")));
        vertexconsumer = sprite.wrap(vertexconsumer);
        listmodel.renderToBuffer(poseStack, vertexconsumer, packedLightCoords, OverlayTexture.NO_OVERLAY);
        if (!boat.isUnderWater()) {
            VertexConsumer vertexconsumer1 = bufferSource.getBuffer(RenderType.waterMask());
            if (listmodel instanceof WaterPatchModel waterpatchmodel) {
                waterpatchmodel.waterPatch().render(poseStack, vertexconsumer1, packedLightCoords, OverlayTexture.NO_OVERLAY);
            }
        }

        poseStack.popPose();
        super.render(boat, angle, partialTick, poseStack, bufferSource, packedLightCoords);
    }

    public ListModel<Boat> createDyedBoatModel(EntityRendererProvider.Context context, boolean hasChest) {
        ModelPart modelpart = context.bakeLayer(location);
        if (set.getDetail().getBoat().equals(WoodSetInfo.BoatType.RAFT)) {
            return hasChest ? new ChestRaftModel(modelpart) : new RaftModel(modelpart);
        } else {
            return hasChest ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
        }
    }

    public ResourceLocation getTextureLocation() {
        return texture;
    }
}

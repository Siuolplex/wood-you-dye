package io.siuolplex.wood_you_dye.mixin;

import io.siuolplex.wood_you_dye.registry.WoodYouDyeSignTypes;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WoodType.class)
public abstract class WoodTypeMixin {
    @Shadow
    private static WoodType register(WoodType type) {
        return null;
    }

    @Inject(method = "<clinit>", at = @At(value = "RETURN"))
    private static void a(CallbackInfo ci) {
        WoodYouDyeSignTypes.init();
        register(WoodYouDyeSignTypes.RED_WOOD);
        register(WoodYouDyeSignTypes.ORANGE_WOOD);
        register(WoodYouDyeSignTypes.YELLOW_WOOD);
        register(WoodYouDyeSignTypes.LIME_WOOD);
        register(WoodYouDyeSignTypes.GREEN_WOOD);
        register(WoodYouDyeSignTypes.BLUE_WOOD);
        register(WoodYouDyeSignTypes.CYAN_WOOD);
        register(WoodYouDyeSignTypes.LIGHT_BLUE_WOOD);
        register(WoodYouDyeSignTypes.PURPLE_WOOD);
        register(WoodYouDyeSignTypes.PINK_WOOD);
        register(WoodYouDyeSignTypes.MAGENTA_WOOD);
        register(WoodYouDyeSignTypes.BROWN_WOOD);
        register(WoodYouDyeSignTypes.WHITE_WOOD);
        register(WoodYouDyeSignTypes.LIGHT_GRAY_WOOD);
        register(WoodYouDyeSignTypes.GRAY_WOOD);
        register(WoodYouDyeSignTypes.BLACK_WOOD);
    }
}

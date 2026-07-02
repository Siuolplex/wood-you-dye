package io.siuolplex.wood_you_dye.mixin;

import io.siuolplex.wood_you_dye.entity.boat.DyedBoat;
import io.siuolplex.wood_you_dye.entity.boat.DyedChestBoat;
import io.siuolplex.wood_you_dye.item.DyedBoatItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatItem.class)
public class BoatItemMixin {
    @Shadow
    @Final
    private boolean hasChest;

    @Inject(method = "getBoat", at = @At("HEAD"), cancellable = true)
    void woodYouDye$setAsWYDBoat(Level level, HitResult hitResult, ItemStack itemStack, Player player, CallbackInfoReturnable<Boat> cir) {
        if (((Object)this) instanceof DyedBoatItem dyedBoatItem) {
            Vec3 vec3 = hitResult.getLocation();
            Boat boat = (this.hasChest ? new DyedChestBoat(level, vec3.x, vec3.y, vec3.z, dyedBoatItem.getSet()) : new DyedBoat(level, vec3.x, vec3.y, vec3.z, dyedBoatItem.getSet()));
            if (level instanceof ServerLevel serverlevel) {
                EntityType.<Boat>createDefaultStackConfig(serverlevel, itemStack, player).accept(boat);
            }
            cir.setReturnValue(boat);
            cir.cancel();
        }
    }
}

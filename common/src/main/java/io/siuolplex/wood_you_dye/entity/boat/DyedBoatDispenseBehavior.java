package io.siuolplex.wood_you_dye.entity.boat;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LevelEvent;
import org.jetbrains.annotations.NotNull;

// Combo of the Quark boat behavior and the 26.1 boat behavior code.
public class DyedBoatDispenseBehavior extends DefaultDispenseItemBehavior {
    private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
    private final EntityType<? extends Boat> type;

    public DyedBoatDispenseBehavior(final EntityType<? extends Boat> type) {
        this.type = type;
    }

    @NotNull
    @Override
    public ItemStack execute(BlockSource world, @NotNull ItemStack stack) {
        Direction direction = world.state().getValue(DispenserBlock.FACING);
        ServerLevel level = world.level();
        BlockPos pos = world.pos().relative(direction);
        if (!level.getFluidState(pos).is(FluidTags.WATER)) {
            if(!level.getBlockState(pos).isAir() || !level.getFluidState(pos.below()).is(FluidTags.WATER)) {
                return this.defaultDispenseItemBehavior.dispense(world, stack);
            }
        }

        Boat boat = type.spawn(level, world.pos().offset(direction.getStepX(), direction.getStepY(), direction.getStepZ()), MobSpawnType.DISPENSER);
        boat.setYRot(direction.toYRot());
        level.addFreshEntity(boat);
        stack.shrink(1);
        return stack;
    }

    @Override
    protected void playSound(BlockSource world) {
        world.level().levelEvent(LevelEvent.SOUND_DISPENSER_DISPENSE, world.pos(), 0);
    }
}

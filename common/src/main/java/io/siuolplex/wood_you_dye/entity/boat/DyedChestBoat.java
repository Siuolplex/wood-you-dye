package io.siuolplex.wood_you_dye.entity.boat;

import io.siuolplex.wood_you_dye.AnotherWoodSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class DyedChestBoat extends ChestBoat {
    Supplier<Item> dropItem;
    private AnotherWoodSet set;

    public DyedChestBoat(EntityType<? extends Boat> type, Level level, AnotherWoodSet set) {
        super(type, level);
        this.set = set;
    }

    public DyedChestBoat(Level level, double x, double y, double z, AnotherWoodSet set) {
        this(set.ENTITIES.PLANK_CHEST_BOAT, level, set);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public Item getDropItem() {
        return set.ITEMS.PLANK_CHEST_BOAT;
    }

    @Override
    public void setVariant(Type ign) {}
}

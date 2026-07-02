package io.siuolplex.wood_you_dye.entity.boat;

import io.gremstudio.gremlib.util.WoodSetInfo;
import io.siuolplex.wood_you_dye.AnotherWoodSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class DyedBoat extends Boat {
    Supplier<Item> dropItem;
    AnotherWoodSet set;

    public DyedBoat(EntityType<? extends Boat> type, Level level, AnotherWoodSet set) {
        super(type, level);
        this.set = set;
    }

    public DyedBoat(Level level, double x, double y, double z, AnotherWoodSet set) {
        this(set.ENTITIES.PLANK_BOAT, level, set);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public Item getDropItem() {
        return set.ITEMS.PLANK_BOAT;
    }

    @Override
    public void setVariant(Type ign) {
        if (set.getDetail().getBoat().equals(WoodSetInfo.BoatType.RAFT)) {
            super.setVariant(Type.BAMBOO);
        } else {
            super.setVariant(Type.OAK);
        }
    }

}

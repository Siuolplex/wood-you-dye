package io.siuolplex.wood_you_dye.item;

import io.siuolplex.wood_you_dye.AnotherWoodSet;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;

public class DyedBoatItem extends BoatItem {
    private final AnotherWoodSet set;


    public DyedBoatItem(boolean hasChest, Properties properties, AnotherWoodSet set) {
        super(hasChest, Boat.Type.OAK, properties);
        this.set = set;
    }

    public AnotherWoodSet getSet() {
        return set;
    }
}

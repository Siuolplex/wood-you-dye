package io.siuolplex.wood_you_dye.mixin;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockSetType.class)
public interface BlockSetTypeMixin {
    @Invoker("register")
    static BlockSetType registerBlockSetType(BlockSetType type) {
        throw new AssertionError();
    }
}

package io.siuolplex.wood_you_dye;

import io.siuolplex.wood_you_dye.block.*;
import io.siuolplex.wood_you_dye.block.sign.WoodYouDyeHangingSignBlock;
import io.siuolplex.wood_you_dye.block.sign.WoodYouDyeHangingWallSignBlock;
import io.siuolplex.wood_you_dye.block.sign.WoodYouDyeSignBlock;
import io.siuolplex.wood_you_dye.block.sign.WoodYouDyeWallSignBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Function;
import java.util.function.Supplier;

public class PrefixedWoodSet {
    String namespace;
    String setName;
    String prefix;
    boolean prefixTextureOnly = false;
    // These need to be suppliers as the root properties need to not be mutable.
    Supplier<BlockBehaviour.Properties> logProperties;
    Supplier<BlockBehaviour.Properties> plankProperties;
    BlockSetType blockSetType;
    WoodType woodType;
    boolean mosaic = false;


    public PrefixedWoodSet(String namespace, String setName, String prefix, Supplier<BlockBehaviour.Properties> plankProperties, Supplier<BlockBehaviour.Properties> logProperties, BlockSetType type) {
        this.namespace = namespace;
        this.setName = setName;
        this.prefix = prefix;

        this.plankProperties = plankProperties;
        this.logProperties = logProperties;
        this.blockSetType = type;
        this.woodType = new WoodType(namespace + ":" + prefix + "/" + setName, type); // Todo: Make this better suited for non-standard wood types.
    }

    public PrefixedWoodSet setMosaic(boolean mosaic) {
        this.mosaic = mosaic;
        return this;
    }

    // <26.1 exclusive, in order to preserve data from previous versions
    public PrefixedWoodSet setPrefixTextureOnly(boolean prefixTextureOnly) {
        this.prefixTextureOnly = prefixTextureOnly;
        return this;
    }

    private ResourceLocation assembleID(String typeSuffix) {
        return assembleID("", typeSuffix);
    }

    private ResourceLocation assembleID(String typePrefix, String typeSuffix) {
        return ResourceLocation.fromNamespaceAndPath(namespace, ((!prefixTextureOnly) ? prefix + "/" : "") + typePrefix + setName + typeSuffix);
    }

    public void createSet() {
        BlockData log = new BlockData(assembleID("_log"), RotatedPillarBlock::new, logProperties).createItemData();
        BlockData strippedLog = new BlockData(assembleID("stripped_", "_log"), RotatedPillarBlock::new, logProperties).createItemData();
        BlockData wood = new BlockData(assembleID("_wood"), RotatedPillarBlock::new, logProperties).createItemData();
        BlockData strippedWood = new BlockData(assembleID("stripped_", "_wood"), RotatedPillarBlock::new, logProperties).createItemData();

        BlockData planks = new BlockData(assembleID("_planks"), Block::new, plankProperties).createItemData();
        BlockData plankSlab = new BlockData(assembleID( "_plank_slab"), SlabBlock::new, plankProperties).createItemData();
        BlockData plankStairs = new BlockData(assembleID("_plank_stairs"), prop -> new WoodYouDyeStairBlock(planks.getBlock().defaultBlockState(), prop), plankProperties).createItemData();
        BlockData plankFence = new BlockData(assembleID( "_plank_fence"), FenceBlock::new, plankProperties).createItemData();
        BlockData plankFenceGate = new BlockData(assembleID("_plank_fence_gate"), prop -> new WoodYouDyeFenceGateBlock(woodType, prop), plankProperties).createItemData();
        BlockData plankPressurePlate = new BlockData(assembleID( "_plank_pressure_plate"), prop -> new WoodYouDyePressurePlateBlock(blockSetType, prop), plankProperties).createItemData();
        BlockData plankButton = new BlockData(assembleID("_plank_button"), prop -> new WoodYouDyeWoodenButtonBlock(blockSetType, prop), plankProperties).createItemData();
        BlockData plankDoor = new BlockData(assembleID( "_plank_door"), prop -> new WoodYouDyeDoorBlock(blockSetType, prop), plankProperties).createItemData();
        BlockData plankTrapdoor = new BlockData(assembleID("_plank_trapdoor"), prop -> new WoodYouDyeTrapdoorBlock(blockSetType, prop), plankProperties).createItemData();
        BlockData plankSign = new BlockData(assembleID( "_plank_sign"), prop -> new WoodYouDyeSignBlock(woodType, prop), plankProperties);
        BlockData plankWallSign = new BlockData(assembleID( "_plank_wall_sign"), prop -> new WoodYouDyeWallSignBlock(woodType, prop), plankProperties);
        BlockData plankHangingSign = new BlockData(assembleID( "_plank_hanging_sign"), prop -> new WoodYouDyeHangingSignBlock(woodType, prop), plankProperties);
        BlockData plankWallHangingSign = new BlockData(assembleID( "_plank_wall_hanging_sign"), prop -> new WoodYouDyeHangingWallSignBlock(woodType, prop), plankProperties);

        if (mosaic) {
            BlockData mosaic = new BlockData(assembleID("_plank_mosaic"), Block::new, plankProperties).createItemData();
            BlockData mosaicSlab = new BlockData(assembleID("_plank_mosaic_slab"), SlabBlock::new, plankProperties).createItemData();
            BlockData mosaicStairs = new BlockData(assembleID("_plank_mosaic_stairs"), prop -> new WoodYouDyeStairBlock(mosaic.getBlock().defaultBlockState(), prop), plankProperties).createItemData();
        }
    }

    public void registerBlocks() {

    }

    // I dont like how the relation with BlockData and ItemData. This is partly an experiment though.
    public static class BlockData {
        private ResourceLocation id;
        private Function<BlockBehaviour.Properties, Block> blockFunc; // Allows me to construct a block when it is time, also useful for ResourceLocation purposes in 26.1+
        private Supplier<BlockBehaviour.Properties> properties; // Easier to pass around properties between.

        private Block block = null;

        private ItemData itemData = null;

        public BlockData(ResourceLocation id, Function<BlockBehaviour.Properties, Block> blockFunc, Supplier<BlockBehaviour.Properties> properties) {
            this.id = id;
            this.blockFunc = blockFunc;
            this.properties = properties;
        }

        public ResourceKey<Block> getKey() {
            return ResourceKey.create(Registries.BLOCK, id);
        }
        public ItemData getItemData() {
            return this.itemData;
        }

        public Block getBlock() {
            if (block == null) {
                throw new IllegalAccessError("Block " + id.toString() + " has not been created yet");
            } else {
                return this.block;
            }
        }

        public BlockData createItemData() {
            return this.createItemData(Item.Properties::new);
        }

        public BlockData createItemData(Supplier<Item.Properties> itemProperties) {
            return this.createItemData(props -> new BlockItem(this.getBlock(), props), itemProperties);
        }

        public BlockData createItemData(Function<Item.Properties, Item> itemFunc, Supplier<Item.Properties> itemProperties) {
            itemData = new ItemData(id, itemFunc, itemProperties);
            return this;
        }

        public void registerBlock() {
            block = Registry.register(BuiltInRegistries.BLOCK, getKey(), blockFunc.apply(properties.get()));
        }
    }

    public static class ItemData {
        private ResourceLocation id;

        private Function<Item.Properties, Item> itemFunc;
        private Supplier<Item.Properties> properties;

        private Item item;

        public ItemData(ResourceLocation id, Function<Item.Properties, Item> itemFunc, Supplier<Item.Properties> properties) {
            this.id = id;
            this.itemFunc = itemFunc;
            this.properties = properties;
        }

        public ResourceKey<Item> getKey() {
            return ResourceKey.create(Registries.ITEM, id);
        }

        public void registerBlock() {
            item = Registry.register(BuiltInRegistries.ITEM, getKey(), itemFunc.apply(properties.get()));
        }
    }
}

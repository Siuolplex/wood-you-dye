package io.siuolplex.wood_you_dye;

import io.siuolplex.gremlib.block.*;
import io.siuolplex.gremlib.block.sign.GremCeilingHangingSignBlock;
import io.siuolplex.gremlib.block.sign.GremSignBlock;
import io.siuolplex.gremlib.block.sign.GremWallHangingSignBlock;
import io.siuolplex.gremlib.block.sign.GremWallSignBlock;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class PrefixedWoodSet {
    String namespace;
    String setName;
    String prefix;
    // These need to be suppliers as the root properties need to not be mutable.
    Supplier<BlockBehaviour.Properties> logProperties;
    Supplier<BlockBehaviour.Properties> plankProperties;
    BlockSetType blockSetType;
    WoodType woodType;
    boolean hasMosaics = false;
    boolean hasLogs = true;

    public List<BlockData> allBlocks = new ArrayList<>();
    public List<ItemData> allItems = new ArrayList<>();

    BlockData log;
    BlockData strippedLog;
    BlockData wood;
    BlockData strippedWood;

    BlockData planks;
    BlockData plankSlab;
    BlockData plankStairs;
    BlockData plankFence;
    BlockData plankFenceGate;
    BlockData plankPressurePlate;
    BlockData plankButton;
    BlockData plankDoor;
    BlockData plankTrapdoor;
    BlockData plankSign;
    BlockData plankWallSign;
    BlockData plankHangingSign;
    BlockData plankWallHangingSign;

    BlockData mosaic; 
    BlockData mosaicSlab;
    BlockData mosaicStairs;

    ItemData signItem;
    ItemData hangingSignItem;
    //EntityData boatEntity;
    ItemData boatItem;


    public PrefixedWoodSet(String namespace, String setName, String prefix, Supplier<BlockBehaviour.Properties> plankProperties, Supplier<BlockBehaviour.Properties> logProperties, BlockSetType type, WoodType woodType) {
        this.namespace = namespace;
        this.setName = setName;
        this.prefix = prefix;

        this.plankProperties = plankProperties;
        this.logProperties = logProperties;
        this.blockSetType = type;
        this.woodType = woodType;
    }

    public PrefixedWoodSet setHasMosaics(boolean hasMosaics) {
        this.hasMosaics = hasMosaics;
        return this;
    }

    public PrefixedWoodSet setHasLogs(boolean hasLogs) {
        this.hasLogs = hasLogs;
        return this;
    }

    private Identifier assembleID(String typeSuffix) {
        return assembleID("", typeSuffix);
    }

    private Identifier assembleID(String typePrefix, String typeSuffix) {
        return Identifier.fromNamespaceAndPath(namespace, prefix + "/" + typePrefix + setName + typeSuffix);
    }

    public void assembleSet() {
        if (this.hasLogs) {
            this.log = addBlockData(new BlockData(assembleID("_log"), RotatedPillarBlock::new, logProperties).createItemData());
            this.strippedLog = addBlockData(new BlockData(assembleID("stripped_", "_log"), RotatedPillarBlock::new, logProperties).createItemData());
            this.wood = addBlockData(new BlockData(assembleID("_wood"), RotatedPillarBlock::new, logProperties).createItemData());
            this.strippedWood = addBlockData(new BlockData(assembleID("stripped_", "_wood"), RotatedPillarBlock::new, logProperties).createItemData());
        }

        this.planks = addBlockData(new BlockData(assembleID("_planks"), Block::new, plankProperties).createItemData());
        this.plankSlab = addBlockData(new BlockData(assembleID( "_plank_slab"), SlabBlock::new, plankProperties).createItemData());
        this.plankStairs = addBlockData(new BlockData(assembleID("_plank_stairs"), prop -> new GremStairBlock(planks.getBlock().defaultBlockState(), prop), plankProperties).createItemData());
        this.plankFence = addBlockData(new BlockData(assembleID( "_plank_fence"), FenceBlock::new, plankProperties).createItemData());
        this.plankFenceGate = addBlockData(new BlockData(assembleID("_plank_fence_gate"), prop -> new FenceGateBlock(woodType, prop), plankProperties).createItemData());
        this.plankPressurePlate = addBlockData(new BlockData(assembleID( "_plank_pressure_plate"), prop -> new GremPressurePlateBlock(blockSetType, prop), plankProperties).createItemData());
        this.plankButton = addBlockData(new BlockData(assembleID("_plank_button"), prop -> new GremButtonBlock(blockSetType, 40, prop), plankProperties).createItemData());
        this.plankDoor = addBlockData(new BlockData(assembleID( "_plank_door"), prop -> new GremDoorBlock(blockSetType, prop), plankProperties).createItemData());
        this.plankTrapdoor = addBlockData(new BlockData(assembleID("_plank_trapdoor"), prop -> new GremTrapdoorBlock(blockSetType, prop), plankProperties).createItemData());
        this.plankSign = addBlockData(new BlockData(assembleID( "_plank_sign"), prop -> new GremSignBlock(woodType, prop), plankProperties));
        this.plankWallSign = addBlockData(new BlockData(assembleID( "_plank_wall_sign"), prop -> new GremWallSignBlock(woodType, prop), plankProperties));
        this.plankHangingSign = addBlockData(new BlockData(assembleID( "_plank_hanging_sign"), prop -> new GremCeilingHangingSignBlock(woodType, prop), plankProperties));
        this.plankWallHangingSign = addBlockData(new BlockData(assembleID( "_plank_wall_hanging_sign"), prop -> new GremWallHangingSignBlock(woodType, prop), plankProperties));

        if (hasMosaics) {
            this.mosaic = addBlockData(new BlockData(assembleID("_plank_mosaic"), Block::new, plankProperties).createItemData());
            this.mosaicSlab = addBlockData(new BlockData(assembleID("_plank_mosaic_slab"), SlabBlock::new, plankProperties).createItemData());
            this.mosaicStairs = addBlockData(new BlockData(assembleID("_plank_mosaic_stairs"), prop -> new GremStairBlock(mosaic.getBlock().defaultBlockState(), prop), plankProperties).createItemData());
        }

        this.signItem = addItemData(new ItemData(assembleID( "_plank_sign"), prop -> new SignItem(plankSign.block, plankWallSign.block, prop), Item.Properties::new));
        this.hangingSignItem = addItemData(new ItemData(assembleID( "_plank_hanging_sign"), prop -> new HangingSignItem(plankHangingSign.block, plankWallHangingSign.block, prop), Item.Properties::new));
        //this.boatItem = addItemData(new ItemData(assembleID( "_plank_boat"), prop -> new BoatItem( prop), Item.Properties::new));
    }
    
    public BlockData addBlockData(BlockData data) {
        allBlocks.add(data);
        if (data.getItemData() != null) {
            allItems.add(data.getItemData());
        }

        return data;
    }

    public ItemData addItemData(ItemData data) {
        allItems.add(data);
        return data;
    }

    public void registerBlocks() {
        for (BlockData data : allBlocks) {
            data.registerBlock();
        }
    }

    public void registerItems() {
        for (ItemData data : allItems) {
            data.registerItem();
            WoodYouDyeItems.itemGroupHolder.add(data.item);
        }
    }

    // I dont like how the relation with BlockData and ItemData. This is partly an experiment though.
    public static class BlockData {
        private Identifier id;
        private Function<BlockBehaviour.Properties, Block> blockFunc; // Allows me to construct a block when it is time, also useful for ResourceLocation purposes in 26.1+
        private Supplier<BlockBehaviour.Properties> properties; // Easier to pass around properties between.

        private Block block = null;

        private ItemData itemData = null;

        public BlockData(Identifier id, Function<BlockBehaviour.Properties, Block> blockFunc, Supplier<BlockBehaviour.Properties> properties) {
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
            block = Registry.register(BuiltInRegistries.BLOCK, getKey(), blockFunc.apply(properties.get().setId(getKey())));
        }
    }

    public static class ItemData {
        private Identifier id;

        private Function<Item.Properties, Item> itemFunc;
        private Supplier<Item.Properties> properties;

        private Item item;

        public ItemData(Identifier id, Function<Item.Properties, Item> itemFunc, Supplier<Item.Properties> properties) {
            this.id = id;
            this.itemFunc = itemFunc;
            this.properties = properties;
        }

        public ResourceKey<Item> getKey() {
            return ResourceKey.create(Registries.ITEM, id);
        }

        public void registerItem() {
            item = Registry.register(BuiltInRegistries.ITEM, getKey(), itemFunc.apply(properties.get().setId(getKey())));
        }
    }
}

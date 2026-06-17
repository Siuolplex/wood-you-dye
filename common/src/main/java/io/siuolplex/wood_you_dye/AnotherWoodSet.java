package io.siuolplex.wood_you_dye;

import io.gremstudio.gremlib.Gremlib;
import io.gremstudio.gremlib.block.*;
import io.gremstudio.gremlib.util.WoodSetInfo;
import io.siuolplex.wood_you_dye.block.DyedCeilingHangingSignBlock;
import io.siuolplex.wood_you_dye.block.DyedSignBlock;
import io.siuolplex.wood_you_dye.block.DyedWallHangingSignBlock;
import io.siuolplex.wood_you_dye.block.DyedWallSignBlock;
import io.siuolplex.wood_you_dye.client.ClientWoodSet;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class AnotherWoodSet {
    String setName;
    
    String permutationName; // What color am I?
    String variantName; // What category am I?

    WoodSetInfo detail;

    Supplier<BlockBehaviour.Properties> logProperties;
    Supplier<BlockBehaviour.Properties> plankProperties;
    Supplier<Item.Properties> itemProps;

    WoodType woodType;
    BlockSetType blockSetType;

    ClientWoodSet clientSet;

    private AnotherWoodSet(String setName, String permutationName, String variantName, Supplier<BlockBehaviour.Properties> logProperties, Supplier<BlockBehaviour.Properties> plankProperties, Supplier<Item.Properties> itemProps, WoodType woodType, BlockSetType blockSetType, WoodSetInfo setInfo) {
        if (logProperties == null) {
            logProperties = plankProperties;
        }
        if (blockSetType == null) {
            blockSetType = woodType.setType();
        }
        if (setInfo == null) {
            detail = WoodSetInfo.OVERWORLD;
        }

        this.setName = setName;
        this.permutationName = permutationName;
        this.logProperties = logProperties;
        this.plankProperties = plankProperties;
        this.itemProps = itemProps;
        this.woodType = woodType;
        this.blockSetType = blockSetType;
        this.variantName = variantName;
        this.detail = setInfo;

       if (Gremlib.LOADER.isClient()) {
           this.clientSet = new ClientWoodSet(this);
       }
    }

    public AnotherWoodSet.Blocks BLOCKS = new Blocks();
    public AnotherWoodSet.Items ITEMS = new Items();
    public AnotherWoodSet.Entities ENTITIES = new Entities();
    public AnotherWoodSet.BlockEntities BLOCK_ENTITIES = new BlockEntities();

    public String getSetName() {
        return setName;
    }

    public String getPermutationName() {
        return permutationName;
    }

    public String getVariantName() {
        return variantName;
    }

    public WoodSetInfo getDetail() {
        return detail;
    }

    public WoodType getWoodType() {
        return woodType;
    }

    public BlockSetType getBlockSetType() {
        return blockSetType;
    }

    public static class Builder {
        private final String setName;

        String permutationName;
        String variantName = "";

        WoodSetInfo detail;

        Supplier<BlockBehaviour.Properties> logProperties;
        Supplier<BlockBehaviour.Properties> plankProperties;
        Supplier<Item.Properties> itemProps;

        WoodType woodType;
        BlockSetType blockSetType;

        public Builder(String setName) {
            this.setName = setName;
        }

        public Builder setWoodType(WoodType woodType) {
            this.woodType = woodType;
            return this;
        }

        public Builder setBlockSetType(BlockSetType type) {
            this.blockSetType = type;
            return this;
        }

        public Builder setWoodDetail(WoodSetInfo detail) {
            this.detail = detail;
            return this;
        }

        public Builder setLogProperties(Supplier<BlockBehaviour.Properties> props) {
            this.logProperties = props;
            return this;
        }

        public Builder setPlankProperties(Supplier<BlockBehaviour.Properties> props) {
            this.plankProperties = props;
            return this;
        }

        public Builder setItemProperties(Supplier<Item.Properties> props) {
            this.itemProps = props;
            return this;
        }

        public Builder setPermutationName(String permutationName) {
            this.permutationName = permutationName;
            return this;
        }

        public Builder setVariantName(String variantName) {
            this.variantName = variantName;
            return this;
        }

        public AnotherWoodSet build() {
            if (woodType == null) {
                throw new IncompleteBuilderError("No wood type found");
            } else if (plankProperties == null) {
                throw new IncompleteBuilderError("No plank properties found");
            } else if (itemProps == null) {
                throw new IncompleteBuilderError("No item properties found");
            }

            return new AnotherWoodSet(setName, permutationName, variantName, logProperties, plankProperties, itemProps, woodType, blockSetType, detail);
        }

        public static class IncompleteBuilderError extends Error {
            public IncompleteBuilderError(String error) {
                super(error);
            }
        }

    }

    //I've done loops and a bunch of other shit.
    public class Blocks {
        public static List<Block> listOfBlocksIUseForDatagen = new ArrayList<>();

        public Block LOG;
        public Block STRIPPED_LOG;
        public Block WOOD;
        public Block STRIPPED_WOOD;

        public Block PLANKS;
        public Block PLANK_SLAB;
        public Block PLANK_STAIRS;
        public Block PLANK_FENCE;
        public Block PLANK_FENCE_GATE;
        public Block PLANK_PRESSURE_PLATE;
        public Block PLANK_BUTTON;
        public Block PLANK_DOOR;
        public Block PLANK_TRAPDOOR;
        public Block PLANK_SIGN;
        public Block PLANK_WALL_SIGN;
        public Block PLANK_HANGING_SIGN;
        public Block PLANK_WALL_HANGING_SIGN;
        public Block PLANK_SHELF;

        public Block MOSAIC;
        public Block MOSAIC_SLAB;
        public Block MOSAIC_STAIRS;

        public static Block register(String id, Function<BlockBehaviour.Properties, Block> func, BlockBehaviour.Properties properties) {
            ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, WoodYouDye.INSTANCE.createId(id));
            Block block = Registry.register(BuiltInRegistries.BLOCK, key, func.apply(properties.setId(key)));
            listOfBlocksIUseForDatagen.add(block);
            return block;
        }

        public void setRegister() {
            if (detail.hasLogs()) {
                LOG = register(setName + "_" + detail.getLogs(), RotatedPillarBlock::new, logProperties.get());
                STRIPPED_LOG = register("stripped_" + setName + "_" + detail.getLogs(), RotatedPillarBlock::new, logProperties.get());
                if (detail.hasWoods()) {
                    WOOD = register(setName + "_" + detail.getWoods(), RotatedPillarBlock::new, logProperties.get());
                    STRIPPED_WOOD = register("stripped_" + setName + "_" + detail.getWoods(), RotatedPillarBlock::new, logProperties.get());
                }
            }
            
            PLANKS = register(setName + "_planks", Block::new, plankProperties.get());
            PLANK_SLAB = register(setName + "_plank_slab", SlabBlock::new, plankProperties.get());
            PLANK_STAIRS = register(setName + "_plank_stairs", prop -> new GremStairBlock(PLANKS.defaultBlockState(), prop), plankProperties.get());
            PLANK_FENCE = register(setName + "_plank_fence",  FenceBlock::new, plankProperties.get());
            PLANK_FENCE_GATE = register(setName + "_plank_fence_gate", prop -> new FenceGateBlock(woodType, prop), plankProperties.get());
            PLANK_PRESSURE_PLATE = register(setName + "_plank_pressure_plate", prop -> new GremPressurePlateBlock(blockSetType, prop), plankProperties.get());
            PLANK_BUTTON = register(setName + "_plank_button", prop -> new GremButtonBlock(blockSetType, 40, prop), plankProperties.get());
            PLANK_DOOR = register(setName + "_plank_door", prop -> new GremDoorBlock(blockSetType, prop), plankProperties.get());
            PLANK_TRAPDOOR = register(setName + "_plank_trapdoor", prop -> new GremTrapdoorBlock(blockSetType, prop), plankProperties.get());
            PLANK_SIGN = register(setName + "_plank_sign", prop -> new DyedSignBlock(woodType, prop, WoodYouDye.INSTANCE.createId("dyed_wood/entity/" + AnotherWoodSet.this.getVariantName() + "/sign_" + AnotherWoodSet.this.getPermutationName())), plankProperties.get());
            PLANK_WALL_SIGN = register(setName + "_plank_wall_sign", prop -> new DyedWallSignBlock(woodType, prop, WoodYouDye.INSTANCE.createId("dyed_wood/entity/" + AnotherWoodSet.this.getVariantName() + "/sign_" + AnotherWoodSet.this.getPermutationName())), plankProperties.get());
            PLANK_HANGING_SIGN = register(setName + "_plank_hanging_sign", prop -> new DyedCeilingHangingSignBlock(woodType, prop, WoodYouDye.INSTANCE.createId("dyed_wood/entity/" + AnotherWoodSet.this.getVariantName() + "/hanging_sign_" + AnotherWoodSet.this.getPermutationName()), WoodYouDye.INSTANCE.createId("dyed_wood/gui/" + AnotherWoodSet.this.getVariantName() + "/hanging_sign_" + AnotherWoodSet.this.getPermutationName())), plankProperties.get());
            PLANK_WALL_HANGING_SIGN = register(setName + "_plank_wall_hanging_sign", prop -> new DyedWallHangingSignBlock(woodType, prop, WoodYouDye.INSTANCE.createId("dyed_wood/entity/" + AnotherWoodSet.this.getVariantName() + "/hanging_sign_" + AnotherWoodSet.this.getPermutationName()), WoodYouDye.INSTANCE.createId("dyed_wood/gui/" + AnotherWoodSet.this.getVariantName() + "/hanging_sign_" + AnotherWoodSet.this.getPermutationName())), plankProperties.get());
            PLANK_SHELF = register(setName + "_plank_shelf", ShelfBlock::new, plankProperties.get());


            if (detail.canDoMosaic()) {
                MOSAIC = register(setName + "_mosaic", Block::new, plankProperties.get());
                MOSAIC_SLAB = register(setName + "_mosaic_slab", SlabBlock::new, plankProperties.get());
                MOSAIC_STAIRS = register(setName + "_mosaic_stairs", (prop) -> new GremStairBlock(MOSAIC.defaultBlockState(), prop), plankProperties.get());
            }

            if (detail.hasLogs()) {
                Gremlib.LOADER.blocks().addToStrippables(BLOCKS.LOG, BLOCKS.STRIPPED_LOG);
                if (detail.hasWoods()) {
                    Gremlib.LOADER.blocks().addToStrippables(BLOCKS.WOOD, BLOCKS.STRIPPED_WOOD);
                }
            }
        }
    }

    public class Items {
        public Item LOG;
        public Item STRIPPED_LOG;
        public Item WOOD;
        public Item STRIPPED_WOOD;

        public Item PLANKS;
        public Item PLANK_SLAB;
        public Item PLANK_STAIRS;
        public Item PLANK_FENCE;
        public Item PLANK_FENCE_GATE;
        public Item PLANK_PRESSURE_PLATE;
        public Item PLANK_BUTTON;
        public Item PLANK_DOOR;
        public Item PLANK_TRAPDOOR;
        public Item PLANK_SIGN;
        public Item PLANK_HANGING_SIGN;
        public Item PLANK_BOAT;
        public Item PLANK_CHEST_BOAT;
        public Item PLANK_SHELF;

        public Item MOSAIC;
        public Item MOSAIC_SLAB;
        public Item MOSAIC_STAIRS;

        public static Item register(String id, Function<Item.Properties, Item> func, Item.Properties properties) {
            ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, WoodYouDye.INSTANCE.createId(id));
            Item item = Registry.register(BuiltInRegistries.ITEM, key, func.apply(properties.setId(key)));
            WoodYouDyeItems.itemGroupHolder.add(item);
            return item;
        }

        public void setRegister() {
            if (detail.hasLogs()) {
                LOG = register(setName + "_" + detail.getLogs(), prop -> new BlockItem(BLOCKS.LOG, prop), itemProps.get());
                STRIPPED_LOG = register("stripped_" + setName + "_" + detail.getLogs(), prop -> new BlockItem(BLOCKS.STRIPPED_LOG, prop), itemProps.get());
                if (detail.hasWoods()) {
                    WOOD = register(setName + "_" + detail.getWoods(), prop -> new BlockItem(BLOCKS.WOOD, prop), itemProps.get());
                    STRIPPED_WOOD = register("stripped_" + setName + "_" + detail.getWoods(), prop -> new BlockItem(BLOCKS.STRIPPED_WOOD, prop), itemProps.get());
                }
            }

            PLANKS = register(setName + "_planks", prop -> new BlockItem(BLOCKS.PLANKS, prop), itemProps.get());
            PLANK_SLAB = register(setName + "_plank_slab", prop -> new BlockItem(BLOCKS.PLANK_SLAB, prop), itemProps.get());
            PLANK_STAIRS = register(setName + "_plank_stairs", prop -> new BlockItem(BLOCKS.PLANK_STAIRS, prop), itemProps.get());
            PLANK_FENCE = register(setName + "_plank_fence",  prop -> new BlockItem(BLOCKS.PLANK_FENCE, prop), itemProps.get());
            PLANK_FENCE_GATE = register(setName + "_plank_fence_gate", prop -> new BlockItem(BLOCKS.PLANK_FENCE_GATE, prop), itemProps.get());
            PLANK_PRESSURE_PLATE = register(setName + "_plank_pressure_plate", prop -> new BlockItem(BLOCKS.PLANK_PRESSURE_PLATE, prop), itemProps.get());
            PLANK_BUTTON = register(setName + "_plank_button", prop -> new BlockItem(BLOCKS.PLANK_BUTTON, prop), itemProps.get());
            PLANK_DOOR = register(setName + "_plank_door", prop -> new BlockItem(BLOCKS.PLANK_DOOR, prop), itemProps.get());
            PLANK_TRAPDOOR = register(setName + "_plank_trapdoor", prop -> new BlockItem(BLOCKS.PLANK_TRAPDOOR, prop), itemProps.get());
            PLANK_SIGN = register(setName + "_plank_sign", prop -> new SignItem(BLOCKS.PLANK_SIGN, BLOCKS.PLANK_WALL_SIGN, prop), itemProps.get());
            PLANK_HANGING_SIGN = register(setName + "_plank_hanging_sign", prop -> new HangingSignItem(BLOCKS.PLANK_HANGING_SIGN, BLOCKS.PLANK_WALL_HANGING_SIGN, prop), itemProps.get());
            PLANK_SHELF = register(setName + "_plank_shelf", prop -> new BlockItem(BLOCKS.PLANK_SHELF, prop), itemProps.get());

            if (detail.canDoMosaic()) {
                MOSAIC = register(setName + "_mosaic", prop -> new BlockItem(BLOCKS.MOSAIC, prop), itemProps.get());
                MOSAIC_SLAB = register(setName + "_mosaic_slab", prop -> new BlockItem(BLOCKS.MOSAIC_SLAB, prop), itemProps.get());
                MOSAIC_STAIRS = register(setName + "_mosaic_stairs", prop -> new BlockItem(BLOCKS.MOSAIC_STAIRS, prop), itemProps.get());
            }

            if (detail.hasBoat()) {
                PLANK_BOAT = register(setName + "_" + detail.getBoat().name, prop -> new BoatItem(ENTITIES.PLANK_BOAT, prop), itemProps.get().stacksTo(1));
                PLANK_CHEST_BOAT = register(setName + "_chest_" + detail.getBoat().name, prop -> new BoatItem(ENTITIES.PLANK_CHEST_BOAT, prop), itemProps.get().stacksTo(1));
            }
        }
    }

    public class Entities {
        public EntityType<? extends AbstractBoat> PLANK_BOAT;
        public EntityType<? extends AbstractChestBoat> PLANK_CHEST_BOAT;

        public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
            ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, WoodYouDye.INSTANCE.createId(id));
            return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
        }

        public void setRegister() {
            if (detail.hasBoat()) {
                WoodSetInfo.BoatType boatType = detail.getBoat();
                if (boatType.equals(WoodSetInfo.BoatType.BOAT)) {
                    PLANK_BOAT = register(setName + "_" + boatType.name, EntityType.Builder.<Boat>of((entityType, level) -> boatType.boatFactory.apply(() -> ITEMS.PLANK_BOAT, entityType, level), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
                    PLANK_CHEST_BOAT = register(setName + "_chest_" + boatType.name, EntityType.Builder.<ChestBoat>of((entityType, level) -> boatType.chestBoatFactory.apply(() -> ITEMS.PLANK_BOAT, entityType, level), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
                } else if (boatType.equals(WoodSetInfo.BoatType.RAFT)) {
                    PLANK_BOAT = register(setName + "_" + boatType.name, EntityType.Builder.<Raft>of((entityType, level) -> boatType.raftFactory.apply(() -> ITEMS.PLANK_BOAT, entityType, level), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
                    PLANK_CHEST_BOAT = register(setName + "_chest_" + boatType.name, EntityType.Builder.<ChestRaft>of((entityType, level) -> boatType.chestRaftFactory.apply(() -> ITEMS.PLANK_BOAT, entityType, level), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
                }
            }

            if (Gremlib.LOADER.isClient()) {
                clientSet.registerRenderers();
            }
        }
    }

    public class BlockEntities {
        // Really this is just post register stuff for the most part.
        public void setRegister() {
            Gremlib.LOADER.blocks().addBlocksToBE(BlockEntityType.SIGN, AnotherWoodSet.this.BLOCKS.PLANK_SIGN, AnotherWoodSet.this.BLOCKS.PLANK_WALL_SIGN);
            Gremlib.LOADER.blocks().addBlocksToBE(BlockEntityType.HANGING_SIGN, AnotherWoodSet.this.BLOCKS.PLANK_HANGING_SIGN, AnotherWoodSet.this.BLOCKS.PLANK_WALL_HANGING_SIGN);
            Gremlib.LOADER.blocks().addBlockToBE(BlockEntityType.SHELF, AnotherWoodSet.this.BLOCKS.PLANK_SHELF);
        }
    }
}

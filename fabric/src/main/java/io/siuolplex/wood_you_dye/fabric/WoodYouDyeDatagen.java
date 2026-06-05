package io.siuolplex.wood_you_dye.fabric;

import io.siuolplex.gremlib.util.WoodSetInfo;
import io.siuolplex.wood_you_dye.AnotherWoodSet;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeWoodSets;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.SideChainPart;
import org.jspecify.annotations.NonNull;

public class WoodYouDyeDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(WYDModelProvider::new);
    }

    private static class WYDModelProvider extends FabricModelProvider {
        private WYDModelProvider(FabricPackOutput generator) {
            super(generator);
        }

        @Override
        public void generateBlockStateModels(@NonNull BlockModelGenerators blockModelGenerators) {
            for (AnotherWoodSet set : WoodYouDyeWoodSets.WOODSETS) {
                WoodSetInfo setInfo = set.getDetail();
                if (setInfo.getLogInfo().getFirst()) {
                    TextureMapping logMapping = createLogMapping(set, false);
                    TextureMapping strippedLogMappings = createLogMapping(set, true);
                    
                    generateLog(blockModelGenerators, set.BLOCKS.LOG, logMapping);
                    generateLog(blockModelGenerators, set.BLOCKS.STRIPPED_LOG, strippedLogMappings);

                    if (setInfo.getWoodInfo().getFirst()) {
                        generateWood(blockModelGenerators, set.BLOCKS.WOOD, logMapping);
                        generateWood(blockModelGenerators, set.BLOCKS.STRIPPED_WOOD, strippedLogMappings);
                    }
                }
                
                Block planksBlock = set.BLOCKS.PLANKS;
                TextureMapping planksMapping = createPlanksMapping(set);

                blockModelGenerators.createTrivialBlock(planksBlock, _ -> TexturedModel.createAllSame(createPlanksMaterial(set)));

                generateSlab(blockModelGenerators, set.BLOCKS.PLANK_SLAB, planksMapping, planksBlock);
                generateStairs(blockModelGenerators, set.BLOCKS.PLANK_STAIRS, planksMapping);
                generateFence(blockModelGenerators, set.BLOCKS.PLANK_FENCE, planksMapping);
                generateFenceGate(blockModelGenerators, set.BLOCKS.PLANK_FENCE_GATE, planksMapping);
                generatePressurePlate(blockModelGenerators, set.BLOCKS.PLANK_PRESSURE_PLATE, planksMapping);
                generateButton(blockModelGenerators, set.BLOCKS.PLANK_BUTTON, planksMapping);
                generateSign(blockModelGenerators, set.BLOCKS.PLANK_SIGN, set.BLOCKS.PLANK_WALL_SIGN, planksBlock);
                generateSign(blockModelGenerators, set.BLOCKS.PLANK_HANGING_SIGN, set.BLOCKS.PLANK_WALL_HANGING_SIGN, planksBlock);

                TextureMapping doorMapping = createDoorMapping(set);
                generateDoor(blockModelGenerators, set.BLOCKS.PLANK_DOOR, doorMapping);

                TextureMapping trapdoorMapping = createTrapdoorMapping(set);
                generateTrapdoor(blockModelGenerators, set.BLOCKS.PLANK_TRAPDOOR, trapdoorMapping);

                TextureMapping shelfMapping = createShelfMapping(set);
                generateShelf(blockModelGenerators, set.BLOCKS.PLANK_SHELF, shelfMapping);
                
                if (setInfo.canDoMosaic()) {
                    TextureMapping mosaicMapping = createMosaicMapping(set);

                    blockModelGenerators.createTrivialBlock(planksBlock, _ -> TexturedModel.createAllSame(createMosaicMaterial(set)));

                    generateSlab(blockModelGenerators, set.BLOCKS.MOSAIC_SLAB,  mosaicMapping, set.BLOCKS.MOSAIC);
                    generateStairs(blockModelGenerators, set.BLOCKS.MOSAIC_STAIRS, mosaicMapping);

                }
            }
        }

        @Override
        public void generateItemModels(@NonNull ItemModelGenerators generators) {
            for (AnotherWoodSet set : WoodYouDyeWoodSets.WOODSETS) {
                flatItemCreator(generators, set, "door", set.ITEMS.PLANK_DOOR);

                flatItemCreator(generators, set, "sign", set.ITEMS.PLANK_SIGN);

                flatItemCreator(generators, set, "hanging_sign", set.ITEMS.PLANK_HANGING_SIGN);

                if (set.getDetail().getBoatInfo().getFirst()) {
                    String name = set.getDetail().getBoatInfo().getSecond().name;
                    flatItemCreator(generators, set, name, set.ITEMS.PLANK_BOAT);
                    flatItemCreator(generators, set, "chest_" + name, set.ITEMS.PLANK_CHEST_BOAT);

                }
            }
        }

        void flatItemCreator(ItemModelGenerators generators, AnotherWoodSet set, String itemName, Item item) {
            generators.itemModelOutput.accept(item, ItemModelUtils.plainModel(
                    ModelTemplates.FLAT_ITEM.create(item, createItemMapping(set, itemName), generators.modelOutput)
            ));
        }

        public void generateSlab(BlockModelGenerators generator, Block slab, TextureMapping mapping, Block sourceBlock) {
            Identifier bottom = ModelTemplates.SLAB_BOTTOM.create(slab, mapping, generator.modelOutput);
            Identifier top = ModelTemplates.SLAB_TOP.create(slab, mapping, generator.modelOutput);
            MultiVariant source = BlockModelGenerators.plainVariant(ModelTemplates.CUBE.getDefaultModelLocation(sourceBlock));
            generator.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, BlockModelGenerators.plainVariant(bottom), BlockModelGenerators.plainVariant(top), source));
            generator.registerSimpleItemModel(slab, bottom);
        }

        public void generateStairs(BlockModelGenerators generator, Block stairs, TextureMapping mapping) {
            MultiVariant inner = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_INNER.create(stairs, mapping, generator.modelOutput));
            Identifier straight = ModelTemplates.STAIRS_STRAIGHT.create(stairs, mapping, generator.modelOutput);
            MultiVariant outer = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_OUTER.create(stairs, mapping, generator.modelOutput));
            generator.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, inner, BlockModelGenerators.plainVariant(straight), outer));
            generator.registerSimpleItemModel(stairs, straight);
        }

        public void generateFence(BlockModelGenerators generator, Block fence, TextureMapping mapping) {
            MultiVariant post = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_POST.create(fence, mapping, generator.modelOutput));
            MultiVariant side = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_SIDE.create(fence, mapping, generator.modelOutput));
            generator.blockStateOutput.accept(BlockModelGenerators.createFence(fence, post, side));
            Identifier inventory = ModelTemplates.FENCE_INVENTORY.create(fence, mapping, generator.modelOutput);
            generator.registerSimpleItemModel(fence, inventory);
        }

        public void generateFenceGate(BlockModelGenerators generator, Block fenceGate, TextureMapping mapping) {
            MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_OPEN.create(fenceGate, mapping, generator.modelOutput));
            MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_CLOSED.create(fenceGate, mapping, generator.modelOutput));
            MultiVariant openWall = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGate, mapping, generator.modelOutput));
            MultiVariant closedWall = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGate, mapping, generator.modelOutput));
            generator.blockStateOutput.accept(BlockModelGenerators.createFenceGate(fenceGate, open, closed, openWall, closedWall, true));
        }
        
        public void generatePressurePlate(BlockModelGenerators generator, Block pressurePlate, TextureMapping mapping) {
            MultiVariant off = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_UP.create(pressurePlate, mapping, generator.modelOutput));
            MultiVariant on = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_DOWN.create(pressurePlate, mapping, generator.modelOutput));
            generator.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(pressurePlate, off, on));
        }

        public void generateButton(BlockModelGenerators generator, Block button, TextureMapping mapping) {
            MultiVariant normal = BlockModelGenerators.plainVariant(ModelTemplates.BUTTON.create(button, mapping, generator.modelOutput));
            MultiVariant pressed = BlockModelGenerators.plainVariant(ModelTemplates.BUTTON_PRESSED.create(button, mapping, generator.modelOutput));
            generator.blockStateOutput.accept(BlockModelGenerators.createButton(button, normal, pressed));
            generator.registerSimpleItemModel(button, ModelTemplates.BUTTON_INVENTORY.create(button, mapping, generator.modelOutput));
        }

        // Holy fuck thats a lot of models
        public void generateDoor(BlockModelGenerators generator, Block door, TextureMapping mapping) {
            MultiVariant doorBottomLeft = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT.create(door, mapping, generator.modelOutput));
            MultiVariant doorBottomLeftOpen = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(door, mapping, generator.modelOutput));
            MultiVariant doorBottomRight = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT.create(door, mapping, generator.modelOutput));
            MultiVariant doorBottomRightOpen = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(door, mapping, generator.modelOutput));
            MultiVariant doorTopLeft = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_LEFT.create(door, mapping, generator.modelOutput));
            MultiVariant doorTopLeftOpen = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_LEFT_OPEN.create(door, mapping, generator.modelOutput));
            MultiVariant doorTopRight = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_RIGHT.create(door, mapping, generator.modelOutput));
            MultiVariant doorTopRightOpen = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(door, mapping, generator.modelOutput));
            //generator.registerSimpleFlatItemModel(door.asItem());
            generator.blockStateOutput.accept(BlockModelGenerators.createDoor(door, doorBottomLeft, doorBottomLeftOpen, doorBottomRight, doorBottomRightOpen, doorTopLeft, doorTopLeftOpen, doorTopRight, doorTopRightOpen));
        }

        // Orientable, does it REALLY matter if we use orientable or not?
        public void generateTrapdoor(BlockModelGenerators generator, Block trapdoor, TextureMapping mapping) {
            MultiVariant top = BlockModelGenerators.plainVariant(ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(trapdoor, mapping, generator.modelOutput));
            Identifier bottom = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(trapdoor, mapping, generator.modelOutput);
            MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(trapdoor, mapping, generator.modelOutput));
            generator.blockStateOutput.accept(BlockModelGenerators.createOrientableTrapdoor(trapdoor, top, BlockModelGenerators.plainVariant(bottom), open));
            generator.registerSimpleItemModel(trapdoor, bottom);
        }

        public void generateSign(BlockModelGenerators generator, Block sign, Block wallSign, Block sourceBlock) {
            MultiVariant model = generator.createParticleOnlyBlockModel(sign, sourceBlock);
            generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(sign, model));
            generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(wallSign, model));
            //generator.registerSimpleFlatItemModel(sign.asItem());
        }

        public void generateLog(BlockModelGenerators generator, Block log, TextureMapping mapping) {
            Identifier model = ModelTemplates.CUBE_COLUMN.create(log, mapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(log, BlockModelGenerators.plainVariant(model)));
            generator.registerSimpleItemModel(log, model);
        }

        public void generateWood(BlockModelGenerators generator, Block log, TextureMapping mapping) {
            TextureMapping woodMapping = mapping.copyAndUpdate(TextureSlot.END, mapping.get(TextureSlot.SIDE));
            Identifier model = ModelTemplates.CUBE_COLUMN.create(log, woodMapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(log, BlockModelGenerators.plainVariant(model)));
            generator.registerSimpleItemModel(log, model);
        }

        public void generateShelf(BlockModelGenerators generators, Block shelf, TextureMapping mapping) {
            MultiPartGenerator generator = MultiPartGenerator.multiPart(shelf);
            generators.addShelfPart(shelf, mapping, generator, ModelTemplates.SHELF_BODY, null, null);
            generators.addShelfPart(shelf, mapping, generator, ModelTemplates.SHELF_UNPOWERED, false, null);
            generators.addShelfPart(shelf, mapping, generator, ModelTemplates.SHELF_UNCONNECTED, true, SideChainPart.UNCONNECTED);
            generators.addShelfPart(shelf, mapping, generator, ModelTemplates.SHELF_LEFT, true, SideChainPart.LEFT);
            generators.addShelfPart(shelf, mapping, generator, ModelTemplates.SHELF_CENTER, true, SideChainPart.CENTER);
            generators.addShelfPart(shelf, mapping, generator, ModelTemplates.SHELF_RIGHT, true, SideChainPart.RIGHT);
            generators.blockStateOutput.accept(generator);
            generators.registerSimpleItemModel(shelf, ModelTemplates.SHELF_INVENTORY.create(shelf, mapping, generators.modelOutput));
        }

        public TextureMapping createPlanksMapping(AnotherWoodSet woodSet) {
            return TextureMapping.cube(createPlanksMaterial(woodSet));
        }

        public TextureMapping createMosaicMapping(AnotherWoodSet woodSet) {
            return TextureMapping.cube(createPlanksMaterial(woodSet));
        }

        public TextureMapping createDoorMapping(AnotherWoodSet woodSet) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            Identifier doorTopID = Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/door_top_" + permutationName);
            Identifier doorBottomID = Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/door_bottom_" + permutationName);
            return TextureMapping.door(new Material(doorTopID), new Material(doorBottomID));
        }

        public TextureMapping createLogMapping(AnotherWoodSet woodSet, boolean isStripped) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }

            String stripped = (isStripped) ? "stripped_" : "";

            Identifier logID = Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/" + stripped + "log_" + permutationName);
            Identifier logTopID = Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/" + stripped + "log_top_" + permutationName);
            return new TextureMapping()
                    .put(TextureSlot.SIDE, new Material(logID))
                    .put(TextureSlot.END, new Material(logTopID))
                    .put(TextureSlot.PARTICLE, new Material(logID));
        }

        public TextureMapping createTrapdoorMapping(AnotherWoodSet woodSet) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            Identifier trapdoorId = Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/trapdoor_" + permutationName);
            return TextureMapping.defaultTexture(new Material(trapdoorId));
        }

        public TextureMapping createShelfMapping(AnotherWoodSet woodSet) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            Identifier shelfID = Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/shelf_" + permutationName);

            return new TextureMapping().put(TextureSlot.ALL, new Material(shelfID))
                    .put(TextureSlot.PARTICLE, createPlanksMaterial(woodSet));
        }

        public TextureMapping createItemMapping(AnotherWoodSet woodSet, String itemName) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            Identifier itemID = Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/item" + variantName + "/" + itemName + "_" + permutationName);
            return TextureMapping.layer0(new Material(itemID));
        }

        public Material createPlanksMaterial(AnotherWoodSet woodSet) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            Identifier id = Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/planks_" + permutationName);
            return new Material(id);
        }

        public Material createMosaicMaterial(AnotherWoodSet woodSet) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            Identifier id = Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/mosaic_" + permutationName);
            return new Material(id);
        }
        
        public void generateModel(BlockModelGenerators blockModelGenerators) {

        }
        
        
        public Material generateMaterial(AnotherWoodSet woodSet) {
            return new Material(Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block/" + woodSet.getPermutationName() + "/planks_" + woodSet.getSetName().replace("dyed_", "")));
        }
    }
    /*
    private static final List<Item> DYES_LIST = List.of(Items.RED_DYE, Items.ORANGE_DYE, Items.YELLOW_DYE,
            Items.LIME_DYE, Items.GREEN_DYE, Items.BLUE_DYE, Items.CYAN_DYE, Items.LIGHT_BLUE_DYE, Items.PURPLE_DYE,
            Items.PINK_DYE, Items.MAGENTA_DYE, Items.BROWN_DYE, Items.WHITE_DYE, Items.LIGHT_GRAY_DYE, Items.GRAY_DYE, Items.BLACK_DYE);

    private static final List<Item> PLANKS = List.of(WoodYouDyeItems.RED_PLANKS, WoodYouDyeItems.ORANGE_PLANKS,
            WoodYouDyeItems.YELLOW_PLANKS, WoodYouDyeItems.LIME_PLANKS, WoodYouDyeItems.GREEN_PLANKS, WoodYouDyeItems.BLUE_PLANKS,
            WoodYouDyeItems.CYAN_PLANKS, WoodYouDyeItems.LIGHT_BLUE_PLANKS, WoodYouDyeItems.PURPLE_PLANKS, WoodYouDyeItems.PINK_PLANKS,
            WoodYouDyeItems.MAGENTA_PLANKS, WoodYouDyeItems.BROWN_PLANKS, WoodYouDyeItems.WHITE_PLANKS, WoodYouDyeItems.LIGHT_GRAY_PLANKS,
            WoodYouDyeItems.GRAY_PLANKS, WoodYouDyeItems.BLACK_PLANKS);

    private static final List<Item> SLABS = List.of(WoodYouDyeItems.RED_PLANK_SLAB, WoodYouDyeItems.ORANGE_PLANK_SLAB,
            WoodYouDyeItems.YELLOW_PLANK_SLAB, WoodYouDyeItems.LIME_PLANK_SLAB, WoodYouDyeItems.GREEN_PLANK_SLAB, WoodYouDyeItems.BLUE_PLANK_SLAB,
            WoodYouDyeItems.CYAN_PLANK_SLAB, WoodYouDyeItems.LIGHT_BLUE_PLANK_SLAB, WoodYouDyeItems.PURPLE_PLANK_SLAB,
            WoodYouDyeItems.PINK_PLANK_SLAB, WoodYouDyeItems.MAGENTA_PLANK_SLAB, WoodYouDyeItems.BROWN_PLANK_SLAB, WoodYouDyeItems.WHITE_PLANK_SLAB,
            WoodYouDyeItems.LIGHT_GRAY_PLANK_SLAB, WoodYouDyeItems.GRAY_PLANK_SLAB, WoodYouDyeItems.BLACK_PLANK_SLAB);

    private static final List<Item> STAIRS = List.of(WoodYouDyeItems.RED_PLANK_STAIRS, WoodYouDyeItems.ORANGE_PLANK_STAIRS,
            WoodYouDyeItems.YELLOW_PLANK_STAIRS, WoodYouDyeItems.LIME_PLANK_STAIRS, WoodYouDyeItems.GREEN_PLANK_STAIRS,
            WoodYouDyeItems.BLUE_PLANK_STAIRS, WoodYouDyeItems.CYAN_PLANK_STAIRS, WoodYouDyeItems.LIGHT_BLUE_PLANK_STAIRS,
            WoodYouDyeItems.PURPLE_PLANK_STAIRS, WoodYouDyeItems.PINK_PLANK_STAIRS, WoodYouDyeItems.MAGENTA_PLANK_STAIRS,
            WoodYouDyeItems.BROWN_PLANK_STAIRS, WoodYouDyeItems.WHITE_PLANK_STAIRS, WoodYouDyeItems.LIGHT_GRAY_PLANK_STAIRS,
            WoodYouDyeItems.GRAY_PLANK_STAIRS, WoodYouDyeItems.BLACK_PLANK_STAIRS);

    private static final List<Item> FENCES = List.of(WoodYouDyeItems.RED_PLANK_FENCE, WoodYouDyeItems.ORANGE_PLANK_FENCE,
            WoodYouDyeItems.YELLOW_PLANK_FENCE, WoodYouDyeItems.LIME_PLANK_FENCE, WoodYouDyeItems.GREEN_PLANK_FENCE,
            WoodYouDyeItems.BLUE_PLANK_FENCE, WoodYouDyeItems.CYAN_PLANK_FENCE, WoodYouDyeItems.LIGHT_BLUE_PLANK_FENCE,
            WoodYouDyeItems.PURPLE_PLANK_FENCE, WoodYouDyeItems.PINK_PLANK_FENCE, WoodYouDyeItems.MAGENTA_PLANK_FENCE,
            WoodYouDyeItems.BROWN_PLANK_FENCE, WoodYouDyeItems.WHITE_PLANK_FENCE, WoodYouDyeItems.LIGHT_GRAY_PLANK_FENCE,
            WoodYouDyeItems.GRAY_PLANK_FENCE, WoodYouDyeItems.BLACK_PLANK_FENCE);

    private static final List<Item> FENCE_GATES = List.of(WoodYouDyeItems.RED_PLANK_FENCE_GATE, WoodYouDyeItems.ORANGE_PLANK_FENCE_GATE,
            WoodYouDyeItems.YELLOW_PLANK_FENCE_GATE, WoodYouDyeItems.LIME_PLANK_FENCE_GATE, WoodYouDyeItems.GREEN_PLANK_FENCE_GATE,
            WoodYouDyeItems.BLUE_PLANK_FENCE_GATE, WoodYouDyeItems.CYAN_PLANK_FENCE_GATE, WoodYouDyeItems.LIGHT_BLUE_PLANK_FENCE_GATE,
            WoodYouDyeItems.PURPLE_PLANK_FENCE_GATE, WoodYouDyeItems.PINK_PLANK_FENCE_GATE, WoodYouDyeItems.MAGENTA_PLANK_FENCE_GATE,
            WoodYouDyeItems.BROWN_PLANK_FENCE_GATE, WoodYouDyeItems.WHITE_PLANK_FENCE_GATE, WoodYouDyeItems.LIGHT_GRAY_PLANK_FENCE_GATE,
            WoodYouDyeItems.GRAY_PLANK_FENCE_GATE, WoodYouDyeItems.BLACK_PLANK_FENCE_GATE);

    private static final List<Item> DOORS = List.of(WoodYouDyeItems.RED_PLANK_DOOR, WoodYouDyeItems.ORANGE_PLANK_DOOR,
            WoodYouDyeItems.YELLOW_PLANK_DOOR, WoodYouDyeItems.LIME_PLANK_DOOR, WoodYouDyeItems.GREEN_PLANK_DOOR, WoodYouDyeItems.BLUE_PLANK_DOOR,
            WoodYouDyeItems.CYAN_PLANK_DOOR, WoodYouDyeItems.LIGHT_BLUE_PLANK_DOOR, WoodYouDyeItems.PURPLE_PLANK_DOOR,
            WoodYouDyeItems.PINK_PLANK_DOOR, WoodYouDyeItems.MAGENTA_PLANK_DOOR, WoodYouDyeItems.BROWN_PLANK_DOOR, WoodYouDyeItems.WHITE_PLANK_DOOR,
            WoodYouDyeItems.LIGHT_GRAY_PLANK_DOOR, WoodYouDyeItems.GRAY_PLANK_DOOR, WoodYouDyeItems.BLACK_PLANK_DOOR);

    private static final List<Item> TRAPDOORS = List.of(WoodYouDyeItems.RED_PLANK_TRAPDOOR, WoodYouDyeItems.ORANGE_PLANK_TRAPDOOR,
            WoodYouDyeItems.YELLOW_PLANK_TRAPDOOR, WoodYouDyeItems.LIME_PLANK_TRAPDOOR, WoodYouDyeItems.GREEN_PLANK_TRAPDOOR,
            WoodYouDyeItems.BLUE_PLANK_TRAPDOOR, WoodYouDyeItems.CYAN_PLANK_TRAPDOOR, WoodYouDyeItems.LIGHT_BLUE_PLANK_TRAPDOOR,
            WoodYouDyeItems.PURPLE_PLANK_TRAPDOOR, WoodYouDyeItems.PINK_PLANK_TRAPDOOR, WoodYouDyeItems.MAGENTA_PLANK_TRAPDOOR,
            WoodYouDyeItems.BROWN_PLANK_TRAPDOOR, WoodYouDyeItems.WHITE_PLANK_TRAPDOOR, WoodYouDyeItems.LIGHT_GRAY_PLANK_TRAPDOOR,
            WoodYouDyeItems.GRAY_PLANK_TRAPDOOR, WoodYouDyeItems.BLACK_PLANK_TRAPDOOR);

    private static final List<Item> BUTTONS = List.of(WoodYouDyeItems.RED_PLANK_BUTTON, WoodYouDyeItems.ORANGE_PLANK_BUTTON,
            WoodYouDyeItems.YELLOW_PLANK_BUTTON, WoodYouDyeItems.LIME_PLANK_BUTTON, WoodYouDyeItems.GREEN_PLANK_BUTTON,
            WoodYouDyeItems.BLUE_PLANK_BUTTON, WoodYouDyeItems.CYAN_PLANK_BUTTON, WoodYouDyeItems.LIGHT_BLUE_PLANK_BUTTON,
            WoodYouDyeItems.PURPLE_PLANK_BUTTON, WoodYouDyeItems.PINK_PLANK_BUTTON, WoodYouDyeItems.MAGENTA_PLANK_BUTTON,
            WoodYouDyeItems.BROWN_PLANK_BUTTON, WoodYouDyeItems.WHITE_PLANK_BUTTON, WoodYouDyeItems.LIGHT_GRAY_PLANK_BUTTON,
            WoodYouDyeItems.GRAY_PLANK_BUTTON, WoodYouDyeItems.BLACK_PLANK_BUTTON);

    private static final List<Item> PRESSURE_PLATES = List.of(WoodYouDyeItems.RED_PLANK_PRESSURE_PLATE, WoodYouDyeItems.ORANGE_PLANK_PRESSURE_PLATE,
            WoodYouDyeItems.YELLOW_PLANK_PRESSURE_PLATE, WoodYouDyeItems.LIME_PLANK_PRESSURE_PLATE, WoodYouDyeItems.GREEN_PLANK_PRESSURE_PLATE,
            WoodYouDyeItems.BLUE_PLANK_PRESSURE_PLATE, WoodYouDyeItems.CYAN_PLANK_PRESSURE_PLATE, WoodYouDyeItems.LIGHT_BLUE_PLANK_PRESSURE_PLATE,
            WoodYouDyeItems.PURPLE_PLANK_PRESSURE_PLATE, WoodYouDyeItems.PINK_PLANK_PRESSURE_PLATE, WoodYouDyeItems.MAGENTA_PLANK_PRESSURE_PLATE,
            WoodYouDyeItems.BROWN_PLANK_PRESSURE_PLATE, WoodYouDyeItems.WHITE_PLANK_PRESSURE_PLATE, WoodYouDyeItems.LIGHT_GRAY_PLANK_PRESSURE_PLATE,
            WoodYouDyeItems.GRAY_PLANK_PRESSURE_PLATE, WoodYouDyeItems.BLACK_PLANK_PRESSURE_PLATE);

    private static final List<Item> SIGNS = List.of(WoodYouDyeItems.RED_PLANK_SIGN, WoodYouDyeItems.ORANGE_PLANK_SIGN,
            WoodYouDyeItems.YELLOW_PLANK_SIGN, WoodYouDyeItems.LIME_PLANK_SIGN, WoodYouDyeItems.GREEN_PLANK_SIGN, WoodYouDyeItems.BLUE_PLANK_SIGN,
            WoodYouDyeItems.CYAN_PLANK_SIGN, WoodYouDyeItems.LIGHT_BLUE_PLANK_SIGN, WoodYouDyeItems.PURPLE_PLANK_SIGN,
            WoodYouDyeItems.PINK_PLANK_SIGN, WoodYouDyeItems.MAGENTA_PLANK_SIGN, WoodYouDyeItems.BROWN_PLANK_SIGN, WoodYouDyeItems.WHITE_PLANK_SIGN,
            WoodYouDyeItems.LIGHT_GRAY_PLANK_SIGN, WoodYouDyeItems.GRAY_PLANK_SIGN, WoodYouDyeItems.BLACK_PLANK_SIGN);

    private static final List<Item> HANGING_SIGNS = List.of(WoodYouDyeItems.RED_PLANK_HANGING_SIGN, WoodYouDyeItems.ORANGE_PLANK_HANGING_SIGN,
            WoodYouDyeItems.YELLOW_PLANK_HANGING_SIGN, WoodYouDyeItems.LIME_PLANK_HANGING_SIGN, WoodYouDyeItems.GREEN_PLANK_HANGING_SIGN,
            WoodYouDyeItems.BLUE_PLANK_HANGING_SIGN, WoodYouDyeItems.CYAN_PLANK_HANGING_SIGN, WoodYouDyeItems.LIGHT_BLUE_PLANK_HANGING_SIGN,
            WoodYouDyeItems.PURPLE_PLANK_HANGING_SIGN, WoodYouDyeItems.PINK_PLANK_HANGING_SIGN, WoodYouDyeItems.MAGENTA_PLANK_HANGING_SIGN,
            WoodYouDyeItems.BROWN_PLANK_HANGING_SIGN, WoodYouDyeItems.WHITE_PLANK_HANGING_SIGN, WoodYouDyeItems.LIGHT_GRAY_PLANK_HANGING_SIGN,
            WoodYouDyeItems.GRAY_PLANK_HANGING_SIGN, WoodYouDyeItems.BLACK_PLANK_HANGING_SIGN);

    private static final List<Block> RED_PLANKS = List.of(WoodYouDyeBlocks.RED_PLANKS, WoodYouDyeBlocks.RED_PLANK_SLAB,
            WoodYouDyeBlocks.RED_PLANK_STAIRS, WoodYouDyeBlocks.RED_PLANK_FENCE, WoodYouDyeBlocks.RED_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.RED_PLANK_BUTTON, WoodYouDyeBlocks.RED_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.RED_PLANK_DOOR,
            WoodYouDyeBlocks.RED_PLANK_TRAPDOOR, WoodYouDyeBlocks.RED_PLANK_SIGN, WoodYouDyeBlocks.RED_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.RED_PLANK_HANGING_SIGN, WoodYouDyeBlocks.RED_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> YELLOW_PLANKS = List.of(WoodYouDyeBlocks.YELLOW_PLANKS, WoodYouDyeBlocks.YELLOW_PLANK_SLAB,
            WoodYouDyeBlocks.YELLOW_PLANK_STAIRS, WoodYouDyeBlocks.YELLOW_PLANK_FENCE, WoodYouDyeBlocks.YELLOW_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.YELLOW_PLANK_BUTTON, WoodYouDyeBlocks.YELLOW_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.YELLOW_PLANK_DOOR,
            WoodYouDyeBlocks.YELLOW_PLANK_TRAPDOOR, WoodYouDyeBlocks.YELLOW_PLANK_SIGN, WoodYouDyeBlocks.YELLOW_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.YELLOW_PLANK_HANGING_SIGN, WoodYouDyeBlocks.YELLOW_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> ORANGE_PLANKS = List.of(WoodYouDyeBlocks.ORANGE_PLANKS, WoodYouDyeBlocks.ORANGE_PLANK_SLAB,
            WoodYouDyeBlocks.ORANGE_PLANK_STAIRS, WoodYouDyeBlocks.ORANGE_PLANK_FENCE, WoodYouDyeBlocks.ORANGE_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.ORANGE_PLANK_BUTTON, WoodYouDyeBlocks.ORANGE_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.ORANGE_PLANK_DOOR,
            WoodYouDyeBlocks.ORANGE_PLANK_TRAPDOOR, WoodYouDyeBlocks.ORANGE_PLANK_SIGN, WoodYouDyeBlocks.ORANGE_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.ORANGE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.ORANGE_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> LIME_PLANKS = List.of(WoodYouDyeBlocks.LIME_PLANKS, WoodYouDyeBlocks.LIME_PLANK_SLAB,
            WoodYouDyeBlocks.LIME_PLANK_STAIRS, WoodYouDyeBlocks.LIME_PLANK_FENCE, WoodYouDyeBlocks.LIME_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.LIME_PLANK_BUTTON, WoodYouDyeBlocks.LIME_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.LIME_PLANK_DOOR,
            WoodYouDyeBlocks.LIME_PLANK_TRAPDOOR, WoodYouDyeBlocks.LIME_PLANK_SIGN, WoodYouDyeBlocks.LIME_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.LIME_PLANK_HANGING_SIGN, WoodYouDyeBlocks.LIME_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> GREEN_PLANKS = List.of(WoodYouDyeBlocks.GREEN_PLANKS, WoodYouDyeBlocks.GREEN_PLANK_SLAB,
            WoodYouDyeBlocks.GREEN_PLANK_STAIRS, WoodYouDyeBlocks.GREEN_PLANK_FENCE, WoodYouDyeBlocks.GREEN_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.GREEN_PLANK_BUTTON, WoodYouDyeBlocks.GREEN_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.GREEN_PLANK_DOOR,
            WoodYouDyeBlocks.GREEN_PLANK_TRAPDOOR, WoodYouDyeBlocks.GREEN_PLANK_SIGN, WoodYouDyeBlocks.GREEN_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.GREEN_PLANK_HANGING_SIGN, WoodYouDyeBlocks.GREEN_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> BLUE_PLANKS = List.of(WoodYouDyeBlocks.BLUE_PLANKS, WoodYouDyeBlocks.BLUE_PLANK_SLAB,
            WoodYouDyeBlocks.BLUE_PLANK_STAIRS, WoodYouDyeBlocks.BLUE_PLANK_FENCE, WoodYouDyeBlocks.BLUE_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.BLUE_PLANK_BUTTON, WoodYouDyeBlocks.BLUE_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.BLUE_PLANK_DOOR,
            WoodYouDyeBlocks.BLUE_PLANK_TRAPDOOR, WoodYouDyeBlocks.BLUE_PLANK_SIGN, WoodYouDyeBlocks.BLUE_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.BLUE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.BLUE_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> CYAN_PLANKS = List.of(WoodYouDyeBlocks.CYAN_PLANKS, WoodYouDyeBlocks.CYAN_PLANK_SLAB,
            WoodYouDyeBlocks.CYAN_PLANK_STAIRS, WoodYouDyeBlocks.CYAN_PLANK_FENCE, WoodYouDyeBlocks.CYAN_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.CYAN_PLANK_BUTTON, WoodYouDyeBlocks.CYAN_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.CYAN_PLANK_DOOR,
            WoodYouDyeBlocks.CYAN_PLANK_TRAPDOOR, WoodYouDyeBlocks.CYAN_PLANK_SIGN, WoodYouDyeBlocks.CYAN_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.CYAN_PLANK_HANGING_SIGN, WoodYouDyeBlocks.CYAN_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> LIGHT_BLUE_PLANKS = List.of(WoodYouDyeBlocks.LIGHT_BLUE_PLANKS, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_SLAB,
            WoodYouDyeBlocks.LIGHT_BLUE_PLANK_STAIRS, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_FENCE, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.LIGHT_BLUE_PLANK_BUTTON, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_DOOR,
            WoodYouDyeBlocks.LIGHT_BLUE_PLANK_TRAPDOOR, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_SIGN, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.LIGHT_BLUE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> PINK_PLANKS = List.of(WoodYouDyeBlocks.PINK_PLANKS, WoodYouDyeBlocks.PINK_PLANK_SLAB,
            WoodYouDyeBlocks.PINK_PLANK_STAIRS, WoodYouDyeBlocks.PINK_PLANK_FENCE, WoodYouDyeBlocks.PINK_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.PINK_PLANK_BUTTON, WoodYouDyeBlocks.PINK_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.PINK_PLANK_DOOR,
            WoodYouDyeBlocks.PINK_PLANK_TRAPDOOR, WoodYouDyeBlocks.PINK_PLANK_SIGN, WoodYouDyeBlocks.PINK_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.PINK_PLANK_HANGING_SIGN, WoodYouDyeBlocks.PINK_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> PURPLE_PLANKS = List.of(WoodYouDyeBlocks.PURPLE_PLANKS, WoodYouDyeBlocks.PURPLE_PLANK_SLAB,
            WoodYouDyeBlocks.PURPLE_PLANK_STAIRS, WoodYouDyeBlocks.PURPLE_PLANK_FENCE, WoodYouDyeBlocks.PURPLE_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.PURPLE_PLANK_BUTTON, WoodYouDyeBlocks.PURPLE_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.PURPLE_PLANK_DOOR,
            WoodYouDyeBlocks.PURPLE_PLANK_TRAPDOOR, WoodYouDyeBlocks.PURPLE_PLANK_SIGN, WoodYouDyeBlocks.PURPLE_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.PURPLE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.PURPLE_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> MAGENTA_PLANKS = List.of(WoodYouDyeBlocks.MAGENTA_PLANKS, WoodYouDyeBlocks.MAGENTA_PLANK_SLAB,
            WoodYouDyeBlocks.MAGENTA_PLANK_STAIRS, WoodYouDyeBlocks.MAGENTA_PLANK_FENCE, WoodYouDyeBlocks.MAGENTA_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.MAGENTA_PLANK_BUTTON, WoodYouDyeBlocks.MAGENTA_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.MAGENTA_PLANK_DOOR,
            WoodYouDyeBlocks.MAGENTA_PLANK_TRAPDOOR, WoodYouDyeBlocks.MAGENTA_PLANK_SIGN, WoodYouDyeBlocks.MAGENTA_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.MAGENTA_PLANK_HANGING_SIGN, WoodYouDyeBlocks.MAGENTA_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> BROWN_PLANKS = List.of(WoodYouDyeBlocks.BROWN_PLANKS, WoodYouDyeBlocks.BROWN_PLANK_SLAB,
            WoodYouDyeBlocks.BROWN_PLANK_STAIRS, WoodYouDyeBlocks.BROWN_PLANK_FENCE, WoodYouDyeBlocks.BROWN_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.BROWN_PLANK_BUTTON, WoodYouDyeBlocks.BROWN_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.BROWN_PLANK_DOOR,
            WoodYouDyeBlocks.BROWN_PLANK_TRAPDOOR, WoodYouDyeBlocks.BROWN_PLANK_SIGN, WoodYouDyeBlocks.BROWN_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.BROWN_PLANK_HANGING_SIGN, WoodYouDyeBlocks.BROWN_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> WHITE_PLANKS = List.of(WoodYouDyeBlocks.WHITE_PLANKS, WoodYouDyeBlocks.WHITE_PLANK_SLAB,
            WoodYouDyeBlocks.WHITE_PLANK_STAIRS, WoodYouDyeBlocks.WHITE_PLANK_FENCE, WoodYouDyeBlocks.WHITE_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.WHITE_PLANK_BUTTON, WoodYouDyeBlocks.WHITE_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.WHITE_PLANK_DOOR,
            WoodYouDyeBlocks.WHITE_PLANK_TRAPDOOR, WoodYouDyeBlocks.WHITE_PLANK_SIGN, WoodYouDyeBlocks.WHITE_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.WHITE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.WHITE_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> LIGHT_GRAY_PLANKS = List.of(WoodYouDyeBlocks.LIGHT_GRAY_PLANKS, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_SLAB,
            WoodYouDyeBlocks.LIGHT_GRAY_PLANK_STAIRS, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_FENCE, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.LIGHT_GRAY_PLANK_BUTTON, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_DOOR,
            WoodYouDyeBlocks.LIGHT_GRAY_PLANK_TRAPDOOR, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_SIGN, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.LIGHT_GRAY_PLANK_HANGING_SIGN, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> GRAY_PLANKS = List.of(WoodYouDyeBlocks.GRAY_PLANKS, WoodYouDyeBlocks.GRAY_PLANK_SLAB,
            WoodYouDyeBlocks.GRAY_PLANK_STAIRS, WoodYouDyeBlocks.GRAY_PLANK_FENCE, WoodYouDyeBlocks.GRAY_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.GRAY_PLANK_BUTTON, WoodYouDyeBlocks.GRAY_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.GRAY_PLANK_DOOR,
            WoodYouDyeBlocks.GRAY_PLANK_TRAPDOOR, WoodYouDyeBlocks.GRAY_PLANK_SIGN, WoodYouDyeBlocks.GRAY_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.GRAY_PLANK_HANGING_SIGN, WoodYouDyeBlocks.GRAY_PLANK_WALL_HANGING_SIGN);

    private static final List<Block> BLACK_PLANKS = List.of(WoodYouDyeBlocks.BLACK_PLANKS, WoodYouDyeBlocks.BLACK_PLANK_SLAB,
            WoodYouDyeBlocks.BLACK_PLANK_STAIRS, WoodYouDyeBlocks.BLACK_PLANK_FENCE, WoodYouDyeBlocks.BLACK_PLANK_FENCE_GATE,
            WoodYouDyeBlocks.BLACK_PLANK_BUTTON, WoodYouDyeBlocks.BLACK_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.BLACK_PLANK_DOOR,
            WoodYouDyeBlocks.BLACK_PLANK_TRAPDOOR, WoodYouDyeBlocks.BLACK_PLANK_SIGN, WoodYouDyeBlocks.BLACK_PLANK_WALL_SIGN,
            WoodYouDyeBlocks.BLACK_PLANK_HANGING_SIGN, WoodYouDyeBlocks.BLACK_PLANK_WALL_HANGING_SIGN);

    private static final List<List<Block>> COLORS_OF_PLANKS = List.of(RED_PLANKS, YELLOW_PLANKS, ORANGE_PLANKS, LIME_PLANKS,
            GREEN_PLANKS, BLUE_PLANKS, CYAN_PLANKS, LIGHT_BLUE_PLANKS, PINK_PLANKS, PURPLE_PLANKS, MAGENTA_PLANKS,
            BROWN_PLANKS, WHITE_PLANKS, LIGHT_GRAY_PLANKS, GRAY_PLANKS, BLACK_PLANKS);

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(WYDLootTableProvider::new);
        pack.addProvider(WYDRecipeProvider::new);
        pack.addProvider(WYDItemTagProvider::new);
        pack.addProvider(WYDBlockTagProvider::new);
        pack.addProvider(WYDModelProvider::new);
    }

    private static class WYDModelProvider extends FabricModelProvider {
        private WYDModelProvider(FabricDataOutput generator) {
            super(generator);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
            for (List<Block> color : COLORS_OF_PLANKS) {
                for (int i = 0; i < color.size(); i++) {
                    Block block = color.get(i);
                    switch (block) {
                        case SlabBlock slabBlock -> {
                            Block planks = color.getFirst();
                            TextureMapping textureMapping = TextureMapping.cube(planks);
                            ResourceLocation bottomSlabID = ModelTemplates.SLAB_BOTTOM.create(slabBlock, textureMapping, blockModelGenerators.modelOutput);
                            ResourceLocation topSlabID = ModelTemplates.SLAB_TOP.create(slabBlock, textureMapping, blockModelGenerators.modelOutput);
                            ResourceLocation sourceBlockID = ModelTemplates.CUBE.getDefaultModelLocation(planks);
                            blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createSlab(slabBlock, bottomSlabID, topSlabID, sourceBlockID));
                        }
                        case StairBlock stairBlock -> {
                            Block planks = color.getFirst();
                            TextureMapping textureMapping = TextureMapping.cube(planks);
                            ResourceLocation innerStairsID = ModelTemplates.STAIRS_INNER.create(stairBlock, textureMapping, blockModelGenerators.modelOutput);
                            ResourceLocation straightStairsID = ModelTemplates.STAIRS_STRAIGHT.create(stairBlock, textureMapping, blockModelGenerators.modelOutput);
                            ResourceLocation outerStairsID = ModelTemplates.STAIRS_OUTER.create(stairBlock, textureMapping, blockModelGenerators.modelOutput);
                            blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createStairs(stairBlock, innerStairsID, straightStairsID, outerStairsID));
                        }
                        case FenceBlock fenceBlock -> {
                            Block planks = color.getFirst();
                            TextureMapping textureMapping = TextureMapping.cube(planks);
                            ResourceLocation postifiedFenceID = ModelTemplates.FENCE_POST.create(fenceBlock, textureMapping, blockModelGenerators.modelOutput);
                            ResourceLocation sidifiedFenceID = ModelTemplates.FENCE_SIDE.create(fenceBlock, textureMapping, blockModelGenerators.modelOutput);
                            ResourceLocation itemizedFenceID = ModelTemplates.FENCE_INVENTORY.create(fenceBlock, textureMapping, blockModelGenerators.modelOutput);
                            blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createFence(fenceBlock, postifiedFenceID, sidifiedFenceID));
                            blockModelGenerators.delegateItemModel(fenceBlock, itemizedFenceID);
                        }
                        case FenceGateBlock fenceGateBlock -> {
                            Block planks = color.getFirst();
                            TextureMapping textureMapping = TextureMapping.cube(planks);
                            ResourceLocation openGateID = ModelTemplates.FENCE_GATE_OPEN.create(fenceGateBlock, textureMapping, blockModelGenerators.modelOutput);
                            ResourceLocation closedGateID = ModelTemplates.FENCE_GATE_CLOSED.create(fenceGateBlock, textureMapping, blockModelGenerators.modelOutput);
                            ResourceLocation openWallGateID = ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGateBlock, textureMapping, blockModelGenerators.modelOutput);
                            ResourceLocation closedWallGateID = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGateBlock, textureMapping, blockModelGenerators.modelOutput);
                            blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createFenceGate(fenceGateBlock, openGateID, closedGateID, openWallGateID, closedWallGateID, true));
                        }
                        case ButtonBlock buttonBlock -> {
                            Block planks = color.getFirst();
                            TextureMapping textureMapping = TextureMapping.cube(planks);
                            ResourceLocation buttonID = ModelTemplates.BUTTON.create(buttonBlock, textureMapping, blockModelGenerators.modelOutput);
                            ResourceLocation pressedButtonID = ModelTemplates.BUTTON_PRESSED.create(buttonBlock, textureMapping, blockModelGenerators.modelOutput);
                            ResourceLocation buttonNightAtTheInventoryID = ModelTemplates.BUTTON_INVENTORY.create(buttonBlock, textureMapping, blockModelGenerators.modelOutput);
                            blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createButton(buttonBlock, buttonID, pressedButtonID));
                            blockModelGenerators.delegateItemModel(buttonBlock, buttonNightAtTheInventoryID);
                        }
                        case PressurePlateBlock pressurePlateBlock -> {
                            Block planks = color.getFirst();
                            TextureMapping textureMapping = TextureMapping.cube(planks);
                            ResourceLocation pressurePlateID = ModelTemplates.PRESSURE_PLATE_UP.create(pressurePlateBlock, textureMapping, blockModelGenerators.modelOutput);
                            ResourceLocation pressedPressurePlateID = ModelTemplates.PRESSURE_PLATE_DOWN.create(pressurePlateBlock, textureMapping, blockModelGenerators.modelOutput);
                            blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(pressurePlateBlock, pressurePlateID, pressedPressurePlateID));
                        }
                        case DoorBlock doorBlock -> blockModelGenerators.createDoor(doorBlock);
                        case TrapDoorBlock trapDoorBlock -> blockModelGenerators.createTrapdoor(trapDoorBlock);
                        case SignBlock signBlock -> {
                            Block planks = color.getFirst();
                            TextureMapping textureMapping = TextureMapping.cube(planks);
                            ResourceLocation particlesID = ModelTemplates.PARTICLE_ONLY.create(signBlock, textureMapping, blockModelGenerators.modelOutput);
                            blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(signBlock, particlesID));
                            blockModelGenerators.skipAutoItemBlock(signBlock);
                        }
                        default -> {
                            blockModelGenerators.createTrivialCube(block);
                        }
                    }
                }
            }
        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerators) {
            for (Item signItem : SIGNS) {
                itemModelGenerators.generateFlatItem(signItem, ModelTemplates.FLAT_ITEM);
            }
            for (Item signItem : HANGING_SIGNS) {
                itemModelGenerators.generateFlatItem(signItem, ModelTemplates.FLAT_ITEM);
            }
        }
    }

    private static class WYDLootTableProvider extends FabricBlockLootTableProvider {
        protected WYDLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            for (Block block : WoodYouDyeBlocks.blockHolder) {

                if (block instanceof DoorBlock doorBlock) {
                    add(doorBlock, createDoorTable(doorBlock));
                } else if (block instanceof SlabBlock slabBlock) {
                    add(slabBlock, createSlabItemTable(slabBlock));
                } else {
                    add(block, createSingleItemTable(block));
                }
            }
        }
    }

    public class WYDItemTagProvider extends FabricTagProvider<Item> {
        TagKey<Item> PLANKS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "planks"));
        TagKey<Item> SLAB_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "slabs"));
        TagKey<Item> STAIRS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "stairs"));
        TagKey<Item> FENCE_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "fences"));
        TagKey<Item> GATE_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "fence_gates"));
        TagKey<Item> DOOR_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "doors"));
        TagKey<Item> TRAPDOOR_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "trapdoors"));
        TagKey<Item> BUTTON_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "button"));
        TagKey<Item> PRESSURE_PLATE_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "pressure_plates"));
        TagKey<Item> SIGN_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "signs"));
        TagKey<Item> HANGING_SIGN_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "hanging_signs"));


        public WYDItemTagProvider(FabricDataOutput output, CompletableFuture lookupProvider) {
            super(output, Registries.ITEM, lookupProvider);
        }

        @Override
        public String getName() {
            return "WoodYouDyeItemTags";
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            PLANKS.forEach((item) -> getOrCreateTagBuilder(PLANKS_TAG).add(item));
            SLABS.forEach((item) -> getOrCreateTagBuilder(SLAB_TAG).add(item));
            STAIRS.forEach((item) -> getOrCreateTagBuilder(STAIRS_TAG).add(item));
            FENCES.forEach((item) -> getOrCreateTagBuilder(FENCE_TAG).add(item));
            FENCE_GATES.forEach((item) -> getOrCreateTagBuilder(GATE_TAG).add(item));
            DOORS.forEach((item) -> getOrCreateTagBuilder(DOOR_TAG).add(item));
            TRAPDOORS.forEach((item) -> getOrCreateTagBuilder(TRAPDOOR_TAG).add(item));
            BUTTONS.forEach((item) -> getOrCreateTagBuilder(BUTTON_TAG).add(item));
            PRESSURE_PLATES.forEach((item) -> getOrCreateTagBuilder(PRESSURE_PLATE_TAG).add(item));
            SIGNS.forEach((item) -> getOrCreateTagBuilder(SIGN_TAG).add(item));
            HANGING_SIGNS.forEach((item) -> getOrCreateTagBuilder(HANGING_SIGN_TAG).add(item));
            getOrCreateTagBuilder(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "mineable/axe")))
                    .addTag(PLANKS_TAG).addTag(SLAB_TAG).addTag(STAIRS_TAG).addTag(FENCE_TAG).addTag(GATE_TAG).addTag(DOOR_TAG).addTag(TRAPDOOR_TAG).addTag(BUTTON_TAG)
                    .addTag(PRESSURE_PLATE_TAG).addTag(SIGN_TAG).addTag(HANGING_SIGN_TAG);
            getOrCreateTagBuilder(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "fence_gates"))).addTag(GATE_TAG);
            getOrCreateTagBuilder(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "planks"))).addTag(PLANKS_TAG);
            getOrCreateTagBuilder(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "wooden_doors"))).addTag(DOOR_TAG);
            getOrCreateTagBuilder(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "wooden_fences"))).addTag(FENCE_TAG);
            getOrCreateTagBuilder(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "wooden_pressure_plates"))).addTag(PRESSURE_PLATE_TAG);
        }
    }

    public class WYDBlockTagProvider extends FabricTagProvider<Block> {
        TagKey<Block> PLANKS_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "planks"));
        TagKey<Block> SLAB_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "slabs"));
        TagKey<Block> STAIRS_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "stairs"));
        TagKey<Block> FENCE_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "fences"));
        TagKey<Block> GATE_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "fence_gates"));
        TagKey<Block> DOOR_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "doors"));
        TagKey<Block> TRAPDOOR_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "trapdoors"));
        TagKey<Block> BUTTON_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "button"));
        TagKey<Block> PRESSURE_PLATE_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "pressure_plates"));
        TagKey<Block> SIGN_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "signs"));
        TagKey<Block> HANGING_SIGN_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "hanging_signs"));
        private static final List<Block> PLANKS_BLOCK = List.of(WoodYouDyeBlocks.RED_PLANKS, WoodYouDyeBlocks.ORANGE_PLANKS,
                WoodYouDyeBlocks.YELLOW_PLANKS, WoodYouDyeBlocks.LIME_PLANKS, WoodYouDyeBlocks.GREEN_PLANKS, WoodYouDyeBlocks.BLUE_PLANKS,
                WoodYouDyeBlocks.CYAN_PLANKS, WoodYouDyeBlocks.LIGHT_BLUE_PLANKS, WoodYouDyeBlocks.PURPLE_PLANKS, WoodYouDyeBlocks.PINK_PLANKS,
                WoodYouDyeBlocks.MAGENTA_PLANKS, WoodYouDyeBlocks.BROWN_PLANKS, WoodYouDyeBlocks.WHITE_PLANKS, WoodYouDyeBlocks.LIGHT_GRAY_PLANKS,
                WoodYouDyeBlocks.GRAY_PLANKS, WoodYouDyeBlocks.BLACK_PLANKS);

        private static final List<Block> SLABS_BLOCK = List.of(WoodYouDyeBlocks.RED_PLANK_SLAB, WoodYouDyeBlocks.ORANGE_PLANK_SLAB,
                WoodYouDyeBlocks.YELLOW_PLANK_SLAB, WoodYouDyeBlocks.LIME_PLANK_SLAB, WoodYouDyeBlocks.GREEN_PLANK_SLAB, WoodYouDyeBlocks.BLUE_PLANK_SLAB,
                WoodYouDyeBlocks.CYAN_PLANK_SLAB, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_SLAB, WoodYouDyeBlocks.PURPLE_PLANK_SLAB,
                WoodYouDyeBlocks.PINK_PLANK_SLAB, WoodYouDyeBlocks.MAGENTA_PLANK_SLAB, WoodYouDyeBlocks.BROWN_PLANK_SLAB, WoodYouDyeBlocks.WHITE_PLANK_SLAB,
                WoodYouDyeBlocks.LIGHT_GRAY_PLANK_SLAB, WoodYouDyeBlocks.GRAY_PLANK_SLAB, WoodYouDyeBlocks.BLACK_PLANK_SLAB);

        private static final List<Block> STAIRS_BLOCK = List.of(WoodYouDyeBlocks.RED_PLANK_STAIRS, WoodYouDyeBlocks.ORANGE_PLANK_STAIRS,
                WoodYouDyeBlocks.YELLOW_PLANK_STAIRS, WoodYouDyeBlocks.LIME_PLANK_STAIRS, WoodYouDyeBlocks.GREEN_PLANK_STAIRS,
                WoodYouDyeBlocks.BLUE_PLANK_STAIRS, WoodYouDyeBlocks.CYAN_PLANK_STAIRS, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_STAIRS,
                WoodYouDyeBlocks.PURPLE_PLANK_STAIRS, WoodYouDyeBlocks.PINK_PLANK_STAIRS, WoodYouDyeBlocks.MAGENTA_PLANK_STAIRS,
                WoodYouDyeBlocks.BROWN_PLANK_STAIRS, WoodYouDyeBlocks.WHITE_PLANK_STAIRS, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_STAIRS,
                WoodYouDyeBlocks.GRAY_PLANK_STAIRS, WoodYouDyeBlocks.BLACK_PLANK_STAIRS);

        private static final List<Block> FENCES_BLOCK = List.of(WoodYouDyeBlocks.RED_PLANK_FENCE, WoodYouDyeBlocks.ORANGE_PLANK_FENCE,
                WoodYouDyeBlocks.YELLOW_PLANK_FENCE, WoodYouDyeBlocks.LIME_PLANK_FENCE, WoodYouDyeBlocks.GREEN_PLANK_FENCE,
                WoodYouDyeBlocks.BLUE_PLANK_FENCE, WoodYouDyeBlocks.CYAN_PLANK_FENCE, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_FENCE,
                WoodYouDyeBlocks.PURPLE_PLANK_FENCE, WoodYouDyeBlocks.PINK_PLANK_FENCE, WoodYouDyeBlocks.MAGENTA_PLANK_FENCE,
                WoodYouDyeBlocks.BROWN_PLANK_FENCE, WoodYouDyeBlocks.WHITE_PLANK_FENCE, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_FENCE,
                WoodYouDyeBlocks.GRAY_PLANK_FENCE, WoodYouDyeBlocks.BLACK_PLANK_FENCE);

        private static final List<Block> FENCE_GATES_BLOCK = List.of(WoodYouDyeBlocks.RED_PLANK_FENCE_GATE, WoodYouDyeBlocks.ORANGE_PLANK_FENCE_GATE,
                WoodYouDyeBlocks.YELLOW_PLANK_FENCE_GATE, WoodYouDyeBlocks.LIME_PLANK_FENCE_GATE, WoodYouDyeBlocks.GREEN_PLANK_FENCE_GATE,
                WoodYouDyeBlocks.BLUE_PLANK_FENCE_GATE, WoodYouDyeBlocks.CYAN_PLANK_FENCE_GATE, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_FENCE_GATE,
                WoodYouDyeBlocks.PURPLE_PLANK_FENCE_GATE, WoodYouDyeBlocks.PINK_PLANK_FENCE_GATE, WoodYouDyeBlocks.MAGENTA_PLANK_FENCE_GATE,
                WoodYouDyeBlocks.BROWN_PLANK_FENCE_GATE, WoodYouDyeBlocks.WHITE_PLANK_FENCE_GATE, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_FENCE_GATE,
                WoodYouDyeBlocks.GRAY_PLANK_FENCE_GATE, WoodYouDyeBlocks.BLACK_PLANK_FENCE_GATE);

        private static final List<Block> DOORS_BLOCK = List.of(WoodYouDyeBlocks.RED_PLANK_DOOR, WoodYouDyeBlocks.ORANGE_PLANK_DOOR,
                WoodYouDyeBlocks.YELLOW_PLANK_DOOR, WoodYouDyeBlocks.LIME_PLANK_DOOR, WoodYouDyeBlocks.GREEN_PLANK_DOOR, WoodYouDyeBlocks.BLUE_PLANK_DOOR,
                WoodYouDyeBlocks.CYAN_PLANK_DOOR, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_DOOR, WoodYouDyeBlocks.PURPLE_PLANK_DOOR,
                WoodYouDyeBlocks.PINK_PLANK_DOOR, WoodYouDyeBlocks.MAGENTA_PLANK_DOOR, WoodYouDyeBlocks.BROWN_PLANK_DOOR, WoodYouDyeBlocks.WHITE_PLANK_DOOR,
                WoodYouDyeBlocks.LIGHT_GRAY_PLANK_DOOR, WoodYouDyeBlocks.GRAY_PLANK_DOOR, WoodYouDyeBlocks.BLACK_PLANK_DOOR);

        private static final List<Block> TRAPDOORS_BLOCK = List.of(WoodYouDyeBlocks.RED_PLANK_TRAPDOOR, WoodYouDyeBlocks.ORANGE_PLANK_TRAPDOOR,
                WoodYouDyeBlocks.YELLOW_PLANK_TRAPDOOR, WoodYouDyeBlocks.LIME_PLANK_TRAPDOOR, WoodYouDyeBlocks.GREEN_PLANK_TRAPDOOR,
                WoodYouDyeBlocks.BLUE_PLANK_TRAPDOOR, WoodYouDyeBlocks.CYAN_PLANK_TRAPDOOR, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_TRAPDOOR,
                WoodYouDyeBlocks.PURPLE_PLANK_TRAPDOOR, WoodYouDyeBlocks.PINK_PLANK_TRAPDOOR, WoodYouDyeBlocks.MAGENTA_PLANK_TRAPDOOR,
                WoodYouDyeBlocks.BROWN_PLANK_TRAPDOOR, WoodYouDyeBlocks.WHITE_PLANK_TRAPDOOR, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_TRAPDOOR,
                WoodYouDyeBlocks.GRAY_PLANK_TRAPDOOR, WoodYouDyeBlocks.BLACK_PLANK_TRAPDOOR);

        private static final List<Block> BUTTONS_BLOCK = List.of(WoodYouDyeBlocks.RED_PLANK_BUTTON, WoodYouDyeBlocks.ORANGE_PLANK_BUTTON,
                WoodYouDyeBlocks.YELLOW_PLANK_BUTTON, WoodYouDyeBlocks.LIME_PLANK_BUTTON, WoodYouDyeBlocks.GREEN_PLANK_BUTTON,
                WoodYouDyeBlocks.BLUE_PLANK_BUTTON, WoodYouDyeBlocks.CYAN_PLANK_BUTTON, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_BUTTON,
                WoodYouDyeBlocks.PURPLE_PLANK_BUTTON, WoodYouDyeBlocks.PINK_PLANK_BUTTON, WoodYouDyeBlocks.MAGENTA_PLANK_BUTTON,
                WoodYouDyeBlocks.BROWN_PLANK_BUTTON, WoodYouDyeBlocks.WHITE_PLANK_BUTTON, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_BUTTON,
                WoodYouDyeBlocks.GRAY_PLANK_BUTTON, WoodYouDyeBlocks.BLACK_PLANK_BUTTON);

        private static final List<Block> PRESSURE_PLATES_BLOCK = List.of(WoodYouDyeBlocks.RED_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.ORANGE_PLANK_PRESSURE_PLATE,
                WoodYouDyeBlocks.YELLOW_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.LIME_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.GREEN_PLANK_PRESSURE_PLATE,
                WoodYouDyeBlocks.BLUE_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.CYAN_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_PRESSURE_PLATE,
                WoodYouDyeBlocks.PURPLE_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.PINK_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.MAGENTA_PLANK_PRESSURE_PLATE,
                WoodYouDyeBlocks.BROWN_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.WHITE_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_PRESSURE_PLATE,
                WoodYouDyeBlocks.GRAY_PLANK_PRESSURE_PLATE, WoodYouDyeBlocks.BLACK_PLANK_PRESSURE_PLATE);

        private static final List<Block> SIGNS_BLOCK = List.of(WoodYouDyeBlocks.RED_PLANK_SIGN, WoodYouDyeBlocks.ORANGE_PLANK_SIGN,
                WoodYouDyeBlocks.YELLOW_PLANK_SIGN, WoodYouDyeBlocks.LIME_PLANK_SIGN, WoodYouDyeBlocks.GREEN_PLANK_SIGN, WoodYouDyeBlocks.BLUE_PLANK_SIGN,
                WoodYouDyeBlocks.CYAN_PLANK_SIGN, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_SIGN, WoodYouDyeBlocks.PURPLE_PLANK_SIGN,
                WoodYouDyeBlocks.PINK_PLANK_SIGN, WoodYouDyeBlocks.MAGENTA_PLANK_SIGN, WoodYouDyeBlocks.BROWN_PLANK_SIGN, WoodYouDyeBlocks.WHITE_PLANK_SIGN,
                WoodYouDyeBlocks.LIGHT_GRAY_PLANK_SIGN, WoodYouDyeBlocks.GRAY_PLANK_SIGN, WoodYouDyeBlocks.BLACK_PLANK_SIGN, WoodYouDyeBlocks.RED_PLANK_WALL_SIGN, WoodYouDyeBlocks.ORANGE_PLANK_WALL_SIGN,
                WoodYouDyeBlocks.YELLOW_PLANK_WALL_SIGN, WoodYouDyeBlocks.LIME_PLANK_WALL_SIGN, WoodYouDyeBlocks.GREEN_PLANK_WALL_SIGN, WoodYouDyeBlocks.BLUE_PLANK_WALL_SIGN,
                WoodYouDyeBlocks.CYAN_PLANK_WALL_SIGN, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_WALL_SIGN, WoodYouDyeBlocks.PURPLE_PLANK_WALL_SIGN,
                WoodYouDyeBlocks.PINK_PLANK_WALL_SIGN, WoodYouDyeBlocks.MAGENTA_PLANK_WALL_SIGN, WoodYouDyeBlocks.BROWN_PLANK_WALL_SIGN, WoodYouDyeBlocks.WHITE_PLANK_WALL_SIGN,
                WoodYouDyeBlocks.LIGHT_GRAY_PLANK_WALL_SIGN, WoodYouDyeBlocks.GRAY_PLANK_WALL_SIGN, WoodYouDyeBlocks.BLACK_PLANK_WALL_SIGN);

        private static final List<Block> HANGING_SIGNS_BLOCK = List.of(WoodYouDyeBlocks.RED_PLANK_HANGING_SIGN, WoodYouDyeBlocks.ORANGE_PLANK_HANGING_SIGN,
                WoodYouDyeBlocks.YELLOW_PLANK_HANGING_SIGN, WoodYouDyeBlocks.LIME_PLANK_HANGING_SIGN, WoodYouDyeBlocks.GREEN_PLANK_HANGING_SIGN,
                WoodYouDyeBlocks.BLUE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.CYAN_PLANK_HANGING_SIGN, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_HANGING_SIGN,
                WoodYouDyeBlocks.PURPLE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.PINK_PLANK_HANGING_SIGN, WoodYouDyeBlocks.MAGENTA_PLANK_HANGING_SIGN,
                WoodYouDyeBlocks.BROWN_PLANK_HANGING_SIGN, WoodYouDyeBlocks.WHITE_PLANK_HANGING_SIGN, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_HANGING_SIGN,
                WoodYouDyeBlocks.GRAY_PLANK_HANGING_SIGN, WoodYouDyeBlocks.BLACK_PLANK_HANGING_SIGN, WoodYouDyeBlocks.RED_PLANK_WALL_HANGING_SIGN, WoodYouDyeBlocks.ORANGE_PLANK_WALL_HANGING_SIGN,
                WoodYouDyeBlocks.YELLOW_PLANK_WALL_HANGING_SIGN, WoodYouDyeBlocks.LIME_PLANK_WALL_HANGING_SIGN, WoodYouDyeBlocks.GREEN_PLANK_WALL_HANGING_SIGN,
                WoodYouDyeBlocks.BLUE_PLANK_WALL_HANGING_SIGN, WoodYouDyeBlocks.CYAN_PLANK_WALL_HANGING_SIGN, WoodYouDyeBlocks.LIGHT_BLUE_PLANK_WALL_HANGING_SIGN,
                WoodYouDyeBlocks.PURPLE_PLANK_WALL_HANGING_SIGN, WoodYouDyeBlocks.PINK_PLANK_WALL_HANGING_SIGN, WoodYouDyeBlocks.MAGENTA_PLANK_WALL_HANGING_SIGN,
                WoodYouDyeBlocks.BROWN_PLANK_WALL_HANGING_SIGN, WoodYouDyeBlocks.WHITE_PLANK_WALL_HANGING_SIGN, WoodYouDyeBlocks.LIGHT_GRAY_PLANK_WALL_HANGING_SIGN,
                WoodYouDyeBlocks.GRAY_PLANK_WALL_HANGING_SIGN, WoodYouDyeBlocks.BLACK_PLANK_WALL_HANGING_SIGN);


        public WYDBlockTagProvider(FabricDataOutput output, CompletableFuture lookupProvider) {
            super(output, Registries.BLOCK, lookupProvider);
        }

        @Override
        public String getName() {
            return "WoodYouDyeBlockTags";
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            PLANKS_BLOCK.forEach((block) -> getOrCreateTagBuilder(PLANKS_TAG).add(block));
            SLABS_BLOCK.forEach((block) -> getOrCreateTagBuilder(SLAB_TAG).add(block));
            STAIRS_BLOCK.forEach((block) -> getOrCreateTagBuilder(STAIRS_TAG).add(block));
            FENCES_BLOCK.forEach((block) -> getOrCreateTagBuilder(FENCE_TAG).add(block));
            FENCE_GATES_BLOCK.forEach((block) -> getOrCreateTagBuilder(GATE_TAG).add(block));
            DOORS_BLOCK.forEach((block) -> getOrCreateTagBuilder(DOOR_TAG).add(block));
            TRAPDOORS_BLOCK.forEach((block) -> getOrCreateTagBuilder(TRAPDOOR_TAG).add(block));
            BUTTONS_BLOCK.forEach((block) -> getOrCreateTagBuilder(BUTTON_TAG).add(block));
            PRESSURE_PLATES_BLOCK.forEach((block) -> getOrCreateTagBuilder(PRESSURE_PLATE_TAG).add(block));
            SIGNS_BLOCK.forEach((block) -> getOrCreateTagBuilder(SIGN_TAG).add(block));
            HANGING_SIGNS_BLOCK.forEach((block) -> getOrCreateTagBuilder(HANGING_SIGN_TAG).add(block));
            getOrCreateTagBuilder(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "mineable/axe")))
                    .addTag(PLANKS_TAG).addTag(SLAB_TAG).addTag(STAIRS_TAG).addTag(FENCE_TAG).addTag(GATE_TAG).addTag(DOOR_TAG).addTag(TRAPDOOR_TAG).addTag(BUTTON_TAG)
                    .addTag(PRESSURE_PLATE_TAG).addTag(SIGN_TAG).addTag(HANGING_SIGN_TAG);
            getOrCreateTagBuilder(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "fence_gates"))).addTag(GATE_TAG);
            getOrCreateTagBuilder(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "planks"))).addTag(PLANKS_TAG);
            getOrCreateTagBuilder(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "wooden_doors"))).addTag(DOOR_TAG);
            getOrCreateTagBuilder(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "wooden_fences"))).addTag(FENCE_TAG);
            getOrCreateTagBuilder(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "wooden_pressure_plates"))).addTag(PRESSURE_PLATE_TAG);
            getOrCreateTagBuilder(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "vanilla_planks"))).add(Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.ACACIA_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.MANGROVE_PLANKS, Blocks.BAMBOO_PLANKS, Blocks.CHERRY_PLANKS, Blocks.WARPED_PLANKS, Blocks.CRIMSON_PLANKS);
        }
    }

    public class WYDRecipeProvider extends RecipeProvider {
        TagKey<Item> VANILLA_PLANKS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("wood_you_dye", "vanilla_planks"));

        public WYDRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        public void buildRecipes(RecipeOutput recipeOutput) {
            for(int i = 0; i < 16; i++) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, PLANKS.get(i)).requires(VANILLA_PLANKS_TAG).requires(DYES_LIST.get(i)).unlockedBy(getHasName(DYES_LIST.get(i)), has(DYES_LIST.get(i))).save(recipeOutput);
                RecipeProvider.slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, SLABS.get(i), PLANKS.get(i));
                RecipeProvider.stairBuilder(STAIRS.get(i), Ingredient.of(PLANKS.get(i))).unlockedBy(getHasName(PLANKS.get(i)), has(PLANKS.get(i))).save(recipeOutput);
                RecipeProvider.fenceBuilder(FENCES.get(i), Ingredient.of(PLANKS.get(i))).unlockedBy(getHasName(PLANKS.get(i)), has(PLANKS.get(i))).save(recipeOutput);
                RecipeProvider.fenceGateBuilder(FENCE_GATES.get(i), Ingredient.of(PLANKS.get(i))).unlockedBy(getHasName(PLANKS.get(i)), has(PLANKS.get(i))).save(recipeOutput);
                RecipeProvider.doorBuilder(DOORS.get(i), Ingredient.of(PLANKS.get(i))).unlockedBy(getHasName(PLANKS.get(i)), has(PLANKS.get(i))).save(recipeOutput);
                RecipeProvider.trapdoorBuilder(TRAPDOORS.get(i), Ingredient.of(PLANKS.get(i))).unlockedBy(getHasName(PLANKS.get(i)), has(PLANKS.get(i))).save(recipeOutput);
                RecipeProvider.buttonBuilder(BUTTONS.get(i), Ingredient.of(PLANKS.get(i))).unlockedBy(getHasName(PLANKS.get(i)), has(PLANKS.get(i))).save(recipeOutput);
                RecipeProvider.pressurePlate(recipeOutput, PRESSURE_PLATES.get(i), PLANKS.get(i));
                RecipeProvider.signBuilder(SIGNS.get(i), Ingredient.of(PLANKS.get(i))).unlockedBy(getHasName(PLANKS.get(i)), has(PLANKS.get(i))).save(recipeOutput);
                RecipeProvider.hangingSign(recipeOutput, HANGING_SIGNS.get(i), PLANKS.get(i));
            }
        }

        @Override
        public String getName() {
            return "WoodYouDyeRecipes";
        }
    }
     */
}

package io.siuolplex.wood_you_dye.fabric;

import io.gremstudio.gremlib.Gremlib;
import io.gremstudio.gremlib.util.WoodSetInfo;
import io.siuolplex.wood_you_dye.AnotherWoodSet;
import io.siuolplex.wood_you_dye.WoodYouDye;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeItems;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeWoodSets;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SideChainPart;
import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class WoodYouDyeDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(WYDModelProvider::new);
        pack.addProvider(WYDItemTagProvider::new);
        pack.addProvider(WYDBlockTagProvider::new);
        pack.addProvider(WYDRecipeProvider.WYDRecipeWrapper::new);
        pack.addProvider(WYDBlockLootTableProvider::new);
        pack.addProvider(WYDEnglishLangProvider::new);
    }

    private static class WYDModelProvider extends FabricModelProvider {
        private WYDModelProvider(FabricPackOutput generator) {
            super(generator);
        }

        @Override
        public void generateBlockStateModels(@NonNull BlockModelGenerators blockModelGenerators) {
            for (AnotherWoodSet set : WoodYouDyeWoodSets.WOODSETS) {
                WoodSetInfo setInfo = set.getDetail();
                if (setInfo.hasLogs()) {
                    TextureMapping logMapping = createLogMapping(set, false);
                    TextureMapping strippedLogMappings = createLogMapping(set, true);
                    
                    generateLog(blockModelGenerators, set.BLOCKS.LOG, logMapping);
                    generateLog(blockModelGenerators, set.BLOCKS.STRIPPED_LOG, strippedLogMappings);

                    if (setInfo.hasWoods()) {
                        generateWood(blockModelGenerators, set.BLOCKS.WOOD, logMapping);
                        generateWood(blockModelGenerators, set.BLOCKS.STRIPPED_WOOD, strippedLogMappings);
                    }
                }
                
                Block planksBlock = set.BLOCKS.PLANKS;
                TextureMapping planksMapping = createPlanksMapping(set);

                blockModelGenerators.createTrivialBlock(planksBlock, _ -> TexturedModel.createAllSame(createPlanksMaterial(set)));

                generateSlab(blockModelGenerators, set.BLOCKS.PLANK_SLAB, planksMapping, planksBlock);
                generateStairs(blockModelGenerators, set.BLOCKS.PLANK_STAIRS, planksMapping);
                if (set.getDetail().getName().equals("bamboo")) {
                    TextureMapping fenceMapping = createFenceMapping(set);
                    generateCustomFence(blockModelGenerators, set.BLOCKS.PLANK_FENCE, fenceMapping);

                    TextureMapping gateMappings = createFenceGateMapping(set);
                    generateCustomFenceGate(blockModelGenerators, set.BLOCKS.PLANK_FENCE_GATE, gateMappings);
                } else {
                    generateFence(blockModelGenerators, set.BLOCKS.PLANK_FENCE, planksMapping);
                    generateFenceGate(blockModelGenerators, set.BLOCKS.PLANK_FENCE_GATE, planksMapping);
                }
                generatePressurePlate(blockModelGenerators, set.BLOCKS.PLANK_PRESSURE_PLATE, planksMapping);
                generateButton(blockModelGenerators, set.BLOCKS.PLANK_BUTTON, planksMapping);
                generateSign(blockModelGenerators, set.BLOCKS.PLANK_SIGN, set.BLOCKS.PLANK_WALL_SIGN, createPlanksMaterial(set));
                generateSign(blockModelGenerators, set.BLOCKS.PLANK_HANGING_SIGN, set.BLOCKS.PLANK_WALL_HANGING_SIGN, createPlanksMaterial(set));

                TextureMapping doorMapping = createDoorMapping(set);
                generateDoor(blockModelGenerators, set.BLOCKS.PLANK_DOOR, doorMapping);

                TextureMapping trapdoorMapping = createTrapdoorMapping(set);
                generateTrapdoor(blockModelGenerators, set.BLOCKS.PLANK_TRAPDOOR, trapdoorMapping);

                TextureMapping shelfMapping = createShelfMapping(set);
                generateShelf(blockModelGenerators, set.BLOCKS.PLANK_SHELF, shelfMapping);
                
                if (setInfo.canDoMosaic()) {
                    TextureMapping mosaicMapping = createMosaicMapping(set);

                    blockModelGenerators.createTrivialBlock(set.BLOCKS.MOSAIC, _ -> TexturedModel.createAllSame(createMosaicMaterial(set)));

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

                if (set.getDetail().hasBoat()) {
                    String name = set.getDetail().getBoat().name;
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
        
        public void generateCustomFence(BlockModelGenerators generator, Block fence, TextureMapping mapping) {
            MultiVariant post = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_POST.create(fence, mapping, generator.modelOutput));
            MultiVariant north = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_SIDE_NORTH.create(fence, mapping, generator.modelOutput));
            MultiVariant east = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_SIDE_EAST.create(fence, mapping, generator.modelOutput));
            MultiVariant south = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_SIDE_SOUTH.create(fence, mapping, generator.modelOutput));
            MultiVariant west = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_SIDE_WEST.create(fence, mapping, generator.modelOutput));
            generator.blockStateOutput.accept(BlockModelGenerators.createCustomFence(fence, post, north, east, south, west));
            Identifier inventory = ModelTemplates.CUSTOM_FENCE_INVENTORY.create(fence, mapping, generator.modelOutput);
            generator.registerSimpleItemModel(fence, inventory);

        }

        public void generateFenceGate(BlockModelGenerators generator, Block fenceGate, TextureMapping mapping) {
            MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_OPEN.create(fenceGate, mapping, generator.modelOutput));
            MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_CLOSED.create(fenceGate, mapping, generator.modelOutput));
            MultiVariant openWall = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGate, mapping, generator.modelOutput));
            MultiVariant closedWall = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGate, mapping, generator.modelOutput));
            generator.blockStateOutput.accept(BlockModelGenerators.createFenceGate(fenceGate, open, closed, openWall, closedWall, true));
        }
        
        public void generateCustomFenceGate(BlockModelGenerators generator, Block fenceGate, TextureMapping mapping) {
            MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_GATE_OPEN.create(fenceGate, mapping, generator.modelOutput));
            MultiVariant closed = BlockModelGenerators.plainVariant(
                    ModelTemplates.CUSTOM_FENCE_GATE_CLOSED.create(fenceGate, mapping, generator.modelOutput)
            );
            MultiVariant openWall = BlockModelGenerators.plainVariant(
                    ModelTemplates.CUSTOM_FENCE_GATE_WALL_OPEN.create(fenceGate, mapping, generator.modelOutput)
            );
            MultiVariant closedWall = BlockModelGenerators.plainVariant(
                    ModelTemplates.CUSTOM_FENCE_GATE_WALL_CLOSED.create(fenceGate, mapping, generator.modelOutput)
            );
            generator.blockStateOutput.accept(BlockModelGenerators.createFenceGate(fenceGate, open, closed, openWall, closedWall, false));
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

        public void generateSign(BlockModelGenerators generator, Block sign, Block wallSign, Material sourceMaterial) {
            MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.PARTICLE_ONLY.create(sign, TextureMapping.particle(sourceMaterial), generator.modelOutput));
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

        public TextureMapping createFenceMapping(AnotherWoodSet woodSet) {
            return (new TextureMapping()).put(TextureSlot.TEXTURE, createFenceMaterial(woodSet, false)).put(TextureSlot.PARTICLE, createFenceMaterial(woodSet, true));
        }

        public TextureMapping createFenceGateMapping(AnotherWoodSet woodSet) {
            return (new TextureMapping()).put(TextureSlot.TEXTURE, createFenceGateMaterial(woodSet, false)).put(TextureSlot.PARTICLE, createFenceMaterial(woodSet, true));
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

        public Material createFenceMaterial(AnotherWoodSet woodSet, boolean particle) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            Identifier id = Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/plank_fence_" + (particle ? "particle_" : "") + permutationName);
            return new Material(id);
        }

        public Material createFenceGateMaterial(AnotherWoodSet woodSet, boolean particle) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            Identifier id = Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/plank_fence_gate_" + (particle ? "particle_" : "") + permutationName);
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
        
        
        public Material generateMaterial(AnotherWoodSet woodSet) {
            return new Material(Identifier.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block/" + woodSet.getPermutationName() + "/planks_" + woodSet.getSetName().replace("dyed_", "")));
        }
    }

    public static class WYDItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
        List<WrappedTagBuilder<Item>> allTheTags = new ArrayList<>();

        public WYDItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
            super(output, registryLookupFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            
            WrappedTagBuilder<Item> nonFlammableWood = makeBuilder(quickKey(Identifier.withDefaultNamespace("non_flammable_wood")));
            WrappedTagBuilder<Item> planksThatBurn = makeBuilder(quickKey("c", "planks_that_burn"));

            WrappedTagBuilder<Item> logs = makeBuilder(quickKey("c", "logs")); // Specifically just logs.
            WrappedTagBuilder<Item> strippedLogs = makeBuilder(quickKey("c", "stripped_logs"));
            WrappedTagBuilder<Item> woods = makeBuilder(quickKey("c", "woods"));
            WrappedTagBuilder<Item> strippedWoods = makeBuilder(quickKey("c", "stripped_woods"));

            WrappedTagBuilder<Item> planks = makeBuilder(quickKey(Identifier.withDefaultNamespace("planks")));
            WrappedTagBuilder<Item> slabs = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_slabs")));
            WrappedTagBuilder<Item> stairs = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_stairs")));
            WrappedTagBuilder<Item> fences = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_fences"))); // You know it is broken in vanilla? It shouldnt be adding burn time for nether fences.
            WrappedTagBuilder<Item> fenceGates = makeBuilder(quickKey(Identifier.withDefaultNamespace("fence_gates")));
            WrappedTagBuilder<Item> doors = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_doors")));
            WrappedTagBuilder<Item> trapdoors = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_trapdoors")));
            WrappedTagBuilder<Item> buttons = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_buttons")));
            WrappedTagBuilder<Item> pressurePlates = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_pressure_plates")));
            WrappedTagBuilder<Item> signs = makeBuilder(quickKey(Identifier.withDefaultNamespace("signs")));
            WrappedTagBuilder<Item> hangingSigns = makeBuilder(quickKey(Identifier.withDefaultNamespace("hanging_signs")));
            WrappedTagBuilder<Item> shelves = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_shelves")));

            List<Item> mosaicSlabs = new ArrayList<>();
            List<Item> mosaicStairs = new ArrayList<>();

            List<Item> boats = new ArrayList<>();

            Map<String, WrappedTagBuilder<Item>> dyeds = new HashMap<>();
            for (DyeColor color : DyeColor.VALUES) {
                dyeds.put(color.getName(), makeBuilder(quickKey("c", "dyed/" + color.getName())));
            }

            for (AnotherWoodSet set : WoodYouDyeWoodSets.WOODSETS) {
                WrappedTagBuilder<Item> allSetItems = makeBuilder(quickKey(WoodYouDye.INSTANCE.createId(set.getVariantName() + "_" + set.getPermutationName() + "_wood_set")));

                if (set.getDetail().hasLogs()) {
                    WrappedTagBuilder<Item> setLogs = makeBuilder(quickKey(WoodYouDye.INSTANCE.createId(set.getVariantName() + "_" + set.getPermutationName() + "_" + set.getDetail().getLogs() + "s")));

                    setLogs.add(set.ITEMS.LOG);
                    logs.add(set.ITEMS.LOG);
                    allSetItems.add(set.ITEMS.LOG);

                    setLogs.add(set.ITEMS.STRIPPED_LOG);
                    strippedLogs.add(set.ITEMS.STRIPPED_LOG);
                    allSetItems.add(set.ITEMS.STRIPPED_LOG);

                    if (set.getDetail().hasWoods()) {
                        setLogs.add(set.ITEMS.WOOD);
                        woods.add(set.ITEMS.WOOD);
                        allSetItems.add(set.ITEMS.WOOD);

                        setLogs.add(set.ITEMS.STRIPPED_WOOD);
                        strippedWoods.add(set.ITEMS.STRIPPED_WOOD);
                        allSetItems.add(set.ITEMS.STRIPPED_WOOD);
                    }
                }

                if (!set.getDetail().canBurn()) {
                    planksThatBurn.add(set.ITEMS.PLANKS);
                }

                planks.add(set.ITEMS.PLANKS);
                allSetItems.add(set.ITEMS.PLANKS);

                slabs.add(set.ITEMS.PLANK_SLAB);
                allSetItems.add(set.ITEMS.PLANK_SLAB);

                stairs.add(set.ITEMS.PLANK_STAIRS);
                allSetItems.add(set.ITEMS.PLANK_STAIRS);

                fences.add(set.ITEMS.PLANK_FENCE);
                allSetItems.add(set.ITEMS.PLANK_FENCE);

                fenceGates.add(set.ITEMS.PLANK_FENCE_GATE);
                allSetItems.add(set.ITEMS.PLANK_FENCE_GATE);

                doors.add(set.ITEMS.PLANK_DOOR);
                allSetItems.add(set.ITEMS.PLANK_DOOR);

                trapdoors.add(set.ITEMS.PLANK_TRAPDOOR);
                allSetItems.add(set.ITEMS.PLANK_TRAPDOOR);

                buttons.add(set.ITEMS.PLANK_BUTTON);
                allSetItems.add(set.ITEMS.PLANK_BUTTON);

                pressurePlates.add(set.ITEMS.PLANK_PRESSURE_PLATE);
                allSetItems.add(set.ITEMS.PLANK_PRESSURE_PLATE);

                signs.add(set.ITEMS.PLANK_SIGN);
                allSetItems.add(set.ITEMS.PLANK_SIGN);

                hangingSigns.add(set.ITEMS.PLANK_HANGING_SIGN);
                allSetItems.add(set.ITEMS.PLANK_HANGING_SIGN);

                shelves.add(set.ITEMS.PLANK_SHELF);
                allSetItems.add(set.ITEMS.PLANK_SHELF);


                if (set.getDetail().canDoMosaic()) {

                    if (set.getDetail().canBurn()) {
                        planksThatBurn.add(set.ITEMS.PLANKS);
                    }
                    allSetItems.add(set.ITEMS.PLANKS);

                    slabs.add(set.ITEMS.MOSAIC_SLAB);
                    allSetItems.add(set.ITEMS.MOSAIC_SLAB);

                    stairs.add(set.ITEMS.MOSAIC_STAIRS);
                    allSetItems.add(set.ITEMS.MOSAIC_STAIRS);

                }

                if (set.getDetail().hasBoat()) {
                    boats.add(set.ITEMS.PLANK_BOAT);
                    allSetItems.add(set.ITEMS.PLANK_BOAT);

                    boats.add(set.ITEMS.PLANK_CHEST_BOAT);
                    allSetItems.add(set.ITEMS.PLANK_CHEST_BOAT);
                }

                dyeds.get(set.getPermutationName()).addWrapped(allSetItems);
                if (!set.getDetail().canBurn()) {
                    nonFlammableWood.addWrapped(allSetItems);
                }
            }

            allTheTags.forEach(tag -> tag.build(this::valueLookupBuilder));
        }

        public TagKey<Item> quickKey(Identifier id) {
            return TagKey.create(Registries.ITEM, id);
        }

        public TagKey<Item> quickKey(String namespace, String path) {
            return quickKey(Identifier.fromNamespaceAndPath(namespace, path));
        }
        
        public WrappedTagBuilder<Item> makeBuilder(TagKey<Item> key) {
            WrappedTagBuilder<Item> builder = new WrappedTagBuilder<>(key);
            allTheTags.add(builder);
            return builder;
        }
    }

    public static class WYDBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
        List<WrappedTagBuilder<Block>> allTheTags = new ArrayList<>();

        public WYDBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
            super(output, registryLookupFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            WrappedTagBuilder<Block> nonFlammableWood = makeBuilder(quickKey(Identifier.withDefaultNamespace("non_flammable_wood")));
            WrappedTagBuilder<Block> planksThatBurn = makeBuilder(quickKey("c", "planks_that_burn"));

            WrappedTagBuilder<Block> logs = makeBuilder(quickKey("c", "logs")); // Specifically just logs.
            WrappedTagBuilder<Block> strippedLogs = makeBuilder(quickKey("c", "stripped_logs"));
            WrappedTagBuilder<Block> woods = makeBuilder(quickKey("c", "woods"));
            WrappedTagBuilder<Block> strippedWoods = makeBuilder(quickKey("c", "stripped_woods"));

            WrappedTagBuilder<Block> planks = makeBuilder(quickKey(Identifier.withDefaultNamespace("planks")));
            WrappedTagBuilder<Block> slabs = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_slabs")));
            WrappedTagBuilder<Block> stairs = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_stairs")));
            WrappedTagBuilder<Block> fences = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_fences"))); // You know it is broken in vanilla? It shouldnt be adding burn time for nether fences.
            WrappedTagBuilder<Block> fenceGates = makeBuilder(quickKey(Identifier.withDefaultNamespace("fence_gates")));
            WrappedTagBuilder<Block> doors = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_doors")));
            WrappedTagBuilder<Block> trapdoors = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_trapdoors")));
            WrappedTagBuilder<Block> buttons = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_buttons")));
            WrappedTagBuilder<Block> pressurePlates = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_pressure_plates")));
            WrappedTagBuilder<Block> signs = makeBuilder(quickKey(Identifier.withDefaultNamespace("standing_signs")));
            WrappedTagBuilder<Block> wallSigns = makeBuilder(quickKey(Identifier.withDefaultNamespace("wall_signs")));
            WrappedTagBuilder<Block> wallHangingSigns = makeBuilder(quickKey(Identifier.withDefaultNamespace("wall_hanging_signs")));
            WrappedTagBuilder<Block> ceilingHangingSigns = makeBuilder(quickKey(Identifier.withDefaultNamespace("ceiling_hanging_signs")));
            WrappedTagBuilder<Block> shelves = makeBuilder(quickKey(Identifier.withDefaultNamespace("wooden_shelves")));

            List<Block> mosaicSlabs = new ArrayList<>();
            List<Block> mosaicStairs = new ArrayList<>();

            List<Block> boats = new ArrayList<>();

            Map<String, WrappedTagBuilder<Block>> dyeds = new HashMap<>();
            for (DyeColor color : DyeColor.VALUES) {
                dyeds.put(color.getName(), makeBuilder(quickKey("c", "dyed/" + color.getName())));
            }

            for (AnotherWoodSet set : WoodYouDyeWoodSets.WOODSETS) {
                WrappedTagBuilder<Block> allSetBlocks = makeBuilder(quickKey(WoodYouDye.INSTANCE.createId(set.getVariantName() + "_" + set.getPermutationName() + "_wood_set")));

                if (set.getDetail().hasLogs()) {
                    WrappedTagBuilder<Block> setLogs = makeBuilder(quickKey(WoodYouDye.INSTANCE.createId(set.getVariantName() + "_" + set.getPermutationName() + "_" + set.getDetail().getLogs() + "s")));

                    setLogs.add(set.BLOCKS.LOG);
                    logs.add(set.BLOCKS.LOG);
                    allSetBlocks.add(set.BLOCKS.LOG);

                    setLogs.add(set.BLOCKS.STRIPPED_LOG);
                    strippedLogs.add(set.BLOCKS.STRIPPED_LOG);
                    allSetBlocks.add(set.BLOCKS.STRIPPED_LOG);

                    if (set.getDetail().hasWoods()) {
                        setLogs.add(set.BLOCKS.WOOD);
                        woods.add(set.BLOCKS.WOOD);
                        allSetBlocks.add(set.BLOCKS.WOOD);

                        setLogs.add(set.BLOCKS.STRIPPED_WOOD);
                        strippedWoods.add(set.BLOCKS.STRIPPED_WOOD);
                        allSetBlocks.add(set.BLOCKS.STRIPPED_WOOD);
                    }
                }

                if (!set.getDetail().canBurn()) {
                    planksThatBurn.add(set.BLOCKS.PLANKS);
                }

                allSetBlocks.add(set.BLOCKS.PLANKS);

                slabs.add(set.BLOCKS.PLANK_SLAB);
                allSetBlocks.add(set.BLOCKS.PLANK_SLAB);

                stairs.add(set.BLOCKS.PLANK_STAIRS);
                allSetBlocks.add(set.BLOCKS.PLANK_STAIRS);

                fences.add(set.BLOCKS.PLANK_FENCE);
                allSetBlocks.add(set.BLOCKS.PLANK_FENCE);

                fenceGates.add(set.BLOCKS.PLANK_FENCE_GATE);
                allSetBlocks.add(set.BLOCKS.PLANK_FENCE_GATE);

                doors.add(set.BLOCKS.PLANK_DOOR);
                allSetBlocks.add(set.BLOCKS.PLANK_DOOR);

                trapdoors.add(set.BLOCKS.PLANK_TRAPDOOR);
                allSetBlocks.add(set.BLOCKS.PLANK_TRAPDOOR);

                buttons.add(set.BLOCKS.PLANK_BUTTON);
                allSetBlocks.add(set.BLOCKS.PLANK_BUTTON);

                pressurePlates.add(set.BLOCKS.PLANK_PRESSURE_PLATE);
                allSetBlocks.add(set.BLOCKS.PLANK_PRESSURE_PLATE);

                signs.add(set.BLOCKS.PLANK_SIGN);
                allSetBlocks.add(set.BLOCKS.PLANK_SIGN);

                wallSigns.add(set.BLOCKS.PLANK_WALL_SIGN);
                allSetBlocks.add(set.BLOCKS.PLANK_WALL_SIGN);

                ceilingHangingSigns.add(set.BLOCKS.PLANK_HANGING_SIGN);
                allSetBlocks.add(set.BLOCKS.PLANK_HANGING_SIGN);

                wallHangingSigns.add(set.BLOCKS.PLANK_WALL_HANGING_SIGN);
                allSetBlocks.add(set.BLOCKS.PLANK_WALL_HANGING_SIGN);

                shelves.add(set.BLOCKS.PLANK_SHELF);
                allSetBlocks.add(set.BLOCKS.PLANK_SHELF);


                if (set.getDetail().canDoMosaic()) {

                    if (set.getDetail().canBurn()) {
                        planksThatBurn.add(set.BLOCKS.PLANKS);
                    }
                    allSetBlocks.add(set.BLOCKS.PLANKS);

                    slabs.add(set.BLOCKS.MOSAIC_SLAB);
                    allSetBlocks.add(set.BLOCKS.MOSAIC_SLAB);

                    stairs.add(set.BLOCKS.MOSAIC_STAIRS);
                    allSetBlocks.add(set.BLOCKS.MOSAIC_STAIRS);
                }

                dyeds.get(set.getPermutationName()).addWrapped(allSetBlocks);
                if (!set.getDetail().canBurn()) {
                    nonFlammableWood.addWrapped(allSetBlocks);
                }
            }

            allTheTags.forEach(tag -> tag.build(this::valueLookupBuilder));
        }

        public TagKey<Block> quickKey(Identifier id) {
            return TagKey.create(Registries.BLOCK, id);
        }

        public TagKey<Block> quickKey(String namespace, String path) {
            return quickKey(Identifier.fromNamespaceAndPath(namespace, path));
        }

        public WrappedTagBuilder<Block> makeBuilder(TagKey<Block> key) {
            WrappedTagBuilder<Block> builder = new WrappedTagBuilder<>(key);
            allTheTags.add(builder);
            return builder;
        }
    }


    // Basically an easier way to do tag building in my opinion.
    public static class WrappedTagBuilder<T> {
        private TagKey<T> tagKey;
        private Set<T> tagMembers = new HashSet<>();
        private Set<TagKey<T>> additionalTags = new HashSet<>();

        public WrappedTagBuilder(TagKey<T> key) {
            this.tagKey = key;
        }

        public WrappedTagBuilder(ResourceKey<? extends Registry<T>> registry, Identifier id) {
            this(TagKey.create(registry, id));
        }

        public WrappedTagBuilder(ResourceKey<? extends Registry<T>> registry, String namespace, String location) {
            this(registry, Identifier.fromNamespaceAndPath(namespace, location));
        }

        public void add(T member) {
            tagMembers.add(member);
        }

        public void add(TagKey<T> key) {
            additionalTags.add(key);
        }

        public void addAll(T... members) {
            tagMembers.addAll(List.of(members));
        }

        public void addAll(TagKey<T>... keys) {
            additionalTags.addAll(List.of(keys));
        }

        public void addWrapped(WrappedTagBuilder<T> tag) {
            additionalTags.add(tag.tagKey);
        }

        // The difference is that this will copy everything in the other tag and throw it in this, and not just a reference.
        public void copyFromWrapped(WrappedTagBuilder<T> tag) {
            tagMembers.addAll(tag.tagMembers);
            additionalTags.addAll(tag.additionalTags);
        }

        public void build(Function<TagKey<T>, TagAppender<T, T>> tagBuild) {
            TagAppender<T, T> builder = tagBuild.apply(tagKey);

            if (!(tagMembers.isEmpty() && additionalTags.isEmpty())) {
                for (T member : tagMembers) {
                    builder.add(member);
                }

                for (TagKey<T> addTag : additionalTags) {
                    builder.addOptionalTag(addTag);
                }
            }


        }
    }

    public static class WYDRecipeProvider extends RecipeProvider {
        public static class WYDRecipeWrapper extends FabricRecipeProvider {
            public WYDRecipeWrapper(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
                super(output, registriesFuture);
            }

            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
                return new WYDRecipeProvider(registries, output);
            }

            @Override
            public String getName() {
                return "WYDRecipes";
            }
        }

        public WYDRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            super(registries, output);
            this.provider = registries;
        }

        HolderLookup.Provider provider;

        @Override
        public void buildRecipes() {
            Map<String, Item> colors = new HashMap<>();
            for (DyeColor value : DyeColor.values()) {
                switch (value) {
                    case RED -> colors.put(value.getName(), Items.RED_DYE);
                    case ORANGE -> colors.put(value.getName(), Items.ORANGE_DYE);
                    case YELLOW -> colors.put(value.getName(), Items.YELLOW_DYE);
                    case LIME -> colors.put(value.getName(), Items.LIME_DYE);
                    case GREEN -> colors.put(value.getName(), Items.GREEN_DYE);
                    case CYAN -> colors.put(value.getName(), Items.CYAN_DYE);
                    case LIGHT_BLUE -> colors.put(value.getName(), Items.LIGHT_BLUE_DYE);
                    case BLUE -> colors.put(value.getName(), Items.BLUE_DYE);
                    case PURPLE -> colors.put(value.getName(), Items.PURPLE_DYE);
                    case MAGENTA -> colors.put(value.getName(), Items.MAGENTA_DYE);
                    case PINK -> colors.put(value.getName(), Items.PINK_DYE);
                    case BROWN -> colors.put(value.getName(), Items.BROWN_DYE);
                    case WHITE -> colors.put(value.getName(), Items.WHITE_DYE);
                    case LIGHT_GRAY -> colors.put(value.getName(), Items.LIGHT_GRAY_DYE);
                    case GRAY -> colors.put(value.getName(), Items.GRAY_DYE);
                    case BLACK -> colors.put(value.getName(), Items.BLACK_DYE);
                }
            }
            
            for (AnotherWoodSet set : WoodYouDyeWoodSets.WOODSETS) {
                Item dye = colors.get(set.getPermutationName());

                if (set.getDetail().hasLogs()) {
                    shapeless(RecipeCategory.BUILDING_BLOCKS, set.ITEMS.LOG, 8).requires(Ingredient.of(provider.getOrThrow(TagKey.create(Registries.ITEM, Gremlib.INSTANCE.createId("wood/" + set.getDetail().getName() + "/log")))), 8).requires(dye).unlockedBy(getHasName(dye), has(dye)).save(output, "wood_you_dye:" + set.getSetName() + "_log_dyed");

                    if (set.getDetail().hasWoods()) {
                        woodFromLogs(set.ITEMS.LOG, set.ITEMS.WOOD);
                        woodFromLogs(set.ITEMS.STRIPPED_LOG, set.ITEMS.STRIPPED_WOOD);
                    }

                    planksFromLog(set.ITEMS.PLANKS, TagKey.create(Registries.ITEM, WoodYouDye.INSTANCE.createId(set.getVariantName() + "_" + set.getPermutationName() + "_" + set.getDetail().getLogs() + "s")), (set.getDetail().getName().equals("bamboo") ? 2 : 4));
                }

                shapeless(RecipeCategory.BUILDING_BLOCKS, set.ITEMS.PLANKS, 8).requires(Ingredient.of(provider.getOrThrow(TagKey.create(Registries.ITEM, Gremlib.INSTANCE.createId("wood/" + set.getDetail().getName() + "/planks")))), 8).requires(dye).unlockedBy(getHasName(dye), has(dye)).save(output, "wood_you_dye:" + set.getSetName() + "_planks_dyed");
                slab(RecipeCategory.BUILDING_BLOCKS, set.ITEMS.PLANK_SLAB, set.ITEMS.PLANKS);
                stairBuilder(set.ITEMS.PLANK_STAIRS, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(output);
                fenceBuilder(set.ITEMS.PLANK_FENCE, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(output);
                fenceGateBuilder(set.ITEMS.PLANK_FENCE_GATE, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(output);
                doorBuilder(set.ITEMS.PLANK_DOOR, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(output);
                trapdoorBuilder(set.ITEMS.PLANK_TRAPDOOR, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(output);
                buttonBuilder(set.ITEMS.PLANK_BUTTON, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(output);
                pressurePlate(set.ITEMS.PLANK_PRESSURE_PLATE, set.ITEMS.PLANKS);
                signBuilder(set.ITEMS.PLANK_SIGN, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(output);
                if (set.getDetail().hasLogs()) {
                    hangingSign(set.ITEMS.PLANK_HANGING_SIGN, set.ITEMS.STRIPPED_LOG);
                    shelf(set.ITEMS.PLANK_SHELF, set.ITEMS.STRIPPED_LOG);
                } else {
                    hangingSign(set.ITEMS.PLANK_HANGING_SIGN, set.ITEMS.PLANKS);
                    shelf(set.ITEMS.PLANK_SHELF, set.ITEMS.PLANKS);
                }


                if (set.getDetail().canDoMosaic()) {
                    mosaicBuilder(RecipeCategory.BUILDING_BLOCKS, set.ITEMS.MOSAIC, set.ITEMS.PLANK_SLAB);
                    slab(RecipeCategory.BUILDING_BLOCKS, set.ITEMS.MOSAIC_SLAB, set.ITEMS.MOSAIC);
                    stairBuilder(set.ITEMS.MOSAIC_STAIRS, Ingredient.of(set.ITEMS.MOSAIC)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(output);
                }

                if (set.getDetail().hasBoat()) {
                    woodenBoat(set.ITEMS.PLANK_BOAT, set.ITEMS.PLANKS);
                    chestBoat(set.ITEMS.PLANK_CHEST_BOAT, set.ITEMS.PLANK_BOAT);
                }
            }
        }
    }

    private static class WYDBlockLootTableProvider extends FabricBlockLootSubProvider {
        protected WYDBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(packOutput, registryLookup);
        }

        @Override
        public void generate() {
            for (Block block : AnotherWoodSet.Blocks.listOfBlocksIUseForDatagen) {
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

    private static class WYDEnglishLangProvider extends FabricLanguageProvider {
        protected WYDEnglishLangProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(packOutput, "en_us", registryLookup);
        }

        @Override
        public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
            for (Item item : WoodYouDyeItems.itemGroupHolder) {
                String[] split =  BuiltInRegistries.ITEM.getKey(item).getPath().split("_");
                for (int i = 0; i < split.length; i++) {
                    String partial = split[i];
                    String[] b = partial.split("");
                    b[0] = b[0].toUpperCase();
                    StringBuilder n = new StringBuilder();
                    for (String s : b) {
                        n.append(s);
                    }

                    split[i] = new String(n);
                }

                String name = String.join(" ", split);
                translationBuilder.add(item, name);
            }
        }
    }
}

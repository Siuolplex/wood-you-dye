package io.siuolplex.wood_you_dye.fabric;

import io.gremstudio.gremlib.Gremlib;
import io.gremstudio.gremlib.util.WoodSetInfo;
import io.siuolplex.wood_you_dye.AnotherWoodSet;
import io.siuolplex.wood_you_dye.WoodYouDye;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeItems;
import io.siuolplex.wood_you_dye.registry.WoodYouDyeWoodSets;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.*;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import net.minecraft.data.recipes.ShapelessRecipeBuilder;

import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;

public class WoodYouDyeDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(WYDModelProvider::new);
        pack.addProvider(WYDItemTagProvider::new);
        pack.addProvider(WYDBlockTagProvider::new);
        pack.addProvider(WYDRecipeProvider::new);
        pack.addProvider(WYDBlockLootTableProvider::new);
        pack.addProvider(WYDEnglishLangProvider::new);
    }

    private static class WYDModelProvider extends FabricModelProvider {
        private WYDModelProvider(FabricDataOutput generator) {
            super(generator);
        }

        @Override
        public void generateBlockStateModels(@NotNull BlockModelGenerators blockModelGenerators) {
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

                blockModelGenerators.createTrivialBlock(planksBlock, nice -> TexturedModel.createAllSame(createPlanksMaterial(set)));

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

                
                if (setInfo.canDoMosaic()) {
                    TextureMapping mosaicMapping = createMosaicMapping(set);

                    blockModelGenerators.createTrivialBlock(set.BLOCKS.MOSAIC, a -> TexturedModel.createAllSame(createMosaicMaterial(set)));

                    generateSlab(blockModelGenerators, set.BLOCKS.MOSAIC_SLAB,  mosaicMapping, set.BLOCKS.MOSAIC);
                    generateStairs(blockModelGenerators, set.BLOCKS.MOSAIC_STAIRS, mosaicMapping);

                }
            }
        }

        @Override
        public void generateItemModels(@NotNull ItemModelGenerators generators) {
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
            ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), createItemMapping(set, itemName), generators.output);
        }

        public void generateSlab(BlockModelGenerators generator, Block slab, TextureMapping mapping, Block sourceBlock) {
            ResourceLocation bottom = ModelTemplates.SLAB_BOTTOM.create(slab, mapping, generator.modelOutput);
            ResourceLocation top = ModelTemplates.SLAB_TOP.create(slab, mapping, generator.modelOutput);
            ResourceLocation source = ModelTemplates.CUBE.getDefaultModelLocation(sourceBlock);
            generator.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, bottom, top, source));
            generator.delegateItemModel(slab, bottom);
        }

        public void generateStairs(BlockModelGenerators generator, Block stairs, TextureMapping mapping) {
            ResourceLocation inner = ModelTemplates.STAIRS_INNER.create(stairs, mapping, generator.modelOutput);
            ResourceLocation straight = ModelTemplates.STAIRS_STRAIGHT.create(stairs, mapping, generator.modelOutput);
            ResourceLocation outer = ModelTemplates.STAIRS_OUTER.create(stairs, mapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, inner, straight, outer));
            generator.delegateItemModel(stairs, straight);
        }

        public void generateFence(BlockModelGenerators generator, Block fence, TextureMapping mapping) {
            ResourceLocation post = ModelTemplates.FENCE_POST.create(fence, mapping, generator.modelOutput);
            ResourceLocation side = ModelTemplates.FENCE_SIDE.create(fence, mapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createFence(fence, post, side));
            ResourceLocation inventory = ModelTemplates.FENCE_INVENTORY.create(fence, mapping, generator.modelOutput);
            generator.delegateItemModel(fence, inventory);
        }
        
        public void generateCustomFence(BlockModelGenerators generator, Block fence, TextureMapping mapping) {
            ResourceLocation post = ModelTemplates.CUSTOM_FENCE_POST.create(fence, mapping, generator.modelOutput);
            ResourceLocation north = ModelTemplates.CUSTOM_FENCE_SIDE_NORTH.create(fence, mapping, generator.modelOutput);
            ResourceLocation east = ModelTemplates.CUSTOM_FENCE_SIDE_EAST.create(fence, mapping, generator.modelOutput);
            ResourceLocation south = ModelTemplates.CUSTOM_FENCE_SIDE_SOUTH.create(fence, mapping, generator.modelOutput);
            ResourceLocation west = ModelTemplates.CUSTOM_FENCE_SIDE_WEST.create(fence, mapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createCustomFence(fence, post, north, east, south, west));
            ResourceLocation inventory = ModelTemplates.CUSTOM_FENCE_INVENTORY.create(fence, mapping, generator.modelOutput);
            generator.delegateItemModel(fence, inventory);

        }

        public void generateFenceGate(BlockModelGenerators generator, Block fenceGate, TextureMapping mapping) {
            ResourceLocation open = ModelTemplates.FENCE_GATE_OPEN.create(fenceGate, mapping, generator.modelOutput);
            ResourceLocation closed = ModelTemplates.FENCE_GATE_CLOSED.create(fenceGate, mapping, generator.modelOutput);
            ResourceLocation openWall = ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGate, mapping, generator.modelOutput);
            ResourceLocation closedWall = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGate, mapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createFenceGate(fenceGate, open, closed, openWall, closedWall, true));
        }
        
        public void generateCustomFenceGate(BlockModelGenerators generator, Block fenceGate, TextureMapping mapping) {
            ResourceLocation open = ModelTemplates.CUSTOM_FENCE_GATE_OPEN.create(fenceGate, mapping, generator.modelOutput);
            ResourceLocation closed = ModelTemplates.CUSTOM_FENCE_GATE_CLOSED.create(fenceGate, mapping, generator.modelOutput);
            ResourceLocation openWall = ModelTemplates.CUSTOM_FENCE_GATE_WALL_OPEN.create(fenceGate, mapping, generator.modelOutput);
            ResourceLocation closedWall =
                    ModelTemplates.CUSTOM_FENCE_GATE_WALL_CLOSED.create(fenceGate, mapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createFenceGate(fenceGate, open, closed, openWall, closedWall, false));
        }
        
        public void generatePressurePlate(BlockModelGenerators generator, Block pressurePlate, TextureMapping mapping) {
            ResourceLocation off = ModelTemplates.PRESSURE_PLATE_UP.create(pressurePlate, mapping, generator.modelOutput);
            ResourceLocation on = ModelTemplates.PRESSURE_PLATE_DOWN.create(pressurePlate, mapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(pressurePlate, off, on));
        }

        public void generateButton(BlockModelGenerators generator, Block button, TextureMapping mapping) {
            ResourceLocation normal = ModelTemplates.BUTTON.create(button, mapping, generator.modelOutput);
            ResourceLocation pressed = ModelTemplates.BUTTON_PRESSED.create(button, mapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createButton(button, normal, pressed));
            generator.delegateItemModel(button, ModelTemplates.BUTTON_INVENTORY.create(button, mapping, generator.modelOutput));
        }

        // Holy fuck thats a lot of models
        public void generateDoor(BlockModelGenerators generator, Block door, TextureMapping mapping) {
            ResourceLocation doorBottomLeft = ModelTemplates.DOOR_BOTTOM_LEFT.create(door, mapping, generator.modelOutput);
            ResourceLocation doorBottomLeftOpen = ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(door, mapping, generator.modelOutput);
            ResourceLocation doorBottomRight = ModelTemplates.DOOR_BOTTOM_RIGHT.create(door, mapping, generator.modelOutput);
            ResourceLocation doorBottomRightOpen = ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(door, mapping, generator.modelOutput);
            ResourceLocation doorTopLeft = ModelTemplates.DOOR_TOP_LEFT.create(door, mapping, generator.modelOutput);
            ResourceLocation doorTopLeftOpen = ModelTemplates.DOOR_TOP_LEFT_OPEN.create(door, mapping, generator.modelOutput);
            ResourceLocation doorTopRight = ModelTemplates.DOOR_TOP_RIGHT.create(door, mapping, generator.modelOutput);
            ResourceLocation doorTopRightOpen = ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(door, mapping, generator.modelOutput);
            //generator.registerSimpleFlatItemModel(door.asItem());
            generator.blockStateOutput.accept(BlockModelGenerators.createDoor(door, doorBottomLeft, doorBottomLeftOpen, doorBottomRight, doorBottomRightOpen, doorTopLeft, doorTopLeftOpen, doorTopRight, doorTopRightOpen));
        }

        // Orientable, does it REALLY matter if we use orientable or not?
        public void generateTrapdoor(BlockModelGenerators generator, Block trapdoor, TextureMapping mapping) {
            ResourceLocation top = ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(trapdoor, mapping, generator.modelOutput);
            ResourceLocation bottom = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(trapdoor, mapping, generator.modelOutput);
            ResourceLocation open = ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(trapdoor, mapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createOrientableTrapdoor(trapdoor, top, bottom, open));
            generator.delegateItemModel(trapdoor, bottom);
        }

        public void generateSign(BlockModelGenerators generator, Block sign, Block wallSign, ResourceLocation sourceMaterial) {
            ResourceLocation model = ModelTemplates.PARTICLE_ONLY.create(sign, TextureMapping.particle(sourceMaterial), generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(sign, model));
            generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(wallSign, model));
            //generator.registerSimpleFlatItemModel(sign.asItem());
        }

        public void generateLog(BlockModelGenerators generator, Block log, TextureMapping mapping) {
            ResourceLocation model = ModelTemplates.CUBE_COLUMN.create(log, mapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(log, model));
            generator.delegateItemModel(log, model);
        }

        public void generateWood(BlockModelGenerators generator, Block log, TextureMapping mapping) {
            TextureMapping woodMapping = mapping.copyAndUpdate(TextureSlot.END, mapping.get(TextureSlot.SIDE));
            ResourceLocation model = ModelTemplates.CUBE_COLUMN.create(log, woodMapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(log, model));
            generator.delegateItemModel(log, model);
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
            return TextureMapping.cube(createMosaicMaterial(woodSet));
        }

        public TextureMapping createDoorMapping(AnotherWoodSet woodSet) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            ResourceLocation doorTopID = ResourceLocation.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/door_top_" + permutationName);
            ResourceLocation doorBottomID = ResourceLocation.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/door_bottom_" + permutationName);
            return TextureMapping.door(doorTopID, doorBottomID);
        }

        public TextureMapping createLogMapping(AnotherWoodSet woodSet, boolean isStripped) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }

            String stripped = (isStripped) ? "stripped_" : "";

            ResourceLocation logID = ResourceLocation.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/" + stripped + "log_" + permutationName);
            ResourceLocation logTopID = ResourceLocation.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/" + stripped + "log_top_" + permutationName);
            return new TextureMapping()
                    .put(TextureSlot.SIDE, logID)
                    .put(TextureSlot.END, logTopID)
                    .put(TextureSlot.PARTICLE, logID);
        }

        public TextureMapping createTrapdoorMapping(AnotherWoodSet woodSet) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            ResourceLocation trapdoorId = ResourceLocation.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/trapdoor_" + permutationName);
            return TextureMapping.defaultTexture(trapdoorId);
        }

        public TextureMapping createItemMapping(AnotherWoodSet woodSet, String itemName) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            ResourceLocation itemID = ResourceLocation.fromNamespaceAndPath("wood_you_dye", "dyed_wood/item" + variantName + "/" + itemName + "_" + permutationName);
            return TextureMapping.layer0(itemID);
        }

        public ResourceLocation createPlanksMaterial(AnotherWoodSet woodSet) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            return ResourceLocation.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/planks_" + permutationName);
        }

        public ResourceLocation createFenceMaterial(AnotherWoodSet woodSet, boolean particle) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            return ResourceLocation.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/plank_fence_" + (particle ? "particle_" : "") + permutationName);
        }

        public ResourceLocation createFenceGateMaterial(AnotherWoodSet woodSet, boolean particle) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            return ResourceLocation.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/plank_fence_gate_" + (particle ? "particle_" : "") + permutationName);
        }
        

        public ResourceLocation createMosaicMaterial(AnotherWoodSet woodSet) {
            String variantName = woodSet.getVariantName();
            String permutationName = woodSet.getPermutationName();
            if (!variantName.isEmpty()) {
                variantName = "/" + variantName;
            }
            return ResourceLocation.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block" + variantName + "/mosaic_" + permutationName);
        }
        
        
        public Material generateMaterial(AnotherWoodSet woodSet) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath("wood_you_dye", "dyed_wood/block/" + woodSet.getPermutationName() + "/planks_" + woodSet.getSetName().replace("dyed_", ""));
            return new Material(ResourceLocation.withDefaultNamespace("textures/atlas/blocks.png"), id);
        }
    }

    public static class WYDItemTagProvider extends FabricTagProvider.ItemTagProvider {
        List<WrappedTagBuilder<Item>> allTheTags = new ArrayList<>();

        public WYDItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
            super(output, registryLookupFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            
            WrappedTagBuilder<Item> nonFlammableWood = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("non_flammable_wood")));
            WrappedTagBuilder<Item> planksThatBurn = makeBuilder(quickKey("c", "planks_that_burn"));

            WrappedTagBuilder<Item> logs = makeBuilder(quickKey("c", "logs")); // Specifically just logs.
            WrappedTagBuilder<Item> strippedLogs = makeBuilder(quickKey("c", "stripped_logs"));
            WrappedTagBuilder<Item> woods = makeBuilder(quickKey("c", "woods"));
            WrappedTagBuilder<Item> strippedWoods = makeBuilder(quickKey("c", "stripped_woods"));

            WrappedTagBuilder<Item> planks = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("planks")));
            WrappedTagBuilder<Item> slabs = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_slabs")));
            WrappedTagBuilder<Item> stairs = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_stairs")));
            WrappedTagBuilder<Item> fences = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_fences"))); // You know it is broken in vanilla? It shouldnt be adding burn time for nether fences.
            WrappedTagBuilder<Item> fenceGates = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("fence_gates")));
            WrappedTagBuilder<Item> doors = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_doors")));
            WrappedTagBuilder<Item> trapdoors = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_trapdoors")));
            WrappedTagBuilder<Item> buttons = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_buttons")));
            WrappedTagBuilder<Item> pressurePlates = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_pressure_plates")));
            WrappedTagBuilder<Item> signs = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("signs")));
            WrappedTagBuilder<Item> hangingSigns = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("hanging_signs")));

            List<Item> mosaicSlabs = new ArrayList<>();
            List<Item> mosaicStairs = new ArrayList<>();

            List<Item> boats = new ArrayList<>();

            Map<String, WrappedTagBuilder<Item>> dyeds = new HashMap<>();
            for (DyeColor color : DyeColor.values()) {
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

            allTheTags.forEach(tag -> tag.build(this::getOrCreateTagBuilder));
        }

        public TagKey<Item> quickKey(ResourceLocation id) {
            return TagKey.create(Registries.ITEM, id);
        }

        public TagKey<Item> quickKey(String namespace, String path) {
            return quickKey(ResourceLocation.fromNamespaceAndPath(namespace, path));
        }
        
        public WrappedTagBuilder<Item> makeBuilder(TagKey<Item> key) {
            WrappedTagBuilder<Item> builder = new WrappedTagBuilder<>(key);
            allTheTags.add(builder);
            return builder;
        }
    }

    public static class WYDBlockTagProvider extends FabricTagProvider.BlockTagProvider {
        List<WrappedTagBuilder<Block>> allTheTags = new ArrayList<>();

        public WYDBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
            super(output, registryLookupFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            WrappedTagBuilder<Block> nonFlammableWood = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("non_flammable_wood")));
            WrappedTagBuilder<Block> planksThatBurn = makeBuilder(quickKey("c", "planks_that_burn"));

            WrappedTagBuilder<Block> logs = makeBuilder(quickKey("c", "logs")); // Specifically just logs.
            WrappedTagBuilder<Block> strippedLogs = makeBuilder(quickKey("c", "stripped_logs"));
            WrappedTagBuilder<Block> woods = makeBuilder(quickKey("c", "woods"));
            WrappedTagBuilder<Block> strippedWoods = makeBuilder(quickKey("c", "stripped_woods"));

            WrappedTagBuilder<Block> planks = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("planks")));
            WrappedTagBuilder<Block> slabs = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_slabs")));
            WrappedTagBuilder<Block> stairs = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_stairs")));
            WrappedTagBuilder<Block> fences = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_fences"))); // You know it is broken in vanilla? It shouldnt be adding burn time for nether fences.
            WrappedTagBuilder<Block> fenceGates = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("fence_gates")));
            WrappedTagBuilder<Block> doors = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_doors")));
            WrappedTagBuilder<Block> trapdoors = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_trapdoors")));
            WrappedTagBuilder<Block> buttons = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_buttons")));
            WrappedTagBuilder<Block> pressurePlates = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wooden_pressure_plates")));
            WrappedTagBuilder<Block> signs = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("standing_signs")));
            WrappedTagBuilder<Block> wallSigns = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wall_signs")));
            WrappedTagBuilder<Block> wallHangingSigns = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("wall_hanging_signs")));
            WrappedTagBuilder<Block> ceilingHangingSigns = makeBuilder(quickKey(ResourceLocation.withDefaultNamespace("ceiling_hanging_signs")));

            List<Block> mosaicSlabs = new ArrayList<>();
            List<Block> mosaicStairs = new ArrayList<>();

            List<Block> boats = new ArrayList<>();

            Map<String, WrappedTagBuilder<Block>> dyeds = new HashMap<>();
            for (DyeColor color : DyeColor.values()) {
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

            allTheTags.forEach(tag -> tag.build(this::getOrCreateTagBuilder));
        }

        public TagKey<Block> quickKey(ResourceLocation id) {
            return TagKey.create(Registries.BLOCK, id);
        }

        public TagKey<Block> quickKey(String namespace, String path) {
            return quickKey(ResourceLocation.fromNamespaceAndPath(namespace, path));
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

        public WrappedTagBuilder(ResourceKey<? extends Registry<T>> registry, ResourceLocation id) {
            this(TagKey.create(registry, id));
        }

        public WrappedTagBuilder(ResourceKey<? extends Registry<T>> registry, String namespace, String location) {
            this(registry, ResourceLocation.fromNamespaceAndPath(namespace, location));
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

        public void build(Function<TagKey<T>, FabricTagProvider<T>.FabricTagBuilder> tagBuild) {
            FabricTagProvider<T>.FabricTagBuilder builder = tagBuild.apply(tagKey);

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

    public static class WYDRecipeProvider extends FabricRecipeProvider {
        public WYDRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
            try {
                this.provider = registriesFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        HolderLookup.Provider provider;

        @Override
        public void buildRecipes(RecipeOutput recipeOutput) {
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
                    shapeless(RecipeCategory.BUILDING_BLOCKS, set.ITEMS.LOG, 8).requires(Ingredient.of(TagKey.create(Registries.ITEM, Gremlib.INSTANCE.createId("wood/" + set.getDetail().getName() + "/log"))), 8).requires(dye).unlockedBy(getHasName(dye), has(dye)).save(recipeOutput, "wood_you_dye:" + set.getSetName() + "_log_dyed");

                    if (set.getDetail().hasWoods()) {
                        woodFromLogs(recipeOutput, set.ITEMS.LOG, set.ITEMS.WOOD);
                        woodFromLogs(recipeOutput, set.ITEMS.STRIPPED_LOG, set.ITEMS.STRIPPED_WOOD);
                    }

                    planksFromLog(recipeOutput, set.ITEMS.PLANKS, TagKey.create(Registries.ITEM, WoodYouDye.INSTANCE.createId(set.getVariantName() + "_" + set.getPermutationName() + "_" + set.getDetail().getLogs() + "s")), (set.getDetail().getName().equals("bamboo") ? 2 : 4));
                }

                shapeless(RecipeCategory.BUILDING_BLOCKS, set.ITEMS.PLANKS, 8).requires(Ingredient.of(TagKey.create(Registries.ITEM, Gremlib.INSTANCE.createId("wood/" + set.getDetail().getName() + "/planks"))), 8).requires(dye).unlockedBy(getHasName(dye), has(dye)).save(recipeOutput, "wood_you_dye:" + set.getSetName() + "_planks_dyed");
                slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, set.ITEMS.PLANK_SLAB, set.ITEMS.PLANKS);
                stairBuilder(set.ITEMS.PLANK_STAIRS, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(recipeOutput);
                fenceBuilder(set.ITEMS.PLANK_FENCE, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(recipeOutput);
                fenceGateBuilder(set.ITEMS.PLANK_FENCE_GATE, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(recipeOutput);
                doorBuilder(set.ITEMS.PLANK_DOOR, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(recipeOutput);
                trapdoorBuilder(set.ITEMS.PLANK_TRAPDOOR, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(recipeOutput);
                buttonBuilder(set.ITEMS.PLANK_BUTTON, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(recipeOutput);
                pressurePlate(recipeOutput, set.ITEMS.PLANK_PRESSURE_PLATE, set.ITEMS.PLANKS);
                signBuilder(set.ITEMS.PLANK_SIGN, Ingredient.of(set.ITEMS.PLANKS)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(recipeOutput);
                if (set.getDetail().hasLogs()) {
                    hangingSign(recipeOutput, set.ITEMS.PLANK_HANGING_SIGN, set.ITEMS.STRIPPED_LOG);
                } else {
                    hangingSign(recipeOutput, set.ITEMS.PLANK_HANGING_SIGN, set.ITEMS.PLANKS);
                }


                if (set.getDetail().canDoMosaic()) {
                    mosaicBuilder(recipeOutput, RecipeCategory.BUILDING_BLOCKS, set.ITEMS.MOSAIC, set.ITEMS.PLANK_SLAB);
                    slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, set.ITEMS.MOSAIC_SLAB, set.ITEMS.MOSAIC);
                    stairBuilder(set.ITEMS.MOSAIC_STAIRS, Ingredient.of(set.ITEMS.MOSAIC)).unlockedBy(getHasName(set.ITEMS.PLANKS), has(set.ITEMS.PLANKS)).save(recipeOutput);
                }

                if (set.getDetail().hasBoat()) {
                    woodenBoat(recipeOutput, set.ITEMS.PLANK_BOAT, set.ITEMS.PLANKS);
                    chestBoat(recipeOutput, set.ITEMS.PLANK_CHEST_BOAT, set.ITEMS.PLANK_BOAT);
                }
            }
        }
    }

    private static class WYDBlockLootTableProvider extends FabricBlockLootTableProvider {
        protected WYDBlockLootTableProvider(FabricDataOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
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
        protected WYDEnglishLangProvider(FabricDataOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
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

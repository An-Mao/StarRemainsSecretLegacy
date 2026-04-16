package dev.anye.mc.srsl.datagen;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.block.BlockReg;
import dev.anye.mc.srsl.item.ItemReg;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class _ModelProvider extends ModelProvider {
    public _ModelProvider(PackOutput output) {
        super(output, SRSL.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(BlockReg.STAR_REMAINS.get());
        registerItemModels(itemModels);
    }

    private void registerItemModels(ItemModelGenerators itemModels){
        handheldItem(itemModels, ItemReg.RevelationStone);
        handheldItem(itemModels, ItemReg.STAR_REMAINS_ESSENCE);
        handheldItem(itemModels, ItemReg.STRUCTURE_SCROLL);
    }

    private <T extends Block> void simpleBlockWithItem(BlockModelGenerators blockModels, DeferredBlock<T> block) {
        blockModels.createTrivialCube(block.get());
    }
    private <T extends Item> void basicItem(ItemModelGenerators itemModels, DeferredItem<T> item){
        itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
    }

    private <T extends Item> void handheldItem(ItemModelGenerators itemModels, DeferredHolder<Item,T> item){
        TextureMapping textureMapping = new TextureMapping();
        textureMapping.put(TextureSlot.LAYER0, new Material(itemLocation(getItemName(item.get()))));
        itemModels.itemModelOutput.accept(item.get(), new CuboidItemModelWrapper.Unbaked(ModelTemplates.FLAT_HANDHELD_ITEM.create(item.get(), textureMapping, itemModels.modelOutput),Optional.empty(), List.of()));
    }


    private Identifier mcLoc(String path){
        return Identifier.withDefaultNamespace(path);
    }

    private String getBlockName(Block block){
        Identifier location = BuiltInRegistries.BLOCK.getKey(block);
        return location.getPath();
    }

    private String getItemName(Item item){
        Identifier location = BuiltInRegistries.ITEM.getKey(item);
        return location.getPath();
    }

    private Identifier blockLocation(String modelName){
        return Identifier.fromNamespaceAndPath(SRSL.MOD_ID, "block/" + modelName);
    }

    private Identifier itemLocation(String modelName){
        return Identifier.fromNamespaceAndPath(SRSL.MOD_ID, "item/" + modelName);
    }

	public Identifier createFlatItemModel(ItemModelGenerators itemModels, Item item, String suffix,
			ModelTemplate modelTemplate) {
		return modelTemplate.create(getModelLocation(item, suffix),
				TextureMapping.layer0(new Material(getModelLocation(item, suffix))), itemModels.modelOutput);
	}

    public Identifier getModelLocation(Item item, String suffix) {
        Identifier resourcelocation = Identifier.fromNamespaceAndPath(SRSL.MOD_ID, getItemName(item));
        return resourcelocation.withPath((p_386751_) -> "item/" + p_386751_ + suffix);
    }

    // Override methods
    @Override
    protected @NotNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.listElements().filter(
            blockReference -> Optional.of(BuiltInRegistries.BLOCK.getKey(blockReference.value()))
                .filter(resourceLocation -> resourceLocation.getNamespace().equals(SRSL.MOD_ID)).isPresent());
    }

    @Override
    protected @NotNull Stream<? extends Holder<Item>> getKnownItems() {
        return BuiltInRegistries.ITEM.listElements().filter(itemReference -> Optional.of(BuiltInRegistries.ITEM.getKey(itemReference.value()))
        .filter(resourceLocation -> resourceLocation.getNamespace().equals(SRSL.MOD_ID)).isPresent());
    }
}

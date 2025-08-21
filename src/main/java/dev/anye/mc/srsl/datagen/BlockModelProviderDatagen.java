package dev.anye.mc.srsl.datagen;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.block.BlockReg;
import dev.anye.mc.srsl.item.RevelationStoneItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BlockModelProviderDatagen extends BlockModelProvider {
    public BlockModelProviderDatagen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SRSL.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simple(BlockReg.STAR_REMAINS);
    }

    private BlockModelBuilder simple(DeferredHolder<Block, ? extends Block> block){
        return withExistingParent(block.getId().getPath(), ResourceLocation.withDefaultNamespace("block/cube_all")).texture("all",ResourceLocation.fromNamespaceAndPath(SRSL.MOD_ID,"block/"+block.getId().getPath()));
    }
}

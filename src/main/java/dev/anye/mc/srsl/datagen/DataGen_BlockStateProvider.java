package dev.anye.mc.srsl.datagen;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.block.BlockReg;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DataGen_BlockStateProvider extends BlockStateProvider {
    public DataGen_BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SRSL.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //simpleBlock(Blocks.INDEX.get(), new ModelFile.UncheckedModelFile(modLoc("block/index")));
        simpleBlockWithItem(BlockReg.STAR_REMAINS.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/star_remains")));
    }


    private <T extends Block> void blockWithItem(DeferredHolder<Block,T> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(),cubeAll(blockRegistryObject.get()));
    }
}

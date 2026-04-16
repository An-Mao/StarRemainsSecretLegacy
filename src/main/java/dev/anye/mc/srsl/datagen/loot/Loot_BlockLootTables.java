package dev.anye.mc.srsl.datagen.loot;

import dev.anye.mc.srsl.block.BlockReg;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Loot_BlockLootTables extends BlockLootSubProvider {
    public Loot_BlockLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(),provider);
    }

    @Override
    protected void generate() {
        this.dropSelf(BlockReg.STAR_REMAINS.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BlockReg.BLOCKS.getEntries().stream().map(blockDeferredHolder -> blockDeferredHolder.getDelegate().value())::iterator;
    }
    /*
    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BlockRegister.BLOCKS.getEntries().stream().map(DeferredHolder::get)::iterator;
    }

 */
}

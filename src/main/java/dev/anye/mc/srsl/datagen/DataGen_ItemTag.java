package dev.anye.mc.srsl.datagen;

import dev.anye.mc.srsl.SRSL;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DataGen_ItemTag extends ItemTagsProvider {
    public DataGen_ItemTag(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> bCompletableFuture) {
        super(packOutput, p_275729_, SRSL.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }
}

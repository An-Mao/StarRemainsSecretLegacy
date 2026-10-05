package dev.anye.mc.srsl.datagen.loot;

import dev.anye.mc.srsl.reg.block.BlockReg;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Set;

public class Loot_BlockLootTables extends BlockLootSubProvider {
	public Loot_BlockLootTables(LootTableSubProvider.Context context) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), context);
	}

	public static MultiRegistryBootstrap create() {
		return new MultiRegistryBootstrap() {
			@Override
			public @NonNull Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
				return Set.of(Registries.LOOT_TABLE);
			}

			@Override
			public void run(MultiRegistryBootstrap.@NonNull BootstrapGetter registries) {
				new LootTableProvider(
						BuiltInLootTables.all(),
						List.of(new LootTableProvider.SubProviderEntry(Loot_BlockLootTables::new, LootContextParamSets.BLOCK))
				);
			}
		};
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

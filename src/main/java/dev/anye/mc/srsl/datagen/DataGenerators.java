package dev.anye.mc.srsl.datagen;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.datagen.loot.Loot_BlockLootTables;
import dev.anye.mc.srsl.datapack.NekoKingDatapack;
import net.minecraft.core.Cloner;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryValidator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public final class DataGenerators {
	private static final RegistryDataLoader.RegistryData<NekoKingDatapack> NEKO_KING_DATAPACK_REGISTRY_DATA = new RegistryDataLoader.RegistryData<>(NekoKingDatapack.KEY, NekoKingDatapack.CODEC, RegistryValidator.none());


	public static final List<RegistryDataLoader.RegistryData<?>> RELOADABLE_REGISTRIES = List.of(
			NEKO_KING_DATAPACK_REGISTRY_DATA
	);


	private static final RegistrySetBuilder WORLD_BUILDER = new RegistrySetBuilder()
			.add(StructureDataGen.create())
			.add(StructureDataGen.createSet())
			;


	private static final RegistrySetBuilder RELOADABLE_BUILDER = new RegistrySetBuilder()
			.add(Loot_BlockLootTables.create())
			.add(DataGen_Recipe.create());


	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator dataGenerator = event.getGenerator();
		PackOutput packOutput = dataGenerator.getPackOutput();

		CompletableFuture<HolderLookup.Provider> worldLookupProvider = event.getWorldLookupProvider();
		CompletableFuture<HolderLookup.Provider> reloadableLookupProvider = event.getReloadableLookupProvider();

		dataGenerator.addProvider(true,DatapackBuiltinEntriesProvider.forWorldLayer(
				packOutput,
				"normal world",
				worldLookupProvider,
				WORLD_BUILDER,
				Set.of(SRSL.MOD_ID)
		));


		dataGenerator.addProvider(true,DatapackBuiltinEntriesProvider.forReloadableLayer(
				packOutput,
				"normal",
				worldLookupProvider,
				reloadableLookupProvider,
				RELOADABLE_BUILDER,
				Set.of(SRSL.MOD_ID)
		));
//
//		dataGenerator.addProvider(true, new DataGen_Recipe.Runner(lookup, packOutput));
//		dataGenerator.addProvider(true, DataGen_LootTableProvider.create(packOutput, lookup));
		dataGenerator.addProvider(true, new _ModelProvider(packOutput));


		dataGenerator.addProvider(true, new DatapackBuiltinEntriesProvider(
				packOutput,
				"srsl enchant",
				RELOADABLE_REGISTRIES,
				createReloadableLookup(worldLookupProvider, reloadableLookupProvider,new RegistrySetBuilder()
						.add(NekoKingDatapack.KEY, NekoKingDatapack::reg)),

				Set.of(SRSL.MOD_ID)));


		DataGen_BlockTag blockTag = dataGenerator.addProvider(true, new DataGen_BlockTag(packOutput, reloadableLookupProvider));
		dataGenerator.addProvider(true, new DataGen_ItemTag(packOutput, reloadableLookupProvider, blockTag.contentsGetter()));
	}
	public static CompletableFuture<RegistrySetBuilder.PatchedRegistries> createReloadableLookup(
			CompletableFuture<HolderLookup.Provider> contextFuture, CompletableFuture<HolderLookup.Provider> vanillaFuture, RegistrySetBuilder packBuilder
	) {
		return contextFuture.thenCombine(vanillaFuture, (context, vanilla) -> {
			Cloner.Factory cloner = new Cloner.Factory();
			RegistryDataLoader.RELOADABLE_REGISTRIES.forEach(registryData -> registryData.runWithArguments(cloner::addCodec));
			RELOADABLE_REGISTRIES.forEach(registryData -> registryData.runWithArguments(cloner::addCodec));
			RegistrySetBuilder.PatchedRegistries newRegistries = packBuilder.buildPatch(context, vanilla, cloner);
			boolean hasAnyPatchedLootData = LootDataType.values().anyMatch(type -> hasAnyPatchedElement(newRegistries, type.registryKey()));
			if (hasAnyPatchedLootData) {
				VanillaRegistries.validateLootData(newRegistries.full());
			}

			return newRegistries;
		});
	}
	private static boolean hasAnyPatchedElement(RegistrySetBuilder.PatchedRegistries newRegistries, ResourceKey<? extends Registry<?>> registry) {
		return newRegistries.patches().lookup(registry).flatMap(lookup -> lookup.listElements().findAny()).isPresent();
	}
}

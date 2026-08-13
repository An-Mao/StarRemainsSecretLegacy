package dev.anye.mc.srsl.datagen;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.datapack.NekoKingDatapack;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator dataGenerator = event.getGenerator();
		PackOutput packOutput = dataGenerator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

		dataGenerator.addProvider(true, new DataGen_Recipe.Runner(lookup, packOutput));
		dataGenerator.addProvider(true, DataGen_LootTableProvider.create(packOutput, lookup));
		dataGenerator.addProvider(true, new _ModelProvider(packOutput));


		dataGenerator.addProvider(true, (DataProvider.Factory<DatapackBuiltinEntriesProvider>) p -> new DatapackBuiltinEntriesProvider(p,
				event.getLookupProvider(),
				new RegistrySetBuilder()
						.add(NekoKingDatapack.KEY, NekoKingDatapack::reg)
						.add(Registries.STRUCTURE, StructureDataGen::reg)
						.add(Registries.STRUCTURE_SET, StructureDataGen::regSet),

				Set.of(SRSL.MOD_ID)));


		DataGen_BlockTag blockTag = dataGenerator.addProvider(true, new DataGen_BlockTag(packOutput, lookup));
		dataGenerator.addProvider(true, new DataGen_ItemTag(packOutput, lookup, blockTag.contentsGetter()));
	}
}

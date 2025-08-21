package dev.anye.mc.srsl.datagen;

import dev.anye.mc.srsl.SRSL;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        dataGenerator.addProvider(event.includeClient(),new DataGen_Recipe(packOutput,lookup));
        dataGenerator.addProvider(event.includeClient(),DataGen_LootTableProvider.create(packOutput,lookup));
        dataGenerator.addProvider(event.includeClient(),new BlockModelProviderDatagen(packOutput,existingFileHelper));
        dataGenerator.addProvider(event.includeClient(),new DataGen_BlockStateProvider(packOutput,existingFileHelper));
        dataGenerator.addProvider(event.includeClient(),new DataGen_ItemModelProvider(packOutput,existingFileHelper));
        dataGenerator.addProvider(event.includeClient(),(DataProvider.Factory<DatapackBuiltinEntriesProvider>) p -> new DatapackBuiltinEntriesProvider(p,event.getLookupProvider(),
                new RegistrySetBuilder().add(Registries.STRUCTURE,StructureDataGen::reg).add(Registries.STRUCTURE_SET,StructureDataGen::regSet),
                Set.of(SRSL.MOD_ID)));


        DataGen_BlockTag blockTag = dataGenerator.addProvider(event.includeClient(), new DataGen_BlockTag(packOutput,lookup,existingFileHelper));
        dataGenerator.addProvider(event.includeClient(),new DataGen_ItemTag(packOutput,lookup,blockTag.contentsGetter(),existingFileHelper));
    }
}

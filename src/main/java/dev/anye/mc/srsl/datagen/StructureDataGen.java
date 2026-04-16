package dev.anye.mc.srsl.datagen;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.structure.StarRemainsStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class StructureDataGen {
    public static final ResourceKey<Structure> Star_Remains = ResourceKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(SRSL.MOD_ID,"star_remains"));
    public static final ResourceKey<StructureSet> Star_Remains_Set = ResourceKey.create(Registries.STRUCTURE_SET, Identifier.fromNamespaceAndPath(SRSL.MOD_ID,"star_remains"));




    public static void reg(BootstrapContext<Structure> bootstrap){
        HolderGetter<Biome> holdergetter = bootstrap.lookup(Registries.BIOME);
        bootstrap.register(Star_Remains,new StarRemainsStructure(new Structure.StructureSettings(holdergetter.getOrThrow(BiomeTags.IS_OVERWORLD))));

    }
    public static void regSet(BootstrapContext<StructureSet> bootstrap){
        HolderGetter<Structure> holdergetter = bootstrap.lookup(Registries.STRUCTURE);
        bootstrap.register(Star_Remains_Set,new StructureSet(holdergetter.getOrThrow(Star_Remains),new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 0x777777)));

    }
}

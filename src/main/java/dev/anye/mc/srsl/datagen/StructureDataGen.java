package dev.anye.mc.srsl.datagen;

import com.mojang.logging.LogUtils;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.reg.structure.StarRemainsStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

import java.util.Set;

public class StructureDataGen {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final ResourceKey<Structure> Star_Remains = ResourceKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(SRSL.MOD_ID, "star_remains"));
	public static final ResourceKey<StructureSet> Star_Remains_Set = ResourceKey.create(Registries.STRUCTURE_SET, Identifier.fromNamespaceAndPath(SRSL.MOD_ID, "star_remains"));


	public static MultiRegistryBootstrap create() {
		return new MultiRegistryBootstrap() {
			@Override
			public @NonNull Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
				return Set.of(Registries.STRUCTURE);
			}

			@Override
			public void run(MultiRegistryBootstrap.@NonNull BootstrapGetter registries) {
				reg(registries.get(Registries.STRUCTURE));
			}
		};
	}

	public static MultiRegistryBootstrap createSet() {
		return new MultiRegistryBootstrap() {
			@Override
			public @NonNull Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
				return Set.of(Registries.STRUCTURE_SET);
			}

			@Override
			public void run(MultiRegistryBootstrap.@NonNull BootstrapGetter registries) {
				regSet(registries.get(Registries.STRUCTURE_SET));
			}
		};
	}


	public static void reg(BootstrapContext<Structure> bootstrap) {
		HolderGetter<Biome> holdergetter = bootstrap.lookup(Registries.BIOME);
		bootstrap.register(Star_Remains, new StarRemainsStructure(new Structure.StructureSettings(holdergetter.getOrThrow(BiomeTags.IS_OVERWORLD))));

	}

	public static void regSet(BootstrapContext<StructureSet> bootstrap) {
		HolderGetter<Structure> holdergetter = bootstrap.lookup(Registries.STRUCTURE);
		bootstrap.register(Star_Remains_Set, new StructureSet(holdergetter.getOrThrow(Star_Remains), new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 0x777777)));

	}
}

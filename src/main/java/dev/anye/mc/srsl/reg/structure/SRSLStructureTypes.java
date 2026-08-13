package dev.anye.mc.srsl.structure;

import dev.anye.mc.srsl.SRSL;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SRSLStructureTypes {
	public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPE = DeferredRegister.create(BuiltInRegistries.STRUCTURE_TYPE, SRSL.MOD_ID);
	public static final DeferredHolder<StructureType<?>, StructureType<StarRemainsStructure>> STAR_REMAINS = STRUCTURE_TYPE.register("star_remains", () -> () -> StarRemainsStructure.CODEC);


	public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPE = DeferredRegister.create(BuiltInRegistries.STRUCTURE_PIECE, SRSL.MOD_ID);
	public static final DeferredHolder<StructurePieceType, StructurePieceType> STAR_REMAINS_PIECE = STRUCTURE_PIECE_TYPE.register("star_remains", () -> (context, tag) -> new StarRemainsPiece(context.structureTemplateManager(), tag));

	private SRSLStructureTypes() {
	}


	public static void reg(IEventBus eventBus) {
		STRUCTURE_TYPE.register(eventBus);
		STRUCTURE_PIECE_TYPE.register(eventBus);
	}
}

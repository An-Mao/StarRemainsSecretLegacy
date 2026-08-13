package dev.anye.mc.srsl.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class StarRemainsStructure extends Structure {
	public static final MapCodec<StarRemainsStructure> CODEC = simpleCodec(StarRemainsStructure::new);

	public StarRemainsStructure(StructureSettings settings) {
		super(settings);
	}

	@Override
	protected @NotNull Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext context) {
		return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, structurePiecesBuilder -> this.generatePieces(structurePiecesBuilder, context));
	}

	private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {
		//ChunkPos chunkpos = context.chunkPos();
		WorldgenRandom worldgenrandom = context.random();
		Rotation rotation = Rotation.getRandom(worldgenrandom);
		BlockPos blockpos = getLowestYIn5by5BoxOffset7Blocks(context, rotation);
		StarRemainsPiece.addPieces(context.structureTemplateManager(), blockpos, rotation, builder, worldgenrandom);
	}

	@Override
	public StructureType<?> type() {
		return SRSLStructureTypes.STAR_REMAINS.value();
	}
}

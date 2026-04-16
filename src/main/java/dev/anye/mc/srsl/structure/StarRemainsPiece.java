package dev.anye.mc.srsl.structure;

import com.mojang.logging.LogUtils;
import dev.anye.mc.srsl.SRSL;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.slf4j.Logger;

public class StarRemainsPiece extends TemplateStructurePiece {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Identifier StarRemains = Identifier.fromNamespaceAndPath(SRSL.MOD_ID,"star_remains");
    public StarRemainsPiece(StructureTemplateManager structureTemplateManager, BlockPos templatePosition, Rotation rotation) {
        super(
                SRSLStructureTypes.STAR_REMAINS_PIECE.value(),
                0,
                structureTemplateManager,
                StarRemains, StarRemains.toString(),
                makeSettings(rotation), templatePosition);
    }
    public StarRemainsPiece(StructureTemplateManager structureTemplateManager, CompoundTag tag) {
        super(StructurePieceType.IGLOO, tag, structureTemplateManager, resourceLocation -> makeSettings(Rotation.valueOf(tag.getString("Rot").get())));
    }
    private static StructurePlaceSettings makeSettings(Rotation rotation) {
        return new StructurePlaceSettings()
                .setRotation(rotation)
                .setMirror(Mirror.NONE)
                //.setRotationPivot(new BlockPos(3, 5, 5))
                .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK)
                .setLiquidSettings(LiquidSettings.IGNORE_WATERLOGGING);
    }
    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {

        if (name.equals("sculk_sensor")) {
            level.setBlock(pos, Blocks.SCULK_SENSOR.defaultBlockState(), 3);
        }
    }
    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
        tag.putString("Rot", this.placeSettings.getRotation().name());
    }
    /*
    @Override
    public void postProcess(
            WorldGenLevel level,
            StructureManager structureManager,
            ChunkGenerator generator,
            RandomSource random,
            BoundingBox box,
            ChunkPos chunkPos,
            BlockPos pos
    ) {
        super.postProcess(level, structureManager, generator, random, box, chunkPos, pos);
        BlockState blockstate = level.getBlockState(templatePosition.below());
        LOGGER.debug("=> tem {} pos {}",templatePosition,pos);
        if (blockstate.is(Blocks.AMETHYST_BLOCK)) {
            level.setBlock(templatePosition, Blocks.SCULK_SENSOR.defaultBlockState(), 3);
        }else if (blockstate.is(Blocks.STRUCTURE_VOID)){
            level.setBlock(templatePosition, Blocks.RAW_GOLD_BLOCK.defaultBlockState(), 3);
        }
    }

     */












    public static void addPieces(
            StructureTemplateManager structureTemplateManager, BlockPos startPos, Rotation rotation, StructurePieceAccessor pieces, RandomSource random
    ) {
        pieces.addPiece(new StarRemainsPiece(structureTemplateManager, startPos, rotation));
    }
}

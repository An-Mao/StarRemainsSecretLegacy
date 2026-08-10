package dev.anye.mc.srsl.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.Consumer;

public class StructureScrollItem extends Item {

    private static final Logger LOGGER = LogUtils.getLogger();
    public StructureScrollItem() {
        super(new Properties().setId(ItemReg.STRUCTURE_SCROLL.getKey()).stacksTo(8));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.srsl.structure_scroll.desc"));
    }


    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        if (level instanceof ServerLevel serverLevel && usedHand == InteractionHand.MAIN_HAND){
            ItemStack itemStack = player.getItemInHand(usedHand);
            if (itemStack.is(this) && !player.getCooldowns().isOnCooldown(itemStack)){
                player.getCooldowns().addCooldown(itemStack,360);
                itemStack.shrink(1);
                serverLevel.registryAccess().lookupOrThrow(Registries.STRUCTURE).getRandom(player.getRandom()).ifPresent(structureReference ->{
                    Structure structure = structureReference.value();
                    ChunkGenerator chunkgenerator = serverLevel.getChunkSource().getGenerator();
                    StructureStart structurestart = structure.generate(
                            structureReference,
                            serverLevel.dimension(),
                            serverLevel.registryAccess(),
                            chunkgenerator,
                            chunkgenerator.getBiomeSource(),
                            serverLevel.getChunkSource().randomState(),
                            serverLevel.getStructureManager(),
                            serverLevel.getSeed(),
                            ChunkPos.containing(BlockPos.containing(player.getPosition(0))),
                            0,
                            serverLevel,
                            biomeHolder -> true
                    );
                    if (!structurestart.isValid()) {
                        LOGGER.error(Component.translatable("commands.place.structure.failed").getString());
                    } else {
                        BoundingBox boundingbox = structurestart.getBoundingBox();
                        ChunkPos chunkStartPos = new ChunkPos(SectionPos.blockToSectionCoord(boundingbox.minX()), SectionPos.blockToSectionCoord(boundingbox.minZ()));
                        ChunkPos chunkEndPos = new ChunkPos(SectionPos.blockToSectionCoord(boundingbox.maxX()), SectionPos.blockToSectionCoord(boundingbox.maxZ()));
                        try {
                            checkLoaded(serverLevel, chunkStartPos, chunkEndPos);
                            ChunkPos.rangeClosed(chunkStartPos, chunkEndPos).forEach(
                                    chunkPos -> structurestart.placeInChunk(
                                            serverLevel,
                                            serverLevel.structureManager(),
                                            chunkgenerator,
                                            serverLevel.getRandom(),
                                            new BoundingBox(
                                                    chunkPos.getMinBlockX(),
                                                    serverLevel.getMinY(),
                                                    chunkPos.getMinBlockZ(),
                                                    chunkPos.getMaxBlockX(),
                                                    serverLevel.getMaxY(),
                                                    chunkPos.getMaxBlockZ()
                                            ),
                                            chunkPos
                                    )
                            );
                        } catch (CommandSyntaxException e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                });
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private static void checkLoaded(ServerLevel level, ChunkPos start, ChunkPos end) throws CommandSyntaxException {
        if (ChunkPos.rangeClosed(start, end).anyMatch(chunkPos -> !level.isLoaded(chunkPos.getWorldPosition()))) {
            throw BlockPosArgument.ERROR_NOT_LOADED.create();
        }
    }
}

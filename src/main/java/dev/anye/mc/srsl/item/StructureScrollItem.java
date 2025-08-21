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
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.List;

public class StructureScrollItem extends Item {

    private static final Logger LOGGER = LogUtils.getLogger();
    public StructureScrollItem() {
        super(new Properties().stacksTo(8));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.srsl.structure_scroll.desc"));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level instanceof ServerLevel serverLevel && usedHand == InteractionHand.MAIN_HAND){
            ItemStack itemStack = player.getItemInHand(usedHand);
            if (itemStack.is(this) && !player.getCooldowns().isOnCooldown(this)){
                player.getCooldowns().addCooldown(this,1000);
                itemStack.shrink(1);
                serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE).getRandom(serverLevel.random).ifPresent(structureReference ->{
                    Structure structure = structureReference.value();
                    ChunkGenerator chunkgenerator = serverLevel.getChunkSource().getGenerator();
                    StructureStart structurestart = structure.generate(
                            serverLevel.registryAccess(),
                            chunkgenerator,
                            chunkgenerator.getBiomeSource(),
                            serverLevel.getChunkSource().randomState(),
                            serverLevel.getStructureManager(),
                            serverLevel.getSeed(),
                            new ChunkPos(BlockPos.containing(player.getPosition(0))),
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
                                                    serverLevel.getMinBuildHeight(),
                                                    chunkPos.getMinBlockZ(),
                                                    chunkPos.getMaxBlockX(),
                                                    serverLevel.getMaxBuildHeight(),
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
                return InteractionResultHolder.success(itemStack);
            }
            return InteractionResultHolder.pass(itemStack);
        }
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    private static void checkLoaded(ServerLevel level, ChunkPos start, ChunkPos end) throws CommandSyntaxException {
        if (ChunkPos.rangeClosed(start, end).anyMatch(chunkPos -> !level.isLoaded(chunkPos.getWorldPosition()))) {
            throw BlockPosArgument.ERROR_NOT_LOADED.create();
        }
    }
}

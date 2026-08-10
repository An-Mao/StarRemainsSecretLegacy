package dev.anye.mc.srsl.enchant.zero.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ChainHarvestingEvent {
    //private static final ExecutorService executor = Executors.newCachedThreadPool();
    private static final HashMap<UUID, List<BlockPos>> temp = new HashMap<>();
    public static void onBlockBreak(BreakBlockEvent event) {
        /*
        if (ChainHarvesting.ENABLE){
            if (event.getState().isAir() || event.isCanceled()) return;
            if (event.getPlayer() != null && event.getPlayer() instanceof ServerPlayer serverPlayer){
                List<BlockPos> blockPosList = temp.getOrDefault(serverPlayer.getUUID(), new ArrayList<>());
                if (blockPosList.contains(event.getPos())) {
                    blockPosList.remove(event.getPos());
                    temp.put(serverPlayer.getUUID(), blockPosList);
                }else {
                    if (serverPlayer.getMainHandItem().getEnchantmentLevel(NekoEnchantments.chainHarvesting) > 0) {
                        ServerLevel level = (ServerLevel) serverPlayer.level();
                        BlockPos pos = event.getPos();
                        Block block = event.getState().getBlock();
                        //checkBlock(level, serverPlayer, pos, block,event.getState().canHarvestBlock(level,pos,serverPlayer), new Depth());
                        boolean drop = event.getState().canHarvestBlock(level, pos, serverPlayer);
                        BlockPos.betweenClosed(getBlockPos(pos, -radius, -depths), getBlockPos(pos, radius, depths)).forEach(blockPos -> {
                            BlockState blockstate = level.getBlockState(blockPos);
                            if (blockstate.getBlock() == block) {
                                List<BlockPos> bps = temp.getOrDefault(serverPlayer.getUUID(), new ArrayList<>());
                                if (!bps.contains(blockPos)) bps.add(blockPos);
                                temp.put(serverPlayer.getUUID(), bps);
                                level.destroyBlock(blockPos, drop, serverPlayer);
                                //executor.submit(() -> level.destroyBlock(blockPos,drop,serverPlayer));
                            }
                        });
                    }
                }
            }
        }

         */
    }

    public static void checkBlock(Level level,ServerPlayer serverPlayer,BlockPos pos,Block block,boolean drop,Depth depth){
        if (depth.getDepth() > ChainHarvesting.radius) return;
        depth.addDepth();
        BlockPos.betweenClosed(pos.getX()-1,pos.getY()-1,pos.getZ()-1,pos.getX()+1,pos.getY() +1,pos.getZ()+1).forEach(blockPos -> {
            if (level.getBlockState(blockPos).getBlock() == block){

                level.destroyBlock(blockPos,drop,serverPlayer);
                checkBlock(level,serverPlayer,blockPos,block,drop,new Depth());
            }
        });
    }
    public static class Depth{
        private int depth;
        public Depth(){
            setDepth(0);
        }
        public Depth(int depth){
            setDepth(depth);
        }
        public void addDepth() {
            this.depth ++ ;
        }
        public void setDepth(int depth) {
            this.depth = depth;
        }
        public int getDepth() {
            return depth;
        }
    }
}

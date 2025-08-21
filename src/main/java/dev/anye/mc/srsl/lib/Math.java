package dev.anye.mc.srsl.lib;

import net.minecraft.core.BlockPos;

public class Math {
    public static BlockPos getBlockPos(BlockPos pos,int r){
        return getBlockPos(pos,r,r);
    }
    public static BlockPos getBlockPos(BlockPos pos,int r,int d){
        return new BlockPos(pos.getX() + r,pos.getY() + d,pos.getZ() + r);
    }
}

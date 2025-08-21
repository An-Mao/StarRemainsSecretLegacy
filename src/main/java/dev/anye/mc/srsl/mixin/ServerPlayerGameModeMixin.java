package dev.anye.mc.srsl.mixin;

import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import dev.anye.mc.srsl.enchant.zero.tool.ChainHarvesting;
import dev.anye.mc.srsl.lib.Math;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {
    @Unique
    private final List<BlockPos> nekoEnchantment$temp = new ArrayList<>();
    @Final
    @Shadow protected ServerPlayer player;
    @Shadow public abstract boolean destroyBlock(BlockPos pPos);
    @Shadow protected ServerLevel level;

    @Inject(method = "destroyBlock", at = @At("HEAD"), cancellable = true)
    public void ne$chain_harvesting$destroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        if (!nekoEnchantment$temp.contains(pos)) {
            if (EnchantHelper.hasEnchant(player.getMainHandItem(), Enchants.chainHarvesting)) {
                Block block = level.getBlockState(pos).getBlock();
                int[] count = {player.getMainHandItem().getMaxDamage() - player.getMainHandItem().getDamageValue()};
                nekoEnchantment$temp.add(pos);
                boolean re = destroyBlock(pos);
                count[0] --;
                /*
                int x = pos.getX(),y=pos.getY(),z=pos.getZ();
                for (int r = -ChainHarvestingEvent.radius;r <= ChainHarvestingEvent.radius; r++ ){
                    for (int d = ChainHarvestingEvent.depths; d >= -ChainHarvestingEvent.depths; d--){
                        BlockPos blockPos = new BlockPos(x + r,y+d,z+r);
                        if (count > 0){
                            BlockState blockstate = level.getBlockState(blockPos);
                            if (blockstate.getBlock() == block) {
                                if (!nekoEnchantment$temp.contains(blockPos)){
                                    nekoEnchantment$temp.add(blockPos);
                                    destroyBlock(blockPos);
                                    count --;
                                }
                            }
                        }
                    }

                }

                 */
                BlockPos.betweenClosed(Math.getBlockPos(pos, -ChainHarvesting.radius, -ChainHarvesting.depths), Math.getBlockPos(pos, ChainHarvesting.radius, ChainHarvesting.depths)).forEach(blockPos -> {
                    if (count[0] > 0){
                        BlockState blockstate = level.getBlockState(blockPos);
                        if (blockstate.getBlock() == block) {
                            if (!nekoEnchantment$temp.contains(blockPos)){
                                nekoEnchantment$temp.add(blockPos);
                                destroyBlock(blockPos);
                                count[0] --;
                            }
                        }
                    }
                });
                cir.setReturnValue(re);
            }
        }else {
            nekoEnchantment$temp.remove(pos);
        }
    }
}

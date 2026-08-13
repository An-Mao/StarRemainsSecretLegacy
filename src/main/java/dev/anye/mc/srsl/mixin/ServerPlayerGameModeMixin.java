package dev.anye.mc.srsl.mixin;

import dev.anye.mc.cores.helper.PosHelper;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.zero.tool.ChainHarvesting;
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

@Mixin(value = ServerPlayerGameMode.class, remap = false)
public abstract class ServerPlayerGameModeMixin {
	@Unique
	private final List<BlockPos> srsl$temp = new ArrayList<>();
	@Final
	@Shadow
	protected ServerPlayer player;

	@Shadow
	public abstract boolean destroyBlock(BlockPos pos);

	@Shadow
	protected ServerLevel level;

	@Inject(method = "destroyBlock", at = @At("HEAD"), cancellable = true)
	public void srsl$chain_harvesting$destroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (!srsl$temp.contains(pos)) {
			if (EnchantHelper.hasEnchant(player.getMainHandItem(), Enchants.chainHarvesting)) {
				Block block = level.getBlockState(pos).getBlock();
				int[] count = {player.getMainHandItem().getMaxDamage() - player.getMainHandItem().getDamageValue()};
				srsl$temp.add(pos);
				boolean re = destroyBlock(pos);
				count[0]--;

				ChainHarvesting chainHarvesting = EnchantReg.Chain_Harvesting.get();

				BlockPos.betweenClosed(PosHelper.getBlockPos(pos, -chainHarvesting.radius, -chainHarvesting.depths), PosHelper.getBlockPos(pos, chainHarvesting.radius, chainHarvesting.depths)).forEach(blockPos -> {
					if (count[0] > 0) {
						BlockState blockstate = level.getBlockState(blockPos);
						if (blockstate.getBlock() == block) {
							if (!srsl$temp.contains(blockPos)) {
								srsl$temp.add(blockPos);
								destroyBlock(blockPos);
								count[0]--;
							}
						}
					}
				});
				cir.setReturnValue(re);
			}
		} else {
			srsl$temp.remove(pos);
		}
	}
}

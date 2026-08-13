package dev.anye.mc.srsl.item;

import dev.anye.mc.srsl.core.*;
import dev.anye.mc.srsl.reg.DataReg;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RevelationStoneItem extends Item {
	private static EnchantRandom enchantRandom = null;

	public RevelationStoneItem() {
		super(new Properties().setId(ItemReg.RevelationStone.getKey()).stacksTo(1));
	}

	@Override
	public @NotNull Component getName(ItemStack pStack) {
		CompoundTag enchantData = EnchantHelper.getAllData(pStack);
		if (enchantData.getString("revelation").isEmpty()) {
			return super.getName(pStack);
		}
		return Component.translatable("item.srsl.revelation_stone.show").append(EnchantReg.getEnchant(enchantData.getString("revelation").get()).getFullName(1));
	}

	@Override
	public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		if (pPlayer instanceof ServerPlayer serverPlayer) {
			ItemStack itemstack = serverPlayer.getItemInHand(pUsedHand);
			CompoundTag enchantData = EnchantHelper.getAllData(itemstack);
			if (enchantData.getString("revelation").isEmpty()) {
				if (enchantRandom == null) {
					enchantRandom = new EnchantRandom(EnchantHelper.getAllEnchants());
				}
				enchantData.putString("revelation", EnchantReg.getResourceLocation(enchantRandom.getRandomItem()).toString());
				itemstack.set(DataReg.Enchant_Data.get(), new EnchantData(enchantData));
			}
		}
		return super.use(pLevel, pPlayer, pUsedHand);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay,
	                            Consumer<Component> tooltipAdder, TooltipFlag flag) {
		CompoundTag enchantData = EnchantHelper.getAllData(stack);
		String revelation = enchantData.getString("revelation").orElse("");
		if (!revelation.isEmpty()) {
			Enchant enchant = EnchantReg.getEnchant(revelation);
			tooltipAdder.accept(Component.literal(" -->").append(Component.translatable(enchant.getDescriptionId() + ".desc")));
		} else {
			tooltipAdder.accept(Component.translatable("item.srsl.revelation_stone.tooltip"));
		}
		super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
	}
}

package dev.anye.mc.srsl.reg.item;

import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.data.DataReg;
import dev.anye.mc.srsl.reg.data.EnchantData;
import dev.anye.mc.srsl.reg.enchant.Enchant;
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
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class RevelationStoneItem extends Item {
	private static final String ID = "revelation";

	public RevelationStoneItem() {
		super(new Properties().setId(ItemReg.RevelationStone.getKey()).stacksTo(1));
	}

	@Override
	public @NotNull Component getName(@NonNull ItemStack itemStack) {
		CompoundTag enchantData = EnchantHelper.getAllData(itemStack);
		if (enchantData.getString(ID).isEmpty()) {
			return super.getName(itemStack);
		}
		return Component.translatable("item.srsl.revelation_stone.show").append(EnchantReg.getEnchant(enchantData.getString("revelation").get()).getFullName(1));
	}

	@Override
	public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		if (pPlayer instanceof ServerPlayer serverPlayer) {
			ItemStack itemstack = serverPlayer.getItemInHand(pUsedHand);
			CompoundTag enchantData = EnchantHelper.getAllData(itemstack);
			if (enchantData.getString(ID).isEmpty()) {
				enchantData.putString(ID, EnchantReg.getResourceLocation(EnchantRandom.getRandom()).toString());
				itemstack.set(DataReg.Enchant_Data.get(), new EnchantData(enchantData));
			}
		}
		return super.use(pLevel, pPlayer, pUsedHand);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay,
	                            Consumer<Component> tooltipAdder, TooltipFlag flag) {
		CompoundTag enchantData = EnchantHelper.getAllData(stack);
		String revelation = enchantData.getString(ID).orElse("");
		if (!revelation.isEmpty()) {
			Enchant enchant = EnchantReg.getEnchant(revelation);
			tooltipAdder.accept(Component.literal(" -->").append(Component.translatable(enchant.getDescriptionId() + ".desc")));
		} else {
			tooltipAdder.accept(Component.translatable("item.srsl.revelation_stone.tooltip"));
		}
		super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
	}
}

package dev.anye.mc.srsl.reg.enchant.zero.item.unbreakable;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import org.jetbrains.annotations.NotNull;

public class Unbreakable extends ZeroItemE {
	public static final String ENCHANTMENT_KEY_UNBREAKABLE = "unbreakable";
	private static final String TOOLTIPS_REFINE = "tooltips.srsl.refine";
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("item","minecraft:netherite_block");
	});
	public final String item;
	public record Data(String item) {
		public static final Data DEFAULT = new Data("minecraft:netherite_block");
	}
	public Unbreakable(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		item = config.asString("item");
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return pStack.getMaxDamage() > 0;
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onAnvil);
	}

	public void onAnvil(AnvilUpdateEvent anvilUpdateEvent) {
		if (!anvilUpdateEvent.getPlayer().level().isClientSide() && EnchantHelper.hasEnchant(anvilUpdateEvent.getLeft(), Enchants.zi_unbreakable)) {

			CompoundTag nbt = EnchantHelper.getEnchantData(anvilUpdateEvent.getLeft()).getTagCopy();
			CompoundTag data = nbt.getCompound("data.unbreakable").orElse(new CompoundTag());
			int n = data.getInt("number").orElse(0);
			if (n >= 5) return;


			Item ritem = anvilUpdateEvent.getRight().getItem();
			//System.out.println(Item);
			if (BuiltInRegistries.ITEM.getKey(ritem).toString().equalsIgnoreCase(item)) {

				ItemStack leftCopy = anvilUpdateEvent.getLeft().copy();
				n++;
				data.putInt("number", n);
				nbt.put("data.unbreakable", data);
				EnchantHelper.setEnchantData(leftCopy, nbt);
				if (n == 5) leftCopy.set(DataComponents.UNBREAKABLE, Unit.INSTANCE);

				anvilUpdateEvent.setOutput(leftCopy);
				anvilUpdateEvent.setXpCost(13);
				anvilUpdateEvent.setMaterialCost(1);
			}
		}
	}
}

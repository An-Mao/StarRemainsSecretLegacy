package dev.anye.mc.srsl.reg.item;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.data.DataReg;
import dev.anye.mc.srsl.reg.data.EnchantData;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NUCreativeTabs {
	private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SRSL.MOD_ID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = TABS.register("srsl_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemReg.RevelationStone.get()))
			.title(Component.translatable("tab.srsl.create_tab"))
			.displayItems((pParameters, pOutput) -> {
				ItemReg.ITEMS.getDeferredRegister().getEntries().forEach(itemDeferredHolder -> pOutput.accept(itemDeferredHolder.get()));
				//pOutput.accept(ItemReg.RevelationStone.get());
				EnchantHelper.getAllEnchants().forEach(enchant -> {
					ItemStack itemStack = new ItemStack(ItemReg.RevelationStone.get());
					CompoundTag nbt = new CompoundTag();
					nbt.putString("revelation", EnchantReg.getResourceLocation(enchant).toString());

					itemStack.set(DataReg.Enchant_Data.get(), new EnchantData(nbt));
					pOutput.accept(itemStack);
				});
			})
			.build());

	public static void reg(IEventBus eventBus) {
		TABS.register(eventBus);
	}
}

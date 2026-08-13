package dev.anye.mc.srsl.block;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.reg.item.ItemReg;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BlockReg {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, SRSL.MOD_ID);
	public static final DeferredHolder<Block, StarRemainsBlock> STAR_REMAINS = registryBlock("star_remains", StarRemainsBlock::new, Component.translatable("item.srsl.star_remains.desc"));


	private static <I extends Block> DeferredHolder<Block, I> registryBlock(String name, Supplier<I> block) {
		DeferredHolder<Block, I> toReturn = BLOCKS.register(name, block);
		registryBlockItem(name, toReturn);
		return toReturn;
	}

	private static <I extends Block> DeferredHolder<Block, I> registryBlock(String name, Supplier<I> block, Component component) {
		DeferredHolder<Block, I> toReturn = BLOCKS.register(name, block);
		registryBlockItem(name, toReturn, component);
		return toReturn;
	}

	private static <I extends Block> DeferredHolder<Item, BlockItem> registryBlockItem(String name, DeferredHolder<Block, I> block) {
		return ItemReg.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), block.getId()))));
	}

	private static <I extends Block> DeferredHolder<Item, BlockItem> registryBlockItem(String name, DeferredHolder<Block, I> block, Component component) {
		return ItemReg.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), block.getId()))) {
			@Override
			public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
				super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
				tooltipAdder.accept(component);
			}
		});
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}

package dev.anye.mc.srsl.block;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.item.ItemReg;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockReg {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, SRSL.MOD_ID);
    public static final DeferredHolder<Block, StarRemainsBlock> STAR_REMAINS = registryBlock("star_remains", StarRemainsBlock::new);



    private static <I extends Block> DeferredHolder<Block, I> registryBlock(String name, Supplier<I> block){
        DeferredHolder<Block, I> toReturn = BLOCKS.register(name,block);
        registryBlockItem(name,toReturn);
        return toReturn;
    }

    private static  <I extends Block>  DeferredHolder<Item, BlockItem> registryBlockItem(String name, DeferredHolder<Block, I> block) {
        return ItemReg.ITEMS.register(name,()->new BlockItem(block.get(),new Item.Properties()));
    }
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}

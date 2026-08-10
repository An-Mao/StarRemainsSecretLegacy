package dev.anye.mc.srsl.item;

import dev.anye.mc.srsl.SRSL;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemReg {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, SRSL.MOD_ID);
    public static final DeferredHolder<Item,RevelationStoneItem> RevelationStone = ITEMS.register("revelation_stone", RevelationStoneItem::new);
    public static final DeferredHolder<Item,StarRemainsEssenceItem> STAR_REMAINS_ESSENCE = ITEMS.register("star_remains_essence", StarRemainsEssenceItem::new);
    public static final DeferredHolder<Item,StructureScrollItem> STRUCTURE_SCROLL = ITEMS.register("structure_scroll", StructureScrollItem::new);





    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}

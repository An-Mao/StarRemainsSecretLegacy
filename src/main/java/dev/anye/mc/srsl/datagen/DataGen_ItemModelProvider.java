package dev.anye.mc.srsl.datagen;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.item.ItemReg;
import dev.anye.mc.srsl.item.RevelationStoneItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DataGen_ItemModelProvider extends ItemModelProvider {
    public DataGen_ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SRSL.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ItemReg.RevelationStone);
        simpleItem(ItemReg.STAR_REMAINS_ESSENCE);
        simpleItem(ItemReg.STRUCTURE_SCROLL);
    }
    private ItemModelBuilder simpleItem(DeferredHolder<Item, ? extends Item> item){
        return withExistingParent(item.getId().getPath(),ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",ResourceLocation.fromNamespaceAndPath(SRSL.MOD_ID,"item/"+item.getId().getPath()));
    }
}

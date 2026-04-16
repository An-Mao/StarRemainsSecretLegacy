package dev.anye.mc.srsl.enchant.zero.item.unbreakable;


import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;


@EventBusSubscriber(modid = SRSL.MOD_ID)
public class UnbreakableEvent {
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.Z_UNBREAKABLE.getId().getPath());
    public static final String Item = EnchantsConfig.INSTANCE.getConfig(EnchantReg.Z_UNBREAKABLE.getId().getPath()).getParameters().get("item").getAsString();
    @SubscribeEvent
    public static void onAnvil(AnvilUpdateEvent anvilUpdateEvent) {
        if (ENABLE && !anvilUpdateEvent.getPlayer().level().isClientSide() && EnchantHelper.hasEnchant(anvilUpdateEvent.getLeft(), Enchants.zi_unbreakable)){

            CompoundTag nbt = EnchantHelper.getEnchantData(anvilUpdateEvent.getLeft()).getTagCopy();
            CompoundTag data = nbt.getCompound("data.unbreakable").orElse(new CompoundTag());
            int n = data.getInt("number").orElse(0);
            if (n >= 5) return;


            Item ritem = anvilUpdateEvent.getRight().getItem();
            //System.out.println(Item);
            if (BuiltInRegistries.ITEM.getKey(ritem).toString().equalsIgnoreCase(Item)){

                ItemStack leftCopy = anvilUpdateEvent.getLeft().copy();
                n++;
                data.putInt("number", n);
                nbt.put("data.unbreakable", data);
                EnchantHelper.setEnchantData(leftCopy, nbt);
                if (n == 5)leftCopy.set(DataComponents.UNBREAKABLE,Unit.INSTANCE);

                anvilUpdateEvent.setOutput(leftCopy);
                anvilUpdateEvent.setXpCost(13);
                anvilUpdateEvent.setMaterialCost(1);
            }
        }
    }
}

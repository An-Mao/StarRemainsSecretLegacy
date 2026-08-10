package dev.anye.mc.srsl.core;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.item.ItemReg;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.GrindstoneEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class ServerEvent {
    @SubscribeEvent
    public static void onAnvil(AnvilUpdateEvent anvilUpdateEvent) {
        if (anvilUpdateEvent.getPlayer() instanceof ServerPlayer) {
            ItemStack right = anvilUpdateEvent.getRight();
            if (right.is(ItemReg.RevelationStone.get()) && right.getCount() > 0) {
                CompoundTag enchantData = EnchantHelper.getAllData(right);
                if (enchantData.getString("revelation").isEmpty())return;
                Enchant revelation = EnchantReg.getEnchant(enchantData.getString("revelation").get());
                ItemStack input = anvilUpdateEvent.getLeft().copy();
                if (revelation.canEnchant(input)) {
                    System.out.println("2");
                    CompoundTag nbt = EnchantHelper.getAllData(input);
                    int refines = nbt.getInt("refine").orElse(0);
                    CompoundTag enchant = nbt.getCompound("enchant").orElse(new CompoundTag());
                    int lvl = enchant.getInt(EnchantReg.getResourceLocation(revelation).toString()).orElse(0);
                    if (lvl < revelation.getMaxLevel()) {
                        System.out.println("3");
                        lvl++;
                        enchant.putInt(EnchantReg.getResourceLocation(revelation).toString(), lvl);
                        nbt.put("enchant", enchant);
                        nbt.putInt("refine", refines + 1);
                        //System.out.println("enchant:" + nbt);
                        input.set(DataReg.Enchant_Data.get(), new EnchantData(nbt));

                        anvilUpdateEvent.setOutput(input);
                        anvilUpdateEvent.setXpCost(revelation.getCost(lvl) + refines );
                        anvilUpdateEvent.setMaterialCost(1);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void onGrind(GrindstoneEvent.OnPlaceItem event) {
        if (event.getOutput().isEmpty()) {
            ItemStack stack;
            if (event.getTopItem().isEmpty() && !event.getBottomItem().isEmpty()) stack = event.getBottomItem().copy();
            else if (!event.getTopItem().isEmpty() && event.getBottomItem().isEmpty())
                stack = event.getTopItem().copy();
            else return;
            if (EnchantHelper.getEnchants(stack).isEmpty()) return;
            stack.set(DataReg.Enchant_Data.get(), new EnchantData(new CompoundTag()));
            event.setOutput(stack);
            event.setXp(1);
            event.setCanceled(false);
        }
    }
    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if ( event.getSource().getEntity() instanceof LivingEntity livingEntity ) {
            EnchantHelper.getEnchants(livingEntity.getMainHandItem()).forEach((ench, lvl) -> ench.doPostAttack(livingEntity,event.getEntity(), lvl));
        }
    }
    @SubscribeEvent
    public static void onHurt(LivingDamageEvent.Post event){
        if ( event.getSource().getEntity() instanceof LivingEntity livingEntity ) {
            EnchantHelper.getArmorItemStacks(livingEntity).forEach(slot -> EnchantHelper.getEnchants(slot).forEach((ench, lvl) -> ench.doPostHurt(event.getEntity(),livingEntity, lvl)));
        }
    }

}

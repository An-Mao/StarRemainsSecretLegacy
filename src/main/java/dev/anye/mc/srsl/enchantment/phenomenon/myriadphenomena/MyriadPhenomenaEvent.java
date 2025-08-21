package dev.anye.mc.srsl.enchantment.phenomenon.myriadphenomena;

import dev.anye.mc.cores.item.ItemHelper;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
public class MyriadPhenomenaEvent {
    private static final String MPKey = "MyriadPhenomenaEnchantment";
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event){
        if (MyriadPhenomena.ENABLE && !event.getEntity().level().isClientSide){
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity){
                Map<Enchantment, Integer> es = getEnchant(livingEntity.getMainHandItem());
                if (es != null) {
                    float atk = 0;
                    for (Map.Entry<Enchantment, Integer> entry : es.entrySet()) {
                        Enchantment enchantment = entry.getKey();
                        atk += enchantment.getDamageBonus(entry.getValue(), event.getEntity().getMobType(), livingEntity.getMainHandItem());
                        enchantment.doPostAttack(livingEntity, event.getEntity(), entry.getValue());
                    }
                    event.setAmount(event.getAmount() + atk);
                }
            }
        }
    }
    @SubscribeEvent
    public static void onAnvil(AnvilUpdateEvent event){
        if (MyriadPhenomena.ENABLE && event.getPlayer() instanceof ServerPlayer){
            if (ItemHelper.hasEnchant(event.getLeft(), NekoEnchantments.myriad_phenomena)){
                ItemStack rightItem = event.getRight();
                if(rightItem.getItem() == Items.BAMBOO) {
                    ItemStack outItem = event.getLeft().copy();
                    if (outItem.getTag() != null) {
                        ListTag listTag = outItem.getEnchantmentTags().copy();
                        outItem.getTag().put(MPKey, listTag);
                        outItem.getEnchantmentTags().clear();
                        outItem.enchant(NekoEnchantments.myriad_phenomena, 1);
                        event.setMaterialCost(1);
                        event.setCost(1);
                        event.setOutput(outItem);
                    }
                }else if (!rightItem.getEnchantmentTags().isEmpty()){
                    event.setCanceled(true);
                }
            }
        }
    }
    private static Map<Enchantment, Integer> getEnchant(ItemStack stack){
        if (stack == null || stack == ItemStack.EMPTY){
            return null;
        }
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(MPKey)){
            ListTag enchantmentsList = tag.getList(MPKey, Tag.TAG_COMPOUND);
            return EnchantmentHelper.deserializeEnchantments(enchantmentsList);
        }
        return null;
    }
}

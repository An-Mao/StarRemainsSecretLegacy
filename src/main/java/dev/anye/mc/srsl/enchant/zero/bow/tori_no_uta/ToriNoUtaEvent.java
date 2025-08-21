package dev.anye.mc.srsl.enchant.zero.bow.tori_no_uta;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.core.Enchants;
import dev.anye.mc.srsl.gui.ToriNoUtaGui;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.entity.player.ArrowNockEvent;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class ToriNoUtaEvent {
    private static final float quota = EnchantsConfig.INSTANCE.getValue(EnchantReg.TORI_NO_UTA,"quota");
    public static boolean CanRun(Level level){return ToriNoUta.ENABLE && ToriNoUtaGui.showEffects && level.isClientSide();}
    @SubscribeEvent
    public static void onArrowNock(ArrowNockEvent event){
        if (CanRun(event.getLevel())) {
            ItemStack itemStack = event.getBow();
            if (!itemStack.isEmpty() && EnchantHelper.hasEnchant(itemStack, Enchants.toriNoUta) && event.getEntity().getOffhandItem().getItem() == Items.FEATHER) {
                ToriNoUtaGui.start = true;
            }

        }
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event){
        if (ToriNoUta.ENABLE) {
            if (event.getEntity() instanceof AbstractArrow arrow && !arrow.level().isClientSide) {
                if (arrow.getOwner() instanceof ServerPlayer serverPlayer) {
                    if (serverPlayer.getOffhandItem().getItem() == Items.FEATHER && EnchantHelper.hasEnchant(serverPlayer.getMainHandItem(), Enchants.toriNoUta)) {

                        serverPlayer.getOffhandItem().shrink(1);
                        arrow.setDeltaMovement(arrow.getDeltaMovement().scale(quota));
                    }
                }
            }
        }
    }



    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event){
        if (CanRun(event.getLevel())) {
            ItemStack itemStack = event.getBow();
            if (!itemStack.isEmpty() && EnchantHelper.hasEnchant(itemStack, Enchants.toriNoUta) && event.getEntity().getOffhandItem().getItem() == Items.FEATHER) {
                ToriNoUtaGui.start = false;
            }

        }
    }

}

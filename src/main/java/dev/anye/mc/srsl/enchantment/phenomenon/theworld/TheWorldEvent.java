package dev.anye.mc.srsl.enchantment.phenomenon.theworld;

import dev.anye.mc.cores.amlib.the$world.TheWorld;
import dev.anye.mc.cores.entity.EntityHelper;
import dev.anye.mc.cores.item.ItemHelper;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
public class TheWorldEvent {
    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event){
        if (TheWorldEnchantment.ENABLE) {
            Entity entity = event.getEntity();
            Entity sEntity = event.getSource().getEntity();
            if (entity == null || sEntity == null) {
                return;
            }
            if (entity instanceof ServerPlayer player && !(sEntity instanceof ServerPlayer)) {
                if (EnchantmentHelper.getEnchantmentLevel(NekoEnchantments.the_world, player) > 0) {
                    setTheWorldTime(sEntity);
                }
            } else if (sEntity instanceof ServerPlayer serverPlayer) {
                if (ItemHelper.hasEnchant(serverPlayer.getMainHandItem(), NekoEnchantments.the_world)) {
                    setTheWorldTime(entity);
                }
            }
        }
    }
    public static void setTheWorldTime(Entity e){
        TheWorld.SetTheWorldState(e,true);
        TheWorld.SetTheWorldTime(e, EntityHelper.getLevelTime(e));
    }
}

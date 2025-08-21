package dev.anye.mc.srsl.enchant.neko.item.nekochop;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class NekoChopEvent{
    private static final ItemStack[] MOB_HEAD = {
            new ItemStack(Items.CREEPER_HEAD),
            new ItemStack(Items.DRAGON_HEAD),
            new ItemStack(Items.ZOMBIE_HEAD),
            new ItemStack(Items.PIGLIN_HEAD),
            new ItemStack(Items.WITHER_SKELETON_SKULL),
            new ItemStack(Items.SKELETON_SKULL),
    };
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NCE{
        @SubscribeEvent
        public static void onDeath(LivingDeathEvent event){
            if (NekoChop.ENABLE && event.getSource().getEntity() instanceof ServerPlayer player){
                Entity de = event.getEntity();
                if (de != null) {
                    int a = getA(de);
                    int lvl = EnchantHelper.getEnchantLevel(player.getMainHandItem(), Enchants.ni_chop);// player.getMainHandItem().getEnchantmentLevel(NekoEnchantments.ni_chop);
                    if (lvl > 0) {
                        if (lvl * 15 > _Math.RD.getIntRandomNumber(1, 100)) {
                            if (a > -1 && a < 6){de.spawnAtLocation(MOB_HEAD[a]);}
                        }
                    }
                }
            }
        }


    }
    private static int getA(Entity de) {
        int a = -1;
        if (de instanceof Creeper){
            a = 0;
        }
        if (de instanceof EnderDragon){
            a = 1;
        }
        if (de instanceof Zombie){
            a = 2;
        }
        if (de instanceof Piglin){
            a = 3;
        }
        if (de instanceof WitherSkeleton){
            a = 4;
        }
        if (de instanceof Skeleton){
            a = 5;
        }
        return a;
    }
}

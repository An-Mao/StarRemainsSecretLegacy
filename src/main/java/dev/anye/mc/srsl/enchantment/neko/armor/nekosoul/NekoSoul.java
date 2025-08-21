package dev.anye.mc.srsl.enchantment.neko.armor.nekosoul;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.neko.armor.NekoEA;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class NekoSoul extends NekoEA {
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.NEKO_SOUL);
    private final float consume = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.NEKO_SOUL,"consume");
    private final float quota = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.NEKO_SOUL,"quota");
    public static final String ENCHANTMENT_KEY_SOUL = "soul";
    public NekoSoul() {
        super(Rarity.VERY_RARE);
    }


    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof ArmorItem || super.canEnchant(pStack);
    }
    /*
    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if (!pAttacker.level().isClientSide){
            if (pTarget instanceof LivingEntity livingEntity){
                if (pAttacker instanceof ServerPlayer serverPlayer) {
                    int souls = serverPlayer.getMainHandItem().getTag().getInt(_AM_Constant.ENCHANTMENT_KEY_SOUL);
                    souls++;
                    serverPlayer.getMainHandItem().getTag().putInt(_AM_Constant.ENCHANTMENT_KEY_SOUL, souls);
                }
            }
        }
    }

     */

    @Override
    public void doPostHurt(LivingEntity pTarget, @NotNull Entity pAttacker, int pLevel) {
        if (!pTarget.level().isClientSide){
            if (pTarget instanceof ServerPlayer serverPlayer){
                ItemStack mitem = serverPlayer.getMainHandItem();
                if (mitem.getItem() != Items.AIR) {
                    if (mitem.getTag() != null) {
                        int souls = mitem.getTag().getInt(ENCHANTMENT_KEY_SOUL);
                        if (souls >= consume) {
                            souls -= (int) consume;
                            serverPlayer.heal(serverPlayer.getMaxHealth() * quota);
                            mitem.getTag().putInt(ENCHANTMENT_KEY_SOUL, souls);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}

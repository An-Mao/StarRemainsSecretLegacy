package dev.anye.mc.srsl.reg.enchant.neko.armor.nekosoul;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.neko.armor.NekoEA;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class NekoSoul extends NekoEA {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.NEKO_SOUL);
	private final float consume = EnchantsConfig.INSTANCE.getValue(EnchantReg.NEKO_SOUL, "consume");
	private final float quota = EnchantsConfig.INSTANCE.getValue(EnchantReg.NEKO_SOUL, "quota");
	public static final String ENCHANTMENT_KEY_SOUL = "soul";

	public NekoSoul() {
		super();
	}


	@Override
	public boolean canEnchant(ItemStack pStack) {
		return isArmor(pStack);
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
		if (!pTarget.level().isClientSide()) {
			if (pTarget instanceof ServerPlayer serverPlayer) {
				ItemStack mitem = serverPlayer.getMainHandItem();
				if (mitem.getItem() != Items.AIR) {
					CompoundTag compoundTag = EnchantHelper.getEnchantData(mitem).getTagCopy();
					CompoundTag soulData = compoundTag.getCompound("soul.data").orElse(new CompoundTag());
					int souls = soulData.getInt(ENCHANTMENT_KEY_SOUL).orElse(0);
					if (souls >= consume) {
						souls -= (int) consume;
						serverPlayer.heal(serverPlayer.getMaxHealth() * quota);
						soulData.putInt(ENCHANTMENT_KEY_SOUL, souls);
						compoundTag.put("soul.data", soulData);
						EnchantHelper.setEnchantData(mitem, compoundTag);
					}

				}
			}
		}
	}
}

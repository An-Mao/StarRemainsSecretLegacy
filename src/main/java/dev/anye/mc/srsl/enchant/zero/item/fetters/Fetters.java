package dev.anye.mc.srsl.enchant.zero.item.fetters;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.zero.item.ZeroItemE;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class Fetters extends ZeroItemE {
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.Z_FETTERS);
    private final float intervals = EnchantsConfig.INSTANCE.getValue(EnchantReg.Z_FETTERS,"intervals");
    public Fetters() {
        super();
    }

    @Override
    public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        super.doPostAttack(pAttacker, pTarget, pLevel);
        if (pTarget instanceof LivingEntity livingEntity){
            CompoundTag dat = livingEntity.getPersistentData();
            long gt = livingEntity.level().getGameTime();
            //System.out.println("gt:"+gt);
            if (gt - dat.getInt("Fetters") < intervals){
                return;
            }
            dat.putLong("Fetters",gt);
            dat.putDouble("Fx",livingEntity.getX());
            dat.putDouble("Fy",livingEntity.getY());
            dat.putDouble("Fz",livingEntity.getZ());
        }
    }
}

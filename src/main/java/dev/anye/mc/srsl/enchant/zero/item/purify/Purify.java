package dev.anye.mc.srsl.enchant.zero.item.purify;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.zero.item.ZeroItemE;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Purify extends ZeroItemE {
    private final float probability = EnchantsConfig.INSTANCE.getValue(EnchantReg.Z_PURIFY,"probability");
    String[] SK={ "Health" ,"Pos"};
    public Purify() {
        super();
    }
    @Override
    public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (pAttacker instanceof ServerPlayer){
            if (pTarget instanceof LivingEntity livingEntity){
                if (_Math.RD.getIntRandomNumber(1,100) < probability) {
                    //nbt.putByte("NoAI", (byte) 1);
                    CompoundTag dat = livingEntity.getPersistentData();
                    Iterable<String> keys = dat.getAllKeys();
                    for (String k : keys){
                        if (!Objects.equals(k, "Health") &&!Objects.equals(k, "Pos") ){
                            dat.remove(k);
                        }
                    }
                    //livingEntity.setInvisible();
                    livingEntity.getTags().clear();
                }
            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}

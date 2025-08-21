package dev.anye.mc.srsl.enchantment.neko.item.nekogod;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.neko.item.NekoEI;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class NekoGod extends NekoEI {
    private final float probability = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.NEKO_GOD,"probability");
    private final float lvl = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.NEKO_GOD,"level");
    public NekoGod() {
        super(Rarity.VERY_RARE);
    }

    @Override
    public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (pAttacker instanceof ServerPlayer serverPlayer && pTarget instanceof LivingEntity mob && !(pTarget instanceof ServerPlayer)) {
            float p = pLevel * probability;
            //0-100
            p += _Math.log2Floor((int) serverPlayer.getLuck());
            if (p > _Math.RD.getIntRandomNumber(1, 100)) {
                //32767
                p = Math.max(0.0F, mob.getHealth() - mob.getMaxHealth() * (serverPlayer.experienceLevel / lvl) * pLevel);
                if (p == 0.0F) {
                    mob.setHealth(0.00001F);
                    mob.die(serverPlayer.damageSources().genericKill());
                    mob.remove(Entity.RemovalReason.KILLED);
                } else {
                    mob.setHealth(p);
                }
            }
        }

    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}

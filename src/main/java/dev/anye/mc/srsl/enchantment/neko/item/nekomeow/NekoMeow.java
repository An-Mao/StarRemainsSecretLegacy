package dev.anye.mc.srsl.enchantment.neko.item.nekomeow;

import dev.anye.mc.srsl.enchantment.neko.item.NekoEI;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class NekoMeow extends NekoEI {
    public NekoMeow() {
        super(Rarity.COMMON);
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (!pAttacker.level().isClientSide){
            if (pAttacker instanceof ServerPlayer serverPlayer) {
                serverPlayer.serverLevel().playSound(null, serverPlayer.getOnPos(), SoundEvents.CAT_HISS, SoundSource.PLAYERS);
            }
        }
    }
    @Override
    public boolean isTreasureOnly() {
        return false;
    }
}

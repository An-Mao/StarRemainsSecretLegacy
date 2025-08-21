package dev.anye.mc.srsl.enchantment.zero.bow.rain_of_arrows;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
public class RainOfArrowsEvent {
    private static final float hPIE = (float) (Math.PI / 180F);
    private static final float number = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.RAIN_OF_ARROWS,"number");
    private static final int minXRot = (int) EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.RAIN_OF_ARROWS,"minXRot");
    private static final int maxXRot = (int) EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.RAIN_OF_ARROWS,"maxXRot");
    private static final int minYRot = (int) EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.RAIN_OF_ARROWS,"minYRot");
    private static final int maxYRot = (int) EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.RAIN_OF_ARROWS,"maxYRot");
    private static final float velocity = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.RAIN_OF_ARROWS,"velocity");
    private static final float inaccuracy = EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.RAIN_OF_ARROWS,"inaccuracy");
    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event){
        if (RainOfArrows.ENABLE && event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = serverPlayer.getMainHandItem();
            if (!itemStack.isEmpty() && itemStack.getEnchantmentLevel(NekoEnchantments.rainOfArrows) > 0 && event.getSource().getDirectEntity() instanceof AbstractArrow arrow){
                CompoundTag tag = arrow.getPersistentData().copy();
                if (!tag.getBoolean("shadow")) {
                    LivingEntity t = event.getEntity();
                    tag.putString("id",ForgeRegistries.ENTITY_TYPES.getKey(arrow.getType()).toString());
                    for (int i = 0; i<number;i++) {
                        EntityType.create(tag, serverPlayer.level()).ifPresent(entity -> {
                            if (entity instanceof AbstractArrow abstractArrow) {
                                abstractArrow.getPersistentData().putBoolean("shadow", true);
                                abstractArrow.setCritArrow(arrow.isCritArrow());
                                abstractArrow.setBaseDamage(arrow.getBaseDamage());
                                abstractArrow.setKnockback(arrow.getKnockback());
                                abstractArrow.setSecondsOnFire(arrow.getRemainingFireTicks());
                                abstractArrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                                double x = t.getX() + _Math.RD.getIntRandomNumber(-1,1);
                                double y = t.getY() + t.getBbHeight() + 2.0F;
                                double z = t.getZ() + _Math.RD.getIntRandomNumber(-1,1);
                                abstractArrow.setPos(x,y,z);
                                int xr = _Math.RD.getIntRandomNumber(minXRot,maxXRot);
                                int yr = _Math.RD.getIntRandomNumber(minYRot,maxYRot);
                                float f = -Mth.sin(yr * hPIE * Mth.cos(xr * hPIE));
                                float f1 = -Mth.sin(xr * hPIE);
                                float f2 = Mth.cos(yr * hPIE) * Mth.cos(xr * hPIE);
                                abstractArrow.shoot(f, f1, f2, velocity, inaccuracy);
                                Vec3 vec3 = serverPlayer.getDeltaMovement();
                                abstractArrow.setDeltaMovement(abstractArrow.getDeltaMovement().add(vec3.x, serverPlayer.onGround() ? 0.0D : vec3.y, vec3.z));
                                serverPlayer.level().addFreshEntity(abstractArrow);
                            }
                        });
                    }
                }
            }
        }
    }
}

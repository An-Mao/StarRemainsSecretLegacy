package dev.anye.mc.srsl.cap.cooldown;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PCDPro implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerCooldown> P_I_CD =
            CapabilityManager.get(new CapabilityToken<PlayerCooldown>() {});
    private PlayerCooldown PCD = null;
    private final LazyOptional<PlayerCooldown> optional = LazyOptional.of(this::createPlayerCooldown);

    private PlayerCooldown createPlayerCooldown() {
        if (this.PCD == null)
        {
            this.PCD = new PlayerCooldown();
        }
        return this.PCD;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == P_I_CD)
        {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerCooldown().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCooldown().loadNBTData(nbt);
    }
}

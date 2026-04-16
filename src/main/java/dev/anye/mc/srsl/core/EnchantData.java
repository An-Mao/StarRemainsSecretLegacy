package dev.anye.mc.srsl.core;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EnchantData {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final EnchantData EMPTY = new EnchantData(new CompoundTag());
    public static final Codec<EnchantData> CODEC = Codec.withAlternative(CompoundTag.CODEC, TagParser.FLATTENED_CODEC)
            .xmap(EnchantData::new, p_327962_ -> p_327962_.tag);
    private final CompoundTag tag;
    public static final StreamCodec<ByteBuf, EnchantData> STREAM_CODEC = ByteBufCodecs.COMPOUND_TAG.map(EnchantData::new, p_329964_ -> p_329964_.tag);
    public EnchantData(CompoundTag p_331981_) {
        this.tag = p_331981_;
    }
    public static EnchantData of(CompoundTag pTag) {
        return new EnchantData(pTag.copy());
    }
    public boolean matchedBy(CompoundTag pTag) {
        return NbtUtils.compareNbt(pTag, this.tag, true);
    }
    public static Predicate<ItemStack> itemMatcher(DataComponentType<EnchantData> pComponentType, CompoundTag pTag) {
        return p_334391_ -> {
            EnchantData enchantdata = p_334391_.getOrDefault(pComponentType, EMPTY);
            return enchantdata.matchedBy(pTag);
        };
    }
    public EnchantData copy() {
        return new EnchantData(this.tag.copy());
    }
    public CompoundTag getTagCopy(){
        return this.tag.copy();
    }
    public CompoundTag getUnsafe() {
        return this.tag;
    }
    public CompoundTag get(String key) {
        return this.tag.getCompound(key).orElse(new CompoundTag());
    }
    public EnchantData update(Consumer<CompoundTag> pUpdater) {
        CompoundTag compoundtag = this.tag.copy();
        pUpdater.accept(compoundtag);
        return new EnchantData(compoundtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnchantData that = (EnchantData) o;
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tag);
    }
}

package dev.anye.mc.srsl.reg.data;

import dev.anye.mc.srsl.SRSL;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DataReg {

	public static final DeferredRegister<DataComponentType<?>> DATAS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, SRSL.MOD_ID);

	public static final
	DeferredHolder<DataComponentType<?>, DataComponentType<EnchantData>> Enchant_Data = DATAS.register("neko_enchant", () -> DataComponentType.<EnchantData>builder().persistent(EnchantData.CODEC).networkSynchronized(EnchantData.STREAM_CODEC).build());

	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE_DEFERRED_REGISTER = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SRSL.MOD_ID);
	public static final
	DeferredHolder<AttachmentType<?>, AttachmentType<CoolDownData>> CoolDown = ATTACHMENT_TYPE_DEFERRED_REGISTER.register("cooldown", () -> AttachmentType.builder(() -> new CoolDownData(new CompoundTag())).build());

	public static void register(IEventBus eventBus) {
		DATAS.register(eventBus);
		ATTACHMENT_TYPE_DEFERRED_REGISTER.register(eventBus);
	}
}

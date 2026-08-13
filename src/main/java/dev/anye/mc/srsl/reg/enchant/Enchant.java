package dev.anye.mc.srsl.reg.enchant;

import dev.anye.core.data._Data;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

public abstract class Enchant extends EnchantSlotCheck {
	protected final boolean enable;
	protected final _Data config;
	protected final ChatFormatting nameColor;
	protected final EnchantConfigData defaultData;

	protected Enchant(EnchantConfigData configData,EnchantConfigData defaultData) {
		this(configData,defaultData,ChatFormatting.GRAY);
	}

	protected Enchant(EnchantConfigData configData,EnchantConfigData defaultData,ChatFormatting chatFormatting) {
		this.nameColor = chatFormatting;
		this.defaultData = defaultData;
		if (configData != null){
			this.enable = configData.enable();
			this.config = configData.parameters();
		}else {
			this.enable = defaultData.enable();
			this.config = defaultData.parameters();
		}
	}

	public Identifier getRegistryName() {
		return EnchantReg.getResourceLocation(this);
	}

	public String getDescriptionId() {
		return "enchant." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath();
	}

	@NotNull
	public Component getFullName(int pLevel) {
		MutableComponent mutablecomponent = Component.translatable(this.getDescriptionId());
		mutablecomponent.withStyle(nameColor);

		if (pLevel > 1) {
			mutablecomponent.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + pLevel));
		}
		return mutablecomponent.append(Component.literal("x"));
	}

	public int getMaxLevel() {
		return 1;
	}

	public int getCost(int level) {
		return 30;
	}

	public int getWeight() {
		return 100;
	}

	public boolean canEnchant(ItemStack stack) {
		return false;
	}

	public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {

	}

	public void doPostHurt(LivingEntity pTarget, Entity pAttacker, int pLevel) {

	}

	public float getDamageBonus(int level, Entity target, ItemStack enchantedItem) {
		return 0;
	}


	public void loadConfig(CompoundTag data) {
	}

	public EnchantConfigData defaultData() {
		return defaultData;
	}


	public final void event(IEventBus modEventBus){
		if (enable) register(modEventBus);
	}

	protected void register(IEventBus modEventBus){}


	public boolean enable(){
		return enable;
	}
}

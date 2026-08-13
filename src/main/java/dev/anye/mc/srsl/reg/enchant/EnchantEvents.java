package dev.anye.mc.srsl.reg.enchant;

import dev.anye.core.color._ColorCDT;
import dev.anye.core.math._Math;
import dev.anye.mc.cores.helper.component.ComponentStyle;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.neko.armor.nekoblessing.NekoBlessing;
import dev.anye.mc.srsl.reg.enchant.neko.armor.nekosoul.NekoSoul;
import dev.anye.mc.srsl.reg.enchant.neko.item.nekoemperor.NekoEmperor;
import dev.anye.mc.srsl.reg.enchant.neko.item.nekoking.NekoKing;
import dev.anye.mc.srsl.reg.enchant.neko.item.nekolove.NekoLove;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class EnchantEvents {
	private static final String TOOLTIPS_REFINE = "tooltips.srsl.refine";
	private static final String TOOLTIPS_KILLS = "tooltips.srsl.kills";
	private static final String TOOLTIPS_SOULS = "tooltips.srsl.souls";
	private static final String TOOLTIPS_BLESSING = "tooltips.srsl.blessing";
	private static final String TOOLTIPS_DUALITY_G = "tooltips.srsl.duality.g";
	private static final String TOOLTIPS_DUALITY_B = "tooltips.srsl.duality.b";
	private static final String[] TOOLTIPS_LOVE = {
			"tooltips.srsl.love_y",
			"tooltips.srsl.love_n"
	};

	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent event) {
		@NotNull ItemStack item = event.getItemStack();
		int indexs = 1;
		Player player = event.getEntity();
		List<Component> tooltip = event.getToolTip();
		if (player != null) {
			MutableComponent ens = Component.literal("");
			float[] damage = {0};

			EnchantHelper.getEnchants(item).forEach((ench, lvl) -> {
				ens.append(Component.literal("[").withColor(_ColorCDT.black)).append(ench.getFullName(lvl)).append(Component.literal("]").withColor(_ColorCDT.white));
				damage[0] += ench.getDamageBonus(lvl, player, item);
			});
			tooltip.add(indexs, ens);
			indexs++;
            /*
            indexs += Duality(item,tooltip,indexs);
            indexs += Love(player,item,tooltip,indexs);
            indexs += King(player,item,tooltip,indexs);
            indexs += Emperor(player,item,tooltip,indexs);
            indexs += Soul(player,item,tooltip,indexs);
            indexs += Blessing(player,item,tooltip,indexs);

             */
			if (EnchantReg.DUALITY.get().enable()) {
				indexs += Duality(item, tooltip, indexs);
			}
			if (EnchantReg.NEKO_LOVE.get().enable()) {
				indexs += Love(player, item, tooltip, indexs);
			}
			if (EnchantReg.NEKO_KING.get().enable()) {
				indexs += King(player, item, tooltip, indexs);
			}
			if (EnchantReg.NEKO_EMPEROR.get().enable()) {
				indexs += Emperor(player, item, tooltip, indexs);
			}
			if (EnchantReg.NEKO_SOUL.get().enable()) {
				indexs += Soul(player, item, tooltip, indexs);
			}
			if (EnchantReg.NEKO_BLESSING.get().enable()) {
				indexs += Blessing(player, item, tooltip, indexs);
			}
			if (damage[0] > 0) {
				tooltip.add(indexs, Component.literal("+").withColor(_ColorCDT.yellow).append(damage[0] + " ").withColor(_ColorCDT.red).append(Component.translatable("tooltips.srsl.extra_attack_damage").withColor(_ColorCDT.blue)));
			}

		}
	}

	public static int Duality(ItemStack itemStack, List<Component> tooltip, int index) {
		if (EnchantHelper.hasEnchant(itemStack, Enchants.duality)) {
			CompoundTag dat = EnchantHelper.getEnchantData(itemStack).getTagCopy().getCompound("duality.data").orElse(new CompoundTag());
			float a = dat.getFloat("duality").orElse(0f);
			String s;
			ChatFormatting cr;
			if (a < 0) {
				s = TOOLTIPS_DUALITY_B;
				cr = ChatFormatting.RED;
			} else {
				s = TOOLTIPS_DUALITY_G;
				cr = ChatFormatting.GOLD;
			}
			tooltip.add(index, Component.translatable(s).append(" : ").append(String.valueOf(a)).withStyle(cr));
			return 1;
		}
		return 0;
	}

	public static int Love(Player player, ItemStack item, List<Component> tooltip, int index) {
		if (EnchantHelper.hasEnchant(item, Enchants.ni_love)) {
			CompoundTag dat = EnchantHelper.getEnchantData(item).getTagCopy().getCompound("love.data").orElse(new CompoundTag());
			dat.getString(NekoLove.ENCHANTMENT_KEY_LOVE).ifPresent(uuid -> {
				if (uuid.equals(player.getUUID())) {
					tooltip.add(index, ComponentStyle.Flash(Component.translatable(TOOLTIPS_LOVE[0]).getString()));
				} else {
					tooltip.add(index, Component.translatable(TOOLTIPS_LOVE[1]).withStyle(ChatFormatting.RED));
				}
			});
			return 1;
		}
		return 0;
	}

	public static int King(Player player, ItemStack item, List<Component> tooltip, int index) {
		if (EnchantHelper.hasEnchant(item, Enchants.ni_king)) {

			CompoundTag dat = EnchantHelper.getEnchantData(item).getTagCopy().getCompound("king.data").orElse(new CompoundTag());
			int tmp = dat.getInt(NekoKing.ENCHANTMENT_KEY_REFINE).orElse(0);
			if (tmp < 200) {
				ChatFormatting color = ChatFormatting.WHITE;
				if (tmp < 100 && tmp >= 50) {
					color = ChatFormatting.YELLOW;
				}
				if (tmp >= 100) {
					color = ChatFormatting.DARK_BLUE;
				}
				tooltip.add(index, Component.translatable(TOOLTIPS_REFINE).append(" : ").append(String.valueOf(tmp)).withStyle(color));
			} else {
				MutableComponent str = Component.translatable(TOOLTIPS_REFINE).append(" : ").append(String.valueOf(tmp));
				tooltip.add(index, ComponentStyle.Flash(str.getString()));
			}
			return 1;
		}
		return 0;
	}

	public static int Emperor(Player player, ItemStack item, List<Component> tooltip, int index) {
		if (EnchantHelper.hasEnchant(item, Enchants.ni_emperor)) {
			CompoundTag dat = EnchantHelper.getEnchantData(item).getTagCopy().getCompound("emperor.data").orElse(new CompoundTag());
			int tmp = dat.getInt(NekoEmperor.ENCHANTMENT_KEY_KILL).orElse(0);
			if (tmp < 1000) {
				ChatFormatting color = ChatFormatting.WHITE;
				if (tmp < 700 && tmp >= 300) {
					color = ChatFormatting.RED;
				}
				if (tmp >= 700) {
					color = ChatFormatting.DARK_RED;
				}
				tooltip.add(index, Component.translatable(TOOLTIPS_KILLS)
						.append(" : ")
						.append(String.valueOf(tmp))
						.withStyle(color));
			} else {
				MutableComponent str = Component.translatable(TOOLTIPS_KILLS).append(" : ").append(String.valueOf(tmp));
				tooltip.add(index, ComponentStyle.Flash(str.getString()));
			}
			return 1;
		}
		return 0;
	}

	public static int Soul(Player player, ItemStack item, List<Component> tooltip, int index) {
		if (EnchantHelper.hasEnchant(item, Enchants.na_soul)) {
			CompoundTag dat = EnchantHelper.getEnchantData(item).getTagCopy().getCompound("soul.data").orElse(new CompoundTag());
			int tmp = dat.getInt(NekoSoul.ENCHANTMENT_KEY_SOUL).orElse(0);
			if (tmp < 10000) {
				ChatFormatting color = ChatFormatting.WHITE;
				if (tmp < 6000 && tmp >= 3000) {
					color = ChatFormatting.GRAY;
				}
				if (tmp >= 6000) {
					color = ChatFormatting.DARK_GRAY;
				}
				tooltip.add(index, Component.translatable(TOOLTIPS_SOULS)
						.append(" : ")
						.append(String.valueOf(tmp))
						.withStyle(color));
			} else {
				MutableComponent str = Component.translatable(TOOLTIPS_SOULS).append(" : ").append(String.valueOf(tmp));
				tooltip.add(index, ComponentStyle.Flash(str.getString()));
			}
			return 1;
		}
		return 0;
	}

	public static int Blessing(Player player, ItemStack item, List<Component> tooltip, int index) {
		if (EnchantHelper.hasEnchant(item, Enchants.na_blessing)) {
			CompoundTag dat = EnchantHelper.getEnchantData(item).getTagCopy().getCompound("blessing.data").orElse(new CompoundTag());
			int tmp = dat.getInt(NekoBlessing.ENCHANTMENT_KEY_BLESSING).orElse(0);
			if (tmp < 1000) {
				ChatFormatting color = ChatFormatting.WHITE;
				if (tmp < 300 && tmp >= 100) {
					color = ChatFormatting.LIGHT_PURPLE;
				}
				if (tmp >= 300) {
					color = ChatFormatting.DARK_PURPLE;
				}
				tooltip.add(index, Component.translatable(TOOLTIPS_BLESSING)
						.append(" : ")
						.append(String.valueOf(tmp))
						.withStyle(color));
			} else {
				MutableComponent str = Component.translatable(TOOLTIPS_BLESSING).append(" : ").append(String.valueOf(tmp));
				tooltip.add(index, ComponentStyle.Flash(str.getString()));
			}
			return 1;
		}
		return 0;
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof Warden warden) {
			if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
				ItemEntity itemEntity = new ItemEntity(serverPlayer.level(), warden.getX(), warden.getY() + 0.5, warden.getZ(), new ItemStack(Items.ECHO_SHARD, _Math.RD.getIntRandomNumber(1, 3)));
				itemEntity.setDeltaMovement(0, 0.1, 0);
				serverPlayer.level().addFreshEntity(itemEntity);
			}
		}
	}
}

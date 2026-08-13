package dev.anye.mc.srsl.core;

import dev.anye.mc.srsl.enchant.zero.item.unbreakable.Unbreakable;
import net.neoforged.neoforge.registries.DeferredHolder;

public class Enchants extends EnchantReg {

	//armor
	public static Enchant na_blessing = getEnchantment(NEKO_BLESSING);
	public static Enchant na_life = getEnchantment(NEKO_LIFE);
	public static Enchant na_mirror = getEnchantment(NEKO_MIRROR);
	public static Enchant na_ninja = getEnchantment(NEKO_NINJA);
	public static Enchant na_soul = getEnchantment(NEKO_SOUL);

	//item
	public static Enchant ni_blade = getEnchantment(NEKO_BLADE);
	public static Enchant ni_chop = getEnchantment(NEKO_CHOP);
	public static Enchant ni_day = getEnchantment(NEKO_DAY);
	public static Enchant ni_emperor = getEnchantment(NEKO_EMPEROR);
	public static Enchant ni_god = getEnchantment(NEKO_GOD);
	public static Enchant ni_king = getEnchantment(NEKO_KING);
	public static Enchant ni_love = getEnchantment(NEKO_LOVE);
	public static Enchant ni_meow = getEnchantment(NEKO_MEOW);
	public static Enchant ni_night = getEnchantment(NEKO_NIGHT);

	//armor
	public static Enchant oa_adaptive = getEnchantment(E_ADAPTIVE);
	public static Enchant oa_natural = getEnchantment(E_NATURAL);
	public static Enchant oa_red_lotus = getEnchantment(E_REDLOTUS);
	public static Enchant oa_stance = getEnchantment(E_STANCE);
	public static Enchant oa_warlord = getEnchantment(E_WARLORD);

	//item
	public static Enchant oi_angel = getEnchantment(E_ANGEL);
	public static Enchant oi_cross = getEnchantment(E_CROSS);
	public static Enchant oi_death_sickle = getEnchantment(E_DEATH_SICKLE);
	public static Enchant oi_gan_jiang = getEnchantment(E_GAN_JIANG);
	public static Enchant oi_judgment = getEnchantment(E_JUDGMENT);
	public static Enchant oi_mahogany = getEnchantment(E_MAHOGANY);
	public static Enchant oi_mo_ye = getEnchantment(E_MO_YE);
	public static Enchant oi_zen_stick = getEnchantment(E_Zen_Stick);


	//item
	public static Enchant zi_confusion = getEnchantment(Z_CONFUSION);
	public static Enchant zi_purify = getEnchantment(Z_PURIFY);
	public static Enchant zi_break_defense = getEnchantment(Z_BREAK_DEFENSE);
	public static Enchant zi_bright = getEnchantment(Z_BRIGHT);
	public static Enchant zi_alone = getEnchantment(Z_ALONE);
	public static Enchant zi_fetters = getEnchantment(Z_FETTERS);
	public static Enchant zi_indestructible = getEnchantment(Z_INDESTRUCTIBLE);
	public static Unbreakable zi_unbreakable = getEnchantment(Z_UNBREAKABLE);

	public static Enchant zi_dp = getEnchantment(Z_DP);

	public static Enchant rainOfArrows = getEnchantment(RAIN_OF_ARROWS);

	public static Enchant toriNoUta = getEnchantment(TORI_NO_UTA);
	public static Enchant chainHarvesting = getEnchantment(ChainHarvesting);


	public static Enchant duality = getEnchantment(DUALITY);
	public static Enchant the_world = getEnchantment(THE_WORLD);
	//public static Enchant myriad_phenomena = getEnchantment(MYRIAD_PHENOMENA);

	public static <T extends Enchant> T getEnchantment(DeferredHolder<Enchant, T> registryObject) {
		if (registryObject != null) {
			return registryObject.get();
		}
		return null;
	}
}

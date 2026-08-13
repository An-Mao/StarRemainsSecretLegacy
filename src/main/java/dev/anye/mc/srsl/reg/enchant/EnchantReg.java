package dev.anye.mc.srsl.reg.enchant;

import dev.anye.mc.cores.register.Register;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.config.enchants.EnchantIO;
import dev.anye.mc.srsl.reg.enchant.blood.coagulation.Coagulation;
import dev.anye.mc.srsl.reg.enchant.blood.vampirism.Vampirism;
import dev.anye.mc.srsl.reg.enchant.curse.corrode.Corrode;
import dev.anye.mc.srsl.reg.enchant.curse.disease.CurseDisease;
import dev.anye.mc.srsl.reg.enchant.neko.armor.nekoblessing.NekoBlessing;
import dev.anye.mc.srsl.reg.enchant.neko.armor.nekolife.NekoLife;
import dev.anye.mc.srsl.reg.enchant.neko.armor.nekomirror.NekoMirror;
import dev.anye.mc.srsl.reg.enchant.neko.armor.nekoninja.NekoNinja;
import dev.anye.mc.srsl.reg.enchant.neko.armor.nekosoul.NekoSoul;
import dev.anye.mc.srsl.reg.enchant.neko.item.nekoblade.NekoBlade;
import dev.anye.mc.srsl.reg.enchant.neko.item.nekochop.NekoChop;
import dev.anye.mc.srsl.reg.enchant.neko.item.nekoday.NekoDay;
import dev.anye.mc.srsl.reg.enchant.neko.item.nekoemperor.NekoEmperor;
import dev.anye.mc.srsl.reg.enchant.neko.item.nekogod.NekoGod;
import dev.anye.mc.srsl.reg.enchant.neko.item.nekoking.NekoKing;
import dev.anye.mc.srsl.reg.enchant.neko.item.nekolove.NekoLove;
import dev.anye.mc.srsl.reg.enchant.neko.item.nekomeow.NekoMeow;
import dev.anye.mc.srsl.reg.enchant.neko.item.nekonight.NekoNight;
import dev.anye.mc.srsl.reg.enchant.phenomenon.duality.Duality;
import dev.anye.mc.srsl.reg.enchant.phenomenon.theworld.TheWorldEnchant;
import dev.anye.mc.srsl.reg.enchant.spirit.armor.adaptive.Adaptive;
import dev.anye.mc.srsl.reg.enchant.spirit.armor.natural.Natural;
import dev.anye.mc.srsl.reg.enchant.spirit.armor.redlotus.RedLotus;
import dev.anye.mc.srsl.reg.enchant.spirit.armor.stance.Stance;
import dev.anye.mc.srsl.reg.enchant.spirit.armor.warlord.Warlord;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.angel.Angel;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.cross.Cross;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.deathsickle.DeathSickle;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.ganjiang.GanJiang;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.judgment.Judgment;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.mahogany.Mahogany;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.moye.MoYe;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.zenstick.ZenStick;
import dev.anye.mc.srsl.reg.enchant.zero.bow.rain_of_arrows.RainOfArrows;
import dev.anye.mc.srsl.reg.enchant.zero.bow.tori_no_uta.ToriNoUta;
import dev.anye.mc.srsl.reg.enchant.zero.item.alone.Alone;
import dev.anye.mc.srsl.reg.enchant.zero.item.breakdefense.BreakDefense;
import dev.anye.mc.srsl.reg.enchant.zero.item.bright.Bright;
import dev.anye.mc.srsl.reg.enchant.zero.item.confusion.Confusion;
import dev.anye.mc.srsl.reg.enchant.zero.item.deathproclamation.DeathProclamation;
import dev.anye.mc.srsl.reg.enchant.zero.item.fetters.Fetters;
import dev.anye.mc.srsl.reg.enchant.zero.item.indestructible.Indestructible;
import dev.anye.mc.srsl.reg.enchant.zero.item.purify.Purify;
import dev.anye.mc.srsl.reg.enchant.zero.item.unbreakable.Unbreakable;
import dev.anye.mc.srsl.reg.enchant.zero.tool.ChainHarvesting;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Function;


public class EnchantReg {
	public static final Identifier KEY = Identifier.fromNamespaceAndPath(SRSL.MOD_ID, "enchant");
	public static final Register<Enchant> ENCHANT = new Register<>(KEY, enchantRegistryBuilder -> enchantRegistryBuilder.sync(true));

	public static DeferredHolder<Enchant, Enchant> NEKO_BLADE = reg("neko_blade", NekoBlade::new);
	public static DeferredHolder<Enchant, Enchant> NEKO_GOD = reg("neko_god", NekoGod::new);
	public static DeferredHolder<Enchant, Enchant> NEKO_MEOW = reg("neko_meow", NekoMeow::new);
	public static DeferredHolder<Enchant, Enchant> NEKO_KING = reg("neko_king", NekoKing::new);
	public static DeferredHolder<Enchant, Enchant> NEKO_EMPEROR = reg("neko_emperor", NekoEmperor::new);
	public static DeferredHolder<Enchant, Enchant> NEKO_DAY = reg("neko_day", NekoDay::new);
	public static DeferredHolder<Enchant, Enchant> NEKO_NIGHT = reg("neko_night", NekoNight::new);
	public static DeferredHolder<Enchant, Enchant> NEKO_CHOP = reg("neko_chop", NekoChop::new);
	public static DeferredHolder<Enchant, Enchant> NEKO_LOVE = reg("neko_love", NekoLove::new);
	public static DeferredHolder<Enchant, Enchant> E_ANGEL = reg("e_angel", Angel::new);
	public static DeferredHolder<Enchant, Enchant> E_CROSS = reg("e_cross", Cross::new);
	public static DeferredHolder<Enchant, Enchant> E_DEATH_SICKLE = reg("e_death_sickle", DeathSickle::new);
	public static DeferredHolder<Enchant, Enchant> E_GAN_JIANG = reg("e_gan_jiang", GanJiang::new);
	public static DeferredHolder<Enchant, Enchant> E_JUDGMENT = reg("e_judgment", Judgment::new);
	public static DeferredHolder<Enchant, Enchant> E_MAHOGANY = reg("e_mahogany", Mahogany::new);
	public static DeferredHolder<Enchant, Enchant> E_MO_YE = reg("e_mo_ye", MoYe::new);
	public static DeferredHolder<Enchant, Enchant> E_Zen_Stick = reg("e_zen_stick", ZenStick::new);

	// armor
	public static DeferredHolder<Enchant, Enchant> NEKO_NINJA = reg("neko_ninja", NekoNinja::new);
	public static DeferredHolder<Enchant, Enchant> NEKO_SOUL = reg("neko_soul", NekoSoul::new);
	public static DeferredHolder<Enchant, Enchant> NEKO_BLESSING = reg("neko_blessing", NekoBlessing::new);
	public static DeferredHolder<Enchant, Enchant> NEKO_MIRROR = reg("neko_mirror", NekoMirror::new);
	public static DeferredHolder<Enchant, Enchant> NEKO_LIFE = reg("neko_life", NekoLife::new);
	public static DeferredHolder<Enchant, Enchant> E_ADAPTIVE = reg("e_adaptive", Adaptive::new);
	public static DeferredHolder<Enchant, Enchant> E_NATURAL = reg("e_natural", Natural::new);
	public static DeferredHolder<Enchant, Enchant> E_REDLOTUS = reg("e_red_lotus", RedLotus::new);
	public static DeferredHolder<Enchant, Enchant> E_STANCE = reg("e_stance", Stance::new);
	public static DeferredHolder<Enchant, Enchant> E_WARLORD = reg("e_warlord", Warlord::new);


	//item
	public static DeferredHolder<Enchant, Enchant> Z_CONFUSION = reg("z_confusion", Confusion::new);
	public static DeferredHolder<Enchant, Enchant> Z_PURIFY = reg("zi_purify", Purify::new);
	public static DeferredHolder<Enchant, Enchant> Z_BREAK_DEFENSE = reg("zi_break_defense", BreakDefense::new);
	public static DeferredHolder<Enchant, Enchant> Z_BRIGHT = reg("zi_bright", Bright::new);
	public static DeferredHolder<Enchant, Enchant> Z_ALONE = reg("zi_alone", Alone::new);

	public static DeferredHolder<Enchant, Enchant> Z_DP = reg("zi_dp", DeathProclamation::new);
	public static DeferredHolder<Enchant, Enchant> Z_FETTERS = reg("zi_fetters", Fetters::new);
	public static DeferredHolder<Enchant, Enchant> Z_INDESTRUCTIBLE = reg("zi_indestructible", Indestructible::new);
	public static DeferredHolder<Enchant, Unbreakable> Z_UNBREAKABLE = reg("zi_unbreakable", Unbreakable::new);
	public static DeferredHolder<Enchant, Enchant> RAIN_OF_ARROWS = reg("rain_of_arrows", RainOfArrows::new);
	public static DeferredHolder<Enchant, ToriNoUta> TORI_NO_UTA = reg("tori_no_uta", ToriNoUta::new);

	public static DeferredHolder<Enchant, ChainHarvesting> Chain_Harvesting = reg("chain_harvesting", ChainHarvesting::new);

	public static DeferredHolder<Enchant, Vampirism> B_DRINK_BLOOD = reg("b_vampirism", Vampirism::new);
	public static DeferredHolder<Enchant, Enchant> B_COAGULATION = reg("b_coagulation", Coagulation::new);

	public static DeferredHolder<Enchant, Enchant> CURSE_DISEASE = reg("curse_disease", CurseDisease::new);
	public static DeferredHolder<Enchant, Enchant> CORRODE = reg("corrode", Corrode::new);


	public static DeferredHolder<Enchant, Enchant> DUALITY = reg("duality", Duality::new);
	public static DeferredHolder<Enchant, Enchant> THE_WORLD = reg("the_world", TheWorldEnchant::new);
	//public static DeferredHolder<Enchant, Enchant> MYRIAD_PHENOMENA = reg("myriad_phenomena", MyriadPhenomena::new);



	public static <T extends Enchant> DeferredHolder<Enchant,T> reg(String name , Function<EnchantConfigData , T> function) {
		return ENCHANT.register(name, () -> function.apply(EnchantIO.get(name)));
	}
	/*
	public static <T extends Enchant> DeferredHolder<Enchant, T> reg(String name, Supplier<T> sup) {

		EnchantConfigData enchantConfigData = EnchantIO.get(name);
		if (EnchantsConfig.INSTANCE.isEnable(EnchantIO.get(name),name)) {
			return ENCHANT.register(name, sup);
		} else {
			return null;
		}
	}

	 */


	public static void register(IEventBus eventBus) {
		ENCHANT.register(eventBus);
	}

	public static Enchant getEnchant(String resourceLocation) {
		return getEnchant(Identifier.tryParse(resourceLocation));
	}

	public static Enchant getEnchant(Identifier resourceLocation) {
		return ENCHANT.registry().get(resourceLocation).orElseThrow().value();
	}

	public static Identifier getResourceLocation(Enchant enchant) {
		return ENCHANT.registry().getKey(enchant);
	}
}

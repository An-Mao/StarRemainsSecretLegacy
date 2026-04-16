package dev.anye.mc.srsl.core;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.enchant.blood.coagulation.Coagulation;
import dev.anye.mc.srsl.enchant.blood.vampirism.Vampirism;
import dev.anye.mc.srsl.enchant.curse.corrode.Corrode;
import dev.anye.mc.srsl.enchant.curse.disease.CurseDisease;
import dev.anye.mc.srsl.enchant.neko.armor.nekoblessing.NekoBlessing;
import dev.anye.mc.srsl.enchant.neko.armor.nekolife.NekoLife;
import dev.anye.mc.srsl.enchant.neko.armor.nekomirror.NekoMirror;
import dev.anye.mc.srsl.enchant.neko.armor.nekoninja.NekoNinja;
import dev.anye.mc.srsl.enchant.neko.armor.nekosoul.NekoSoul;
import dev.anye.mc.srsl.enchant.neko.item.nekoblade.NekoBlade;
import dev.anye.mc.srsl.enchant.neko.item.nekochop.NekoChop;
import dev.anye.mc.srsl.enchant.neko.item.nekoday.NekoDay;
import dev.anye.mc.srsl.enchant.neko.item.nekoemperor.NekoEmperor;
import dev.anye.mc.srsl.enchant.neko.item.nekogod.NekoGod;
import dev.anye.mc.srsl.enchant.neko.item.nekoking.NekoKing;
import dev.anye.mc.srsl.enchant.neko.item.nekolove.NekoLove;
import dev.anye.mc.srsl.enchant.neko.item.nekomeow.NekoMeow;
import dev.anye.mc.srsl.enchant.neko.item.nekonight.NekoNight;
import dev.anye.mc.srsl.enchant.phenomenon.duality.Duality;
import dev.anye.mc.srsl.enchant.phenomenon.theworld.TheWorldEnchant;
import dev.anye.mc.srsl.enchant.spirit.armor.adaptive.Adaptive;
import dev.anye.mc.srsl.enchant.spirit.armor.natural.Natural;
import dev.anye.mc.srsl.enchant.spirit.armor.redlotus.RedLotus;
import dev.anye.mc.srsl.enchant.spirit.armor.stance.Stance;
import dev.anye.mc.srsl.enchant.spirit.armor.warlord.Warlord;
import dev.anye.mc.srsl.enchant.spirit.sword.angel.Angel;
import dev.anye.mc.srsl.enchant.spirit.sword.cross.Cross;
import dev.anye.mc.srsl.enchant.spirit.sword.deathsickle.DeathSickle;
import dev.anye.mc.srsl.enchant.spirit.sword.ganjiang.GanJiang;
import dev.anye.mc.srsl.enchant.spirit.sword.judgment.Judgment;
import dev.anye.mc.srsl.enchant.spirit.sword.mahogany.Mahogany;
import dev.anye.mc.srsl.enchant.spirit.sword.moye.MoYe;
import dev.anye.mc.srsl.enchant.spirit.sword.zenstick.ZenStick;
import dev.anye.mc.srsl.enchant.zero.bow.rain_of_arrows.RainOfArrows;
import dev.anye.mc.srsl.enchant.zero.bow.tori_no_uta.ToriNoUta;
import dev.anye.mc.srsl.enchant.zero.item.alone.Alone;
import dev.anye.mc.srsl.enchant.zero.item.breakdefense.BreakDefense;
import dev.anye.mc.srsl.enchant.zero.item.bright.Bright;
import dev.anye.mc.srsl.enchant.zero.item.confusion.Confusion;
import dev.anye.mc.srsl.enchant.zero.item.deathproclamation.DeathProclamation;
import dev.anye.mc.srsl.enchant.zero.item.fetters.Fetters;
import dev.anye.mc.srsl.enchant.zero.item.indestructible.Indestructible;
import dev.anye.mc.srsl.enchant.zero.item.purify.Purify;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import dev.anye.mc.srsl.enchant.zero.item.unbreakable.Unbreakable;

import java.util.function.Function;
import java.util.function.Supplier;


public class EnchantReg {
    public static final Identifier KEY =  Identifier.fromNamespaceAndPath(SRSL.MOD_ID, "enchant");
    public static final ResourceKey<Registry<Enchant>> REGISTRY_KEY = ResourceKey.createRegistryKey(KEY);
    public static final Registry<Enchant> REGISTRY = new RegistryBuilder<>(REGISTRY_KEY)
            .sync(true)
            //.defaultKey(ResourceLocation.fromNamespaceAndPath("yourmodid", "empty"))
            // Effectively limits the max count. Generally discouraged, but may make sense in settings such as networking.
            .maxId(256)
            // Build the registry.
            .create();
    public static final DeferredRegister<Enchant> ENCHANT = DeferredRegister.create(REGISTRY, SRSL.MOD_ID);
    //public static final Supplier<Registry<Enchant>> REGISTRY = ENCHANT.getRegistry();

    //public static final RegistryObject<EnchantmentCore> BASE = ENCHANT.register("base", BaseSkill::new);

    public static DeferredHolder<Enchant, Enchant> NEKO_BLADE = reg("neko_blade", NekoBlade::new);
    public static DeferredHolder<Enchant, Enchant> NEKO_GOD = reg("neko_god", NekoGod::new);
    public static DeferredHolder<Enchant, Enchant> NEKO_MEOW = reg("neko_meow", NekoMeow::new);
    public static DeferredHolder<Enchant, Enchant> NEKO_KING = reg("neko_king", NekoKing::new);
    public static DeferredHolder<Enchant, Enchant> NEKO_EMPEROR = reg("neko_emperor", NekoEmperor::new);
    public static DeferredHolder<Enchant, Enchant> NEKO_DAY = reg("neko_day", NekoDay::new);
    public static DeferredHolder<Enchant, Enchant> NEKO_NIGHT = reg("neko_night", NekoNight::new);
    public static DeferredHolder<Enchant, Enchant> NEKO_CHOP = reg("neko_chop", NekoChop::new);
    public static DeferredHolder<Enchant, Enchant> NEKO_LOVE= reg("neko_love", NekoLove::new);
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
    public static DeferredHolder<Enchant, Enchant> TORI_NO_UTA = reg("tori_no_uta", ToriNoUta::new);

    public static DeferredHolder<Enchant, Enchant> ChainHarvesting = reg("chain_harvesting", dev.anye.mc.srsl.enchant.zero.tool.ChainHarvesting::new);

    public static DeferredHolder<Enchant, Enchant> B_DRINK_BLOOD = reg("b_vampirism", Vampirism::new);
    public static DeferredHolder<Enchant, Enchant> B_COAGULATION = reg("b_coagulation", Coagulation::new);

    public static DeferredHolder<Enchant, Enchant> CURSE_DISEASE = reg("curse_disease", CurseDisease::new);
    public static DeferredHolder<Enchant, Enchant> CORRODE = reg("corrode", Corrode::new);


    public static DeferredHolder<Enchant, Enchant> DUALITY = reg("duality", Duality::new);
    public static DeferredHolder<Enchant, Enchant> THE_WORLD = reg("the_world", TheWorldEnchant::new);
    //public static DeferredHolder<Enchant, Enchant> MYRIAD_PHENOMENA = reg("myriad_phenomena", MyriadPhenomena::new);





    public static DeferredHolder<Enchant, Enchant> reg(String name , Function<String , Enchant> function){
        if (EnchantsConfig.INSTANCE.isEnable(name)) {
            return ENCHANT.register(name, () -> function.apply(name));
        }
        return null;
    }
    public static <T extends Enchant> DeferredHolder<Enchant, T> reg(String name, Supplier<T> sup) {
        if (EnchantsConfig.INSTANCE.isEnable(name)) {
            return ENCHANT.register(name, sup);
        }else {
            return null;
        }
    }


    public static void register(IEventBus eventBus){
        ENCHANT.register(eventBus);
    }
    public static Enchant getEnchant(String resourceLocation){
        return getEnchant(Identifier.tryParse(resourceLocation));
    }
    public static Enchant getEnchant(Identifier resourceLocation){
        return REGISTRY.get(resourceLocation).get().value();
    }
    public static Identifier getResourceLocation(Enchant enchant){
        return REGISTRY.getKey(enchant);
    }
}

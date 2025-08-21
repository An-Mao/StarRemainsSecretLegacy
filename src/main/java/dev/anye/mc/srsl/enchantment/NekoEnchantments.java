package dev.anye.mc.srsl.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.RegistryObject;

public class NekoEnchantments extends EnchantmentRegister{

    //armor
    public static Enchantment na_blessing = getEnchantment(NEKO_BLESSING);
    public static Enchantment na_life = getEnchantment(NEKO_LIFE);
    public static Enchantment na_mirror = getEnchantment(NEKO_MIRROR);
    public static Enchantment na_ninja = getEnchantment(NEKO_NINJA);
    public static Enchantment na_soul = getEnchantment(NEKO_SOUL);

    //item
    public static Enchantment ni_blade = getEnchantment(NEKO_BLADE);
    public static Enchantment ni_chop = getEnchantment(NEKO_CHOP);
    public static Enchantment ni_day = getEnchantment(NEKO_DAY);
    public static Enchantment ni_emperor = getEnchantment(NEKO_EMPEROR);
    public static Enchantment ni_god = getEnchantment(NEKO_GOD);
    public static Enchantment ni_king = getEnchantment(NEKO_KING);
    public static Enchantment ni_love = getEnchantment(NEKO_LOVE);
    public static Enchantment ni_meow = getEnchantment(NEKO_MEOW);
    public static Enchantment ni_night = getEnchantment(NEKO_NIGHT);

    //armor
    public static Enchantment oa_adaptive = getEnchantment(E_ADAPTIVE);
    public static Enchantment oa_natural = getEnchantment(E_NATURAL);
    public static Enchantment oa_red_lotus = getEnchantment(E_REDLOTUS);
    public static Enchantment oa_stance = getEnchantment(E_STANCE);
    public static Enchantment oa_warlord = getEnchantment(E_WARLORD);

    //item
    public static Enchantment oi_angel = getEnchantment(E_ANGEL);
    public static Enchantment oi_cross = getEnchantment(E_CROSS);
    public static Enchantment oi_death_sickle = getEnchantment(E_DEATH_SICKLE);
    public static Enchantment oi_gan_jiang = getEnchantment(E_GAN_JIANG);
    public static Enchantment oi_judgment = getEnchantment(E_JUDGMENT);
    public static Enchantment oi_mahogany = getEnchantment(E_MAHOGANY);
    public static Enchantment oi_mo_ye = getEnchantment(E_MO_YE);
    public static Enchantment oi_zen_stick = getEnchantment(E_Zen_Stick);


    //item
    public static Enchantment zi_confusion = getEnchantment(Z_CONFUSION);
    public static Enchantment zi_purify = getEnchantment(Z_PURIFY);
    public static Enchantment zi_break_defense = getEnchantment(Z_BREAK_DEFENSE);
    public static Enchantment zi_bright = getEnchantment(Z_BRIGHT);
    public static Enchantment zi_alone = getEnchantment(Z_ALONE);
    public static Enchantment zi_fetters = getEnchantment(Z_FETTERS);
    public static Enchantment zi_indestructible = getEnchantment(Z_INDESTRUCTIBLE);
    public static Enchantment zi_unbreakable = getEnchantment(Z_UNBREAKABLE);

    public static Enchantment zi_dp = getEnchantment(Z_DP);

    public static Enchantment rainOfArrows = getEnchantment(RAIN_OF_ARROWS);

    public static Enchantment toriNoUta = getEnchantment(TORI_NO_UTA);
    public static Enchantment chainHarvesting = getEnchantment(ChainHarvesting);




    public static Enchantment duality = getEnchantment(DUALITY);
    public static Enchantment the_world = getEnchantment(THE_WORLD);
    public static Enchantment myriad_phenomena = getEnchantment(MYRIAD_PHENOMENA);

    public static Enchantment getEnchantment(RegistryObject<Enchantment> registryObject){
        if (registryObject != null){
            return registryObject.get();
        }
        return null;
    }
}

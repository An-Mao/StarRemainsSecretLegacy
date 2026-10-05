package dev.anye.mc.srsl.config.enchants;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.cdt._SuffixCDT;
import dev.anye.core.exception._IOException;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.srsl.config.ConfigCore;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EnchantIO extends _JsonConfig<EnchantConfigData> {
	private static final Map<String, EnchantConfigData> ENCHANT = new HashMap<>();


	public EnchantIO(String filePath) {
		super(_File.getFilePath(ConfigCore.CONFIG_DIR_ENCHANT, filePath), (EnchantConfigData) null, new TypeToken<>() {
		});
	}

	public static void Load() {
		ENCHANT.clear();
		_File.getFiles(ConfigCore.CONFIG_DIR_ENCHANT, _SuffixCDT.JSON_SUFFIX).forEach(path -> {
			new EnchantIO(path.getFileName().toString()).read(enchantConfigData -> ENCHANT.put(_File.getFileNameWithoutExtension(path.getFileName().toString()), enchantConfigData));
		});
	}

	public static @Nullable EnchantConfigData get(String key){
		return ENCHANT.get(key);
	}
	public static void IfPresent(String key,Consumer<EnchantConfigData> consumer){
		if (ENCHANT.get(key) instanceof EnchantConfigData enchantConfigData){
			consumer.accept(enchantConfigData);
		}
	}


	public static void OutDefaultConfig(){
		EnchantReg.ENCHANT.foreach(enchant -> {
			String filePath = _File.getFilePath(ConfigCore.CONFIG_DIR_ENCHANT,enchant.getRegistryName().getPath() + _SuffixCDT.JSON_SUFFIX);
			File file = new File(filePath);
			if (!file.exists()) {
				try {
					try (OutputStreamWriter output = _File.startWriterWithUtf8(filePath)) {
						GSON.toJson(enchant.defaultData(), new TypeToken<>() {}.getType(),output);
					}
				} catch (IOException e) {
					throw new _IOException(e);
				}
			}
		});
		/*
		_Pack.writeFiles(
				"assets/srsl/config/enchant/",
				EnchantIO.FILE_PATH, _SuffixCDT.JSON_SUFFIX,
				"neko_blade",
				"neko_god",
				"neko_meow",
				"neko_king",
				"neko_emperor",
				"neko_day",
				"neko_night",
				"neko_chop",
				"neko_love",
				"e_angel",
				"e_cross",
				"e_death_sickle",
				"e_gan_jiang",
				"e_judgment",
				"e_mahogany",
				"e_mo_ye",
				"e_zen_stick",
				"neko_ninja",
				"neko_soul",
				"neko_blessing",
				"neko_mirror",
				"neko_life",
				"e_adaptive",
				"e_natural",
				"e_red_lotus",
				"e_stance",
				"e_warlord",
				"z_confusion",
				"zi_purify",
				"zi_break_defense",
				"zi_bright",
				"zi_alone",
				"zi_dp",
				"zi_fetters",
				"zi_indestructible",
				"zi_unbreakable",
				"b_vampirism",
				"b_coagulation",
				"curse_disease",
				"corrode",
				"duality",
				"the_world",
				"myriad_phenomena",
				"rain_of_arrows",
				"tori_no_uta",
				"chain_harvesting"
		);

		 */
	}
}

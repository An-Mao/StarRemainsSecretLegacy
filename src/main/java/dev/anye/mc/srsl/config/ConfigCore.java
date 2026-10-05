package dev.anye.mc.srsl.config;


import dev.anye.core.system._File;
import dev.anye.mc.srsl.config.enchants.EnchantIO;

public class ConfigCore {
	public static final String CONFIG_DIR = _File.getFileFullPathWithRun("config", "SRSL");
	public static final String CONFIG_DIR_ENCHANT = _File.getFilePath(CONFIG_DIR, "enchant");

	public static boolean outDefaultConfig = true;

	static {
		_File.checkAndCreateDir(CONFIG_DIR);
		_File.checkAndCreateDir(CONFIG_DIR_ENCHANT);
	}

	public static void init() {
		Config.INSTANCE.read(data -> outDefaultConfig = data.outputDefaultConfig);
	}

	public static void load() {
		Config.INSTANCE.update(configData -> {
			if (configData.outputDefaultConfig()) {
				configData.setOutputDefaultConfig(false);
			}
		});
		Config.INSTANCE.save();
		EnchantIO.Load();
	}

	private ConfigCore() {
	}

}

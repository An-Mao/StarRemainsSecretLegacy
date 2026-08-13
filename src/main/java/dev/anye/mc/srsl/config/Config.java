package dev.anye.mc.srsl.config;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;

public class Config extends _JsonConfig<Config.Data> {
	public static final String CONFIG_DIR = _File.getFilePath(ConfigCore.CONFIG_DIR, "config.json");
	public static final Config INSTANCE = new Config();

	public Config() {
		super(CONFIG_DIR, Data.DEFAULT, new TypeToken<>() {});
	}

	public static class Data {
		public static final Data DEFAULT = new Data(true);

		boolean outputDefaultConfig;

		public Data(boolean outputDefaultConfig) {
			this.outputDefaultConfig = outputDefaultConfig;
		}

		public void setOutputDefaultConfig(boolean outputDefaultConfig) {
			this.outputDefaultConfig = outputDefaultConfig;
		}

		public boolean outputDefaultConfig() {
			return outputDefaultConfig;
		}
	}
}

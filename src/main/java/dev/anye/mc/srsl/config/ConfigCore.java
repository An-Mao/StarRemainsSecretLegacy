package dev.anye.mc.srsl.config;

import dev.anye.core.system._File;

public class ConfigCore {
    public static final String ConfigDir = _File.getFileFullPathWithRun("config/NekoEnchantment/");
    static {
        _File.checkAndCreateDir(ConfigDir);
    }
    public static void init(){
    }
}

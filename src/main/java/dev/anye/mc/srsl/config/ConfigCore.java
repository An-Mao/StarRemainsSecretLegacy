package dev.anye.mc.srsl.config;


import dev.anye.core.system._File;

public class ConfigCore {
    public static final String ConfigDir = _File.getFileFullPathWithRun("config/NekoEnchant/");
    static {
        init();
    }
    public static void init(){
        _File.checkAndCreateDir(ConfigDir);
    }
}

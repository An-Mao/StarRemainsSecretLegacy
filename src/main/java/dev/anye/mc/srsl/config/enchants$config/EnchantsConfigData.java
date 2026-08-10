package dev.anye.mc.srsl.config.enchants$config;


import com.google.gson.JsonObject;

public class EnchantsConfigData {
    private boolean enable;
    private JsonObject parameters;

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setParameters(JsonObject parameters) {
        this.parameters = parameters;
    }

    public JsonObject getParameters() {
        return parameters;
    }
}

package dev.anye.mc.srsl.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonCore {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void check(String sourceJson,String targetJsonFilePath) {
        try {
            JsonObject sourceJsonObj = JsonParser.parseString(sourceJson).getAsJsonObject();
            JsonObject targetJsonObj = readJsonFromFile(targetJsonFilePath);
            mergeJsonObjects(sourceJsonObj, targetJsonObj);
            writeJsonToFile(targetJsonObj, targetJsonFilePath);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static JsonObject readJsonFromFile(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    private static void writeJsonToFile(JsonObject jsonObj, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonObj.toString());
        }
    }
    private static void mergeJsonObjects(JsonObject sourceObj, JsonObject targetObj) {
        sourceObj.entrySet().forEach(entry -> {
            String key = entry.getKey();
            JsonElement valueSource = entry.getValue();
            if (targetObj.has(key)) {
                // If the targetObj already contains the key, we need to merge the values
                JsonElement valueTarget = targetObj.get(key);
                if (valueSource.isJsonObject() && valueTarget.isJsonObject()) {
                    // Recursively merge the nested JSON objects
                    mergeJsonObjects(valueSource.getAsJsonObject(), valueTarget.getAsJsonObject());
                }
                // If the value is not a JsonObject, do nothing (retain the existing value in targetObj)
            } else {
                // If the key is not present in targetObj, add it
                targetObj.add(key, valueSource);
            }
        });
    }
}

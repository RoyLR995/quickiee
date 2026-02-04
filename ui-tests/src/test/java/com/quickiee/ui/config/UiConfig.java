package com.quickiee.ui.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class UiConfig {
    private static final Map<String, String> ENV = loadDotEnv();

    private UiConfig() {}

    public static String baseUrl() {
        String env = get("UI_BASE_URL");
        if (env == null || env.isBlank()) {
            return "https://quickiee-eta.vercel.app";
        }
        return env.endsWith("/") ? env.substring(0, env.length() - 1) : env;
    }

    public static String email() {
        String env = get("E2E_EMAIL");
        if (env == null || env.isBlank()) {
            throw new IllegalStateException("E2E_EMAIL is required");
        }
        return env;
    }

    public static String password() {
        String env = get("E2E_PASSWORD");
        if (env == null || env.isBlank()) {
            throw new IllegalStateException("E2E_PASSWORD is required");
        }
        return env;
    }

    public static boolean headless() {
        String env = get("HEADLESS");
        return env == null || env.isBlank() || Boolean.parseBoolean(env);
    }

    private static String get(String key) {
        String value = System.getenv(key);
        if (value != null && !value.isBlank()) {
            return value;
        }
        return ENV.get(key);
    }

    private static Map<String, String> loadDotEnv() {
        Map<String, String> map = new HashMap<>();
        Path envPath = Path.of(System.getProperty("user.dir"), ".env");
        if (!Files.exists(envPath)) {
            return map;
        }
        try (BufferedReader reader = Files.newBufferedReader(envPath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }
                int idx = trimmed.indexOf('=');
                if (idx <= 0) {
                    continue;
                }
                String key = trimmed.substring(0, idx).trim();
                String value = trimmed.substring(idx + 1).trim();
                map.put(key, value);
            }
        } catch (IOException ignored) {
        }
        return map;
    }
}

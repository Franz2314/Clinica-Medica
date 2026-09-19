package config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import utilidades.ProjectPaths;

public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private final Properties properties = new Properties();

    private AppConfig() {
        cargarArchivo();
    }

    public static AppConfig get() {
        return INSTANCE;
    }

    public PersistenceMode getPersistenceMode() {
        return PersistenceMode.from(properties.getProperty("app.persistence.mode", "MEMORY"));
    }

    public String getPostgresUrl() {
        return require("postgres.url");
    }

    public String getPostgresUser() {
        return require("postgres.user");
    }

    public String getPostgresPassword() {
        return require("postgres.password");
    }

    public Path getConfigFile() {
        return ProjectPaths.resolveProject("config", "app.properties");
    }

    private void cargarArchivo() {
        Path configFile = getConfigFile();
        if (!Files.exists(configFile)) {
            return;
        }
        try (InputStream input = Files.newInputStream(configFile)) {
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer la configuracion en " + configFile, e);
        }
    }

    private String require(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Falta la propiedad '" + key + "' en " + getConfigFile() + ".");
        }
        return value.trim();
    }
}

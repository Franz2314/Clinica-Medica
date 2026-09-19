package config;

public enum PersistenceMode {
    MEMORY,
    POSTGRES;

    public static PersistenceMode from(String raw) {
        if (raw == null || raw.isBlank()) {
            return MEMORY;
        }
        return switch (raw.trim().toUpperCase()) {
            case "POSTGRES" -> POSTGRES;
            case "MEMORY", "MEMORIA" -> MEMORY;
            default -> throw new IllegalArgumentException("Modo de persistencia no valido: " + raw);
        };
    }
}

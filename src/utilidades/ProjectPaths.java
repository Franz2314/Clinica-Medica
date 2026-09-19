package utilidades;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ProjectPaths {
    private static final Path PROJECT_ROOT = detectarRaizProyecto();

    private ProjectPaths() {
    }

    public static Path projectRoot() {
        return PROJECT_ROOT;
    }

    public static Path resolveProject(String first, String... more) {
        return PROJECT_ROOT.resolve(Paths.get(first, more)).normalize();
    }

    private static Path detectarRaizProyecto() {
        Path actual = Paths.get("").toAbsolutePath().normalize();
        while (actual != null) {
            if (Files.exists(actual.resolve("nbproject")) && Files.exists(actual.resolve("src"))) {
                return actual;
            }
            actual = actual.getParent();
        }
        return Paths.get("").toAbsolutePath().normalize();
    }
}

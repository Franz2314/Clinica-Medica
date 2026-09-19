package vista;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import utilidades.ProjectPaths;

public final class fuentesinterfaz {
    private static final String RECURSOS = "recursos";
    private static final Font LOCAL_BAHNSCHRIFT = loadFont("BAHNSCHRIFT.TTF");
    private static final Font LOCAL_SEGOE_REGULAR = loadFont("segoeuithis.ttf");
    private static final Font LOCAL_SEGOE_BOLD = loadFont("segoeuithibd.ttf");
    private static final Font LOCAL_SEGOE_LIGHT = loadFont("segoeuithisz.ttf");
    private static final boolean HAS_BAHNSCHRIFT = LOCAL_BAHNSCHRIFT != null || hasFont("Bahnschrift");
    private static final boolean HAS_SEGOE = LOCAL_SEGOE_REGULAR != null || hasFont("Segoe UI This") || hasFont("Segoe UI");

    private fuentesinterfaz() {
    }

    public static Font title(float size) {
        if (LOCAL_BAHNSCHRIFT != null) {
            return LOCAL_BAHNSCHRIFT.deriveFont(Font.PLAIN, size);
        }
        if (HAS_BAHNSCHRIFT) {
            return new Font("Bahnschrift SemiBold", Font.PLAIN, Math.round(size));
        }
        if (LOCAL_SEGOE_BOLD != null) {
            return LOCAL_SEGOE_BOLD.deriveFont(Font.PLAIN, size);
        }
        if (hasFont("Segoe UI This")) {
            return new Font("Segoe UI This", Font.BOLD, Math.round(size));
        }
        return new Font("Segoe UI", Font.BOLD, Math.round(size));
    }

    public static Font heading(float size) {
        if (LOCAL_SEGOE_BOLD != null) {
            return LOCAL_SEGOE_BOLD.deriveFont(Font.PLAIN, size);
        }
        if (hasFont("Segoe UI This")) {
            return new Font("Segoe UI This", Font.BOLD, Math.round(size));
        }
        if (HAS_SEGOE) {
            return new Font("Segoe UI Semibold", Font.PLAIN, Math.round(size));
        }
        return new Font("Segoe UI", Font.BOLD, Math.round(size));
    }

    public static Font body(float size) {
        if (LOCAL_SEGOE_REGULAR != null) {
            return LOCAL_SEGOE_REGULAR.deriveFont(Font.PLAIN, size);
        }
        if (hasFont("Segoe UI This")) {
            return new Font("Segoe UI This", Font.PLAIN, Math.round(size));
        }
        if (HAS_SEGOE) {
            return new Font("Segoe UI", Font.PLAIN, Math.round(size));
        }
        return new Font("SansSerif", Font.PLAIN, Math.round(size));
    }

    public static Font strong(float size) {
        if (LOCAL_SEGOE_BOLD != null) {
            return LOCAL_SEGOE_BOLD.deriveFont(Font.PLAIN, size);
        }
        if (hasFont("Segoe UI This")) {
            return new Font("Segoe UI This", Font.BOLD, Math.round(size));
        }
        if (HAS_SEGOE) {
            return new Font("Segoe UI", Font.BOLD, Math.round(size));
        }
        return new Font("SansSerif", Font.BOLD, Math.round(size));
    }

    public static Font bannerTitle(float size) {
        Font base;
        if (hasFont("Segoe UI Semibold")) {
            base = new Font("Segoe UI Semibold", Font.PLAIN, Math.round(size));
        } else if (LOCAL_SEGOE_BOLD != null) {
            base = LOCAL_SEGOE_BOLD.deriveFont(Font.PLAIN, size);
        } else if (hasFont("Bahnschrift SemiBold")) {
            base = new Font("Bahnschrift SemiBold", Font.PLAIN, Math.round(size));
        } else if (hasFont("Bahnschrift Light")) {
            base = new Font("Bahnschrift Light", Font.PLAIN, Math.round(size));
        } else if (LOCAL_SEGOE_LIGHT != null) {
            base = LOCAL_SEGOE_LIGHT.deriveFont(Font.PLAIN, size);
        } else if (LOCAL_BAHNSCHRIFT != null) {
            base = LOCAL_BAHNSCHRIFT.deriveFont(Font.PLAIN, size);
        } else {
            base = title(size);
        }
        return withTracking(base, 0.06f);
    }

    public static Font bannerSubtitle(float size) {
        Font base;
        if (hasFont("Segoe UI Semibold")) {
            base = new Font("Segoe UI Semibold", Font.PLAIN, Math.round(size));
        } else if (LOCAL_SEGOE_BOLD != null) {
            base = LOCAL_SEGOE_BOLD.deriveFont(Font.PLAIN, size);
        } else if (LOCAL_SEGOE_REGULAR != null) {
            base = LOCAL_SEGOE_REGULAR.deriveFont(Font.PLAIN, size);
        } else {
            base = body(size);
        }
        return withTracking(base, 0.02f);
    }

    private static Font loadFont(String fileName) {
        File file = ProjectPaths.resolveProject(RECURSOS, fileName).toFile();
        if (!file.exists()) {
            return null;
        }
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, file);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font;
        } catch (FontFormatException | IOException e) {
            return null;
        }
    }

    private static boolean hasFont(String name) {
        String[] fuentes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String fuente : fuentes) {
            if (fuente.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private static Font withTracking(Font base, float tracking) {
        Map<TextAttribute, Object> attributes = new HashMap<>(base.getAttributes());
        attributes.put(TextAttribute.TRACKING, tracking);
        return base.deriveFont(attributes);
    }
}

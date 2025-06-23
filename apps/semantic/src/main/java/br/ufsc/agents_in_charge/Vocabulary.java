package br.ufsc.agents_in_charge;

/**
 * Vocabulário e URIs base para a aplicação
 */
public class Vocabulary {
    private static String getEnv(String name, String defaultValue) {
        String value = System.getenv(name);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        return value;
    }

    // Base URI para os recursos
    public static final String BASE_URI = getEnv("KG_BASE_URI", "http://localhost/kg/");
    public static final String PROPERTY_URI = BASE_URI + "property/";
    public static final String SERVER_URI = getEnv("WOT_SERVER_URI", "http://localhost/api/separating/");
}
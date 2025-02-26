package managers;

public class FontManager {

    private static FontManager instance = null;

    // prevent instantiation
    protected FontManager() {
    }


    public static FontManager getInstance() {
        if (instance == null)
            instance = new FontManager();
        return instance;
    }
}

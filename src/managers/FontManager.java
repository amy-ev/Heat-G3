package managers;

import java.util.Arrays;

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

    // test --------
    public void setFont(Object... args){
            System.out.println(Arrays.toString(args));

    }


    public static void main(String[] args) {
        FontManager fm = FontManager.getInstance();
        fm.setFont(1, "2", "3", "hello", 20.5);
    }
    // test end --------
}

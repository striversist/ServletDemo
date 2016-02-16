package demo.utils;

import java.io.Closeable;
import java.io.IOException;

public class Utils {

    public static void closeSilently(Closeable closeable) {
        if (closeable == null)
            return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

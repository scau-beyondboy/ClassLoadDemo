package com.scau.beyondboy.classloaddemo;

import java.io.Closeable;
import java.io.IOException;
public class StreamUtil {

    public static final void close(Closeable obj) {
        if (obj != null) {
            try {
                obj.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
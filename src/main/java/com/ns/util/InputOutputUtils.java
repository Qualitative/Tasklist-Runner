package com.ns.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class InputOutputUtils {

    private static final String UTF8 = "UTF-8";

    public static BufferedReader createUnicodeReader(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, UTF8);
        BufferedReader in = new BufferedReader(isr);
        return in;
    }

    public static BufferedWriter createUnicodeWriter(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osr = new OutputStreamWriter(fos, UTF8);
        BufferedWriter out = new BufferedWriter(osr);
        return out;
    }
}

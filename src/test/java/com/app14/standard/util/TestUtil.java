package com.app14.standard.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.util.Scanner;

public class TestUtil {
    private static final PrintStream ORIGINAL_OUT = System.out;

    public static Scanner genScanner(String input) {
        return new Scanner(input);
    }

    public static ByteArrayOutputStream setOutToByteArray() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));
        return output;
    }

    public static void clearSetOutToByteArray(ByteArrayOutputStream output) {
        System.setOut(ORIGINAL_OUT);
        try {
            output.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
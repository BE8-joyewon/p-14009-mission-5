package com.app14;

import com.app14.standard.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AppTest {
    private static final Path baseDir = Paths.get("db/wiseSaying");

    public static void clear() {
        try {
            if (Files.exists(baseDir)) {
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(baseDir)) {
                    for (Path path : stream) {
                        if (!path.getFileName().toString().equals("lastId.txt")) {
                            Files.delete(path);
                        }
                    }
                }
            } else {
                Files.createDirectories(baseDir);
            }

            Path lastIdPath = baseDir.resolve("lastId.txt");
            Files.writeString(lastIdPath, "0");

        } catch (IOException e) {
            throw new RuntimeException("테스트 초기화 실패: ", e);
        }
    }

    public static String run(String input) {
        Scanner scanner = TestUtil.genScanner(input + "종료\n");
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();

        App.run(scanner);

        String out = byteArrayOutputStream.toString().trim();
        TestUtil.clearSetOutToByteArray(byteArrayOutputStream);
        return out;
    }

    @Test
    @DisplayName("앱 실행")
    void t1() {
        final String out = AppTest.run("");

        assertThat(out)
                .contains("== 명언 앱 ==")
                .contains("명령)");
    }
}
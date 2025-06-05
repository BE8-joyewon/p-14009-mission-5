package com.app14.standard.util;

import com.app14.domain.wiseSaying.entity.WiseSaying;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonUtils {
    public static String toJsonString(WiseSaying ws, int indentLevel) {
        String indent = "  ".repeat(indentLevel);
        String innerIndent = "  ".repeat(indentLevel + 1);

        return indent + "{\n" +
                innerIndent + "\"id\": " + ws.getId() + ",\n" +
                innerIndent + "\"content\": \"" + ws.getContent() + "\",\n" +
                innerIndent + "\"author\": \"" + ws.getAuthor() + "\"\n" +
                indent + "}";
    }

    public static WiseSaying fromJson(Path path) {
        try {
            String json = Files.readString(path);
            int id = Integer.parseInt(getJsonValue(json, "id"));
            String content = getJsonValue(json, "content");
            String author = getJsonValue(json, "author");
            return new WiseSaying(id, content, author);
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
        return null;
    }

    private static String getJsonValue(String json, String key) {
        int idx = json.indexOf("\"" + key + "\"");
        if (idx == -1) return "";

        int start = json.indexOf(':', idx) + 1;
        while (json.charAt(start) == ' ' || json.charAt(start) == '\"') start++;

        int end = start;
        boolean isString = json.charAt(start - 1) == '\"';
        while (isString ? json.charAt(end) != '\"' : Character.isDigit(json.charAt(end))) end++;

        return json.substring(start, end).trim();
    }

    public static String toJsonArrayString(List<WiseSaying> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (WiseSaying ws : list) {
            sb.append(toJsonString(ws, 1));
            sb.append(",\n");
        }
        sb.delete(sb.length() - 2, sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }
}

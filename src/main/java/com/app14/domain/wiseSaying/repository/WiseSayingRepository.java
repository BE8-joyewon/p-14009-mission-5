package com.app14.domain.wiseSaying.repository;

import com.app14.domain.wiseSaying.entity.WiseSaying;
import com.app14.standard.util.JsonUtils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class WiseSayingRepository {
    private final Path baseDir = Paths.get("db/wiseSaying");
    private final Path lastIdPath = baseDir.resolve("lastId.txt");

    public int getLastId() {
        try {
            return Integer.parseInt(Files.readString(lastIdPath).trim());
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
        return 0;
    }

    public void saveLastId(int id) {
        try {
            Files.writeString(lastIdPath, String.valueOf(id));
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void save(WiseSaying ws) {
        Path path = baseDir.resolve(ws.getId() + ".json");
        try {
            Files.writeString(path, JsonUtils.toJsonString(ws, 0));
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void delete(WiseSaying ws) {
        Path path = baseDir.resolve(ws.getId() + ".json");
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public Optional<WiseSaying> findById(int id) {
        Path path = baseDir.resolve(id + ".json");
        if(!Files.exists(path)) return Optional.empty();

        return Optional.ofNullable(JsonUtils.fromJson(path));
    }

    public List<WiseSaying> findAll() {
        List<WiseSaying> list = new ArrayList<>();
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(baseDir, "*.json");
            for (Path path : stream) {
                WiseSaying ws = JsonUtils.fromJson(path);
                if (ws != null) list.add(ws);
            }
            list.sort(Comparator.comparing(WiseSaying::getId));
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
        return list;
    }

    public void saveAll() {
        Path path = baseDir.resolve("data.json");
        List<WiseSaying> list = findAll();
        String json = JsonUtils.toJsonArrayString(list);
        try {
            Files.writeString(path, json);
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public List<WiseSaying> findByContent(String keyword) {
        return findAll().stream()
                .filter(ws -> ws.getContent().contains(keyword))
                .toList();
    }

    public List<WiseSaying> findByAuthor(String keyword) {
        return findAll().stream()
                .filter(ws -> ws.getAuthor().contains(keyword))
                .toList();
    }
}

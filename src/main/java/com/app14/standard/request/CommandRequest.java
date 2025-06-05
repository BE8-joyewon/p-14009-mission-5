package com.app14.standard.request;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandRequest {
    private final String commandName;
    private final Map<String, String> paramMap;

    public CommandRequest(String input) {
        String[] parts = input.split("\\?", 2);
        commandName = parts[0].trim();

        String queryString = parts.length > 1 ? parts[1] : "";
        paramMap = Arrays.stream(queryString.split("&"))
                .map(s -> s.split("=", 2))
                .filter(arr -> arr.length == 2 && !arr[0].isBlank())
                .collect(Collectors.toMap(
                        arr -> arr[0].trim(),
                        arr -> arr[1].trim()
                ));
    }

    public String getCommandName() {
        return commandName;
    }

    public String getOption(String name, String defaultValue) {
        return paramMap.getOrDefault(name, defaultValue);
    }

    public int getOptionAsInt(String name, int defaultValue) {
        String value = getOption(name, "");
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}

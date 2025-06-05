package com.app14.domain.wiseSaying.controller;

import com.app14.domain.wiseSaying.entity.WiseSaying;
import com.app14.domain.wiseSaying.service.WiseSayingService;
import com.app14.standard.request.CommandRequest;
import com.app14.standard.util.Page;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WiseSayingController {
    private final WiseSayingService service;
    private final Scanner scanner;

    public WiseSayingController(WiseSayingService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void register() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        int id = service.register(content, author).getId();
        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    public void list(CommandRequest rq) {
        String keywordType = rq.getOption("keywordType", "all");
        String keyword = rq.getOption("keyword", "");
        int pageSize = rq.getOptionAsInt("pageSize", 5);
        int pageNo = rq.getOptionAsInt("page", 1);

        Page<WiseSaying> page = service.searchPage(keywordType, keyword, pageSize, pageNo);

        if (!keywordType.equals("all")) {
            System.out.println("----------------------");
            System.out.println("검색타입 : " + keywordType);
            System.out.println("검색어 : " + keyword);
            System.out.println("----------------------");
        }
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (WiseSaying ws : page.getPageItems()) {
            System.out.println(ws.getId() + " / " + ws.getAuthor() + " / " + ws.getContent());
        }
        System.out.println("----------------------");
        System.out.printf("페이지 : ");
        System.out.println(
                IntStream.rangeClosed(1, page.getTotalPage())
                        .mapToObj(i -> i == page.getCurrentPage() ? "[" + i + "]" : String.valueOf(i))
                        .collect(Collectors.joining(" / "))
        );
    }

    public void delete(CommandRequest rq) {
        int id = rq.getOptionAsInt("id", -1);
        if (service.deleteById(id)) System.out.println(id + "번 명언이 삭제되었습니다.");
        else System.out.println(id + "번 명언은 존재하지 않습니다.");
    }

    public void modify(CommandRequest rq) {
        int id = rq.getOptionAsInt("id", -1);
        Optional<WiseSaying> optional = service.findbyId(id);
        if (optional.isEmpty()) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }

        WiseSaying ws = optional.get();
        System.out.println("명언(기존) : " + ws.getContent());
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.println("작가(기존) : " + ws.getAuthor());
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        service.modify(id, content, author);
    }

    public void build() {
        service.buildJsonFile();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}

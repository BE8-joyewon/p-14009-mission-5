package com.app14;

import com.app14.domain.wiseSaying.controller.WiseSayingController;
import com.app14.domain.wiseSaying.repository.WiseSayingRepository;
import com.app14.domain.wiseSaying.service.WiseSayingService;
import com.app14.standard.request.CommandRequest;

import java.util.Scanner;

public class App {
    static Scanner scanner = null;

    public static void run(Scanner scanner) {
        App.scanner = scanner;
        main(null);
    }

    public static void main(String[] args) {
        WiseSayingRepository repository = new WiseSayingRepository();
        WiseSayingService service = new WiseSayingService(repository);
        WiseSayingController controller = new WiseSayingController(service, scanner);

        if (scanner == null) scanner = new Scanner(System.in);
        service.initSampleIfEmpty();

        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine().trim();
            CommandRequest rq = new CommandRequest(command);

            switch (rq.getCommandName()) {
                case "종료" -> {
                    return;
                }
                case "등록" -> controller.register();
                case "목록" -> controller.list(rq);
                case "삭제" -> controller.delete(rq);
                case "수정" -> controller.modify(rq);
                case "빌드" -> controller.build();
            }
        }
    }
}

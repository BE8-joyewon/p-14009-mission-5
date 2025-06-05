package com.app14.domain.wiseSaying.controller;

import com.app14.AppTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {

    final String commandRegister1 = """
                등록
                현재를 사랑하라.
                작자미상
            """;

    final String commandRegister2 = """
                등록
                과거에 집착하지 마라.
                작자미상
            """;

    @BeforeEach
    void beforeEach() {
        AppTest.clear();
    }

    /*
    @Test
    @DisplayName("등록")
    void t3() {
        final String out = AppTest.run(
                commandRegister1
        );

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }


    @Test
    @DisplayName("등록: 명언번호 증가")
    void t4() {
        final String out = AppTest.run(
                commandRegister1 + commandRegister2
        );

        assertThat(out)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    void t5() {
        final String out = AppTest.run(
                commandRegister1 + commandRegister2 + "목록\n"
        );

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제")
    void t6() {
        final String out = AppTest.run(
                commandRegister1 + "삭제?id=1\n"
        );

        assertThat(out)
                .contains("1번 명언이 삭제되었습니다.");
    }

    @Test
    @DisplayName("삭제: 존재하지 않는 명언 삭제")
    void t7() {
        final String out = AppTest.run(
                commandRegister1 + "삭제?id=1\n" + "삭제?id=1\n"
        );

        assertThat(out)
                .contains("1번 명언이 삭제되었습니다.")
                .contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("수정")
    void t8() {
        final String out = AppTest.run(
                commandRegister1 + commandRegister2 + """
                        수정?id=2
                        현재와 자신을 사랑하라.
                        홍길동
                        목록
                        """
        );

        assertThat(out)
                .contains("명언(기존) : 과거에 집착하지 마라.")
                .contains("작가(기존) : 작자미상")
                .contains("2 / 홍길동 / 현재와 자신을 사랑하라.")
                .doesNotContain("2 / 작자미상 / 과거에 집착하지 마라.");
    }

    @Test
    @DisplayName("빌드")
    void t9() throws IOException {
        final String out = com.back.AppTest.run(
                commandRegister1 + commandRegister2 + "빌드\n"
        );

        assertThat(out)
                .contains("data.json 파일의 내용이 갱신되었습니다.");

        Path path = Paths.get("db/wiseSaying/data.json");
        assertThat(Files.exists(path)).isTrue();
        assertThat(Files.readString(path).trim()).isEqualTo("""
                [
                  {
                    "id": 1,
                    "content": "현재를 사랑하라.",
                    "author": "작자미상"
                  },
                  {
                    "id": 2,
                    "content": "과거에 집착하지 마라.",
                    "author": "작자미상"
                  }
                ]
                """.trim());
    }

    @Test
    @DisplayName("목록: 내용으로 검색")
    void t10() {
        final String out = AppTest.run(
                commandRegister1 + commandRegister2 + """
                        목록?keywordType=content&keyword=과거
                        """
        );

        assertThat(out)
                .contains("검색타입 : content")
                .contains("검색어 : 과거")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("목록: 작가로 검색")
    void t11() {
        final String out = AppTest.run(
                commandRegister1 + commandRegister2 + """
                        목록?keywordType=author&keyword=작자
                        """
        );

        assertThat(out)
                .contains("검색타입 : author")
                .contains("검색어 : 작자")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }
    */

    @Test
    @DisplayName("목록: 페이징")
    void t12() {
        final String out = AppTest.run("목록\n");

        assertThat(out)
                .contains("10 / 작자미상 10 / 명언 10")
                .contains("9 / 작자미상 9 / 명언 9")
                .contains("8 / 작자미상 8 / 명언 8")
                .contains("7 / 작자미상 7 / 명언 7")
                .contains("6 / 작자미상 6 / 명언 6")
                .doesNotContain("5 / 작자미상 5 / 명언 5")
                .doesNotContain("4 / 작자미상 4 / 명언 4")
                .doesNotContain("3 / 작자미상 3 / 명언 3")
                .doesNotContain("2 / 작자미상 2 / 명언 2")
                .doesNotContain("1 / 작자미상 1 / 명언 1")
                .contains("페이지 : [1] / 2");
    }

    @Test
    @DisplayName("목록: 페이징2")
    void t13() {
        final String out = AppTest.run("목록?page=2\n");

        assertThat(out)
                .doesNotContain("10 / 작자미상 10 / 명언 10")
                .doesNotContain("9 / 작자미상 9 / 명언 9")
                .doesNotContain("8 / 작자미상 8 / 명언 8")
                .doesNotContain("7 / 작자미상 7 / 명언 7")
                .doesNotContain("6 / 작자미상 6 / 명언 6")
                .contains("5 / 작자미상 5 / 명언 5")
                .contains("4 / 작자미상 4 / 명언 4")
                .contains("3 / 작자미상 3 / 명언 3")
                .contains("2 / 작자미상 2 / 명언 2")
                .contains("1 / 작자미상 1 / 명언 1")
                .contains("페이지 : 1 / [2]");
    }

    @Test
    @DisplayName("목록: 페이징3")
    void t14() {
        final String out = AppTest.run("목록?pageSize=10\n");

        assertThat(out)
                .contains("10 / 작자미상 10 / 명언 10")
                .contains("9 / 작자미상 9 / 명언 9")
                .contains("8 / 작자미상 8 / 명언 8")
                .contains("7 / 작자미상 7 / 명언 7")
                .contains("6 / 작자미상 6 / 명언 6")
                .contains("5 / 작자미상 5 / 명언 5")
                .contains("4 / 작자미상 4 / 명언 4")
                .contains("3 / 작자미상 3 / 명언 3")
                .contains("2 / 작자미상 2 / 명언 2")
                .contains("1 / 작자미상 1 / 명언 1")
                .contains("페이지 : [1]");
    }
}
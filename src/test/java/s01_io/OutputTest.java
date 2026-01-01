package s01_io;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * 출력 최적화 학습 테스트
 *
 * PS에서 출력이 많을 때 System.out.println()을 반복하면 시간 초과
 *
 * 해결책:
 * 1. StringBuilder에 모아서 한 번에 출력 (가장 간단)
 * 2. BufferedWriter 사용 (전통적 방법)
 *
 * 권장: StringBuilder 방식이 간단하고 실수가 적음
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OutputTest {

    // StringBuilder 기본
    @Nested
    class StringBuilder_기본 {

        @Test
        void append로_문자열을_이어붙인다() {
            StringBuilder sb = new StringBuilder();

            sb.append("Hello");
            sb.append(" ");
            sb.append("World");

            assertThat(sb.toString()).isEqualTo("Hello World");
        }

        @Test
        void append는_체이닝이_가능하다() {
            StringBuilder sb = new StringBuilder();

            sb.append("a").append("b").append("c");

            assertThat(sb.toString()).isEqualTo("abc");
        }

        @Test
        void 다양한_타입을_append할_수_있다() {
            StringBuilder sb = new StringBuilder();

            sb.append("number: ")
                    .append(42)
                    .append(", double: ")
                    .append(3.14)
                    .append(", char: ")
                    .append('X')
                    .append(", boolean: ")
                    .append(true);

            assertThat(sb.toString()).isEqualTo("number: 42, double: 3.14, char: X, boolean: true");
        }

        @Test
        void 개행_추가_방법들() {
            StringBuilder sb = new StringBuilder();

            sb.append("line1\n"); // 직접 \n 추가
            sb.append("line2").append("\n"); // append로 \n 추가
            sb.append("line3");

            assertThat(sb.toString()).isEqualTo("line1\nline2\nline3");
        }
    }

    // StringBuilder PS 패턴
    @Nested
    class StringBuilder_PS_패턴 {

        @Test
        void 여러_결과를_한_번에_출력() {
            int[] results = {1, 2, 3, 4, 5};
            StringBuilder sb = new StringBuilder();

            for (int r : results) {
                sb.append(r).append("\n");
            }

            assertThat(sb.toString()).isEqualTo("1\n2\n3\n4\n5\n");
        }

        @Test
        void 공백으로_구분된_출력() {
            int[] arr = {1, 2, 3, 4, 5};
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < arr.length; i++) {
                if (i > 0) sb.append(" ");
                sb.append(arr[i]);
            }

            assertThat(sb.toString()).isEqualTo("1 2 3 4 5");
        }

        @Test
        void _2차원_배열_출력() {
            int[][] board = {{1, 2, 3}, {4, 5, 6}};
            StringBuilder sb = new StringBuilder();

            for (int[] row : board) {
                for (int i = 0; i < row.length; i++) {
                    if (i > 0) sb.append(" ");
                    sb.append(row[i]);
                }
                sb.append("\n");
            }

            assertThat(sb.toString()).isEqualTo("1 2 3\n4 5 6\n");
        }

        @Test
        void Yes_No_출력_패턴() {
            StringBuilder sb = new StringBuilder();

            boolean[] answers = {true, false, true};
            for (boolean ans : answers) {
                sb.append(ans ? "Yes" : "No").append("\n");
            }

            assertThat(sb.toString()).isEqualTo("Yes\nNo\nYes\n");
        }

        @Test
        void 마지막에_한_번만_toString_호출() {
            StringBuilder sb = new StringBuilder();

            for (int i = 1; i <= 3; i++) {
                sb.append(i).append("\n");
            }

            // 마지막에 한 번만 출력
            String output = sb.toString();
            // System.out.print(output);  // 실제 PS에서

            assertThat(output).isEqualTo("1\n2\n3\n");
        }
    }

    // StringBuilder 수정 메서드
    @Nested
    class StringBuilder_수정 {

        @Test
        void setCharAt으로_특정_위치_문자_변경() {
            StringBuilder sb = new StringBuilder("Hello");

            sb.setCharAt(0, 'J');

            assertThat(sb.toString()).isEqualTo("Jello");
        }

        @Test
        void deleteCharAt으로_특정_위치_문자_삭제() {
            StringBuilder sb = new StringBuilder("Hello");

            sb.deleteCharAt(1);

            assertThat(sb.toString()).isEqualTo("Hllo");
        }

        @Test
        void delete로_범위_삭제() {
            StringBuilder sb = new StringBuilder("Hello World");

            sb.delete(5, 11); // " World" 삭제

            assertThat(sb.toString()).isEqualTo("Hello");
        }

        @Test
        void insert로_중간에_삽입() {
            StringBuilder sb = new StringBuilder("Hello!");

            sb.insert(5, " World");

            assertThat(sb.toString()).isEqualTo("Hello World!");
        }

        @Test
        void reverse로_뒤집기() {
            StringBuilder sb = new StringBuilder("Hello");

            sb.reverse();

            assertThat(sb.toString()).isEqualTo("olleH");
        }

        @Test
        void reverse는_원본을_변경한다() {
            StringBuilder sb = new StringBuilder("ABC");

            StringBuilder result = sb.reverse();

            assertThat(result).isSameAs(sb); // 같은 객체
            assertThat(sb.toString()).isEqualTo("CBA");
        }

        @Test
        void setLength로_길이_조절() {
            StringBuilder sb = new StringBuilder("Hello World");

            sb.setLength(5); // 뒤쪽 잘라내기

            assertThat(sb.toString()).isEqualTo("Hello");
        }

        @Test
        void setLength_0으로_초기화() {
            StringBuilder sb = new StringBuilder("Hello");

            sb.setLength(0); // 비우기 (new StringBuilder()보다 효율적)

            assertThat(sb.toString()).isEmpty();
            assertThat(sb.length()).isZero();
        }
    }

    // BufferedWriter 기본
    @Nested
    class BufferedWriter_기본 {

        @Test
        void write로_문자열을_쓴다() throws IOException {
            StringWriter sw = new StringWriter();
            BufferedWriter bw = new BufferedWriter(sw);

            bw.write("Hello World");
            bw.flush();

            assertThat(sw.toString()).isEqualTo("Hello World");
        }

        @Test
        void newLine으로_개행을_추가한다() throws IOException {
            StringWriter sw = new StringWriter();
            BufferedWriter bw = new BufferedWriter(sw);

            bw.write("line1");
            bw.newLine();
            bw.write("line2");
            bw.flush();

            assertThat(sw.toString()).contains("line1");
            assertThat(sw.toString()).contains("line2");
            assertThat(sw.toString()).isEqualTo("line1\nline2");
        }

        @Test
        void 개행은_직접_write해도_된다() throws IOException {
            StringWriter sw = new StringWriter();
            BufferedWriter bw = new BufferedWriter(sw);

            bw.write("line1\n");
            bw.write("line2");
            bw.flush();

            assertThat(sw.toString()).isEqualTo("line1\nline2");
        }
    }
}

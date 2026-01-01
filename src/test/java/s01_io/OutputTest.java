package s01_io;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    // BufferedWriter 핵심 함정
    @Nested
    class BufferedWriter_함정 {

        @Test
        void write에_int를_넣으면_문자로_해석된다_함정() throws IOException {
            StringWriter sw = new StringWriter();
            BufferedWriter bw = new BufferedWriter(sw);

            // 숫자 65를 출력을 예상했지만
            bw.write(65); // 65는 'A'의 ASCII 코드
            bw.flush();

            assertThat(sw.toString()).isEqualTo("A"); // "65"가 아님
        }

        @Test
        void 숫자를_출력하려면_문자열로_변환해야_한다() throws IOException {
            StringWriter sw = new StringWriter();
            BufferedWriter bw = new BufferedWriter(sw);

            int number = 65;
            bw.write(String.valueOf(number)); // 문자열로 변환
            // 또는: bw.write(Integer.toString(number));
            // 또는: bw.write(number + "");
            bw.flush();

            assertThat(sw.toString()).isEqualTo("65");
        }

        @Test
        void flush를_안_하면_출력이_안_될_수_있다() throws IOException {
            StringWriter sw = new StringWriter();
            BufferedWriter bw = new BufferedWriter(sw);

            bw.write("Hello");
            // flush() 없이는 버퍼에만 있고 출력 안 될 수 있음
            // (StringWriter는 바로 반영되지만, 실제 파일/콘솔은 다름)

            bw.flush(); // 버퍼 비우기

            assertThat(sw.toString()).isEqualTo("Hello");
        }

        @Test
        void close는_자동으로_flush한다() throws IOException {
            StringWriter sw = new StringWriter();
            BufferedWriter bw = new BufferedWriter(sw);

            bw.write("Hello");
            bw.close(); // close 시 자동 flush

            assertThat(sw.toString()).isEqualTo("Hello");
        }
    }

    // PrintWriter (편의성 vs 성능)
    @Nested
    class PrintWriter_사용 {

        @Test
        void println으로_편하게_출력() {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

            pw.println("Hello");
            pw.println(123); // 숫자도 바로 출력 가능
            pw.println(3.14);
            pw.flush();

            assertThat(sw.toString()).contains("Hello");
            assertThat(sw.toString()).contains("123");
            assertThat(sw.toString()).contains("3.14");
        }

        @Test
        void printf로_포맷팅() {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

            pw.printf("Name: %s, Age: %d%n", "Kim", 25);
            pw.flush();

            assertThat(sw.toString()).contains("Name: Kim, Age: 25");
        }

        @Test
        void print는_개행_없이_출력() {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

            pw.print("Hello");
            pw.print(" ");
            pw.print("World");
            pw.flush();

            assertThat(sw.toString()).isEqualTo("Hello World");
        }
    }

    // try-with-resources 패턴
    @Nested
    class 리소스_관리 {

        @Test
        void try_with_resources로_자동_close() throws IOException {
            StringWriter sw = new StringWriter();

            try (BufferedWriter bw = new BufferedWriter(sw)) {
                bw.write("Hello");
            } // 자동으로 close() 호출됨 (flush 포함)

            assertThat(sw.toString()).isEqualTo("Hello");
        }

        @Test
        void PS에서는_보통_close_생략해도_됨() {
            // PS 환경에서는 프로그램 종료 시 자동 정리됨
            // 하지만 명시적으로 하는 것이 좋은 습관

            // StringBuilder는 I/O 리소스가 아니라 메모리 객체라서 close 필요없음
            StringBuilder sb = new StringBuilder();
            sb.append("test");

            // System.out.print(sb)는 close 필요 없음
            assertThat(sb.toString()).isEqualTo("test");
        }
    }

    // 성능 비교 개념
    @Nested
    class 성능_개념 {

        @Test
        void System_out_println_반복은_느리다() {
            // 개념 설명용 테스트
            // System.out.println()은 매 호출마다 동기화 + 버퍼 플러시 발생
            // N이 10만 이상이면 시간 초과 위험

            int n = 100;
            StringBuilder sb = new StringBuilder();

            // 느린 방법 (개념만):
            // for (int i = 0; i < n; i++) System.out.println(i);

            /*
             * 왜 느린가?
             * println() 호출마다:
             * 1. synchronized 락 획득 (스레드 안전 보장)
             * 2. 버퍼에 쓰기
             * 3. 버퍼 flush (실제 출력)
             * 4. 락 해제
             *
             * → 10만 번 반복하면 이 과정이 10만 번
             */

            // 빠른 방법:
            for (int i = 0; i < n; i++) {
                sb.append(i).append("\n");
            }

            assertThat(sb.toString().split("\n")).hasSize(n);
        }

        @Test
        void 문자열_더하기_반복도_느리다() {
            // String은 불변이라 + 연산마다 새 객체 생성
            // O(N^2) 시간복잡도

            int n = 100;

            // 느린 방법 (개념만):
            // String result = "";
            // for (int i = 0; i < n; i++) result += i + "\n"; // 매번 새 String 객체 생성

            // 빠른 방법: StringBuilder
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(i).append("\n"); // 같은 객체에 추가
            }

            assertThat(sb.length()).isGreaterThan(0);
        }
    }
}

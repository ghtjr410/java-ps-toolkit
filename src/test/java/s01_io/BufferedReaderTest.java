package s01_io;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * BufferedReader 학습 테스트
 *
 * PS에서 Scanner 대신 BufferedReader를 쓰는 이유:
 * - Scanner: 정규식 기반 파싱 → 느림
 * - BufferedReader: 단순 문자열 읽기 → 빠름 (약 5~10배)
 *
 * 핵심 패턴:
 * BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 * String line = br.readLine();
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BufferedReaderTest {

    // ========================================
    // 테스트용 헬퍼 메서드: 문자열을 입력처럼 읽기
    // ========================================
    private BufferedReader createReader(String input) {
        return new BufferedReader(new StringReader(input));
    }

    @Nested
    class readLine_기본_동작 {

        @Test
        void 한_줄을_읽으면_개행문자는_제외된다() throws IOException {
            BufferedReader br = createReader("hello\n");

            String line = br.readLine();

            assertThat(line).isEqualTo("hello");
            assertThat(line).doesNotContain("\n");
        }

        @Test
        void 여러_줄을_순차적으로_읽는다() throws IOException {
            BufferedReader br = createReader("first\nsecond\nthird\n");

            assertThat(br.readLine()).isEqualTo("first");
            assertThat(br.readLine()).isEqualTo("second");
            assertThat(br.readLine()).isEqualTo("third");
        }

        @Test
        void 빈_줄도_빈_문자열로_읽힌다() throws IOException {
            BufferedReader br = createReader("first\n\nthird\n");

            assertThat(br.readLine()).isEqualTo("first");
            assertThat(br.readLine()).isEqualTo(""); // 빈 줄!
            assertThat(br.readLine()).isEqualTo("third");
        }

        @Test
        void 공백만_있는_줄은_공백_문자열로_읽힌다() throws IOException {
            BufferedReader br = createReader("   \n");

            String line = br.readLine();

            assertThat(line).isEqualTo("   ");
            assertThat(line).isNotEmpty();
            assertThat(line.trim()).isEmpty();
        }
    }

    // EOF (End Of File) 처리
    @Nested
    class EOF_처리 {

        @Test
        void 더_이상_읽을_줄이_없으면_null을_반환한다() throws IOException {
            BufferedReader br = createReader("only one line");

            assertThat(br.readLine()).isEqualTo("only one line");
            assertThat(br.readLine()).isNull(); // EOF!
            assertThat(br.readLine()).isNull(); // 계속 null
        }

        @Test
        void EOF까지_모든_줄_읽기_패턴() throws IOException {
            BufferedReader br = createReader("1\n2\n3");

            StringBuilder result = new StringBuilder();
            String line;

            // PS에서 입력 개수가 안 주어질 때 사용하는 패턴
            while ((line = br.readLine()) != null) {
                result.append(line).append(",");
            }

            assertThat(result.toString()).isEqualTo("1,2,3,");
        }

        @Test
        void 빈_입력은_즉시_null을_반환한다() throws IOException {
            BufferedReader br = createReader("");

            assertThat(br.readLine()).isNull();
        }
    }

    // 개행 문자 종류 (Windows vs Unix)
    @Nested
    class 개행_문자_처리 {

        @Test
        void Unix_개행문자_LF를_처리한다() throws IOException {
            BufferedReader br = createReader("a\nb"); // \n = LF

            assertThat(br.readLine()).isEqualTo("a");
            assertThat(br.readLine()).isEqualTo("b");
        }

        @Test
        void Windows_개행문자_CRLF를_처리한다() throws IOException {
            BufferedReader br = createReader("a\r\nb"); // \r\n = CRLF

            assertThat(br.readLine()).isEqualTo("a");
            assertThat(br.readLine()).isEqualTo("b");
        }

        @Test
        void 구형_Mac_개행문자_CR만_있어도_처리한다() throws IOException {
            BufferedReader br = createReader("a\rb"); // \r = CR

            assertThat(br.readLine()).isEqualTo("a");
            assertThat(br.readLine()).isEqualTo("b");
        }
    }

    @Nested
    class PS_실전_패턴 {

        @Test
        void 첫_줄에_개수_그_다음부터_데이터_패턴() throws IOException {
            String input = "3\n10\n20\n30";
            BufferedReader br = createReader(input);

            int n = Integer.parseInt(br.readLine());
            int[] arr = new int[n];

            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(br.readLine());
            }

            assertThat(n).isEqualTo(3);
            assertThat(arr).containsExactly(10, 20, 30);
        }

        @Test
        void 한_줄에_여러_숫자는_StringTokenizer와_함께() throws IOException {
            String input = "5\n1 2 3 4 5";
            BufferedReader br = createReader(input);

            int n = Integer.parseInt(br.readLine());
            String[] parts = br.readLine().split(" ");

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(parts[i]);
            }

            assertThat(arr).containsExactly(1, 2, 3, 4, 5);
        }

        @Test
        void 테스트케이스_개수만큼_반복_패턴() throws IOException {
            String input = "2\ncase1\ncase2";
            BufferedReader br = createReader(input);

            int T = Integer.parseInt(br.readLine());
            String[] results = new String[T];

            for (int t = 0; t < T; t++) {
                results[t] = br.readLine();
            }

            assertThat(results).containsExactly("case1", "case2");
        }
    }
}

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
}

package s01_io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * StringTokenizer 학습 테스트
 *
 * PS에서 가장 많이 쓰이고, 가장 많이 실수하는 클래스
 *
 * 핵심 차이점 (반드시 기억):
 * - StringTokenizer: 연속 구분자 → 무시 (빈 토큰 없음)
 * - split(): 연속 구분자 → 빈 문자열 생성
 *
 * 사용 패턴:
 * StringTokenizer st = new StringTokenizer(br.readLine());
 * int a = Integer.parseInt(st.nextToken());
 * int b = Integer.parseInt(st.nextToken());
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StringTokenizerTest {

    @Nested
    class 기본_사용법 {

        @Test
        void nextToken으로_토큰을_하나씩_가져온다() {
            StringTokenizer st = new StringTokenizer("apple banana cherry");

            assertThat(st.nextToken()).isEqualTo("apple");
            assertThat(st.nextToken()).isEqualTo("banana");
            assertThat(st.nextToken()).isEqualTo("cherry");
        }

        @Test
        void hasMoreTokens로_남은_토큰_여부를_확인한다() {
            StringTokenizer st = new StringTokenizer("a b");

            assertThat(st.hasMoreTokens()).isTrue();
            st.nextToken();
            assertThat(st.hasMoreTokens()).isTrue();
            st.nextToken();
            assertThat(st.hasMoreTokens()).isFalse();
        }

        @Test
        void 토큰이_없는데_nextToken_호출하면_예외() {
            StringTokenizer st = new StringTokenizer("only");
            st.nextToken();

            assertThatThrownBy(st::nextToken).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        void countTokens는_남은_토큰_개수를_반환한다() {
            StringTokenizer st = new StringTokenizer("a b c d e");

            assertThat(st.countTokens()).isEqualTo(5);

            st.nextToken();
            st.nextToken();

            assertThat(st.countTokens()).isEqualTo(3); // 남은 개수
        }

        @Test
        void countTokens_호출해도_토큰_위치는_변하지_않는다() {
            StringTokenizer st = new StringTokenizer("first second");

            int count = st.countTokens(); // 위치 안 변함
            String first = st.nextToken();

            assertThat(count).isEqualTo(2);
            assertThat(first).isEqualTo("first"); // 정상적으로 첫 번째
        }
    }

    // 기본 구분자 (공백, 탭, 개행, 캐리지리턴, 폼피드)
    @Nested
    class 기본_구분자 {

        @Test
        void 기본_구분자는_공백이다() {
            StringTokenizer st = new StringTokenizer("a b c");

            assertThat(st.countTokens()).isEqualTo(3);
        }

        @Test
        void 탭도_기본_구분자이다() {
            StringTokenizer st = new StringTokenizer("a\tb\tc");

            assertThat(st.countTokens()).isEqualTo(3);
            assertThat(st.nextToken()).isEqualTo("a");
            assertThat(st.nextToken()).isEqualTo("b");
            assertThat(st.nextToken()).isEqualTo("c");
        }

        @Test
        void 개행도_기본_구분자이다() {
            StringTokenizer st = new StringTokenizer("a\nb\nc");

            assertThat(st.countTokens()).isEqualTo(3);
        }

        @Test
        void 공백_탭_개행_혼합도_처리한다() {
            StringTokenizer st = new StringTokenizer("a \t\n b");

            assertThat(st.countTokens()).isEqualTo(2);
            assertThat(st.nextToken()).isEqualTo("a");
            assertThat(st.nextToken()).isEqualTo("b");
        }
    }

    // ⭐⭐⭐ 연속 구분자 처리 - 핵심
    @Nested
    class 연속_구분자_처리_핵심 {
        // StringTokenizer는 빈 토큰을 절대 만들지 않는다.

        @Test
        void StringTokenizer는_연속_구분자를_무시한다() {
            // 공백이 여러 개여도 빈 토큰이 생기지 않는다
            StringTokenizer st = new StringTokenizer("a  b   c");

            assertThat(st.countTokens()).isEqualTo(3); // 빈 토큰 없음
            assertThat(st.nextToken()).isEqualTo("a");
            assertThat(st.nextToken()).isEqualTo("b");
            assertThat(st.nextToken()).isEqualTo("c");
        }

        @Test
        void 앞뒤_공백도_무시한다() {
            StringTokenizer st = new StringTokenizer("  hello  ");

            assertThat(st.countTokens()).isEqualTo(1);
            assertThat(st.nextToken()).isEqualTo("hello");
        }

        @Test
        void 공백만_있으면_토큰이_없다() {
            StringTokenizer st = new StringTokenizer("   ");

            assertThat(st.countTokens()).isEqualTo(0);
            assertThat(st.hasMoreTokens()).isFalse();
        }

        @Test
        void 빈_문자열이면_토큰이_없다() {
            StringTokenizer st = new StringTokenizer("");

            assertThat(st.countTokens()).isEqualTo(0);
            assertThat(st.hasMoreTokens()).isFalse();
        }
    }
}

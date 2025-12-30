package s01_io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * 숫자 파싱 학습 테스트
 *
 * PS에서 입력을 숫자로 바꿀 때 발생하는 실수들:
 * - 공백이 포함된 문자열 파싱
 * - int 범위 초과
 * - parseInt vs valueOf 혼동
 *
 * 핵심:
 * - parseInt는 공백을 허용하지 않는다
 * - 큰 수는 Long.parseLong()을 써야 한다
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ParsingTest {

    // Integer.parseInt() 기본
    @Nested
    class parseInt_기본 {

        @Test
        void 문자열을_int로_변환한다() {
            String s = "123";

            int result = Integer.parseInt(s);

            assertThat(result).isEqualTo(123);
        }

        @Test
        void 음수도_파싱한다() {
            String s = "-456";

            int result = Integer.parseInt(s);

            assertThat(result).isEqualTo(-456);
        }

        @Test
        void 양수_부호도_허용한다() {
            String s = "+789";

            int result = Integer.parseInt(s);

            assertThat(result).isEqualTo(789);
        }

        @Test
        void 앞에_0이_있어도_10진수로_파싱한다() {
            // 주의: JavaScript와 다르게 8진수로 해석하지 않음
            String s = "0123";

            int result = Integer.parseInt(s);

            assertThat(result).isEqualTo(123); // 8진수 83이 아님
        }
    }

    // 공백 처리 - 핵심 함정
    @Nested
    class 공백_처리_핵심 {

        @Test
        void 앞에_공백이_있으면_예외() {
            String s = " 123";

            assertThatThrownBy(() -> Integer.parseInt(s)).isInstanceOf(NumberFormatException.class);
        }

        @Test
        void 뒤에_공백이_있으면_예외() {
            String s = "123 ";

            assertThatThrownBy(() -> Integer.parseInt(s)).isInstanceOf(NumberFormatException.class);
        }

        @Test
        void 중간에_공백이_있으면_예외() {
            String s = "1 23";

            assertThatThrownBy(() -> Integer.parseInt(s)).isInstanceOf(NumberFormatException.class);
        }

        @Test
        void trim으로_앞뒤_공백을_제거하고_파싱() {
            String s = "  123  ";

            int result = Integer.parseInt(s.trim());

            assertThat(result).isEqualTo(123);
        }

        @Test
        void StringTokenizer_nextToken은_공백이_없으므로_안전() {
            // StringTokenizer의 장점: nextToken()은 구분자 제외한 순수 토큰 반환
            java.util.StringTokenizer st = new java.util.StringTokenizer("  123  456  ");

            int a = Integer.parseInt(st.nextToken()); // trim 불필요!
            int b = Integer.parseInt(st.nextToken());

            assertThat(a).isEqualTo(123);
            assertThat(b).isEqualTo(456);
        }
    }

    // Long.parseLong()
    @Nested
    class parseLong {

        @Test
        void int_범위를_초과하는_수는_long으로_파싱() {
            String s = "3000000000"; // 30억, int 범위(약 21억) 초과

            assertThatThrownBy(() -> Integer.parseInt(s)).isInstanceOf(NumberFormatException.class);

            long result = Long.parseLong(s);
            assertThat(result).isEqualTo(3_000_000_000L);
        }

        @Test
        void int_최대값은_약_21억() {
            assertThat(Integer.MAX_VALUE).isEqualTo(2_147_483_647);

            String maxInt = "2147483647";
            assertThat(Integer.parseInt(maxInt)).isEqualTo(Integer.MAX_VALUE);

            String overflow = "2147483648"; // MAX_VALUE + 1
            assertThatThrownBy(() -> Integer.parseInt(overflow)).isInstanceOf(NumberFormatException.class);
        }

        @Test
        void long_최대값은_약_922경() {
            assertThat(Long.MAX_VALUE).isEqualTo(9_223_372_036_854_775_807L);
        }

        @Test
        void PS에서_N이_10의12승_이상이면_long_필수() {
            // 흔한 PS 제약: 1 ≤ N ≤ 10^12
            String trillion = "1000000000000";

            long result = Long.parseLong(trillion);

            assertThat(result).isEqualTo(1_000_000_000_000L);
        }
    }

    // 진법 변환 (radix)
    @Nested
    class 진법_변환 {

        @Test
        void _2진수를_10진수로_변환() {
            String binary = "1010";

            int result = Integer.parseInt(binary, 2);

            assertThat(result).isEqualTo(10);
        }

        @Test
        void _8진수를_10진수로_변환() {
            String octal = "17";

            int result = Integer.parseInt(octal, 8);

            assertThat(result).isEqualTo(15); // 1*8 + 7 = 15
        }

        @Test
        void _16진수를_10진수로_변환() {
            String hex = "FF";

            int result = Integer.parseInt(hex, 16);

            assertThat(result).isEqualTo(255);
        }

        @Test
        void _16진수는_대소문자_무관() {
            assertThat(Integer.parseInt("ff", 16)).isEqualTo(255);
            assertThat(Integer.parseInt("FF", 16)).isEqualTo(255);
            assertThat(Integer.parseInt("Ff", 16)).isEqualTo(255);
        }

        @Test
        void 유효하지_않은_진법_문자면_예외() {
            String binary = "102"; // 2진수에 2가 있음

            assertThatThrownBy(() -> Integer.parseInt(binary, 2)).isInstanceOf(NumberFormatException.class);
        }
    }

    // parseInt vs valueOf
    @Nested
    class parseInt_vs_valueOf {

        @Test
        void parseInt는_기본형_int를_반환() {
            int result = Integer.parseInt("123");

            assertThat(result).isEqualTo(123);
        }

        @Test
        void valueOf는_Integer_객체를_반환() {
            Integer result = Integer.valueOf("123");

            assertThat(result).isEqualTo(123);
        }

        @Test
        void valueOf는_내부적으로_parseInt를_호출_후_박싱() {
            /**
             *     - 내부구현
             *
             *     public static Integer valueOf(String s) throws NumberFormatException {
             *         return Integer.valueOf(parseInt(s, 10));
             *     }
             */

            // 성능상 parseInt가 약간 유리 (박싱 비용 없음)
            // PS에서는 보통 parseInt 사용
            int primitive = Integer.parseInt("123");
            Integer boxed = Integer.valueOf("123");

            assertThat(primitive).isEqualTo(boxed);
        }

        @Test
        void valueOf의_캐싱_범위는_마이너스128부터_127() {
            // -128 ~ 127 범위는 캐싱됨
            Integer a = Integer.valueOf("100");
            Integer b = Integer.valueOf("100");
            assertThat(a == b).isTrue(); // 같은 객체

            Integer c = Integer.valueOf("200");
            Integer d = Integer.valueOf("200");
            assertThat(c == d).isFalse(); // 다른 객체
        }
    }
}

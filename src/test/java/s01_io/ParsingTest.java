package s01_io;

import static org.assertj.core.api.Assertions.assertThat;

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
}

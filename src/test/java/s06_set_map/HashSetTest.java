package s06_set_map;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * HashSet 학습 테스트
 *
 * 핵심 특성:
 * - 중복 허용 안 함
 * - 순서 보장 안 함 (순회 순서 예측 불가!)
 * - O(1) 추가/삭제/조회 (해시 기반)
 *
 * PS 활용:
 * - 중복 제거
 * - 존재 여부 빠른 확인
 * - 방문 체크 (visited)
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class HashSetTest {

    // 기본 사용법
    @Nested
    class 기본_사용법 {

        @Test
        void add로_요소를_추가한다() {
            Set<Integer> set = new HashSet<>();

            set.add(1);
            set.add(2);
            set.add(3);

            assertThat(set).hasSize(3);
            assertThat(set).contains(1, 2, 3);
        }

        @Test
        void 중복_요소는_추가되지_않는다() {
            Set<Integer> set = new HashSet<>();

            set.add(1);
            set.add(1);
            set.add(1);

            assertThat(set).hasSize(1);
        }

        @Test
        void contains로_존재_여부를_확인한다() {
            Set<Integer> set = new HashSet<>();
            set.add(10);
            set.add(20);

            assertThat(set.contains(10)).isTrue();
            assertThat(set.contains(30)).isFalse();
        }

        @Test
        void remove로_요소를_삭제한다() {
            Set<Integer> set = new HashSet<>();
            set.add(1);
            set.add(2);

            set.remove(1);

            assertThat(set).containsExactly(2);
        }

        @Test
        void size와_isEmpty() {
            Set<Integer> set = new HashSet<>();

            assertThat(set.isEmpty()).isTrue();
            assertThat(set.size()).isZero();

            set.add(1);

            assertThat(set.isEmpty()).isFalse();
            assertThat(set.size()).isEqualTo(1);
        }

        @Test
        void clear로_모든_요소를_삭제한다() {
            Set<Integer> set = new HashSet<>();
            set.add(1);
            set.add(2);
            set.add(3);

            set.clear();

            assertThat(set).isEmpty();
        }
    }
}

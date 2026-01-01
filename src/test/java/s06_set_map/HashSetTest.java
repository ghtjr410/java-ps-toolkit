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

    // add() 반환값 - 핵심
    @Nested
    class add_반환값_핵심 {

        @Test
        void add는_새_요소면_true를_반환한다() {
            Set<Integer> set = new HashSet<>();

            boolean result = set.add(1);

            assertThat(result).isTrue();
        }

        @Test
        void add는_이미_있는_요소면_false를_반환한다() {
            Set<Integer> set = new HashSet<>();
            set.add(1);

            boolean result = set.add(1); // 중복

            assertThat(result).isFalse();
        }

        @Test
        void add_반환값으로_contains_호출을_생략할_수_있다() {
            Set<Integer> set = new HashSet<>();
            int num = 5;

            // 비효율: 두 번 조회
            // if (!set.contains(num)) {
            //     set.add(num);
            // }

            // 효율: 한 번에
            if (set.add(num)) {
                // 새 요소일 때 처리
            }

            assertThat(set).contains(5);
        }

        @Test
        void add_반환값으로_중복_체크_패턴() {
            Set<Integer> set = new HashSet<>();
            int[] arr = {1, 2, 2, 3, 3, 3};

            int duplicateCount = 0;
            for (int num : arr) {
                if (!set.add(num)) {
                    duplicateCount++;
                }
            }

            assertThat(duplicateCount).isEqualTo(3); // 2가 1번, 3이 2번 중복
        }

        @Test
        void 첫_번째_중복_발견_시_바로_리턴_패턴() {
            int[] arr = {1, 2, 3, 2, 4};
            Set<Integer> set = new HashSet<>();

            int firstDuplicate = -1;
            for (int num : arr) {
                if (!set.add(num)) {
                    firstDuplicate = num;
                    break;
                }
            }

            assertThat(firstDuplicate).isEqualTo(2);
        }
    }
}

package s06_set_map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Comparator;
import java.util.TreeSet;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * TreeSet 학습 테스트
 *
 * 핵심 특성:
 * - 자동 정렬 (기본: 오름차순)
 * - O(log n) 추가/삭제/조회 (Red-Black Tree 기반)
 * - 중복 허용 안 함
 *
 * PS 활용 (매우 유용):
 * - 정렬된 상태 유지가 필요할 때
 * - 특정 값 이상/이하/초과/미만 요소 찾기 (floor, ceiling, lower, higher)
 * - 범위 검색 (subSet, headSet, tailSet)
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TreeSetTest {

    // 기본 사용법 & 자동 정렬
    @Nested
    class 기본_사용법_자동_정렬 {

        @Test
        void 삽입_순서와_관계없이_정렬된다() {
            TreeSet<Integer> set = new TreeSet<>();

            set.add(5);
            set.add(1);
            set.add(3);
            set.add(2);
            set.add(4);

            assertThat(set).containsExactly(1, 2, 3, 4, 5); // 자동 정렬
        }

        @Test
        void 기본_정렬은_오름차순() {
            TreeSet<Integer> set = new TreeSet<>();
            set.add(30);
            set.add(10);
            set.add(20);

            assertThat(set.first()).isEqualTo(10);
            assertThat(set.last()).isEqualTo(30);
        }

        @Test
        void 문자열도_사전순_정렬() {
            TreeSet<String> set = new TreeSet<>();
            set.add("banana");
            set.add("apple");
            set.add("cherry");

            assertThat(set).containsExactly("apple", "banana", "cherry");
        }

        @Test
        void 중복은_허용되지_않는다() {
            TreeSet<Integer> set = new TreeSet<>();
            set.add(1);
            set.add(1);
            set.add(1);

            assertThat(set).hasSize(1);
        }
    }

    // Comparator로 정렬 기준 변경
    @Nested
    class 커스텀_정렬 {

        @Test
        void reverseOrder로_내림차순() {
            TreeSet<Integer> set = new TreeSet<>(Comparator.reverseOrder());

            set.add(1);
            set.add(3);
            set.add(2);

            assertThat(set).containsExactly(3, 2, 1);
        }

        @Test
        void 커스텀_Comparator() {
            // 절대값 기준 정렬
            TreeSet<Integer> set = new TreeSet<>(Comparator.comparingInt(Math::abs));

            set.add(-5);
            set.add(3);
            set.add(-1);
            set.add(4);

            assertThat(set).containsExactly(-1, 3, 4, -5);
        }

        @Test
        void Comparable_구현_안된_객체는_예외() {
            TreeSet<Object> set = new TreeSet<>();

            assertThatThrownBy(() -> set.add(new Object())).isInstanceOf(ClassCastException.class);
        }
    }

    // first(), last() - 최솟값, 최댓값
    @Nested
    class first_last_최소_최대 {

        @Test
        void first는_최솟값을_반환() {
            TreeSet<Integer> set = new TreeSet<>();
            set.add(30);
            set.add(10);
            set.add(20);

            assertThat(set.first()).isEqualTo(10);
        }

        @Test
        void last는_최댓값을_반환() {
            TreeSet<Integer> set = new TreeSet<>();
            set.add(30);
            set.add(10);
            set.add(20);

            assertThat(set.last()).isEqualTo(30);
        }

        @Test
        void 비어있으면_NoSuchElementException() {
            TreeSet<Integer> set = new TreeSet<>();

            assertThatThrownBy(set::first).isInstanceOf(java.util.NoSuchElementException.class);
        }

        @Test
        void pollFirst는_최솟값을_꺼내고_삭제() {
            TreeSet<Integer> set = new TreeSet<>();
            set.add(3);
            set.add(1);
            set.add(2);

            Integer min = set.pollFirst();

            assertThat(min).isEqualTo(1);
            assertThat(set).containsExactly(2, 3); // 1이 제거됨
        }

        @Test
        void pollLast는_최댓값을_꺼내고_삭제() {
            TreeSet<Integer> set = new TreeSet<>();
            set.add(3);
            set.add(1);
            set.add(2);

            Integer max = set.pollLast();

            assertThat(max).isEqualTo(3);
            assertThat(set).containsExactly(1, 2); // 3이 제거됨
        }

        @Test
        void poll은_비어있으면_null_반환() {
            TreeSet<Integer> set = new TreeSet<>();

            assertThat(set.pollFirst()).isNull();
            assertThat(set.pollLast()).isNull();
        }
    }
}

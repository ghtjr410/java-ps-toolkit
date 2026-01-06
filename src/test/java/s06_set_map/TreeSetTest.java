package s06_set_map;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * LinkedHashSet 및 Set 구현체 비교 학습 테스트
 *
 * Set 구현체 선택 가이드:
 * - HashSet: 순서 필요 없음, 빠름 (O(1)) → 기본 선택
 * - LinkedHashSet: 삽입 순서 유지, 빠름 (O(1))
 * - TreeSet: 정렬 유지, floor/ceiling 필요 (O(log n))
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TreeSetTest {

    // LinkedHashSet 기본
    @Nested
    class LinkedHashSet_기본 {

        @Test
        void 삽입_순서가_유지된다() {
            Set<Integer> set = new LinkedHashSet<>();

            set.add(3);
            set.add(1);
            set.add(4);
            set.add(1); // 중복, 무시됨
            set.add(5);
            set.add(9);
            set.add(2);

            assertThat(set).containsExactly(3, 1, 4, 5, 9, 2); // 삽입 순서!
        }

        @Test
        void 이미_있는_요소_추가해도_순서_안_바뀜() {
            Set<Integer> set = new LinkedHashSet<>();
            set.add(1);
            set.add(2);
            set.add(3);

            set.add(1); // 이미 있음

            // 1의 위치는 그대로 첫 번째
            assertThat(set).containsExactly(1, 2, 3);
        }

        @Test
        void 중복_제거하면서_순서_유지() {
            List<Integer> listWithDuplicates = List.of(3, 1, 2, 3, 1, 4, 2, 5);

            Set<Integer> unique = new LinkedHashSet<>(listWithDuplicates);

            assertThat(unique).containsExactly(3, 1, 2, 4, 5); // 첫 등장 순서!
        }
    }
}

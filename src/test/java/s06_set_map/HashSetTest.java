package s06_set_map;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
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

    // 순서 보장 안 됨 - 핵심
    @Nested
    class 순서_보장_안됨_핵심 {

        @Test
        void 순회_순서는_삽입_순서와_다를_수_있다() {
            Set<Integer> set = new HashSet<>();
            set.add(3);
            set.add(1);
            set.add(2);

            List<Integer> iterationOrder = new ArrayList<>(set);

            // 순서가 3, 1, 2가 아닐 수 있음
            // HashSet은 해시값 기반으로 저장하므로 순서 예측 불가
            assertThat(iterationOrder).containsExactlyInAnyOrder(1, 2, 3);
            // containsExactly가 아닌 containsExactlyInAnyOrder
        }

        @Test
        void 같은_요소라도_순서가_다를_수_있다는_것을_인지() {
            // 이 테스트는 개념 설명용
            // 실제로 HashSet의 순서는 구현에 따라 다를 수 있음
            Set<Integer> set1 = new HashSet<>();
            Set<Integer> set2 = new HashSet<>();

            // 다른 순서로 삽입
            set1.add(1);
            set1.add(2);

            set2.add(2);
            set2.add(1);

            // 내용은 같음
            assertThat(set1).isEqualTo(set2);

            // 하지만 순회 순서는 보장되지 않음을 인지해야 함
        }

        @Test
        void 정렬_필요하면_List로_변환_후_정렬() {
            Set<Integer> set = new HashSet<>();
            set.add(3);
            set.add(1);
            set.add(2);

            List<Integer> sorted = new ArrayList<>(set);
            Collections.sort(sorted);

            assertThat(sorted).containsExactly(1, 2, 3);
        }

        @Test
        void 정렬_필요하면_처음부터_TreeSet_사용() {
            Set<Integer> set = new TreeSet<>();
            set.add(3);
            set.add(1);
            set.add(2);

            // TreeSet은 자동 정렬
            assertThat(set).containsExactly(1, 2, 3);
        }

        @Test
        void 순서가_필요하면_LinkedHashSet_사용() {
            // 순서 필요 시: LinkedHashSet (삽입 순서 유지)
            // 정렬 필요 시: TreeSet (자동 정렬)
            Set<Integer> hashSet = new HashSet<>();
            hashSet.add(3);
            hashSet.add(1);
            hashSet.add(2);

            // HashSet을 List로 바꾸면 순서가 보장되지 않음을 인지
            // 정렬이 필요하면 별도로 정렬해야 함
            List<Integer> sorted = new ArrayList<>(hashSet);
            sorted.sort(null); // 정렬

            assertThat(sorted).containsExactly(1, 2, 3);
        }
    }

    // remove() 동작
    @Nested
    class remove_동작 {

        @Test
        void remove는_삭제_성공시_true_반환() {
            Set<Integer> set = new HashSet<>();
            set.add(1);

            boolean result = set.remove(1);

            assertThat(result).isTrue();
        }

        @Test
        void remove는_없는_요소면_false_반환() {
            Set<Integer> set = new HashSet<>();
            set.add(1);

            boolean result = set.remove(999);

            assertThat(result).isFalse();
        }

        @Test
        void removeAll로_여러_요소_삭제_변경됨() {
            Set<Integer> set = new HashSet<>(Set.of(1, 2, 3, 4));

            boolean result = set.removeAll(Set.of(2, 4));

            assertThat(result).isTrue(); // 삭제된 요소 있음
            assertThat(set).containsExactlyInAnyOrder(1, 3);
        }

        @Test
        void removeAll로_여러_요소_삭제_변경없음() {
            Set<Integer> set = new HashSet<>(Set.of(1, 2, 3, 4));

            boolean result = set.removeAll(Set.of(99, 100));

            assertThat(result).isFalse(); // 삭제된 요소 없음
            assertThat(set).containsExactlyInAnyOrder(1, 2, 3, 4);
        }

        @Test
        void retainAll로_교집합만_유지_변경됨() {
            Set<Integer> set = new HashSet<>(Set.of(1, 2, 3));

            boolean result = set.retainAll(Set.of(2, 3, 4));

            assertThat(result).isTrue(); // 1이 제거됨
            assertThat(set).containsExactlyInAnyOrder(2, 3);
        }

        @Test
        void retainAll로_교집합만_유지_변경없음() {
            Set<Integer> set = new HashSet<>(Set.of(2, 3));

            boolean result = set.retainAll(Set.of(2, 3, 4));

            assertThat(result).isFalse(); // 변경 없음, 이미 교집합 상태
            assertThat(set).containsExactlyInAnyOrder(2, 3);
        }
    }

    // 집합 연산
    @Nested
    class 집합_연산 {

        @Test
        void addAll로_합집합() {
            Set<Integer> set1 = new HashSet<>(Set.of(1, 2, 3));
            Set<Integer> set2 = new HashSet<>(Set.of(3, 4, 5));

            set1.addAll(set2);

            assertThat(set1).containsExactlyInAnyOrder(1, 2, 3, 4, 5);
        }

        @Test
        void retainAll로_교집합() {
            Set<Integer> set1 = new HashSet<>(Set.of(1, 2, 3));
            Set<Integer> set2 = new HashSet<>(Set.of(2, 3, 4));

            set1.retainAll(set2);

            assertThat(set1).containsExactlyInAnyOrder(2, 3);
        }

        @Test
        void removeAll로_차집합() {
            Set<Integer> set1 = new HashSet<>(Set.of(1, 2, 3));
            Set<Integer> set2 = new HashSet<>(Set.of(2, 3, 4));

            set1.removeAll(set2); // set1 - set2

            assertThat(set1).containsExactlyInAnyOrder(1);
        }

        @Test
        void containsAll로_부분집합_확인() {
            Set<Integer> superset = new HashSet<>(Set.of(1, 2, 3, 4, 5));
            Set<Integer> subset = new HashSet<>(Set.of(2, 3));

            assertThat(superset.containsAll(subset)).isTrue();
            assertThat(subset.containsAll(superset)).isFalse();
        }
    }

    // 순회 방법
    @Nested
    class 순회_방법 {

        @Test
        void for_each_loop() {
            Set<Integer> set = new HashSet<>(Set.of(1, 2, 3));
            List<Integer> result = new ArrayList<>();

            for (Integer num : set) {
                result.add(num);
            }

            assertThat(result).containsExactlyInAnyOrder(1, 2, 3);
        }

        @Test
        void forEach_메서드() {
            Set<Integer> set = new HashSet<>(Set.of(1, 2, 3));
            List<Integer> result = new ArrayList<>();

            set.forEach(result::add);

            assertThat(result).containsExactlyInAnyOrder(1, 2, 3);
        }

        @Test
        void Iterator_사용() {
            Set<Integer> set = new HashSet<>(Set.of(1, 2, 3));
            List<Integer> result = new ArrayList<>();

            Iterator<Integer> iter = set.iterator();
            while (iter.hasNext()) {
                result.add(iter.next());
            }

            assertThat(result).containsExactlyInAnyOrder(1, 2, 3);
        }

        @Test
        void Iterator로_안전하게_삭제() {
            Set<Integer> set = new HashSet<>(Set.of(1, 2, 3, 4, 5));

            Iterator<Integer> iter = set.iterator();
            while (iter.hasNext()) {
                if (iter.next() % 2 == 0) {
                    iter.remove(); // 안전한 삭제!
                }
            }

            assertThat(set).containsExactlyInAnyOrder(1, 3, 5);
        }

        @Test
        void removeIf로_조건부_삭제() {
            Set<Integer> set = new HashSet<>(Set.of(1, 2, 3, 4, 5));

            set.removeIf(n -> n % 2 == 0);

            assertThat(set).containsExactlyInAnyOrder(1, 3, 5);
        }
    }

    // PS 실전 패턴
    @Nested
    class PS_실전_패턴 {

        @Test
        void 배열에서_중복_제거() {
            int[] arr = {1, 2, 2, 3, 3, 3, 4};
            Set<Integer> set = new HashSet<>();

            for (int num : arr) {
                set.add(num);
            }

            assertThat(set).hasSize(4);
            assertThat(set).containsExactlyInAnyOrder(1, 2, 3, 4);
        }

        @Test
        void 중복_없는_개수_세기() {
            int[] arr = {1, 2, 2, 3, 3, 3};
            Set<Integer> set = new HashSet<>();

            for (int num : arr) {
                set.add(num);
            }

            int uniqueCount = set.size();

            assertThat(uniqueCount).isEqualTo(3);
        }

        @Test
        void 방문_체크_visited() {
            Set<Integer> visited = new HashSet<>();
            int[] nodes = {1, 2, 3, 2, 4, 1};

            List<Integer> firstVisitOrder = new ArrayList<>();
            for (int node : nodes) {
                if (visited.add(node)) {
                    firstVisitOrder.add(node);
                }
            }

            assertThat(firstVisitOrder).containsExactly(1, 2, 3, 4);
        }

        @Test
        void 두_배열의_공통_요소_찾기() {
            int[] arr1 = {1, 2, 3, 4, 5};
            int[] arr2 = {4, 5, 6, 7, 8};

            Set<Integer> set1 = new HashSet<>();
            for (int n : arr1) set1.add(n);

            List<Integer> common = new ArrayList<>();
            for (int n : arr2) {
                if (set1.contains(n)) {
                    common.add(n);
                }
            }

            assertThat(common).containsExactlyInAnyOrder(4, 5);
        }

        @Test
        void Set을_정렬된_배열로_변환() {
            Set<Integer> set = new HashSet<>(Set.of(5, 2, 8, 1, 9));

            int[] sorted = set.stream().sorted().mapToInt(Integer::intValue).toArray();

            assertThat(sorted).containsExactly(1, 2, 5, 8, 9);
        }

        @Test
        void List에서_중복_제거_후_다시_List로() {
            List<Integer> list = List.of(1, 2, 2, 3, 3, 3);

            List<Integer> unique = new ArrayList<>(new HashSet<>(list));
            // 주의: 순서가 보장되지 않음

            assertThat(unique).containsExactlyInAnyOrder(1, 2, 3);
        }
    }

    // 생성 방법들
    @Nested
    class 생성_방법 {

        @Test
        void 기본_생성자() {
            Set<Integer> set = new HashSet<>();
            set.add(1);

            assertThat(set).containsExactly(1);
        }

        @Test
        void 컬렉션으로_초기화() {
            List<Integer> list = List.of(1, 2, 2, 3);
            Set<Integer> set = new HashSet<>(list);

            assertThat(set).containsExactlyInAnyOrder(1, 2, 3);
        }

        @Test
        void Set_of로_불변_Set_생성() {
            Set<Integer> immutableSet = Set.of(1, 2, 3);

            assertThat(immutableSet).containsExactlyInAnyOrder(1, 2, 3);
            // immutableSet.add(4);  // UnsupportedOperationException
        }

        @Test
        void 초기_용량_지정() {
            // 많은 요소를 추가할 예정이면 초기 용량 지정으로 성능 향상
            Set<Integer> set = new HashSet<>(10000);
            for (int i = 0; i < 10000; i++) {
                set.add(i);
            }

            assertThat(set).hasSize(10000);
        }
    }
}

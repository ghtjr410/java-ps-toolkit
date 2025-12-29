# Java PS Toolkit

> 알고리즘 문제 풀이에 필요한 Java 문법과 API를 학습 테스트로 완전 정복하는 저장소

[![Java Version](https://img.shields.io/badge/Java-11-orange.svg)](https://openjdk.org/projects/jdk/11/)
[![JUnit Version](https://img.shields.io/badge/JUnit-5.10-green.svg)](https://junit.org/junit5/)

## 📌 소개

이 저장소는 **알고리즘 문제 풀이(PS)에서 반복적으로 실수하는 Java 문법과 API**를 학습 테스트로 정리합니다.
```
"문법에서 헤매지 않고, 알고리즘 로직에만 집중한다"
```

### 이런 경험이 있다면

- `StringTokenizer`가 연속 공백을 어떻게 처리하는지 헷갈렸다
- `PriorityQueue`가 최소 힙인지 최대 힙인지 매번 검색했다
- `(a, b) -> a - b` 정렬에서 오버플로우가 터졌다
- `Integer` 비교에서 `==`를 써서 틀렸다
- `substring(start, end)`에서 end가 포함인지 아닌지 헷갈렸다

**→ 이 저장소가 해결해줍니다.**

## 🎯 학습 목표

- PS에서 **자주 실수하는 패턴**을 테스트로 각인
- 단순 암기가 아닌 **"왜 이렇게 동작하는가"** 이해
- 문제 풀이 중 **문법 검색 시간 제로**를 목표
- 실수했던 것들을 **테스트로 기록**하여 반복 방지

## 🛠 기술 스택

| 구분 | 기술 |
|------|------|
| Language | Java 11 |
| Test Framework | JUnit 5 |
| Assertion | AssertJ |
| Build Tool | Gradle |

## 📁 프로젝트 구조
```
src/test/java/
├── phase01_io/              # 입출력 (BufferedReader, StringTokenizer)
├── phase02_primitive/       # 기본형, 형변환, 오버플로우
├── phase03_string/          # String, StringBuilder, 문자열 처리
├── phase04_array/           # 배열, Arrays 유틸리티
├── phase05_list/            # ArrayList, LinkedList, Collections
├── phase06_set_map/         # Set, Map 구현체별 특성
├── phase07_queue_stack/     # Stack, Queue, Deque, PriorityQueue
├── phase08_sorting/         # 정렬, Comparator, 람다
├── phase09_math_bit/        # 수학 연산, BigInteger, 비트 연산
└── phase10_gotchas/         # PS에서 자주 틀리는 함정 모음
```

## 📚 학습 내용

### Phase 1: I/O (입출력) ⭐ 최우선

> PS의 시작과 끝. 입출력 실수만 잡아도 맞왜틀의 절반이 해결된다.

<details>
<summary><b>01. BufferedReader & InputStreamReader</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `BufferedReaderBasicTest` | 기본 사용법, readLine() 특성 |
| `BufferedReaderEOFTest` | EOF 처리, null 체크 |
| `InputStreamReaderEncodingTest` | 인코딩 이슈 |

**핵심 질문**
- `readLine()`은 개행 문자를 포함해서 반환하는가?
- EOF를 어떻게 감지하는가?
- `Scanner` 대신 `BufferedReader`를 쓰는 이유는?

</details>

<details>
<summary><b>02. StringTokenizer</b> ⭐⭐ 핵심</summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `StringTokenizerBasicTest` | 기본 사용법, 기본 구분자 |
| `StringTokenizerDelimiterTest` | 커스텀 구분자, 다중 구분자 |
| `StringTokenizerVsSplitTest` | split()과의 결정적 차이 |
| `StringTokenizerConsecutiveDelimiterTest` | 연속 구분자 처리 (함정!) |
| `StringTokenizerCountTokensTest` | countTokens() 동작 방식 |

**핵심 질문**
- `"a  b".split(" ")`와 `new StringTokenizer("a  b")`의 결과가 다른 이유는?
- `countTokens()`를 호출하면 토큰 위치가 변하는가?
- 구분자 자체를 토큰으로 포함시키려면?

</details>

<details>
<summary><b>03. 숫자 파싱</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `IntegerParseIntTest` | parseInt 기본, 공백 처리 |
| `LongParseLongTest` | long 파싱, 범위 초과 |
| `ParseRadixTest` | 진법 변환 파싱 |
| `NumberFormatExceptionTest` | 예외 발생 케이스 |

**핵심 질문**
- `Integer.parseInt(" 123 ")`는 성공하는가?
- `Integer.parseInt("123")`과 `Integer.valueOf("123")`의 차이는?

</details>

<details>
<summary><b>04. 출력 최적화</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `StringBuilderOutputTest` | StringBuilder 기반 출력 |
| `BufferedWriterTest` | BufferedWriter 사용법, flush |
| `PrintWriterTest` | PrintWriter 특성 |
| `OutputPerformanceTest` | 출력 방식별 성능 비교 |

**핵심 질문**
- `System.out.println()`을 N번 호출하면 왜 느린가?
- `BufferedWriter.write(123)`이 "123"을 출력하지 않는 이유는?
- `flush()`와 `close()`의 차이는?

</details>

---

### Phase 2: 기본형 & 형변환

> 오버플로우 한 번이면 모든 테스트케이스가 틀린다.

<details>
<summary><b>01. int vs long 범위</b> ⭐⭐ 핵심</summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `IntRangeTest` | int 최대/최소값, 경계 테스트 |
| `LongRangeTest` | long 범위, 언제 long을 써야 하는가 |
| `OverflowTest` | 오버플로우 발생 케이스 |
| `MultiplicationOverflowTest` | int * int 곱셈 함정 |
| `CastingTrickTest` | 1L 곱하기, (long) 캐스팅 |

**핵심 질문**
- `100000 * 100000`의 결과는? (int 범위)
- N이 10만일 때, `N * N`을 안전하게 계산하려면?
- `Integer.MAX_VALUE + 1`의 결과는?

</details>

<details>
<summary><b>02. char 연산</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `CharToIntTest` | char - '0', char - 'a' |
| `IntToCharTest` | (char)(num + '0'), (char)(num + 'a') |
| `CharacterMethodsTest` | isDigit, isLetter, isUpperCase |
| `CharArithmeticTest` | char 덧셈, 뺄셈 |

**핵심 질문**
- `'9' - '0'`의 결과 타입은?
- `'a' + 1`의 결과는?
- 대문자를 소문자로 바꾸는 가장 빠른 방법은?

</details>

<details>
<summary><b>03. 형변환 주의점</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `DoubleTruncationTest` | (int) double 버림 |
| `RoundingTest` | Math.round, ceil, floor |
| `IntDivisionTest` | 정수 나눗셈 함정 |
| `ImplicitCastingTest` | 암시적 형변환 |

**핵심 질문**
- `7 / 2`와 `7.0 / 2`의 차이는?
- `(int) -3.7`의 결과는?
- 올림 나눗셈 `(a + b - 1) / b`는 왜 동작하는가?

</details>

---

### Phase 3: String 처리

> PS에서 문자열 처리 실수는 디버깅이 가장 어렵다.

<details>
<summary><b>01. String 기본 메서드</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `CharAtTest` | charAt, 인덱스 범위 |
| `SubstringTest` | substring(start, end) - end 미포함! |
| `IndexOfTest` | indexOf, lastIndexOf, 못찾으면 -1 |
| `LengthTest` | length() vs 배열 length (괄호!) |

**핵심 질문**
- `"hello".substring(1, 3)`의 결과는?
- `"hello".indexOf("x")`의 결과는?
- `String.length()`와 `배열.length`의 차이는?

</details>

<details>
<summary><b>02. String 비교</b> ⭐⭐ 핵심</summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `EqualsVsDoubleEqualsTest` | ==와 equals의 차이 |
| `StringPoolTest` | 리터럴 vs new String |
| `CompareToTest` | compareTo 반환값 의미 |
| `EqualsIgnoreCaseTest` | 대소문자 무시 비교 |

**핵심 질문**
- `"hello" == "hello"`는 true인가?
- `new String("hello") == new String("hello")`는?
- `compareTo()`가 음수를 반환하면 무슨 의미인가?

</details>

<details>
<summary><b>03. String 변환</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `ToCharArrayTest` | 문자열 → char 배열 |
| `SplitTest` | split 정규식 주의, 빈 문자열 |
| `SplitVsStringTokenizerTest` | 결정적 차이 정리 |
| `ReplaceTest` | replace vs replaceAll |
| `TrimTest` | trim, strip (Java 11) |

**핵심 질문**
- `"a.b.c".split(".")`의 결과는? (함정!)
- `"a,,b".split(",")`의 결과는?
- `replace()`와 `replaceAll()`의 차이는?

</details>

<details>
<summary><b>04. StringBuilder</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `StringBuilderAppendTest` | append 체이닝, 타입별 append |
| `StringBuilderModifyTest` | insert, delete, setCharAt |
| `StringBuilderReverseTest` | reverse()는 원본을 변경! |
| `StringVsStringBuilderTest` | 성능 차이, 언제 써야 하는가 |

**핵심 질문**
- `StringBuilder.reverse()`의 반환값은 새 객체인가?
- 문자열 N개를 연결할 때 `+` 연산자의 시간복잡도는?

</details>

---

### Phase 4: 배열 & Arrays

<details>
<summary><b>01. 배열 기본</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `ArrayDeclarationTest` | 선언, 초기화, 기본값 |
| `ArrayDefaultValueTest` | int(0), boolean(false), 객체(null) |
| `TwoDimensionalArrayTest` | 2차원 배열, 가변 길이 |
| `ArrayCopyTest` | 얕은 복사 vs 깊은 복사 |

**핵심 질문**
- `new int[5]`의 초기값은?
- `new String[5]`의 초기값은?
- `int[][] arr = new int[3][]`은 유효한가?

</details>

<details>
<summary><b>02. Arrays 유틸리티</b> ⭐⭐ 핵심</summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `ArraysSortTest` | 기본 정렬, 범위 정렬 |
| `ArraysFillTest` | fill로 초기화 |
| `ArraysCopyOfTest` | copyOf, copyOfRange |
| `ArraysBinarySearchTest` | 이분탐색 (정렬 필수!) |
| `ArraysEqualsTest` | 배열 비교 (==는 안됨!) |
| `ArraysToStringTest` | 디버깅용 출력 |

**핵심 질문**
- `Arrays.binarySearch()`를 정렬 안된 배열에 쓰면?
- `Arrays.copyOf(arr, arr.length + 5)`하면?
- `arr1 == arr2`와 `Arrays.equals(arr1, arr2)`의 차이는?

</details>

<details>
<summary><b>03. 2차원 배열 정렬</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `TwoDArraySortBasicTest` | 첫 번째 원소 기준 정렬 |
| `TwoDArraySortMultiKeyTest` | 다중 조건 정렬 |
| `TwoDArraySortOverflowTest` | 뺄셈 정렬의 오버플로우 함정 |

**핵심 질문**
- `(a, b) -> a[0] - b[0]`에서 오버플로우가 발생하는 경우는?
- 안전한 비교 방법 `Integer.compare()`는 어떻게 쓰는가?

</details>

---

### Phase 5: List 계열

<details>
<summary><b>01. ArrayList</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `ArrayListBasicTest` | add, get, set, size |
| `ArrayListRemoveTest` | remove(index) vs remove(Object) 함정! |
| `ArrayListContainsTest` | contains, indexOf |
| `ArrayListIterationTest` | for-each, 인덱스 순회 |

**핵심 질문**
- `list.remove(1)`과 `list.remove(Integer.valueOf(1))`의 차이는?
- ArrayList의 get() 시간복잡도는?

</details>

<details>
<summary><b>02. List 변환</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `ArraysAsListTest` | Arrays.asList() 함정 (고정 크기!) |
| `NewArrayListFromArrayTest` | 진짜 가변 리스트 만들기 |
| `ListToArrayTest` | toArray() 사용법 |
| `PrimitiveArrayConversionTest` | int[] ↔ List<Integer> |

**핵심 질문**
- `Arrays.asList()`로 만든 리스트에 add()하면?
- `int[]`를 `List<Integer>`로 바꾸는 가장 간단한 방법은?

</details>

<details>
<summary><b>03. Collections 유틸리티</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `CollectionsSortTest` | sort, reverseOrder |
| `CollectionsReverseTest` | reverse (원본 변경!) |
| `CollectionsMinMaxTest` | min, max |
| `CollectionsBinarySearchTest` | 이분탐색 |
| `CollectionsSwapFillTest` | swap, fill |

**핵심 질문**
- `Collections.reverse()`는 새 리스트를 반환하는가?
- `Collections.sort()`와 `list.sort()`의 차이는?

</details>

---

### Phase 6: Set & Map

<details>
<summary><b>01. Set 구현체 비교</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `HashSetTest` | 순서 없음, O(1) |
| `TreeSetTest` | 정렬 유지, O(log n) |
| `LinkedHashSetTest` | 삽입 순서 유지 |
| `SetAddReturnValueTest` | add() 반환값 활용 |

**핵심 질문**
- HashSet의 순회 순서는 보장되는가?
- TreeSet에 Comparable 구현 안된 객체 넣으면?
- `set.add(x)`가 false를 반환하면 무슨 의미인가?

</details>

<details>
<summary><b>02. TreeSet 심화</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `TreeSetFirstLastTest` | first(), last() |
| `TreeSetFloorCeilingTest` | floor(), ceiling() (같거나 작은/큰) |
| `TreeSetLowerHigherTest` | lower(), higher() (미만/초과) |
| `TreeSetSubSetTest` | subSet(), headSet(), tailSet() |

**핵심 질문**
- `floor(5)`와 `lower(5)`의 차이는?
- 5 이상 10 미만의 원소만 가져오려면?

</details>

<details>
<summary><b>03. Map 구현체 비교</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `HashMapTest` | 기본 사용법, 순서 없음 |
| `TreeMapTest` | 키 정렬, NavigableMap |
| `LinkedHashMapTest` | 삽입 순서 유지 |
| `MapNullKeyValueTest` | null 키/값 허용 여부 |

**핵심 질문**
- HashMap에 null 키를 넣을 수 있는가?
- TreeMap에 null 키를 넣을 수 있는가?

</details>

<details>
<summary><b>04. Map 고급 메서드</b> ⭐⭐ 핵심</summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `GetOrDefaultTest` | getOrDefault로 null 방지 |
| `PutIfAbsentTest` | 없을 때만 추가 |
| `ComputeIfAbsentTest` | 값 계산 후 저장 |
| `MergeTest` | 카운팅에 최적 |
| `MapEntryIterationTest` | entrySet 순회 |

**핵심 질문**
- 카운팅할 때 `getOrDefault` + `put` vs `merge` 중 뭐가 나은가?
- `computeIfAbsent()`는 언제 사용하는가?

</details>

---

### Phase 7: Stack, Queue, Deque, PriorityQueue

<details>
<summary><b>01. Stack (레거시)</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `StackBasicTest` | push, pop, peek |
| `StackEmptyTest` | empty() vs isEmpty() |
| `StackVsDequeTest` | Deque 권장 이유 |

**핵심 질문**
- Stack 대신 Deque를 권장하는 이유는?
- Stack을 써야만 하는 경우가 있는가?

</details>

<details>
<summary><b>02. Queue 인터페이스</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `QueueAddVsOfferTest` | add() vs offer() |
| `QueueRemoveVsPollTest` | remove() vs poll() |
| `QueueElementVsPeekTest` | element() vs peek() |
| `LinkedListAsQueueTest` | LinkedList로 Queue 사용 |

**핵심 질문**
- 빈 큐에서 `remove()`와 `poll()`의 차이는?
- PS에서는 어떤 메서드를 쓰는 게 안전한가?

</details>

<details>
<summary><b>03. Deque (양방향 큐)</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `DequeAsStackTest` | push/pop (LIFO) |
| `DequeAsQueueTest` | offer/poll (FIFO) |
| `DequeFirstLastTest` | First/Last 메서드들 |
| `ArrayDequeVsLinkedListTest` | ArrayDeque가 더 빠른 이유 |

**핵심 질문**
- `ArrayDeque`와 `LinkedList`의 성능 차이는?
- Deque를 Stack으로 쓸 때 어떤 메서드를 쓰는가?

</details>

<details>
<summary><b>04. PriorityQueue</b> ⭐⭐⭐ 핵심</summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `PriorityQueueDefaultOrderTest` | 기본이 최소 힙! |
| `PriorityQueueMaxHeapTest` | 최대 힙 만들기 |
| `PriorityQueueCustomComparatorTest` | 객체 정렬 |
| `PriorityQueueIterationPitfallTest` | 순회 순서 ≠ 정렬 순서 (함정!) |
| `PriorityQueuePollOrderTest` | poll() 순서만 보장 |

**핵심 질문**
- `PriorityQueue`의 기본 정렬 순서는?
- 최대 힙으로 만들려면?
- `for(int x : pq)` 순회 결과가 정렬되어 있는가?

</details>

---

### Phase 8: 정렬 & Comparator

<details>
<summary><b>01. Comparable vs Comparator</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `ComparableTest` | 클래스에 정렬 기준 정의 |
| `ComparatorTest` | 외부에서 정렬 기준 지정 |
| `CompareReturnValueTest` | 음수/0/양수 의미 |

**핵심 질문**
- Comparable과 Comparator는 언제 각각 사용하는가?
- `compareTo()`가 양수를 반환하면 순서가 어떻게 되는가?

</details>

<details>
<summary><b>02. Comparator 작성법</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `LambdaComparatorTest` | `(a, b) -> a - b` |
| `IntegerCompareTest` | `Integer.compare()` 안전한 비교 |
| `ComparatorChainingTest` | `thenComparing()` 다중 조건 |
| `ComparatorReverseTest` | `reversed()` |
| `ComparatorComparingTest` | `Comparator.comparing()` |

**핵심 질문**
- `(a, b) -> a - b`가 위험한 이유는?
- 다중 조건 정렬을 깔끔하게 작성하는 방법은?

</details>

<details>
<summary><b>03. 정렬 실전</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `PrimitiveArraySortTest` | 기본형 배열은 Comparator 불가 |
| `WrapperArraySortTest` | Wrapper 배열 정렬 |
| `ListSortTest` | Collections.sort() vs list.sort() |
| `PartialSortTest` | 범위 정렬 |

**핵심 질문**
- `int[]`를 내림차순 정렬하려면?
- `Arrays.sort()`와 `Collections.sort()`의 차이는?

</details>

---

### Phase 9: 수학 & 비트 연산

<details>
<summary><b>01. Math 클래스</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `MathAbsMinMaxTest` | abs, min, max |
| `MathPowSqrtTest` | pow (double 반환!), sqrt |
| `MathRoundingTest` | ceil, floor, round |
| `MathPitfallTest` | abs(Integer.MIN_VALUE) 함정 |

**핵심 질문**
- `Math.pow(2, 10)`의 반환 타입은?
- `Math.abs(Integer.MIN_VALUE)`의 결과는? (함정!)
- `Math.round(-2.5)`의 결과는?

</details>

<details>
<summary><b>02. BigInteger</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `BigIntegerCreationTest` | 생성, valueOf |
| `BigIntegerArithmeticTest` | add, subtract, multiply, divide, mod |
| `BigIntegerCompareTest` | compareTo로만 비교! |
| `BigIntegerConversionTest` | intValue, longValue |

**핵심 질문**
- `BigInteger`끼리 `==`로 비교하면?
- `BigInteger.valueOf()`와 `new BigInteger()`의 차이는?

</details>

<details>
<summary><b>03. 비트 연산</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `BitwiseOperatorTest` | AND, OR, XOR, NOT |
| `ShiftOperatorTest` | <<, >>, >>> |
| `BitTrickTest` | 홀짝, 2의 거듭제곱 판별 |
| `BitCountTest` | Integer.bitCount, toBinaryString |

**핵심 질문**
- `1 << 30`과 `1 << 31`의 차이는?
- `n & (n - 1)`은 무엇을 하는가?
- `n & 1`로 홀짝을 판별하는 원리는?

</details>

---

### Phase 10: 자주 틀리는 함정 모음 ⭐⭐⭐

> 한 번 당하면 절대 안 잊어버리는 함정들

<details>
<summary><b>01. Integer 비교 함정</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `IntegerCachingTest` | -128 ~ 127 캐싱 |
| `IntegerEqualsVsDoubleEqualsTest` | == vs equals |
| `AutoboxingPitfallTest` | 오토박싱과 비교 |

**핵심 질문**
- `Integer a = 100; Integer b = 100; a == b`는?
- `Integer a = 200; Integer b = 200; a == b`는?
- 왜 이런 차이가 발생하는가?

</details>

<details>
<summary><b>02. 부동소수점 함정</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `DoubleEqualityTest` | 0.1 + 0.2 != 0.3 |
| `DoubleComparisonTest` | 오차 허용 비교 |
| `IntegerDivisionPitfallTest` | 정수 나눗셈 결과 |

**핵심 질문**
- `0.1 + 0.2 == 0.3`은 true인가?
- 부동소수점 비교는 어떻게 해야 하는가?

</details>

<details>
<summary><b>03. 컬렉션 수정 함정</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `ConcurrentModificationTest` | 순회 중 삭제 |
| `IteratorRemoveTest` | Iterator.remove() |
| `RemoveIfTest` | removeIf() 활용 |

**핵심 질문**
- for-each 루프에서 remove()하면 무슨 일이?
- 순회하면서 안전하게 삭제하는 방법은?

</details>

<details>
<summary><b>04. 배열/문자열 경계 함정</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `ArrayIndexBoundsTest` | 경계 체크 패턴 |
| `SubstringBoundsTest` | substring 범위 |
| `DxDyPatternTest` | 4방향, 8방향 이동 패턴 |

**핵심 질문**
- `dx, dy` 배열로 이동할 때 경계 체크 순서는?
- `substring(0, s.length())`는 유효한가?

</details>

<details>
<summary><b>05. Null 함정</b></summary>

| 테스트 | 학습 내용 |
|--------|-----------|
| `MapGetNullTest` | Map.get() null 반환 |
| `GetOrDefaultTest` | null 방지 |
| `NullPointerInUnboxingTest` | 언박싱 NPE |

**핵심 질문**
- `map.get(key)`가 null이면 키가 없는 건가, 값이 null인 건가?
- `Integer a = null; int b = a;`하면?

</details>

---

## 📝 학습 테스트 작성 원칙

### 테스트 구조
```java
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StringTokenizerTest {

    @Nested
    class 연속_구분자_처리 {

        @Test
        void StringTokenizer는_연속_구분자를_무시한다() {
            StringTokenizer st = new StringTokenizer("a  b   c");

            assertThat(st.countTokens()).isEqualTo(3);
            assertThat(st.nextToken()).isEqualTo("a");
            assertThat(st.nextToken()).isEqualTo("b");
            assertThat(st.nextToken()).isEqualTo("c");
        }

        @Test
        void split은_연속_구분자_사이에_빈_문자열을_생성한다() {
            String[] result = "a  b".split(" ");

            assertThat(result).containsExactly("a", "", "b");
        }
    }
}
```

### 원칙

| 원칙 | 설명 |
|------|------|
| **한글 메서드명** | `PriorityQueue는_기본이_최소힙이다()` |
| **@Nested 그룹핑** | 관련 테스트를 주제별로 묶기 |
| **함정은 대비로** | 틀리기 쉬운 것 vs 올바른 것 함께 작성 |
| **given/when/then** | 구조 유지, 주석은 생략 |

### 학습 테스트가 다루는 것
```
✅ 기본 사용법 (어떻게 쓰는가)
✅ 동작 원리 (왜 이렇게 동작하는가)
✅ 함정 케이스 (어디서 틀리는가)
✅ 올바른 패턴 (어떻게 해야 안전한가)
✅ 비교 테스트 (A vs B 차이)
```

---

## 🚀 실행 방법
```bash
# 전체 테스트 실행
./gradlew test

# 특정 Phase만 실행
./gradlew test --tests "*phase01*"

# 특정 테스트 클래스만 실행
./gradlew test --tests "StringTokenizerTest"

# 테스트 리포트 확인
open build/reports/tests/test/index.html
```

---

## 📅 권장 학습 순서

| 주차 | Phase | 핵심 |
|------|-------|------|
| Week 1 | Phase 1~2 | I/O, 기본형 → 입출력 실수 80% 해결 |
| Week 2 | Phase 3~4 | String, Array → 문자열/배열 자신감 |
| Week 3 | Phase 5~6 | List, Set, Map → 자료구조 선택 명확 |
| Week 4 | Phase 7~8 | Queue, PQ, 정렬 → PS 핵심 도구 |
| Week 5 | Phase 9~10 | 수학, 함정 → 엣지케이스 대응력 |

---
## 📅 핵심 학습 순서
Phase 1 → 6 → 7 → 10 순서로 핵심만 먼저.

---

<div align="center">

**"문법에서 1초도 헤매지 않는 그날까지"**

</div>
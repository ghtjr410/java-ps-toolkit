package s01_io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
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

    // 연속 구분자 처리 - 핵심
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

    //  split()과의 결정적 차이
    @Nested
    class split과의_차이_핵심 {

        @Test
        void 연속_구분자_StringTokenizer는_빈토큰_없음() {
            StringTokenizer st = new StringTokenizer("a  b");
            List<String> tokens = new ArrayList<>();

            while (st.hasMoreTokens()) {
                tokens.add(st.nextToken());
            }

            assertThat(tokens).containsExactly("a", "b"); // 2개
        }

        @Test
        void 연속_구분자_split은_빈문자열_생성() {
            String[] parts = "a  b".split(" ");

            assertThat(parts).containsExactly("a", "", "b"); // 3개 빈 문자열 포함
        }

        @Test
        void 앞쪽_구분자_StringTokenizer는_무시() {
            StringTokenizer st = new StringTokenizer("  hello");

            assertThat(st.countTokens()).isEqualTo(1);
            assertThat(st.nextToken()).isEqualTo("hello");
        }

        @Test
        void 앞쪽_구분자_split은_빈문자열_생성() {
            String[] parts = "  hello".split(" ");

            assertThat(parts).containsExactly("", "", "hello"); // 앞에 빈 문자열 2
        }

        @Test
        void 뒤쪽_구분자_split은_기본적으로_제거된다() {
            // split의 특이한 동작: 뒤쪽 빈 문자열은 기본적으로 제거됨
            String[] parts = "hello  ".split(" ");

            assertThat(parts).containsExactly("hello"); // 뒤쪽 빈 문자열 제거됨
        }

        @Test
        void 뒤쪽_구분자_split에_limit_마이너스1이면_유지() {
            String[] parts = "hello  ".split(" ", -1);

            assertThat(parts).containsExactly("hello", "", ""); // 빈 문자열 유지
        }

        @Test
        void 종합_비교_테스트() {
            String input = "  a  b  c  ";

            // StringTokenizer
            StringTokenizer st = new StringTokenizer(input);
            List<String> stResult = new ArrayList<>();
            while (st.hasMoreTokens()) {
                stResult.add(st.nextToken());
            }

            // split
            String[] splitResult = input.split(" ");

            assertThat(stResult).containsExactly("a", "b", "c"); // 깔끔
            assertThat(splitResult).containsExactly("", "", "a", "", "b", "", "c"); // 빈 문자열 많음
        }
    }

    // 커스텀 구분자
    @Nested
    class 커스텀_구분자 {

        @Test
        void 쉼표를_구분자로_사용() {
            StringTokenizer st = new StringTokenizer("a,b,c", ",");

            assertThat(st.countTokens()).isEqualTo(3);
            assertThat(st.nextToken()).isEqualTo("a");
            assertThat(st.nextToken()).isEqualTo("b");
            assertThat(st.nextToken()).isEqualTo("c");
        }

        @Test
        void 여러_문자를_각각_구분자로_사용() {
            // 두 번째 파라미터의 각 문자가 모두 구분자
            StringTokenizer st = new StringTokenizer("a,b:c;d", ",;:");

            assertThat(st.countTokens()).isEqualTo(4);
            assertThat(st.nextToken()).isEqualTo("a");
            assertThat(st.nextToken()).isEqualTo("b");
            assertThat(st.nextToken()).isEqualTo("c");
            assertThat(st.nextToken()).isEqualTo("d");
        }

        @Test
        void 구분자는_문자열이_아니라_문자들의_집합이다() {
            // "ab"가 구분자가 아니라, 'a'와 'b' 각각이 구분자
            StringTokenizer st = new StringTokenizer("xaybz", "ab");

            assertThat(st.countTokens()).isEqualTo(3);
            assertThat(st.nextToken()).isEqualTo("x");
            assertThat(st.nextToken()).isEqualTo("y");
            assertThat(st.nextToken()).isEqualTo("z");
        }

        @Test
        void 커스텀_구분자에도_연속_구분자_무시_적용() {
            StringTokenizer st = new StringTokenizer("a,,b,,,c", ",");

            assertThat(st.countTokens()).isEqualTo(3); // 빈 토큰 없음
        }
    }

    // 구분자를 토큰으로 포함 (returnDelims)
    @Nested
    class 구분자를_토큰으로_포함 {

        @Test
        void returnDelims가_true면_구분자도_토큰으로_반환() {
            StringTokenizer st = new StringTokenizer("aaa+bb-c", "+-", true);

            assertThat(st.countTokens()).isEqualTo(5);
            assertThat(st.nextToken()).isEqualTo("aaa");
            assertThat(st.nextToken()).isEqualTo("+"); // 구분자도 토큰
            assertThat(st.nextToken()).isEqualTo("bb");
            assertThat(st.nextToken()).isEqualTo("-"); // 구분자도 토큰
            assertThat(st.nextToken()).isEqualTo("c");
        }

        @Test
        void 수식_파싱에_활용() {
            StringTokenizer st = new StringTokenizer("10+20*30", "+*", true);

            assertThat(st.nextToken()).isEqualTo("10");
            assertThat(st.nextToken()).isEqualTo("+");
            assertThat(st.nextToken()).isEqualTo("20");
            assertThat(st.nextToken()).isEqualTo("*");
            assertThat(st.nextToken()).isEqualTo("30");
        }

        @Test
        void returnDelims에서도_연속_구분자는_각각_반환() {
            StringTokenizer st = new StringTokenizer("a++b", "+", true);

            assertThat(st.nextToken()).isEqualTo("a");
            assertThat(st.nextToken()).isEqualTo("+");
            assertThat(st.nextToken()).isEqualTo("+"); // 두 번째 +도 별도 토큰
            assertThat(st.nextToken()).isEqualTo("b");
        }
    }

    // PS 실전 패턴
    @Nested
    class PS_실전_패턴 {

        @Test
        void 한_줄에_여러_정수_읽기() {
            String line = "1 2 3 4 5";
            StringTokenizer st = new StringTokenizer(line);

            int[] arr = new int[st.countTokens()];
            int idx = 0;

            while (st.hasMoreTokens()) {
                arr[idx++] = Integer.parseInt(st.nextToken());
            }

            assertThat(arr).containsExactly(1, 2, 3, 4, 5);
        }

        @Test
        void 정해진_개수만큼_읽기() {
            String line = "10 20 30";
            StringTokenizer st = new StringTokenizer(line);

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            assertThat(a).isEqualTo(10);
            assertThat(b).isEqualTo(20);
            assertThat(c).isEqualTo(30);
        }

        @Test
        void 좌표_입력_패턴() {
            // BFS, DFS 문제에서 시작점/도착점
            String line = "3 5"; // x y
            StringTokenizer st = new StringTokenizer(line);

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            assertThat(x).isEqualTo(3);
            assertThat(y).isEqualTo(5);
        }

        @Test
        void 그래프_간선_입력_패턴() {
            // 다익스트라, MST 등 가중치 그래프 문제
            String line = "1 2 10"; // from to weight
            StringTokenizer st = new StringTokenizer(line);

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            assertThat(from).isEqualTo(1);
            assertThat(to).isEqualTo(2);
            assertThat(weight).isEqualTo(10);
        }

        @Test
        void N과_M_입력_후_2차원_배열_읽기() {
            // 미로, 게임판 등 격자 문제
            String firstLine = "2 3"; // N M
            String[] dataLines = {"1 2 3", "4 5 6"};

            StringTokenizer st = new StringTokenizer(firstLine);
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            int[][] board = new int[N][M];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(dataLines[i]);
                for (int j = 0; j < M; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            assertThat(board).isDeepEqualTo(new int[][] {{1, 2, 3}, {4, 5, 6}});
        }
    }

    // 주의사항 & 팁
    @Nested
    class 주의사항 {

        @Test
        void StringTokenizer는_스레드_안전하지_않다() {
            // 단일 스레드 PS 환경에서는 문제없음
            // 문서화 목적의 테스트
            StringTokenizer st = new StringTokenizer("a b c");
            assertThat(st.countTokens()).isEqualTo(3);
        }

        @Test
        void StringTokenizer는_레거시_클래스지만_PS에서는_여전히_유용() {
            // Java 공식 문서에서는 split() 권장
            // 하지만 PS에서는 StringTokenizer가 더 편리한 경우 많음:
            // 1. 연속 구분자 자동 처리
            // 2. 빈 문자열 신경 안 써도 됨
            StringTokenizer st = new StringTokenizer("  1   2   3  ");

            assertThat(st.countTokens()).isEqualTo(3);
        }

        @Test
        void split_정규식_특수문자_주의() {
            // split은 정규식을 받으므로 . | 등은 이스케이프 필요
            String input = "a.b.c";

            // 틀린 방법: .은 정규식에서 "모든 문자"
            String[] wrong = input.split(".");
            assertThat(wrong).isEmpty(); // 모든 문자가 구분자!

            // 맞는 방법 1: 이스케이프
            String[] correct1 = input.split("\\.");
            assertThat(correct1).containsExactly("a", "b", "c");

            // 맞는 방법 2: StringTokenizer (정규식 아님)
            StringTokenizer st = new StringTokenizer(input, ".");
            assertThat(st.countTokens()).isEqualTo(3);
        }
    }
}

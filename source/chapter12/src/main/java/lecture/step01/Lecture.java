package lecture.step01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lecture {
    private int pass;       // 이수 여부 판단 기준 점수
    private String title;   // 과목명
    private List<Integer> scores = new ArrayList<>(); // 학생들의 성적을 보관

    public Lecture(String title, int pass, List<Integer> scores) {
        this.title = title;
        this.pass = pass;
        this.scores = scores;
    }

    // 학생들 평균 계산
    public double average() {
        return scores.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    // 전체 학생들의 성적 반환
    public List<Integer> getScores() {
        return Collections.unmodifiableList(scores);
    }

    // 강의를 이수한 학생의 수와 낙제한 학생의 수를 형 식에 맞게 반환
    public String evaluate() {
        return String.format("Pass:%d Fail:%d", passCount(), failCount());
    }

    private long passCount() {
        return scores.stream().filter(score -> score >= pass).count();
    }

    private long failCount() {
        return scores.size() - passCount();
    }
}

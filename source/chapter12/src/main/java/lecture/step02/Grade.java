package lecture.step02;

public class Grade {

    private String name; // 등급의 이름
    private int uppser, lower; // 등급 범위 (최소 성적, 최대 성적)

    public Grade(String name, int uppser, int lower) {
        this.name = name;
        this.uppser = uppser;
        this.lower = lower;
    }

    public String getName() {
        return name;
    }

    public boolean isName(String name) {
        return this.name.equals(name);
    }

    public boolean include(int score) {
        return score >= lower && score <= uppser;
    }
}

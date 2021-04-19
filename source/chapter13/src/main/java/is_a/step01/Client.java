package is_a.step01;

public class Client {
    public void flyBird(Bird bird) {
        // 인자로 전달된 모든 bird가 Penguin의 인스턴스가 아닐 경우에만 메시지 전송
        if (!(bird instanceof Penguin)) {
            bird.fly();
        }
    }
}

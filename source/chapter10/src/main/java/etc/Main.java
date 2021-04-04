package etc;

import java.util.Properties;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        stack.push("1st");
        stack.push("2nd");
        stack.push("3rd");

        stack.add(0, "4th");

        // assertEquals("4th", stack.pop()); 에러 발생

        Properties properties = new Properties();
        properties.setProperty("Bjarne Stroustrup", "C++");
        properties.setProperty("James Gosling", "Java");

        properties.put("Dennis Ritchie", 67);

        // assertEquals("C", properties.getProperty("Dennis Ritchie")); 에러 발생
    }
}

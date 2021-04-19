package rectangle;

public class Client {
    public static void main(String[] args) {
        Square square = new Square(10, 10, 10);
        resize(square, 50, 100);
    }

    public static void resize(Rectangle rectangle, int width, int height) {
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        assert rectangle.getWidth() == width && rectangle.getHeight() == height;
    }
}

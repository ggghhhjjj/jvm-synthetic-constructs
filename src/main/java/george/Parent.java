package george;

class Parent {
    private int i;

    private class Nested {
        int getI() {
            return i;
        }
    }
}

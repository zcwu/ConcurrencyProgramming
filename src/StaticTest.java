public class StaticTest {

    static int a;

    StaticTest(int a) {
        this.a = a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public static void main(String[] args) {

        StaticTest staticTest1 = new StaticTest(1);
        System.out.println(staticTest1.a);

        StaticTest staticTest2 = new StaticTest(2);
        System.out.println(staticTest1.a);
        System.out.println(staticTest2.a);

        staticTest1.setA(3);
        System.out.println(staticTest1.a);
        System.out.println(staticTest2.a);
    }
}

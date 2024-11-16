public class Main {
    public static void main(String[] args) {
        String s = "abc";
        String s_ = new String("abc");
        System.out.println("s == s_: " + (s == s_));
        System.out.println("s.equals(s_): " + s.equals(s_));
    }
}
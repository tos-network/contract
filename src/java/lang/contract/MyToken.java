package java.lang.contract;

public class MyToken extends ERC20  {
    public MyToken() {
        super("MyToken", "MTK");
    }
}

class MyTokenTest {
    public static void main(String[] args) {
        try {
            System.out.println("step1");
            MyToken myToken = new MyToken();
            System.out.println("step2");
            myToken.call(); 
            System.out.println("step3");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("step4");
        }
        System.out.println("step5");
    }
}
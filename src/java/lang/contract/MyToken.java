package java.lang.contract;

import java.lang.annotation.Pragma;

@Pragma("0.8.18")
public class MyToken extends ERC20  {
    /**
     * The total supply of the token.
     */
    private transient UInt256  _totalSupply;

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
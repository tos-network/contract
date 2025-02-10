package java.lang.contract;

import java.lang.annotation.Pragma;
import java.lang.uint256;
@Pragma("0.8.18")
public class MyToken extends ERC20  {
    /**
     * The total supply of the token.
     */
    private transient uint256  _totalSupply;

    public MyToken() {
        super(string.valueOf("MyToken"), string.valueOf("MTK"));
    }
}

class MyTokenTest {
    public static void main(String[] args) {
        try {
            MyToken myToken = new MyToken();
            myToken.call(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
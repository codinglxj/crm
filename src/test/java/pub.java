import org.junit.Test;
import org.springframework.util.DigestUtils;

/**
 * @authot : lxj
 * @Date : 2021/3/13 9:39
 */
public class pub {
    @Test
    public void test01(){

        System.out.println(DigestUtils.md5DigestAsHex("123".getBytes()));
    }
}

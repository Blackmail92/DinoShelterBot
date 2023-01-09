package vd.martynov.dinoshelterbot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TestClass {

    @Test
    public void test() {
        System.out.println(isPalindrome(12321));
    }

    public boolean isPalindrome(int x) {
        if (x < 0) return false;
        int dummy = x;
        int result = 0;
        while (dummy > 0) {
            result = result * 10 + dummy % 10;
            dummy /= 10;
        }
        return result == x;
    }

}

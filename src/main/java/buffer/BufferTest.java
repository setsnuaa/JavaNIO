package buffer;

import java.nio.IntBuffer;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/4/29 14:17
 * @description:
 */
public class BufferTest {
    public static void main(String[] args) {
        //根据容量创建
        IntBuffer myBuffer = IntBuffer.allocate(10);
        myBuffer.put(1, 1);
        System.out.println(myBuffer.get(0));
        //根据数组创建
        int[] nums = new int[]{1, 2, 3};
        IntBuffer myBuffer2 = IntBuffer.wrap(nums);
        System.out.println(myBuffer2.get(1));
    }
}

package buffer;

import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/4/29 21:02
 * @description:
 */
public class BufferRead {
    public static void main(String[] args) {
        int[] arr = new int[3];
        IntBuffer buffer = IntBuffer.wrap(new int[]{1, 2, 3});
        //记录当前position的值
        buffer.mark();

        //get使用的也是position，和put使用的是同一个
        System.out.println(buffer.get());

        //所以此时再put的话，是在position=1的地方put的
        buffer.put(0);
        System.out.println(Arrays.toString(buffer.array()));

        //回退position的值
        buffer.reset();
        //直接读的话，arr这个数组有多长，就读多少个数
        buffer.get(arr);
        System.out.println(Arrays.toString(arr));

        buffer.reset();
        //也可以指定读几个
        buffer.get(arr, 0, 2);
        System.out.println(Arrays.toString(arr));
    }
}

package buffer;

import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/4/29 20:32
 * @description:
 */
public class BufferWrite {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        //可以直接写，位置自动后移
        buffer.put(1);
        buffer.put(2);
        //也可以指定位置
        buffer.put(3, 1);
        //也可以直接传数组，接着position的位置往后写
        buffer.put(new int[]{7, 7, 7});
        //此时1是接着7的后面写
        buffer.put(1);
        System.out.println(Arrays.toString(buffer.array()));
        System.out.println(buffer);
    }
}

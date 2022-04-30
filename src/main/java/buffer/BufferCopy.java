package buffer;

import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/4/30 15:57
 * @description:
 */
public class BufferCopy {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.wrap(new int[]{1, 2, 3, 4, 5});
        IntBuffer bufferCopy = buffer.duplicate();
        bufferCopy.put(0);

        System.out.println("buffer position " + buffer.position());//output:0
        System.out.println("bufferCopy position " + bufferCopy.position());//output:1
        System.out.println(Arrays.toString(buffer.array()));//output:0,2,3,4,5

        System.out.println(buffer == bufferCopy);//output:false
        System.out.println(buffer.array() == bufferCopy.array());//output:true

        //position,mark,limit都回到初始状态
        buffer.clear();
        bufferCopy.clear();
        //equals()比较剩余数组中的值是否相同
        System.out.println(buffer.equals(bufferCopy));
    }
}

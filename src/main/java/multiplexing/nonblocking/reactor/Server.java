package multiplexing.nonblocking.reactor;

import java.io.IOException;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/8 15:12
 * @description:
 */
public class Server {
    public static void main(String[] args) {
        try (Reactor reactor = new Reactor()) {
            reactor.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

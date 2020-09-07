package my.study.thread;

import org.openjdk.jol.info.ClassLayout;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @program: HelloThread
 * @description: Synchronized 锁升级过程
 * @author: L
 * @create: 2020-07-02 15:41
 */
public class HelloSynchronized {

    public static void main(String[] args) throws MalformedURLException {

        Object o = new Object();
        String s = ClassLayout.parseInstance(o).toPrintable();
        System.out.println(s);

    }

}

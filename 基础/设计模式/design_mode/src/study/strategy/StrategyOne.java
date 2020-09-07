package study.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @program: design_mode
 * @description: 策略模式第一种
 * @author: L
 * @create: 2020-08-13 11:55
 */
public class StrategyOne {

    private static Map<String, Function<String, strategy>> map = new HashMap<>();

    public static void main(String[] args) {


    }

}

interface strategy {

    String getString();

}

class strategyOneImpl implements strategy {

    @Override
    public String getString() {
        return "逻辑处理一";
    }

}

class strategyTwoImpl implements strategy {

    @Override
    public String getString() {
        return "逻辑处理二";
    }

}

package com.atguigu.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Demo1 {

    public static void main(String[] args) {
        List<User> list = new ArrayList<>();

        User u1 = new User();
        u1.setNum(new BigDecimal(100));

        User u2 = new User();
        u2.setNum(new BigDecimal(200));

//        list.add(u1);
//        list.add(u2);

//        BigDecimal result = list.stream().map(User::getNum)
//                .reduce(BigDecimal::add).get();

        BigDecimal result = list.stream().map(User::getNum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(result);
    }
}

class User {
    private BigDecimal num;

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }
}

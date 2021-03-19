package com.leetcode.other;

import java.util.HashMap;
import java.util.Map;

public class ParkingSystem {

    private Map<Integer, Integer> map;

    public ParkingSystem(int big, int medium, int small) {
        map = new HashMap<>();
        map.put(1, big);
        map.put(2, medium);
        map.put(3, small);
    }

    public boolean addCar(int carType) {
        int num = map.get(carType);
        if (num > 0) {
            map.put(carType, num - 1);
            return true;
        } else {
            return false;
        }
    }

}

package com.leetcode.other;

import java.util.*;

/**
 * 1604. 警告一小时内使用相同员工卡大于等于三次的人
 */
public class AlertEmployeeClockIn {

    public List<String> alertNames(String[] keyName, String[] keyTime) {
        Map<String, List<Integer>> keyMap = new HashMap<>();
        for (int i = 0; i < keyName.length; i++) {
            String key = keyName[i];
            List<Integer> value = keyMap.getOrDefault(key, new ArrayList<>());
            String time = keyTime[i];
            int hour = (time.charAt(0) - '0') * 10 + (time.charAt(1) - '0');
            int minute = (time.charAt(3) - '0') * 10 + (time.charAt(4) - '0');
            value.add(hour * 60 + minute);
            keyMap.put(key, value);
        }

        List<String> res = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : keyMap.entrySet()) {
            List<Integer> timeList = entry.getValue();
            // 时间排序
            Collections.sort(timeList);
            // 连续三个， 取排序后第一个和第三个的时间差，如果小于60分钟，则说明满足条件
            for (int i = 2; i < timeList.size(); i++) {
                int diff = timeList.get(i) - timeList.get(i - 2);
                if (diff <= 60) {
                    res.add(entry.getKey());
                    break;
                }
            }
        }
        Collections.sort(res);
        return res;
    }

}

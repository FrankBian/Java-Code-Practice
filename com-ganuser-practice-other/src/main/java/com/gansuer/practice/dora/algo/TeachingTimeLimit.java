package com.gansuer.practice.dora.algo;

import java.util.List;

/**
 * Created by Frank on 6/23/16.
 */
public class TeachingTimeLimit {

    public int getOverLimitNumber(List<Integer> timeList, int limit) {
        int max = 1, temp = 1;
        for (int i = 1; i < timeList.size(); i++) {
            if (timeList.get(i) == timeList.get(i - 1) + 1) {
                temp++;
            } else {
                temp = 0;
            }
            max = temp > max ? temp : max;
        }
        return max < limit ? 0 : max - limit;
    }
}

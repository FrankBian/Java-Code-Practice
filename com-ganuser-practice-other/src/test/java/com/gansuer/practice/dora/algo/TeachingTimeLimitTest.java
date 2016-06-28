package com.gansuer.practice.dora.algo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Frank on 6/23/16.
 */
public class TeachingTimeLimitTest {

    private TeachingTimeLimit demo;

    @Before
    public void setUp() throws Exception {
        demo = new TeachingTimeLimit();
    }

    @Test
    public void getOverLimitNumber() throws Exception {
        int res = demo.getOverLimitNumber(Arrays.asList(3,4,5,6),3);
        Assert.assertEquals(1,res);

        res = demo.getOverLimitNumber(Arrays.asList(34,35,36,37,38),3);
        Assert.assertEquals(2,res);

        res = demo.getOverLimitNumber(Arrays.asList(34,36,38),1);
        Assert.assertEquals(0,res);
    }

}
package com.gansuer.practice.dora;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 6/16/16.
 */
public class CourseView {

    String course;
    Map<String, List<CoursePlan>> classCoursePlanMap; //key = className

    public CourseView(String course) {
        this.course = course;
        classCoursePlanMap = new HashMap<>();
    }

    public void put(String className, CoursePlan coursePlan) {
        List<CoursePlan> temp = classCoursePlanMap.containsKey(className) ?
                classCoursePlanMap.get(className) : new ArrayList<>();
        temp.add(coursePlan);
        if (!classCoursePlanMap.containsKey(className)) {
            classCoursePlanMap.put(className, temp);
        }
    }
}

package com.gansuer.practice.dora;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Frank on 6/16/16.
 */
public class TeacherView {

    String teacher;
    Map<String, CourseView> courseViewMap; // kye=courseName

    public TeacherView(String teacher) {
        this.teacher = teacher;
        courseViewMap = new HashMap<>();
    }
}

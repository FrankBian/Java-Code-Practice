package com.gansuer.practice.dora;

import com.gansuer.common.util.FileUtils;
import com.gansuer.common.util.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.*;

/**
 * Created by Frank on 6/16/16.
 */
public class ResultHandler {

    private List<CoursePlan> coursePlanList;
    private String fileName;
    private List<TeacherView> teacherViews;
    private Map<String, Map<Integer, Integer>> courseDistributionMap;
    private Map<String, Map<Integer, Integer>> teacherTimeDistribution;

    private final int daysPerWeek;
    private final int lessonsPerDay;
    private final int am;
    private final int pm;

    public ResultHandler(String fileName, int daysPerWeek, int lessonsPerDay, int am, int pm) {
        this.fileName = fileName;
        this.daysPerWeek = daysPerWeek;
        this.lessonsPerDay = lessonsPerDay;
        this.am = am;
        this.pm = pm;
        teacherViews = new ArrayList<>();
        courseDistributionMap = new HashMap<>();
        teacherTimeDistribution = new HashMap<>();
        init();
    }

    private void init() {
        List<String> res = null;
        try {
            res = FileUtils.readFile(this.getClass().getClassLoader().getResource
                    (fileName).getFile());
            StringBuilder stringBuilder = new StringBuilder();
            res.forEach(item -> stringBuilder.append(item));
            String json = stringBuilder.toString();

            coursePlanList = JsonUtils.fromJson(json, new TypeToken<List<CoursePlan>>() {
            }.getType());

            Collections.sort(coursePlanList, (a, b) -> {
                return a.time - b.time;
            });

            TeacherView teacherView = null;
            Map<Integer, Integer> temp = null;
            Map<Integer, Integer> tempTeacher = null;
            for (CoursePlan item : coursePlanList) {
                int sequance = lessonSequanceInDay(item.time);
                if (courseDistributionMap.containsKey(item.course)) {
                    temp = courseDistributionMap.get(item.course);
                    if (temp.containsKey(sequance)) {
                        temp.put(sequance, temp.get(sequance) + 1);
                    } else {
                        temp.put(sequance, 1);
                    }
                } else {
                    temp = new TreeMap<>();
                    courseDistributionMap.put(item.course, temp);
                    temp.put(sequance, 1);
                }

                int day = daySequanceInWeek(item.time);
                if (teacherTimeDistribution.containsKey(item.teacher)) {
                    tempTeacher = teacherTimeDistribution.get(item.teacher);
                    if (tempTeacher.containsKey(day)) {
                        tempTeacher.put(day, tempTeacher.get(day) + 1);
                    } else {
                        tempTeacher.put(day, 1);
                    }
                } else {
                    tempTeacher = new TreeMap<>();
                    tempTeacher.put(day, 1);
                    teacherTimeDistribution.put(item.teacher, tempTeacher);
                }

                teacherView = findTeacher(item.teacher);
                if (teacherView == null) {
                    teacherView = new TeacherView(item.teacher);
                    CourseView courseView = new CourseView(item.course);
                    courseView.put(getClassName(item.grade, item.clazz), item);
                    teacherView.courseViewMap.put(courseView.course, courseView);
                    teacherViews.add(teacherView);
                } else if (teacherView.courseViewMap.containsKey(item.course)) {
                    teacherView.courseViewMap.get(item.course).put(getClassName(item.grade,
                            item.clazz), item);
                } else {
                    CourseView courseView = new CourseView(item.course);
                    teacherView.courseViewMap.put(courseView.course, courseView);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void computeTeachingProgress() {

        System.out.println("老师数 : " + teacherViews.size());
        Map<String, String> unConsistentMap = new HashMap<>();
        for (TeacherView item : teacherViews) {
            for (Map.Entry<String, CourseView> entry : item.courseViewMap.entrySet()) {
                CourseView courseView = entry.getValue();
                int classes = courseView.classCoursePlanMap.size();
                if (classes < 2) continue;

                int[][] timeArrs = new int[classes][];
                int i = 0;
                for (Map.Entry<String, List<CoursePlan>> courseViewItem :
                        courseView.classCoursePlanMap.entrySet()) {
                    List<CoursePlan> value = courseViewItem.getValue();
                    if (value == null) {
                        System.out.println("value == null : " + item.teacher + "," +
                                courseViewItem.getKey());
                        continue;
                    }
                    //如果某个班级只有一门课 则不参与比较
                    if (value.size() < 2) {
                        continue;
                    }
                    timeArrs[i] = new int[value.size()];
                    for (int j = 0; j < value.size(); j++) {
                        timeArrs[i][j] = value.get(j).time;
                    }
                    i++;
                }
                if (!isConsistent(timeArrs)) {
                    unConsistentMap.put(item.teacher, entry.getKey());
                    break;
                }
            }
        }
        System.out.println("------------教案平齐----------------");
        System.out.println("不满足严格的教案平齐的老师人数 : " + unConsistentMap.size());
        int i = 0;
        for (Map.Entry<String, String> entry : unConsistentMap.entrySet()) {
            if (i % 5 == 0) System.out.println();
            System.out.print(entry.getKey() + "--" + entry.getValue() + "   ");
            i++;
        }
        System.out.println();
    }

    private boolean isConsistent(int[][] timeArrs) {
        int len = timeArrs.length;
        int notNull = 0;
        for (int i = 0; i < len; i++) {
            if (timeArrs[i] != null) notNull++;
        }
        if (notNull < 2) return true;
        int cols = 0;
        for (int i = 0; i < len; i++) {
            if (timeArrs[i] == null) continue;
            if (cols < timeArrs[i].length) cols = timeArrs[i].length;
        }
        int min, max;
        for (int col = 0; col < cols; col++) {
            min = getMin(timeArrs, col + 1);
            max = getMax(timeArrs, col);
            if (min == Integer.MAX_VALUE) {
                continue;
            }
            if (max > min) {
                return false;
            }
        }
        return true;
    }

    private int getMax(int[][] arr, int col) {
        int max = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && col < arr[i].length) {
                max = max < arr[i][col] ? arr[i][col] : max;
            }
        }
        return max;
    }

    private int getMin(int[][] arr, int col) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && col < arr[i].length) {
                min = min > arr[i][col] ? arr[i][col] : min;
            }
        }
        return min;
    }

    private String getClassName(String grade, String clazz) {
        return grade + clazz;
    }

    private TeacherView findTeacher(String teacher) {
        for (TeacherView item : teacherViews) {
            if (item.teacher.equals(teacher)) return item;
        }
        return null;
    }

    // [1,lessonsPerDay]
    private int lessonSequanceInDay(int time) {
        return time % lessonsPerDay + 1;
    }

    private int daySequanceInWeek(int time) {
        return time / lessonsPerDay + 1;
    }

    public void computeCourseDistribution() {
        System.out.println();
        System.out.println("------------科目课时分布----------------");
        System.out.println("course number : " + courseDistributionMap.size());
        int sum = 0;
        for (Map.Entry<String, Map<Integer, Integer>> entry : courseDistributionMap.entrySet()) {
            int amNum = 0, pmNum = 0;
            System.out.println("----------------------------course name : " + entry.getKey());
            int total = 0;
            for (Map.Entry<Integer, Integer> item : entry.getValue().entrySet()) {
                total += item.getValue();
                if (item.getKey() <= am) {
                    amNum += item.getValue();
                } else {
                    pmNum += item.getValue();
                }
                System.out.print("第" + item.getKey() + "节 : " + item.getValue() + "    ");
            }
            System.out.println();
            System.out.print("此科目的总课时数 : " + total + "     ");
            System.out.print("上午课时数 :" + amNum + "     ");
            System.out.print("下午课时数 :" + pmNum + "     ");
            System.out.println();
            sum += total;
        }

        assert sum == coursePlanList.size();
    }


    public void computeTeacherCourseDistribution() {

        System.out.println("------------------老师课时分布------------");

        for (Map.Entry<String, Map<Integer, Integer>> entry : teacherTimeDistribution.entrySet()) {
            System.out.println("-----------teacher Name : " + entry.getKey());
            int total = 0;
            for (Map.Entry<Integer, Integer> item : entry.getValue().entrySet()) {
                total += item.getValue();
                System.out.print("星期 " + item.getKey() + " :" + item.getValue() + "     ");
            }
            System.out.println();
            System.out.println("此老师的总课时数 : " + total);
        }
    }

    public static void main(String[] args) {
        String fileName = "cp.json";
        ResultHandler resultHandler = new ResultHandler(fileName, 5, 7, 4, 3);
        resultHandler.computeTeachingProgress();
        resultHandler.computeCourseDistribution();
        resultHandler.computeTeacherCourseDistribution();
    }
}



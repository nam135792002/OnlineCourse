package com.springboot.courses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Student {
    private String name;


    public static void main(String[] args) {
        Student a = new Student();
        Student b = a;
        a.name = "An";
        b.name = "Binh";

        System.out.println(a.name);
        System.out.println(b.name);

    }

//    // em viết hàm này bằng ngôn ngữ java.
//    public List<Course> fromTheirCourses(List<TheirCourse> theirCourses) {
//        List<Course> listCourse = new ArrayList<>();
//
//        // duyệt theirCourse cha, khi đó parent_id = null.
//        for (TheirCourse theirCourse : theirCourses){
//            // lưu tất cả các course, bởi vì trong theirCourse có list children.
//            listCourse.addAll(addAllCourse(theirCourse, null));
//
//        }
//
//        return listCourse;
//    }
//
//    // Hàm đệ quy để chuyển đổi từng đối tượng TheirCourse thành danh sách các Course
//    private List<Course> addAllCourse(TheirCourse theirCourse, long ParentId ) {
//        List<Course> courses = new ArrayList<>();
//
//        // Tạo một đối tượng Course từ đối tượng TheirCourse hiện tại
//        Course course = new Course(theirCourse.id, theirCourse.Name, ParentId);
//        courses.add(course);
//
//        // Nếu danh sách con của theirCourse không rỗng, tiếp tục đệ quy để chuyển đổi các con
//        for (TheirCourse their : theirCourse.Children){
//            // gọi lại đệ quy để duyệt tất cả tập con và lấy parentid là của theirCourse hiện tại
//            courses.addAll(addAllCourse(their, theirCourse.id));
//        }
//
//        return courses;
//    }


}

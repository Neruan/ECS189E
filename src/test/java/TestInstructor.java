import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mimsuk on 3/7/17.
 */
public class TestInstructor {
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    @Test   //Instructor is not assigned to class for homework
    public void case0() {   //Bug#5
        this.admin.createClass("Test", 2017, "Teach", 15);
        this.instructor.addHomework("Teacher","Test", 2017, "HW1","Descrip");
        assertTrue(this.instructor.homeworkExists("Test", 2017, "HW1"));
    }

    @Test   //Homework is added to wrong class
    public void case1() {
        this.admin.createClass("Test", 2017, "Teach", 15);
        this.instructor.addHomework("Teach","Tester", 2017, "HW1","Descrip");
        assertTrue(this.instructor.homeworkExists("Tester", 2017, "HW1"));
    }

    @Test   //Homework is graded by wrong instructor
    public void case2() {   //Bug#6
        this.admin.createClass("Test", 2017, "Teach", 15);
        this.student.registerForClass("Stu1","Test",2017);
        this.student.registerForClass("Stu2","Test",2017);
        this.instructor.addHomework("Teach","Test", 2017, "HW1", "Do it");
        this.student.submitHomework("Stu1", "HW1","Ans", "Test", 2017);
        this.instructor.assignGrade("Teacher", "Test", 2017, "HW1", "Stu1",100);
        assertTrue(this.instructor.getGrade("Test", 2017,"HW1","Stu1") == 100);
    }

    @Test   //Homework is graded but not added
    public void case3() {
        this.admin.createClass("Test", 2017, "Teach", 15);
        this.student.registerForClass("Stu1","Test",2017);
        this.student.registerForClass("Stu2","Test",2017);
        this.student.submitHomework("Stu1", "HW1","Ans", "Test", 2017);
        this.instructor.assignGrade("Teach", "Test", 2017, "HW1", "Stu1",100);
        assertTrue(this.instructor.getGrade("Test", 2017,"HW1","Stu1") == 100);
    }

    @Test   //Homework is added but not submitted
    public void case4() {
        this.admin.createClass("Test", 2017, "Teach", 15);
        this.student.registerForClass("Stu1","Test",2017);
        this.student.registerForClass("Stu2","Test",2017);
        this.instructor.addHomework("Teach","Test", 2017, "HW1", "Do it");
        this.instructor.assignGrade("Teach", "Test", 2017, "HW1", "Stu1",100);
        assertTrue(this.instructor.getGrade("Test", 2017,"HW1","Stu1") == 100);
    }

    @Test   //Homework is not added and not submitted
    public void case5() {
        this.admin.createClass("Test", 2017, "Teach", 15);
        this.student.registerForClass("Stu1","Test",2017);
        this.student.registerForClass("Stu2","Test",2017);
        assertTrue(this.instructor.getGrade("Test", 2017,"HW1","Stu1") == 100);
    }

}

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
public class TestStudent {
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    @Test //Register for class that does not exist
    public void case0() {
        this.admin.createClass("Test", 2017, "Teach", 1);
        this.student.registerForClass("Stu1","Tester",2017);
        assertFalse(this.student.isRegisteredFor("Stu1", "Test", 2017));
    }

    @Test //Enrollment capacity met
    public void case1() {   //Bug#8
        this.admin.createClass("Test", 2017, "Teach", 1);
        this.student.registerForClass("Stu1","Test",2017);
        this.student.registerForClass("Stu2","Test",2017);
        assertFalse(this.student.isRegisteredFor("Stu2", "Test", 2017));
    }

    @Test //Student is not registered for class after dropping
    public void case2() {
        this.admin.createClass("Test", 2017, "Teach", 1);
        this.student.registerForClass("Stu1","Test",2017);
        this.student.dropClass("Stu1", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("Stu1", "Test", 2017));
    }

    @Test //Student submits homework but does not exist
    public void case3() {
        this.admin.createClass("Test", 2017, "Teach", 1);
        this.student.registerForClass("Stu1","Test1",2017);
        this.student.submitHomework("Stu1","HW1", "Ans","Test",2017);
        assertFalse(this.student.hasSubmitted("Stu1","HW1","Test",2017));
    }

    @Test //Student submits homework but is not registered
    public void case4() {   //Bug#9
        this.admin.createClass("Test", 2017, "Teach", 1);
        this.instructor.addHomework("Teach","Test",2017,"HW1","Descrip");
        this.student.submitHomework("Stu1","HW1", "Ans","Test",2017);
        assertFalse(this.student.hasSubmitted("Stu1","HW1","Test",2017));
    }
}


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
public class TestAdmin {

    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }

    @Test
    public void case0() {
        this.admin.createClass("Test", 2017, "Teach", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test   //Year cannot not be in the past
    public void case1() {   //Bug#0
        this.admin.createClass("Test", 2016, "Teach", 10);
        assertTrue(this.admin.classExists("Test", 2016));
    }

    @Test   //max Capcity must be greater than 0
    public void case2() {   //Bug#1
        this.admin.createClass("Test", 2017, "Teach", 0);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test   //Changing capcity must be equal to at least the amount of students registered
    public void case3() {   //Bug#2
        this.admin.createClass("Test", 2017, "Teach", 3);
        this.student.registerForClass("Stu1", "Teach",2017);
        this.student.registerForClass("Stu2","Teach", 2017);
        this.student.registerForClass("Stu3","Teach",2017);
        this.admin.changeCapacity("Test",2017,2);
        assertTrue(this.admin.getClassCapacity("Test",2017) == 2);
    }

    @Test   //Instructor assigned to more than 2 courses a year
    public void case4() {   //Bug#3
        this.admin.createClass("Test1", 2017, "Teach", 15);
        this.admin.createClass("Test2", 2017, "Teach", 15);
        this.admin.createClass("Test3", 2017, "Teach", 15);
        boolean test1,test2,test3;
        test1 = this.admin.classExists("Test1",2017);
        test2 = this.admin.classExists("Test2", 2017);
        test3 = this.admin.classExists("Test3",2017);
        assertTrue(test1 && test2 && test3);
    }

    @Test   //Instructor assigned to more than 2 courses a year
    public void case5() {   //Bug#4
        this.admin.createClass("Test", 2017, "Teach1", 15);
        System.out.print(this.admin.getClassInstructor("Test",2017 ));
        this.admin.createClass("Test", 2017, "Teach2", 15);
        System.out.print(this.admin.getClassInstructor("Test",2017 ));
        assertTrue(this.admin.getClassInstructor("Test",2017 ) != "Teach1" || this.admin.getClassInstructor("Test",2017 ) != "Teach2");
    }
}
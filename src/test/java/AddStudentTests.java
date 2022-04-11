import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import static org.junit.Assert.*;


public class AddStudentTests {

    private String validStudentIdNr = "1000", invalidStudentIdNr = null;
    private String validStundentName = "Raul", invalidStudentName = "";
    private int validGroup = 934, invalidGroup = 0;

    private Validator<Student> studentValidator = null;
    private Validator<Tema> temaValidator = null;
    private Validator<Nota> notaValidator = null;
    private StudentXMLRepository studentXMLRepository = null;
    private TemaXMLRepository temaXMLRepository = null;
    private NotaXMLRepository notaXMLRepository = null;

    private Service service = null;


    @Before
    public void doBeforeEach() {
        this.studentValidator = new StudentValidator();
        this.temaValidator = new TemaValidator();
        this.notaValidator = new NotaValidator();

        this.studentXMLRepository = new StudentXMLRepository(studentValidator, "studenti.xml");
        this.temaXMLRepository = new TemaXMLRepository(temaValidator, "teme.xml");
        this.notaXMLRepository = new NotaXMLRepository(notaValidator, "note.xml");

        this.service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);
    }

    @Test
    public void addStudent_ValidData_Test() {
        Student addedStudent = new Student(validStudentIdNr, validStundentName, validGroup);
        this.service.saveStudent(validStudentIdNr, validStundentName, validGroup);
        Iterable<Student> students = this.service.findAllStudents();
        boolean studentWasAdded = false;
        for (Student s : students)
            if (s.equals(addedStudent)) {
                studentWasAdded = true;
            }
        Assert.assertTrue(studentWasAdded);
        this.service.deleteStudent(validStudentIdNr);
    }

    @Test
    public void addStudent_InvalidGroupNr_Test() {
        try {
            this.service.saveStudent(validStudentIdNr, validStundentName, invalidGroup);
            assertTrue("Invalid group exception should get thrown!", false);
        } catch (Exception e) {
            assertEquals("Grupa invalida! \n", e.getMessage());
        }
    }

    @Test
    public void addStudent_Group_GroupNumberIs_110() {
        try {
            this.service.saveStudent(validStudentIdNr, validStundentName, 110);
            assertTrue("Invalid group exception should get thrown!", false);
        } catch (Exception e) {
            assertEquals("Grupa invalida! \n", e.getMessage());
        }
    }

    @Test
    public void addStudent_Group_GroupNumberIs_111() {
        Student addedStudent = new Student(validStudentIdNr, validStundentName, 111);
        this.service.saveStudent(validStudentIdNr, validStundentName, 111);
        Iterable<Student> students = this.service.findAllStudents();
        boolean studentWasAdded = false;
        for (Student s : students)
            if (s.equals(addedStudent)) {
                studentWasAdded = true;
            }
        Assert.assertTrue(studentWasAdded);
        this.service.deleteStudent(validStudentIdNr);
    }

    @Test
    public void addStudent_Group_GroupNumberIs_112() {
        Student addedStudent = new Student(validStudentIdNr, validStundentName, 112);
        this.service.saveStudent(validStudentIdNr, validStundentName, 112);
        Iterable<Student> students = this.service.findAllStudents();
        boolean studentWasAdded = false;
        for (Student s : students)
            if (s.equals(addedStudent)) {
                studentWasAdded = true;
            }
        Assert.assertTrue(studentWasAdded);
        this.service.deleteStudent(validStudentIdNr);
    }

    @Test
    public void addStudent_Group_GroupNumberIs_936() {
        Student addedStudent = new Student(validStudentIdNr, validStundentName, 936);
        this.service.saveStudent(validStudentIdNr, validStundentName, 936);
        Iterable<Student> students = this.service.findAllStudents();
        boolean studentWasAdded = false;
        for (Student s : students)
            if (s.equals(addedStudent)) {
                studentWasAdded = true;
            }
        Assert.assertTrue(studentWasAdded);
        this.service.deleteStudent(validStudentIdNr);
    }

    @Test
    public void addStudent_Group_GroupNumberIs_937() {
        Student addedStudent = new Student(validStudentIdNr, validStundentName, 937);
        this.service.saveStudent(validStudentIdNr, validStundentName, 937);
        Iterable<Student> students = this.service.findAllStudents();
        boolean studentWasAdded = false;
        for (Student s : students)
            if (s.equals(addedStudent)) {
                studentWasAdded = true;
            }
        Assert.assertTrue(studentWasAdded);
        this.service.deleteStudent(validStudentIdNr);
    }

    @Test
    public void addStudent_Group_GroupNumberIs_938() {
        try {
            this.service.saveStudent(validStudentIdNr, validStundentName, 938);
            assertTrue("Invalid group exception should get thrown!", false);
        } catch (Exception e) {
            assertEquals("Grupa invalida! \n", e.getMessage());
        }
    }

    @Test
    public void addStudent_Id_Null() {
        try {
            this.service.saveStudent(null, validStundentName, 938);
            assertTrue("Student add should not work with null id!", false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void addStudent_Name_Null() {
        try {
            this.service.saveStudent(validStudentIdNr, null, 938);
            assertTrue("Student add should not work with null name!", false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void addStudent_Id_Empty() {
        try {
            this.service.saveStudent("               ", validStundentName, validGroup);
            assertTrue("Invalid group exception should get thrown!", false);
        } catch (Exception e) {
            assertEquals("ID invalid! \n", e.getMessage());
        }
    }

    @Test
    public void addStudent_Name_Empty() {
        try {
            this.service.saveStudent(validStudentIdNr, "    ", validGroup);
            assertTrue("Invalid group exception should get thrown!", false);
        } catch (Exception e) {
            assertEquals("Nume invalid! \n", e.getMessage());
        }
    }

    @Test
    public void addStudent_SameStudentTwice() {
        this.service.saveStudent(validStudentIdNr, validStundentName, validGroup);
        try {
            this.service.saveStudent(validStudentIdNr, validStundentName, validGroup);
            assertTrue("Not unique students should not be added!", false);
        } catch (Exception e) {
            assertEquals("Entitate existenta!", e.getMessage());
        }
        this.service.deleteStudent(validStudentIdNr);
    }

}

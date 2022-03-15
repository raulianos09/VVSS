import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.AfterClass;
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
        this.service.saveStudent(validStudentIdNr,validStundentName,validGroup);
        Iterable<Student> students = this.service.findAllStudents();
        boolean studentWasAdded = false;
        for(Student s : students)
            if(s.equals(addedStudent))
            {
                studentWasAdded = true;
            }
        Assert.assertTrue(studentWasAdded);
        this.service.deleteStudent(validStudentIdNr);
    }

    @Test
    public void addStudent_InvalidGroupNr_Test() {
        Student addedStudent = new Student(validStudentIdNr, validStundentName, invalidGroup);
        this.service.saveStudent(validStudentIdNr,validStundentName,invalidGroup);
        Iterable<Student> students = this.service.findAllStudents();
        boolean studentWasAdded = false;
        for(Student s : students)
            if(s.equals(addedStudent))
            {
                studentWasAdded = true;
            }
        Assert.assertFalse(studentWasAdded);
    }

}

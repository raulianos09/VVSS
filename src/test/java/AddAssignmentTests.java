import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddAssignmentTests {

    private String validAssignmentId = "testId", validAssignmentDescription = "validDescription";
    private String invalidAssignmentId = null, invalidAssignmentDescription = null;
    private int validAssignmentStartWeek = 2, validAssignmentEndWeek = 4;
    private int invalidAssignmentStartWeek = 132465, invalidAssignmentEndWeek = 123456;

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
    public void addAssignment_nullAssignmentID(){
        try {
            service.saveTema(null,validAssignmentDescription,validAssignmentStartWeek,validAssignmentEndWeek);
            assertTrue("Assignment addition with null id should not be possible",false);
        }
        catch (Exception e) {
            assertEquals("ID invalid! \n",e.getMessage());
        }
    }

    @Test
    public void addAssignment_nullDescription()
    {
        try {
            service.saveTema(validAssignmentId,null,validAssignmentStartWeek,validAssignmentEndWeek);
            assertTrue("Assignment addition with null id should not be possible",false);
        }
        catch (Exception e) {
            assertEquals("Descriere invalida! \n",e.getMessage());
        }
    }

}

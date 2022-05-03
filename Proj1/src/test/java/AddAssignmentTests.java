import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Assert;
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

import static org.junit.Assert.*;

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
            service.saveTema(null,validAssignmentDescription,validAssignmentEndWeek,validAssignmentStartWeek);
            fail("Assignment addition with null id should not be possible");
        }
        catch (Exception e) {
            assertEquals("ID invalid! \n",e.getMessage());
        }
    }

    @Test
    public void addAssignment_emptyAssignmentID(){
        try {
            service.saveTema("",validAssignmentDescription,validAssignmentEndWeek,validAssignmentStartWeek);
            fail("Assignment addition with empty id should not be possible");
        }
        catch (Exception e) {
            assertEquals("ID invalid! \n",e.getMessage());
        }
    }

    @Test
    public void addAssignment_nullDescription()
    {
        try {
            service.saveTema(validAssignmentId,null,validAssignmentEndWeek,validAssignmentStartWeek);
            fail("Assignment addition with null id should not be possible");
        }
        catch (Exception e) {
            assertEquals("Descriere invalida! \n",e.getMessage());
        }
    }

    @Test
    public void addAssignment_emptyDescription()
    {
        try {
            service.saveTema(validAssignmentId,"",validAssignmentEndWeek,validAssignmentStartWeek);
            fail("Assignment addition with empty description should not be possible");
        }
        catch (Exception e) {
            assertEquals("Descriere invalida! \n",e.getMessage());
        }
    }

    @Test
    public void addAssignment_StartWeekNegative(){
        try {
            service.saveTema(validAssignmentId,validAssignmentDescription,validAssignmentEndWeek,-35);
            fail("Assignment addition with negative start week should not be possible");
        }
        catch (Exception e) {
            assertEquals("Data de primire invalida! \n",e.getMessage());
        }
    }

    @Test
    public void addAssignment_StartWeekGreatNumber(){
        try {
            service.saveTema(validAssignmentId,validAssignmentDescription,validAssignmentEndWeek,10000000);
            fail("Assignment addition with negative end week should not be possible");
        }
        catch (Exception e) {
            assertEquals("Data de primire invalida! \n",e.getMessage());
        }
    }

    @Test
    public void addAssignment_EndWeekNegative(){
        try {
            service.saveTema(validAssignmentId,validAssignmentDescription,-35,validAssignmentStartWeek);
            fail("Assignment addition with negative end week should not be possible");
        }
        catch (Exception e) {
            assertEquals("Deadline invalid! \n",e.getMessage());
        }
    }

    @Test
    public void addAssignment_EndWeekGreatNumber(){
        try {
            service.saveTema(validAssignmentId,validAssignmentDescription,10000,validAssignmentStartWeek);
            fail("Assignment addition with negative end week should not be possible");
        }
        catch (Exception e) {
            assertEquals("Deadline invalid! \n",e.getMessage());
        }
    }

    @Test
    public void addAssignment_ValidData_StartWeekGreaterThanEndWeek(){
        try {
            service.saveTema(validAssignmentId,validAssignmentDescription,2,4);
            fail("Assignment addition with negative end week should not be possible");
        }
        catch (Exception e) {
            assertEquals("Data de predare este mai mica decat data de primire! \n",e.getMessage());
        }
    }

    @Test
    public void addAssignment_ValidData(){
        Tema addedTema = new Tema(validAssignmentId,validAssignmentDescription,validAssignmentEndWeek,validAssignmentStartWeek);
        this.service.saveTema(validAssignmentId,validAssignmentDescription,validAssignmentEndWeek,validAssignmentStartWeek);
        Iterable<Tema> teme = this.service.findAllTeme();
        boolean temaWasAdded = false;
        for (Tema t : teme)
            if (t.equals(addedTema)) {
                temaWasAdded = true;
            }
        Assert.assertTrue(temaWasAdded);
        this.service.deleteTema(validAssignmentId);
    }

    @Test
    public void addAssignment_SameAssignmentTwice() {
        this.service.saveTema(validAssignmentId,validAssignmentDescription,validAssignmentEndWeek,validAssignmentStartWeek);
        try {
            this.service.saveTema(validAssignmentId,validAssignmentDescription,validAssignmentEndWeek,validAssignmentStartWeek);
            assertTrue("Not unique assignments should not be added!", false);
        } catch (Exception e) {
            assertEquals("Entitate existenta!", e.getMessage());
        }
        this.service.deleteTema(validAssignmentId);
    }
}

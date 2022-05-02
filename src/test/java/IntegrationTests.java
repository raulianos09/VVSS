import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.After;
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

import java.io.PrintWriter;

import static org.junit.Assert.*;


public class IntegrationTests {

    private String validStudentId = "id125";
    private String validTemaId = "id458";
    private Student validStudent = new Student(validStudentId, "Valid student", 934);
    private String invalidStudentId = "invalid";
    private Tema validTema = new Tema(validTemaId, "Tema valida", 10, 2);
    private Nota invalidNota = new Nota(new Pair<>(invalidStudentId, validTemaId), 9, 5, "well done");

    private Validator<Student> studentValidator = null;
    private Validator<Tema> temaValidator = null;
    private Validator<Nota> notaValidator = null;
    private StudentXMLRepository studentXMLRepository = null;
    private TemaXMLRepository temaXMLRepository = null;
    private NotaXMLRepository notaXMLRepository = null;

    private Service service = null;

    private String filenameStudent = "studenti.xml";
    private String filenameAssignment = "teme.xml";
    private String filenameGrade = "note.xml";


    @Before
    public void doBeforeEach() {
        this.studentValidator = new StudentValidator();
        this.temaValidator = new TemaValidator();
        this.notaValidator = new NotaValidator();

        this.studentXMLRepository = new StudentXMLRepository(studentValidator, filenameStudent);
        this.temaXMLRepository = new TemaXMLRepository(temaValidator, filenameAssignment);
        this.notaXMLRepository = new NotaXMLRepository(notaValidator, filenameGrade);

        this.service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);
    }

    @After
    public void doAfterEach() {
        try {
            this.service.deleteStudent(validStudentId);
            this.service.deleteTema(validTemaId);
        } catch (Exception e) {}
    }

    private void addStudent() {
        this.service.saveStudent(validStudent.getID(), validStudent.getNume(), validStudent.getGrupa());
        Iterable<Student> students = this.service.findAllStudents();
        boolean studentWasAdded = false;
        for (Student s : students)
            if (s.equals(this.validStudent)) {
                studentWasAdded = true;
            }
        Assert.assertTrue(studentWasAdded);
    }

    private void addAssignment() {
        this.service.saveTema(validTema.getID(), validTema.getDescriere(), validTema.getDeadline(), validTema.getStartline());
        Iterable<Tema> assignments = this.service.findAllTeme();
        boolean assignmentWasAdded = false;
        for (Tema a: assignments)
            if (a.equals(this.validTema)) {
                assignmentWasAdded = true;
            }
        Assert.assertTrue(assignmentWasAdded);
    }

    private void addGrade() {
        int actual = this.service.saveNota(this.invalidStudentId, this.validTemaId, this.invalidNota.getNota(),
                this.invalidNota.getSaptamanaPredare(), this.invalidNota.getFeedback());
        assertEquals(-1, actual);
    }

    @Test
    public void addStudentIntegration() {
        this.addStudent();
    }

    @Test
    public void addAssignmentIntegration() {
        this.addStudent();
        this.addAssignment();
    }

    @Test
    public void addGradeIntegration() {
        this.addStudent();
        this.addAssignment();
        this.addGrade();
    }

}

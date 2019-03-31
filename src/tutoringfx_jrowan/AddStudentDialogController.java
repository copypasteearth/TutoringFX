package tutoringfx_jrowan;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.ORM;
import models.Student;

/**
 * FXML Controller class
 *
 * @author John Rowan JR897843@wcupa.edu
 * description: this class AddStudentDialogController is for the pop up dialog for adding a student
 *              to the database of students. it contains a member to the main GUI's controller
 *              and also methods to interact with the add student GUI
 */
public class AddStudentDialogController implements Initializable {
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    private FXMLDocumentController controller;
    /**
     * 
     * @param control 
     * description:  this method is used to pass an instance of the Main GUI Controller class
     *               into this class to be used when the user successfully adds a new Student
     */
    public void setMainController(FXMLDocumentController control){
        controller = control;
    }
    /**
     * 
     * @param event 
     * description: this method is used when the the user presses the cancel button on the GUI
     *              and it closes the popup dialog.
     */
    @FXML
    public void onCancelButtonClicked(Event event){
        ((Button)event.getSource()).getScene().getWindow().hide();
    }
    /**
     * 
     * @param event
     * description: this method is used when the user clicks on the add button of the GUI
     *              it validates all of the information entered and then adds the student to the
     *              database as well as opens an alert dialog if the information is not valid.
     */
    @FXML
    public void onAddButtonClicked(Event event){
        try{
            String first = firstName.getText().trim();
        String last = lastName.getText().trim();
        if(first.equals("") || last.equals("")){
            throw new ExpectedException("feilds must be filled in!!");
        }
        String fullName = last + "," + first;
        Student student = ORM.findOne(Student.class, "where name=?", new Object[]{fullName});
        if(student != null){
            throw new ExpectedException("Student already exists!");
        }
        Student newStudent = new Student(fullName,currentDate());
        ORM.store(newStudent);
        controller.finishAddingStudent(newStudent);
        ((Button)event.getSource()).getScene().getWindow().hide();
        }catch(ExpectedException ex){
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setContentText(ex.getMessage());
      alert.show();
        }
        
    }
    /**
     * 
     * @return Date
     * description: this is a helper method that returns the current date that is used
     *              to get a current date for the student when a student is first created.
     */
    public java.sql.Date currentDate() {
    long now = new java.util.Date().getTime();
    java.sql.Date date = new java.sql.Date(now);
    return date;
    }
    /**
     * Initializes the controller class.
     * description: not used but is for initializing the GUI elements at first launch.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}

package tutoringfx_jrowan;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.ORM;
import models.Subject;

/**
 * FXML Controller class
 *
 * @author John Rowan JR897843@wcupa.edu
 * description: this is a Controller Class for the popup dialog GUI that is used for adding a subject to the
 *              database.
 */
public class AddSubjectController implements Initializable {
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    private FXMLDocumentController controller;
    private Collection<Subject> subjectCollection;
    /**
     * 
     * @param control
     * description: this method sets the member of the main controller class for the main GUI and is 
     *              used after the subject is added to the database.
     */
    public void setMainController(FXMLDocumentController control){
        controller = control;
    }
    /**
     * 
     * @param event 
     * description: this method is called when the user clicks on the Add button of the pop up dialog
     *              it first validates that the subject is new and unique and validates that the 
     *              subject input is not empty then it adds the subject to the database and closes the pop up
     */
    @FXML
    public void onAddButtonClicked(Event event){
        try{
            String subject = textField.getText().trim();
        if(subject.equals("")){
            throw new ExpectedException("You must specify a subject name!");
        }
        for(Subject sub : subjectCollection){
            if(sub.getName().equals(subject)){
                throw new ExpectedException("The subject already exists!");
            }
        }
        Subject newSubject = new Subject(subject);
        ORM.store(newSubject);
        controller.finishAddingSubject();
        ((Button)event.getSource()).getScene().getWindow().hide();
        }catch(ExpectedException ex){
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setContentText(ex.getMessage());
      alert.show();
        }
        
    }
    /**
     * 
     * @param event 
     * description: this is a method for when the user clicks on the Cancel button
     *              and then it closes the pop up dialog
     */
    @FXML
    public void onCancelButtonClicked(Event event){
        ((Button)event.getSource()).getScene().getWindow().hide();
    }
    /**
     * Initializes the controller class.
     * description: this initializes the pop up GUI for this controller class to add a subject
     *              it queries the database for all of the subjects and adds them to a text area
     *              the subjectCollection is also used for validation in the add button click method.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       subjectCollection = ORM.findAll(Subject.class);
       for(Subject sub : subjectCollection){
           textArea.appendText(sub.getName() + "\n");
       }
    }    
    
}

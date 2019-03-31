package tutoringfx_jrowan;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.ORM;
import models.Subject;
import models.Tutor;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * FXML Controller class
 *
 * @author John Rowan JR897843@wcupa.edu
 * description: this class is a controller for the pop up GUI which is used for adding a Tutor to the database.
 */
public class AddTutorDialogController implements Initializable {
    @FXML
    private TextField lastName;
    @FXML
    private TextField firstName;
    @FXML
    private TextField emailTF;
    @FXML
    private ComboBox<String> subjects;
    
    private FXMLDocumentController controller;
    private Collection<Subject> subjectCollection;
    /**
     * 
     * @param control 
     * description: this method sets a member reference to the Main Controller and the member is used
     *              to make a call back to the Main Controller once a new Tutor is added.
     */
    public void setMainController(FXMLDocumentController control){
        controller = control;
    }
    /**
     * 
     * @param event
     * description: this method is called when the user clicks the Add button on the GUI
     *              it validates all of the input and throws an exception if any of the input is
     *              not valid opening an alert dialog, then it adds the new tutor to the database
     *              and calls back to the main GUI to process the event that it has added a tutor.
     */
    @FXML
    public void onAddButtonClicked(Event event){
        try{
            String last = lastName.getText().trim();
        String first = firstName.getText().trim();
        String email = emailTF.getText().trim();
        String sub = subjects.getValue();
        if(last.equals("") || first.equals("") || email.equals("")){
            throw new ExpectedException("all fields must be filled in!");
        }
        if(sub == null){
            throw new ExpectedException("A subject must be selected!");
        }
        EmailValidator validator = EmailValidator.getInstance();
        boolean isValid = validator.isValid(email);
        if(!isValid){
            throw new ExpectedException("The email address entered is not valid!");
        }
        
        String fullName = last + "," + first;
        Tutor tutor = ORM.findOne(Tutor.class, "where name=?", new Object[]{fullName});
        if(tutor != null){
            throw new ExpectedException("A tutor with this name already exists!");
        }
        int subId = -1;
        for(Subject subj:subjectCollection){
            if(subj.getName().equals(sub)){
                subId = subj.getId();
            }
        }
        Tutor newTutor = new Tutor(fullName,email,subId);
        ORM.store(newTutor);
        controller.finishAddingTutor(newTutor);
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
     * description: this method is called when the user clicks on the Cancel button
     *              it closes the pop up GUI for adding a tutor.
     */
    public void onCancelButtonClicked(Event event){
        ((Button)event.getSource()).getScene().getWindow().hide();
    }
    /**
     * Initializes the controller class.
     * description: this method initializes the Add Tutor GUI pop up first it gets all of the 
     *              subjects from the database and sets them to the combo box so the user
     *              can select a subject for the new tutor.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        subjectCollection = ORM.findAll(Subject.class);
        //subjects.getItems().addAll(subjectCollection);
        for(Subject sub:subjectCollection){
            subjects.getItems().add(sub.getName());
        }
    }    
    
}

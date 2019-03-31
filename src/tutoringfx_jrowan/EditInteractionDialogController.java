package tutoringfx_jrowan;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import models.Interaction;
import models.ORM;
import models.Student;
import models.Subject;
import models.Tutor;

/**
 * FXML Controller class
 *
 * @author John Rowan JR897843@wcupa.edu
 * description: this class is a controller class for the pop up GUI used for editing
 *              an interaction.
 */
public class EditInteractionDialogController implements Initializable {

    @FXML
    private Label tutorLabel;
    @FXML
    private Label studentLabel;
    @FXML
    private Label subjectLabel;
    @FXML
    private TextArea textArea;
    private Interaction interaction;
    private Subject subject;
    private Tutor tutor;
    private Student student;
    private FXMLDocumentController controller;
    private boolean editing = false;
    /**
     * 
     * @param control 
     * description: this method is used when the pop up first launches to set a reference to
     *              the main GUI controller so when the user is done editing the interaction
     *              this member will make a call to some finishing methods in the Main GUI.
     */
    public void setMainController(FXMLDocumentController control){
        controller = control;
    }
    /**
     * 
     * @param in
     * description: this method sets the currently selected Interaction from the Main GUI Controller class
     */
    public void setInteraction(Interaction in){
        interaction = in;
    }
    /**
     * 
     * @param sub 
     * description: this method sets the currently subject that the current tutor involved in the interaction
     *              teaches.
     */
    public void setSubject(Subject sub){
        subject = sub;
    }
    /**
     * 
     * @param tut 
     * description: this method sets the currently selected tutor from the Main GUI controller class
     */
    public void setTutor(Tutor tut){
        tutor = tut;
    }
    /**
     * 
     * @param stud
     * description: this method sets the currently selected student from the Main GUI controller class
     */
    public void setStudent(Student stud){
        student = stud;
    }
    /**
     * description: this method sets the views of this pop up GUI with the appropriate information
     */
    public void setViews(){
        tutorLabel.setText(tutor.getName());
        studentLabel.setText(student.getName());
        subjectLabel.setText(subject.getName());
        textArea.setText(interaction.getReport());
    }
    /**
     * 
     * @return boolean
     * description: this method is used when the user clicks the closing X button in the top right
     *              of the pop up and it lets the logic recognize if the user has actually edited anything
     *              in the text area.
     */
    public boolean isEdited(){
        return editing;
    }
    /**
     * 
     * @param event 
     * description: this method is called when the user clicks on the Save Changes button in the pop up dialog
     *              it sets the report to the edited text area updates the entry in the database and then calls 
     *              back to the main GUI's controller triggering the finished editing event to update the main GUI.
     */
    @FXML
    public void onSaveChangesButtonPressed(Event event){
        interaction.setReport(textArea.getText());
        ORM.store(interaction);
        controller.finishedEditingInteraction(interaction);
        ((Button)event.getSource()).getScene().getWindow().hide();
    }
    /**
     * 
     * @param event 
     * description: this method is called when the user clicks on the cancel button on the pop up GUI
     *              it checks if the text area has been edited and if so opens a confirmation dialog to 
     *              confirm canceling the changes.
     */
    @FXML
    public void onCancelButtonPressed(Event event){
        if(editing){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setContentText("Are you sure you want to exit this dialog and discard changes?");
          Optional<ButtonType> result = alert.showAndWait();
          if (result.get() == ButtonType.OK) {
           ((Button)event.getSource()).getScene().getWindow().hide();
          }
        }else{
            ((Button)event.getSource()).getScene().getWindow().hide();
        }
    }
    /**
     * 
     * @param event
     * description: this method is called when the user changes anything in the text area 
     *              and it sets the editing boolean to true for future events enabling confirmation
     *              of closing the dialog to discard changes.
     */
    @FXML
    public void onTextAreaEdited(Event event){
        editing = true;
    }
    /**
     * Initializes the controller class.
     * description: method for initializing the pop up which is not used.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}

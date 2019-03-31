package tutoringfx_jrowan;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.DBProps;
import models.Interaction;
import models.ORM;
import models.Student;
import models.Subject;
import models.Tutor;

/**
 *
 * @author John Rowan JR897843@wcupa.edu
 * description: FXMLDocumentController is the controller for the main GUI of the application
 *              there are various members and functions that are defined and linked to the GUI
 *              and used for various functions of the interface
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ListView<Student> studentListView;
    @FXML
    private ListView<Tutor> tutorListView;
    @FXML
    private TextArea textArea;
    @FXML
    private MenuItem viewInteraction;
    @FXML
    private MenuItem createInteraction;
    @FXML
    private MenuItem removeInteraction;
    @FXML
    private MenuItem editInteraction;
    @FXML
    private MenuItem addStudent;
    @FXML
    private Label clearMenu;
    @FXML
    private MenuItem removeStudent;
    private Collection<Student> studentCollection;
    private Collection<Tutor> tutorCollection;
    private Collection<Subject> subjectCollection;
    private Collection<Interaction> interactionCollection;
   
    private final Collection<Integer> tutorIds = new HashSet<>();
    private final Collection<Integer> studentIds = new HashSet<>();
    private Student currentStudent = null;
    private Tutor currentTutor = null;
    private Interaction currentInteraction = null;
    private boolean viewingInteraction = false;
    private boolean sortStudentsByDate = false;
    private Node lastFocused = null;
    /**
     * 
     * @param inter 
     * description: this method is called after editing and Interaction report and saving changes
     *              it refreshes the Interaction Collection from the database and puts the latest
     *              interaction report into the text area.
     */
    public void finishedEditingInteraction(Interaction inter){
        refreshInteractions();
        textArea.setText(inter.getReport());
    }
    /**
     * 
     * @param event
     * description: this method is called when the user clicks on Interaction->Edit Interaction
     *              menu item.  it loads the edit interaction dialog and passes in the currently selected
     *              Student Subject Interaction and Tutor. It listens for the closing action of the dialog and
     *              checks to see if there was any editing and makes the user confirm closing.
     */
    @FXML
    public void onEditInteratcionMenuClick(Event event){
         try{
             URL fxml = getClass().getResource("EditInteractionDialog.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(fxml);
      fxmlLoader.load();
       // get scene from loader
      Scene scene = new Scene(fxmlLoader.getRoot());
 
      // create a stage for the scene
      Stage dialogStage = new Stage();            
      dialogStage.setScene(scene);
 
      // specify dialog title
      dialogStage.setTitle("Editing Interaction");
 
      // make it block the application
      dialogStage.initModality(Modality.APPLICATION_MODAL);
 
      // invoke the dialog
      dialogStage.show();
      EditInteractionDialogController control = fxmlLoader.getController();
      control.setMainController(this);
      control.setInteraction(currentInteraction);
      control.setStudent(currentStudent);
      control.setTutor(currentTutor);
      for(Subject sub : subjectCollection){
          if(sub.getId() == currentTutor.getSubject_id()){
              control.setSubject(sub);
          }
      }
      control.setViews();
      dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event1) {
            if(control.isEdited()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setContentText("Are you sure you want to exit this dialog and discard changes?");
          Optional<ButtonType> result = alert.showAndWait();
          if (result.get() == ButtonType.OK) {
            //event1.consume();
          }else{
              event1.consume();
          }
            }else{
               //event1.consume();
            }
        }
      });
        }catch (IOException ex) {
      ex.printStackTrace(System.err);
      
    }
    }
    /**
     * 
     * @param event
     * description: this method is called when clicking on the Subject->add subject menu item
     *              it opens a dialog having to do with adding a new subject
     */
    @FXML
    public void onAddSubjectMenuClick(Event event){
        try{
             URL fxml = getClass().getResource("AddSubject.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(fxml);
      fxmlLoader.load();
       // get scene from loader
      Scene scene = new Scene(fxmlLoader.getRoot());
 
      // create a stage for the scene
      Stage dialogStage = new Stage();            
      dialogStage.setScene(scene);
 
      // specify dialog title
      dialogStage.setTitle("Add a Subject");
 
      // make it block the application
      dialogStage.initModality(Modality.APPLICATION_MODAL);
 
      // invoke the dialog
      dialogStage.show();
      AddSubjectController control = fxmlLoader.getController();
      control.setMainController(this);
        }catch (IOException ex) {
      ex.printStackTrace(System.err);
      
    }
    }
    /**
     * description: this method is called after adding a new Subject and it refreshes the 
     *              Subject collection.
     */
    public void finishAddingSubject(){
        refreshSubjects();
    }
    /**
     * 
     * @param event
     * description: this method is called when the user clicks on Tutor-> add tutor
     *              menu item. it opens up a new dialog that is used for adding a new tutor
     */
    @FXML
    public void onAddTutorMenuClick(Event event){
        try{
             URL fxml = getClass().getResource("AddTutorDialog.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(fxml);
      fxmlLoader.load();
       // get scene from loader
      Scene scene = new Scene(fxmlLoader.getRoot());
 
      // create a stage for the scene
      Stage dialogStage = new Stage();            
      dialogStage.setScene(scene);
 
      // specify dialog title
      dialogStage.setTitle("Add a Tutor");
 
      // make it block the application
      dialogStage.initModality(Modality.APPLICATION_MODAL);
 
      // invoke the dialog
      dialogStage.show();
      AddTutorDialogController control = fxmlLoader.getController();
      control.setMainController(this);
        }catch (IOException ex) {
      ex.printStackTrace(System.err);
      
    }
        
    }
    /**
     * 
     * @param tutor
     * description: this method is called after adding a tutor, it refreshes the tutor collection
     *              clears the student list highlighting and sets selection and focus to the new tutor
     */
    public void finishAddingTutor(Tutor tutor){
        refreshTutors();
        studentIds.clear();
        studentListView.refresh();
        tutorListView.getItems().clear();
        tutorListView.getItems().addAll(tutorCollection);
        tutorListView.getSelectionModel().select(tutor);
        tutorListView.scrollTo(tutor);
        tutorListView.requestFocus();
        updateTextArea(tutor);
        currentTutor = tutor;
    }
    /**
     * 
     * @param event
     * description: this method is called to validate the Student menu
     *              if a current student is selected it enables the remove student menu item
     */
    @FXML
    public void studentMenuValidation(Event event){
        if(currentStudent == null){
            removeStudent.setDisable(true);
            addStudent.setDisable(false);
            return;
        }
        removeStudent.setDisable(false);
        addStudent.setDisable(false);
    }
    /**
     * 
     * @param event
     * description: this method is called when the user selects Student->add Student
     *              menu item. it opens up the add student dialog and passes this controller
     *              to the addstudentdialog controller.
     */
    @FXML
    public void onAddStudentMenuClick(Event event){
        try{
             URL fxml = getClass().getResource("AddStudentDialog.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(fxml);
      fxmlLoader.load();
       // get scene from loader
      Scene scene = new Scene(fxmlLoader.getRoot());
 
      // create a stage for the scene
      Stage dialogStage = new Stage();            
      dialogStage.setScene(scene);
 
      // specify dialog title
      dialogStage.setTitle("Add a Student");
 
      // make it block the application
      dialogStage.initModality(Modality.APPLICATION_MODAL);
 
      // invoke the dialog
      dialogStage.show();
      AddStudentDialogController control = fxmlLoader.getController();
      control.setMainController(this);
        }catch (IOException ex) {
      ex.printStackTrace(System.err);
      
    }
       
    }
    /**
     * 
     * @param newStudent 
     * description: this method is called after adding a new Student to the database
     *              setting the new student as selected with focus and updates textarea
     */
    public void finishAddingStudent(Student newStudent){
        refreshStudents();
        tutorIds.clear();
        tutorListView.refresh();
        studentListView.getItems().clear();
        studentListView.getItems().addAll(studentCollection);
        studentListView.getSelectionModel().select(newStudent);
        studentListView.scrollTo(newStudent);
        studentListView.requestFocus();
        updateTextArea(newStudent);
        currentStudent = newStudent;
    }
    /**
     * 
     * @param event
     * description: this method is called when the user clicks on the Student->remove
     *              menu item. it prompts the user to make sure that the selected student should
     *              be deleted. first the method deletes all of the associated Interactions and then
     *              finally deletes the Student
     */
    @FXML
    public void onRemoveStudentMenuClick(Event event){
        Student student = studentListView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setContentText("Are you sure you want to remove the student " + student.getName()+" ?");
          Optional<ButtonType> result = alert.showAndWait();
          if (result.get() == ButtonType.OK) {
            for(Interaction inter : interactionCollection){
                if(inter.getStudent_id() == student.getId()){
                    ORM.remove(inter);
                }
            }
            ORM.remove(student);
            removeStudentWork();
          }else{
              event.consume();
          }
    }
    /**
     * description: this method is called after a student is removed from the system
     *              it refreshes the students and interaction collections and resets the
     *              tutor listview highlights
     */
    public void removeStudentWork(){
        refreshInteractions();
        refreshStudents();
        if(studentListView.isFocused()){
            textArea.clear();
        }
        tutorIds.clear();
        tutorListView.refresh();
        studentListView.getItems().clear();
        studentListView.getItems().addAll(studentCollection);
    }
    /**
     * 
     * @param event
     * description: this method is called when the user clicks on the Interaction->viewInteraction
     *              menu item. it gets the currently selected tutor and student and displays the interaction
     *              report that corresponds. it also sets the viewingInteraction boolean to true so that
     *              the meny validation for the Interaction Menu knows to allow editing the Interaction
     */
    @FXML
    public void onViewInteractionClicked(Event event){
        System.out.println("onViewinteractionClicked");
        viewingInteraction = true;
        Tutor tutor = tutorListView.getSelectionModel().getSelectedItem();
        Student student = studentListView.getSelectionModel().getSelectedItem();
        for(Interaction inter : interactionCollection){
            if(inter.getStudent_id() == student.getId() && inter.getTutor_id() == tutor.getId()){
                currentInteraction = inter;
                if(inter.getReport().equals("")){
                    textArea.setText("--- EMPTY ---");
                }else{
                    textArea.setText(inter.getReport());
                }
                textArea.requestFocus();
               
            }
        }
        
    }
    /**
     * 
     * @param event
     * description: this method is called when the user clicks on the Interaction->removeInteraction
     *              menu item. it removed the Interaction from the database and clears the textview if 
     *              the interaction is displayed.
     */
    @FXML
    public void onRemoveInteractionClicked(Event event){
        Tutor tutor = tutorListView.getSelectionModel().getSelectedItem();
        Student student = studentListView.getSelectionModel().getSelectedItem();
        Interaction in = null;
        for(Interaction inter : interactionCollection){
            if(inter.getStudent_id() == student.getId() && inter.getTutor_id() == tutor.getId()){
                in = inter;
            }
        }
        if(textArea.getText().equals("--- EMPTY ---") || textArea.getText().equals(in.getReport())){
            textArea.clear();
        }
        ORM.remove(in);
        refreshInteractions();
        tutorSelect(event);
        studentSelect(event);
    }
    /**
     * 
     * @param event
     * description: this method is called when the user clicks on the Interaction->createInteraction
     *              menu option. it creates a new Interaction with a blank report
     */
    @FXML
    public void onCreateInteractionClicked(Event event){
       
        System.out.println("createinteractionclicked");
        try{
            Tutor tutor = tutorListView.getSelectionModel().getSelectedItem();
        Student student = studentListView.getSelectionModel().getSelectedItem();
        int subjectId = tutor.getSubject_id();
        String subjectName = "";
        for(Subject sub : subjectCollection){
            if(sub.getId() == subjectId){
                subjectName = sub.getName();
            }
        }
        for(Interaction inter : interactionCollection){
            if(inter.getStudent_id() == student.getId()){
                for(Tutor tut : tutorCollection){
                    if(inter.getTutor_id() == tut.getId()){
                        if(tut.getSubject_id() == subjectId){
                           throw new ExpectedException(student.getName() + " already has " + subjectName + " tutor");
                        }
                    }
                }
            }
        }
        Interaction interaction = new Interaction(tutor.getId(),student.getId(),"");
        ORM.store(interaction);
        refreshInteractions();
        tutorSelect(event);
        studentSelect(event);
        }catch(ExpectedException ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setContentText(ex.getMessage());
      alert.show();
        }
         
    }
    /**
     * 
     * @param event
     * description: this method is called when the Interaction menu is initially clicked
     *              and validates all of the menu options enabling or disabling them accordingly
     */
    @FXML
    public void onInteractionMenuSelected(Event event){
        boolean interactionExists = false;
        Tutor tutor = tutorListView.getSelectionModel().getSelectedItem();
        Student student = studentListView.getSelectionModel().getSelectedItem();
        System.out.println(tutor +"  :  " + student);
        if(tutor == null || student == null){
            viewInteraction.setDisable(true);
            createInteraction.setDisable(true);
            removeInteraction.setDisable(true);
            editInteraction.setDisable(true);
            return;
        }
        for(Interaction inter : interactionCollection){
            if(inter.getStudent_id() == student.getId() && inter.getTutor_id() == tutor.getId()){
                viewInteraction.setDisable(false);
                createInteraction.setDisable(true);
                removeInteraction.setDisable(false);
                if(viewingInteraction)
                    editInteraction.setDisable(false);
                else
                    editInteraction.setDisable(true);
                return;
            }
        }
        
            viewInteraction.setDisable(true);
            createInteraction.setDisable(false);
            removeInteraction.setDisable(true);
            editInteraction.setDisable(true);
        
    }
    
    
 /**
  * description: this method is called to poll the database for all of the Interactions
  */   
 public void refreshInteractions(){
     interactionCollection = ORM.findAll(Interaction.class);
 }
 /**
  * description: this method is called to poll the database for all of the Students
  *              depending on which order is currently selected
  */
 public void refreshStudents(){
     if(sortStudentsByDate){
         studentCollection = ORM.findAll(Student.class,"order by enrolled");
     }else{
         studentCollection = ORM.findAll(Student.class,"order by name");
     }
     
 }
 /**
  * description: this method is called to poll the database for all Tutors
  */
 public void refreshTutors(){
     tutorCollection = ORM.findAll(Tutor.class,"order by name");
 }
 /**
  * description: this method is called to poll the database for all Subjects
  */
 public void refreshSubjects(){
     subjectCollection = ORM.findAll(Subject.class);
 }
 /**
  * 
  * @param lastFocused 
  * description: this method is called to set the last focused Node
  */
  void setLastFocused(Node lastFocused) {
    this.lastFocused = lastFocused;
  }
 
 
 /**
  * 
  * @param event 
  * description: this is called when the textarea is focused to put the focus back on either
  *              the studentListView or the tutorListView, whichever was focused last
  */
  @FXML
  private void refocus(Event event) {
    if (lastFocused != null) {
      lastFocused.requestFocus();
    }
  }
 /**
  * 
  * @param event
  * description: this method is called when the user selects a tutor from the tutorListView
  *              it also sets the current tutor and updates the textarea with the tutors
  *              information.  it also handles the studentListViews highlighting
  */
  @FXML
  private void tutorSelect(Event event) {
      viewingInteraction = false;
    Tutor tutor = tutorListView.getSelectionModel().getSelectedItem();
    currentTutor = tutor;
    updateTextArea(tutor);
    studentIds.clear();
    if (tutor == null) {
      refocus(event);
      return;
    }
 
    lastFocused = tutorListView;
  for (Interaction inter : interactionCollection) {
     if(inter.getTutor_id() == tutor.getId()){
         studentIds.add(inter.getStudent_id());
     }
  }
  studentListView.refresh();
    System.out.println("bookList selection: " + tutor);
  }
 /**
  * 
  * @param event 
  * description:  this method is called when the user clicks on the studentListView
  *               it sets up the highlights in the tutor listview and sets the current
  *               student as well as updates the textarea
  */
  @FXML
  private void studentSelect(Event event) {
      viewingInteraction = false;
    Student student = studentListView.getSelectionModel().getSelectedItem();
    currentStudent = student;
    updateTextArea(student);
    tutorIds.clear();
    if (student == null) {
      refocus(event);
      return;
    }
    lastFocused = studentListView;
     for (Interaction inter : interactionCollection) {
     if(inter.getStudent_id() == student.getId()){
         tutorIds.add(inter.getTutor_id());
     }
      
    }
    tutorListView.refresh();
    System.out.println("userList selection: " + student);
  }
  /**
   * 
   * @param student 
   * description: this method is used to update the textarea with a students information
   */
    public void updateTextArea(Student student){
        String str = "";
        str += "id: " + student.getId() + "\n";
        str += "name: " + student.getName() + "\n";
        str += "enrolled: " + student.getEnrolled().toString() + "\n";
        str += "tutored in: " + "[";
        int count = 0;
        for(Interaction inter : interactionCollection){
            if(inter.getStudent_id() == student.getId()){
                for(Tutor tutor : tutorCollection){
                    if(tutor.getId() == inter.getTutor_id()){
                        for(Subject sub : subjectCollection){
                            if(sub.getId() == tutor.getSubject_id()){
                                if(count == 0){
                                    str += sub.getName();
                                    count++;
                                }else{
                                     str += "," + sub.getName();
                                }
                               
                            }
                        }
                    }
                }
            }
        }
        str += "]";
        textArea.setText(str);
    }
    /**
     * 
     * @param tutor 
     * description: this method is used to update the textarea with a tutors information
     */
    public void updateTextArea(Tutor tutor){
        String str = "";
        str += "id: " + tutor.getId() + "\n";
        str += "name: " + tutor.getName() + "\n";
        str += "email: " + tutor.getEmail() + "\n";
        for(Subject sub : subjectCollection){
            if(tutor.getSubject_id() == sub.getId()){
                str += "subject: " + sub.getName();
            }
        }
        textArea.setText(str);
    }
    /**
     * 
     * @param event 
     * description: this method clears all of the focuses in the 2 lists
     *              and clears text in textarea when the edit->clear option is pressed
     */
    @FXML
    public void clear(Event event){
        tutorIds.clear();
        studentIds.clear();
        tutorListView.refresh();
        tutorListView.getSelectionModel().clearSelection();
        studentListView.refresh();
        studentListView.getSelectionModel().clearSelection();
        clearMenu.requestFocus();
        currentStudent = null;
        currentTutor = null;
        textArea.clear();
    }
    /**
     * 
     * @param event 
     * description: this method is called when the student order radio button is
     *              toggled resulting in sorting the Student list accordingly
     */
    @FXML
    public void onRadioButtonListener(Event event){
        //TODO retain selection focus and highlights
        String choice = ((RadioButton) event.getSource()).getText();
        if(choice.equals("Name")){
            sortStudentsByDate = false;
            studentCollection = ORM.findAll(Student.class,"order by name");
            studentListView.getItems().clear();
            studentListView.getItems().addAll(studentCollection);
            studentListView.getSelectionModel().select(currentStudent);
            studentListView.scrollTo(currentStudent);
            refocus(event);
        }else if(choice.equals("Date")){
            sortStudentsByDate = true;
            studentCollection = ORM.findAll(Student.class,"order by enrolled");
            studentListView.getItems().clear();
            studentListView.getItems().addAll(studentCollection);
            studentListView.getSelectionModel().select(currentStudent);
            studentListView.scrollTo(currentStudent);
            refocus(event);
        }
        
    }
    /**
     * 
     * @param location
     * @param resources
     * description: gets all of the different collections from the database
     *              and sets up the cell callbacks for highlighting as well
     *              as sets the listviews up.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       System.out.println("****initialize*****");
        ORM.init(DBProps.getProps());
        studentCollection = ORM.findAll(Student.class,"order by name");
        studentListView.getItems().addAll(studentCollection);
        StudentCellCallBack studentCellCallback = new StudentCellCallBack();
        studentListView.setCellFactory(studentCellCallback);
        studentCellCallback.setHightlightedIds(studentIds);
        tutorCollection = ORM.findAll(Tutor.class,"order by name");
        tutorListView.getItems().addAll(tutorCollection);
        TutorCellCallBack tutorCellCallback = new TutorCellCallBack();
        tutorListView.setCellFactory(tutorCellCallback);
        tutorCellCallback.setHightlightedIds(tutorIds);
        subjectCollection = ORM.findAll(Subject.class);
        for(Subject sub : subjectCollection){
            System.out.println(sub);
        }
        interactionCollection = ORM.findAll(Interaction.class);
        for(Interaction inter : interactionCollection){
            System.out.println(inter);
        }
    }
    
   
    
  
}

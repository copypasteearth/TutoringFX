package tutoringfx_jrowan;

import java.util.Collection;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.Student;

/**
 *
 * @author John Rowan JR897843@wcupa.edu
 * description: this class is a callback for cell rendering in the Student List
 */
public class StudentCellCallBack implements Callback<ListView<Student>, ListCell<Student>> {  
    private Collection<Integer> highlightedIds = null;
 /**
  * 
  * @param highlightedIds
  * description: this method sets the list of ids that should be highlighted
  */
  void setHightlightedIds(Collection<Integer> highlightedIds) {
    this.highlightedIds = highlightedIds;
  }
  /**
   * 
   * @param p
   * @return 
   * description: this method does all of the work on each individual cell when the 
   *              corresponding List View is refreshed it highlights certain students contained
   *              in the highlighted Id's list
   */
  @Override
  public ListCell<Student> call(ListView<Student> p) {
    ListCell<Student> cell = new ListCell<Student>() {
      @Override
      protected void updateItem(Student student, boolean empty) {
        super.updateItem(student, empty);
        if (empty) {
          this.setText(null);
          return;
        }
 
        this.setText(student.getName());
        if (highlightedIds == null) {
          return;
        }
 
        String css = ""
            + "-fx-text-fill: #c00;"
            + "-fx-font-weight: bold;"
            + "-fx-font-style: italic;"
            ;
 
        if (highlightedIds.contains(student.getId())) {
          this.setStyle(css);
        }
        else {
          this.setStyle(null);
        }
      }
    };
    return cell;
  }
}
package tutoringfx_jrowan;

import java.util.Collection;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.Tutor;

/**
 *
 * @author John Rowan JR897843@wcupa.edu
 * description: this class is a call back class used to highlight certain tutors in a list view
 */
public class TutorCellCallBack implements Callback<ListView<Tutor>, ListCell<Tutor>> {  
    private Collection<Integer> highlightedIds = null;
 /**
  * 
  * @param highlightedIds 
  * description: this method is used to set the list of id's of tutors from the main controller
  */
  void setHightlightedIds(Collection<Integer> highlightedIds) {
    this.highlightedIds = highlightedIds;
  }
  /**
   * 
   * @param p
   * @return 
   * description: this method does all of the work of highlighting tutors based on the highlighted Id's list
   */
  @Override
  public ListCell<Tutor> call(ListView<Tutor> p) {
    ListCell<Tutor> cell = new ListCell<Tutor>() {
      @Override
      protected void updateItem(Tutor tutor, boolean empty) {
        super.updateItem(tutor, empty);
        if (empty) {
          this.setText(null);
          return;
        }
        this.setText( tutor.getName() );
        if (highlightedIds == null) {
          return;
        }
 
        String css = ""
            + "-fx-text-fill: #c00;"
            + "-fx-font-weight: bold;"
            + "-fx-font-style: italic;"
            ;
 
        if (highlightedIds.contains(tutor.getId())) {
          this.setStyle(css);
        }
        else {
          this.setStyle(null);
        }
 
        // more code coming
      }
    };
    return cell;
  }
}
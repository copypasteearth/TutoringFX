package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static models.Tutor.TABLE;

/**
 *
 * @author John Rowan JR897843@wcupa.edu
 * description: this is a class to represent a Subject in the MySQL database
 *              it has getters and setters for each of its members and also implements
 *              load, insert, and update members of the Model Class enabling the ORM 
 *              to be able to use this class for database interaction
 */
public class Subject extends Model{
public static final String TABLE = "subject";
private int id = 0;
private String name;
    Subject(){
    
    }
    public Subject(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    void load(ResultSet rs) {
     try {
      id = rs.getInt("id");
      name = rs.getString("name");
    }catch (SQLException ex) {
      throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
    }
    }

    @Override
    void insert() {
      Connection cx = ORM.connection();
    try {
      String sql = String.format(
          "insert into %s (name) values (?)", TABLE);
      PreparedStatement st = cx.prepareStatement(sql);
      int i = 0;
      st.setString(++i, name);
      st.executeUpdate();
      id = ORM.getMaxId(TABLE);
    }catch (SQLException ex) {
      throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
    }
    }

    @Override
    void update() {
         Connection cx = ORM.connection();
    try {
      String sql = String.format(
          "update %s set name=? where id=?", TABLE);
      PreparedStatement st = cx.prepareStatement(sql);
      int i = 0;
      st.setString(++i, name);
      st.setInt(++i, id);
      
      st.executeUpdate();
    }catch (SQLException ex) {
      throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
    }
    }
      @Override
  public String toString() {
    return String.format("(%s,%s)", id, name);
  }
}

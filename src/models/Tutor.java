package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static models.Student.TABLE;

/**
 *
 * @author John Rowan JR897843@wcupa.edu
 * description: this is a class to represent a Tutor in the MySQL database
 *              it has getters and setters for each of its members and also implements
 *              load, insert, and update members of the Model Class enabling the ORM 
 *              to be able to use this class for database interaction
 */
public class Tutor extends Model{
    public static final String TABLE = "tutor";
    private int id = 0;
    private String name;
    private String email;
    private int subject_id;
    Tutor(){
        
    }
    public Tutor(String name,String email,int subject_id){
        this.name = name;
        this.email = email;
        this.subject_id = subject_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
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
      email = rs.getString("email");
      subject_id = rs.getInt("subject_id");
    }catch (SQLException ex) {
      throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
    }
    }

    @Override
    void insert() {
        Connection cx = ORM.connection();
    try {
      String sql = String.format(
          "insert into %s (name,email,subject_id) values (?,?,?)", TABLE);
      PreparedStatement st = cx.prepareStatement(sql);
      int i = 0;
      st.setString(++i, name);
      st.setString(++i, email);
      st.setInt(++i, subject_id);
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
          "update %s set name=?,email=?,subject_id=? where id=?", TABLE);
      PreparedStatement st = cx.prepareStatement(sql);
      int i = 0;
      st.setString(++i, name);
      st.setString(++i, email);
      st.setInt(++i, subject_id);
      st.setInt(++i, id);
      
      st.executeUpdate();
    }catch (SQLException ex) {
      throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
    }
    }
     @Override
  public String toString() {
    return String.format("(%s,%s,%s,%s)", id, name, email,subject_id);
  }
}

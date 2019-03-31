package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author John Rowan JR897843@wcupa.edu
 * description: this is a class to represent a Student in the MySQL database
 *              it has getters and setters for each of its members and also implements
 *              load, insert, and update members of the Model Class enabling the ORM 
 *              to be able to use this class for database interaction
 */
public class Student extends Model{
public static final String TABLE = "student";
private int id = 0;
private String name;
private Date enrolled;
Student(){
    
}
public Student(String name,Date enrolled){
    this.name = name;
    this.enrolled = enrolled;
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Date enrolled) {
        this.enrolled = enrolled;
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
      enrolled = rs.getDate("enrolled");
    }
    catch (SQLException ex) {
      throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
    }
    }

    @Override
    void insert() {
        Connection cx = ORM.connection();
    try {
      String sql = String.format(
          "insert into %s (name,enrolled) values (?,?)", TABLE);
      PreparedStatement st = cx.prepareStatement(sql);
      int i = 0;
      st.setString(++i, name);
      st.setDate(++i, enrolled);
      st.executeUpdate();
      id = ORM.getMaxId(TABLE);
    }
    catch (SQLException ex) {
      throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
    }
    }

    @Override
    void update() {
       Connection cx = ORM.connection();
    try {
      String sql = String.format(
          "update %s set name=?,enrolled=? where id=?", TABLE);
      PreparedStatement st = cx.prepareStatement(sql);
      int i = 0;
      st.setString(++i, name);
      st.setDate(++i, enrolled);
      st.setInt(++i, id);
      
      st.executeUpdate();
    }
    catch (SQLException ex) {
      throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
    }
    }
    @Override
    public String toString() {
    return String.format("(%s,%s,%s)", id, name, enrolled.toString());
  }
}

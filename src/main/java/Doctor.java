import java.util.List;
import org.sql2o.*;

  public class Doctor {
    private int id;
    private String name;

  public Doctor(String name) {
    this.name = name;
  }

  public static List<Doctor> all(){
    String sql = "SELECT * FROM doctors";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql).executeAndFetch(Doctor.class);
    }
  }

  public String getName() {
    return name;
  }

  public int getId(){
    return id;
  }

  @Override
  public boolean equals(Object otherDoctor) {
    if (!(otherDoctor instanceof Doctor)) {
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getName().equals(newDoctor.getName());
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO doctors (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
  }

  public static Doctor find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM doctors WHERE id=:id";
      Doctor doctor = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Doctor.class);
      return doctor;
    }
  }

  public List<Patient> getPatients(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM patients WHERE doctor_id=:id";
      return con.createQuery(sql)
      .addParameter("id", this.id)
      .executeAndFetch(Patient.class);
    }
  }

}

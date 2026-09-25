package rw.schoolpass.model;
import jakarta.persistence.*; import java.time.*;
@Entity @Table(name="attendance_records", uniqueConstraints=@UniqueConstraint(name="uk_student_arrival_day", columnNames={"studentId","arrivalDate"}))
public class AttendanceRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; private Long studentId; private String studentCode; private String studentName; private LocalDate arrivalDate; private LocalDateTime arrivalTime; private String scannerDevice;
 public AttendanceRecord(){} public AttendanceRecord(Long id,String code,String name,LocalDate d,LocalDateTime t,String device){studentId=id;studentCode=code;studentName=name;arrivalDate=d;arrivalTime=t;scannerDevice=device;}
 public Long getId(){return id;} public Long getStudentId(){return studentId;} public String getStudentCode(){return studentCode;} public String getStudentName(){return studentName;} public LocalDate getArrivalDate(){return arrivalDate;} public LocalDateTime getArrivalTime(){return arrivalTime;} public String getScannerDevice(){return scannerDevice;}
}

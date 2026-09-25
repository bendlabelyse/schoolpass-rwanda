package rw.schoolpass.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="audit_logs") public class AuditLog {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; private String username; private String action; private String entityType; private String entityId; private String details; private LocalDateTime createdAt=LocalDateTime.now();
 public AuditLog(){} public AuditLog(String u,String a,String t,String i,String d){username=u;action=a;entityType=t;entityId=i;details=d;}
 public Long getId(){return id;} public String getUsername(){return username;} public String getAction(){return action;} public String getEntityType(){return entityType;} public String getEntityId(){return entityId;} public String getDetails(){return details;} public LocalDateTime getCreatedAt(){return createdAt;}
}

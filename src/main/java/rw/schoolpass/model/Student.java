package rw.schoolpass.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="students", uniqueConstraints={
        @UniqueConstraint(name="uk_student_code_year", columnNames={"studentCode","academicYear"}),
        @UniqueConstraint(name="uk_card_token", columnNames="cardToken")})
public class Student {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String studentCode;
    @Column(nullable=false) private String firstName;
    @Column(nullable=false) private String lastName;
    @Column(nullable=false) private String className;
    private String section;
    private String photoUrl;
    @Column(nullable=false) private String schoolName;
    @Column(nullable=false) private String principalName;
    @Column(nullable=false) private String academicYear;
    @Column(nullable=false, unique=true) private String cardToken;
    private boolean cardActive=true;
    private long feeRequired;
    private long feePaid;
    private String feeStatus;
    private String uniformStatus="UNPAID";
    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime updatedAt=LocalDateTime.now();

    public Long getId(){return id;} public String getStudentCode(){return studentCode;} public void setStudentCode(String v){studentCode=v;}
    public String getFirstName(){return firstName;} public void setFirstName(String v){firstName=v;} public String getLastName(){return lastName;} public void setLastName(String v){lastName=v;}
    public String getClassName(){return className;} public void setClassName(String v){className=v;} public String getSection(){return section;} public void setSection(String v){section=v;}
    public String getPhotoUrl(){return photoUrl;} public void setPhotoUrl(String v){photoUrl=v;} public String getSchoolName(){return schoolName;} public void setSchoolName(String v){schoolName=v;}
    public String getPrincipalName(){return principalName;} public void setPrincipalName(String v){principalName=v;} public String getAcademicYear(){return academicYear;} public void setAcademicYear(String v){academicYear=v;}
    public String getCardToken(){return cardToken;} public void setCardToken(String v){cardToken=v;} public boolean isCardActive(){return cardActive;} public void setCardActive(boolean v){cardActive=v;}
    public long getFeeRequired(){return feeRequired;} public void setFeeRequired(long v){feeRequired=v;} public long getFeePaid(){return feePaid;} public void setFeePaid(long v){feePaid=v;}
    public String getFeeStatus(){return feeStatus;} public void setFeeStatus(String v){feeStatus=v;} public String getUniformStatus(){return uniformStatus;} public void setUniformStatus(String v){uniformStatus=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;} public void touch(){updatedAt=LocalDateTime.now();}
}

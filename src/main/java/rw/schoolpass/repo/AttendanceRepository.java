package rw.schoolpass.repo;
import org.springframework.data.jpa.repository.JpaRepository; import rw.schoolpass.model.AttendanceRecord; import java.time.*; import java.util.*;
public interface AttendanceRepository extends JpaRepository<AttendanceRecord,Long>{ boolean existsByStudentIdAndArrivalDate(Long id,LocalDate d); long countByArrivalDate(LocalDate d); List<AttendanceRecord> findTop100ByArrivalDateOrderByArrivalTimeDesc(LocalDate d); List<AttendanceRecord> findByArrivalDateOrderByArrivalTimeDesc(LocalDate d); }

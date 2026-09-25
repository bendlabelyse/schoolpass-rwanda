package rw.schoolpass.repo;
import org.springframework.data.jpa.repository.JpaRepository; import rw.schoolpass.model.AuditLog; import java.util.*;
public interface AuditLogRepository extends JpaRepository<AuditLog,Long>{ List<AuditLog> findTop100ByOrderByCreatedAtDesc(); }

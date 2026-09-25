package rw.schoolpass.service;
import org.springframework.stereotype.Service; import rw.schoolpass.model.AuditLog; import rw.schoolpass.repo.AuditLogRepository;
@Service public class AuditService { private final AuditLogRepository repo; public AuditService(AuditLogRepository r){repo=r;} public void log(String user,String action,String type,String id,String details){repo.save(new AuditLog(user,action,type,id,details));} }

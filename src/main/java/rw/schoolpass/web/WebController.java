package rw.schoolpass.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import rw.schoolpass.model.AttendanceRecord;
import rw.schoolpass.model.Student;
import rw.schoolpass.repo.AttendanceRepository;
import rw.schoolpass.repo.AuditLogRepository;
import rw.schoolpass.repo.StudentRepository;
import rw.schoolpass.service.AuditService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    private final StudentRepository students;
    private final AttendanceRepository attendance;
    private final AuditLogRepository auditLogs;
    private final AuditService audit;

    public WebController(
            StudentRepository students,
            AttendanceRepository attendance,
            AuditLogRepository auditLogs,
            AuditService audit) {

        this.students = students;
        this.attendance = attendance;
        this.auditLogs = auditLogs;
        this.audit = audit;
    }

    @GetMapping("/")
    String home(Model model) {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    String dashboard(Model model) {
        return "dashboard";
    }

    @GetMapping("/students")
    String students(
            @RequestParam(required = false) String q,
            Model model) {

        List<Student> list;

        if (q == null || q.isBlank()) {
            list = students.findAll();
        } else {
            list = students
                    .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrStudentCodeContainingIgnoreCase(
                            q,
                            q,
                            q
                    );
        }

        model.addAttribute("students", list);
        model.addAttribute("query", q == null ? "" : q);

        return "students";
    }

    @GetMapping("/attendance")
    String attendance(
            @RequestParam(required = false) String date,
            Model model) {

        LocalDate selectedDate;

        if (date == null || date.isBlank()) {
            selectedDate = LocalDate.now();
        } else {
            try {
                selectedDate = LocalDate.parse(date);
            } catch (Exception e) {
                selectedDate = LocalDate.now();
            }
        }

        List<AttendanceRecord> records =
                attendance.findByArrivalDateOrderByArrivalTimeDesc(
                        selectedDate
                );

        model.addAttribute("records", records);
        model.addAttribute("date", selectedDate.toString());

        return "attendance";
    }

    @GetMapping("/audit")
    String audit(Model model) {

        model.addAttribute(
                "logs",
                auditLogs.findTop100ByOrderByCreatedAtDesc()
        );

        return "audit";
    }

    @GetMapping("/scan")
    String scanPage() {
        return "scan";
    }

    @GetMapping("/api/scan")
    @ResponseBody
    Map<String, Object> scan(
            @RequestParam String code,
            @RequestParam(defaultValue = "Browser scanner") String device,
            Authentication auth) {

        String clean = code.trim();

        // 1. First try the secure QR/card credential.
        Student s = students
                .findByCardTokenAndCardActiveTrue(clean)
                .orElse(null);

        // 2. If it is not a QR credential, try the printed Student Code.
        if (s == null) {
            String currentYear =
                    String.valueOf(LocalDate.now().getYear());

            s = students
                    .findByStudentCodeAndAcademicYear(
                            clean,
                            currentYear
                    )
                    .orElse(null);
        }

        // 3. Neither credential was found.
        if (s == null) {
            return Map.of(
                    "ok",
                    false,
                    "message",
                    "Student code or card QR credential not found."
            );
        }

        LocalDate today = LocalDate.now();

        // 4. Prevent duplicate arrival records on the same day.
        if (attendance.existsByStudentIdAndArrivalDate(
                s.getId(),
                today)) {

            audit.log(
                    auth.getName(),
                    "SCAN_DUPLICATE",
                    "STUDENT",
                    String.valueOf(s.getId()),
                    "Duplicate arrival attempt"
            );

            return response(
                    s,
                    true,
                    "Arrival already recorded today."
            );
        }

        // 5. Record the student's arrival.
        attendance.save(
                new AttendanceRecord(
                        s.getId(),
                        s.getStudentCode(),
                        s.getFirstName()
                                + " "
                                + s.getLastName(),
                        today,
                        LocalDateTime.now(),
                        device
                )
        );

        audit.log(
                auth.getName(),
                "ARRIVAL",
                "STUDENT",
                String.valueOf(s.getId()),
                "Arrival recorded using " + device
        );

        return response(
                s,
                false,
                "Arrival recorded successfully."
        );
    }

    private Map<String, Object> response(
            Student s,
            boolean duplicate,
            String message) {

        long balance = Math.max(
                0,
                s.getFeeRequired() - s.getFeePaid()
        );

        return Map.of(
                "ok",
                true,

                "duplicate",
                duplicate,

                "message",
                message,

                "student",
                Map.of(
                        "name",
                        s.getFirstName()
                                + " "
                                + s.getLastName(),

                        "studentCode",
                        s.getStudentCode(),

                        "className",
                        s.getClassName(),

                        "section",
                        s.getSection() == null
                                ? ""
                                : s.getSection(),

                        "photoUrl",
                        s.getPhotoUrl() == null
                                ? ""
                                : s.getPhotoUrl(),

                        "feeRequired",
                        s.getFeeRequired(),

                        "feePaid",
                        s.getFeePaid(),

                        "balance",
                        balance,

                        "feeStatus",
                        s.getFeeStatus(),

                        "uniformStatus",
                        s.getUniformStatus()
                )
        );
    }
}
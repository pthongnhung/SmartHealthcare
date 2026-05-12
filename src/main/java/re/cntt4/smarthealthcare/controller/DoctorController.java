package re.cntt4.smarthealthcare.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import re.cntt4.smarthealthcare.dto.ExaminationRequest;
import re.cntt4.smarthealthcare.entity.Appointment;
import re.cntt4.smarthealthcare.entity.Doctor;
import re.cntt4.smarthealthcare.repository.AppointmentRepository;
import re.cntt4.smarthealthcare.repository.DoctorRepository;
import re.cntt4.smarthealthcare.repository.MedicineRepository;
import re.cntt4.smarthealthcare.repository.UserProfileRepository;
import re.cntt4.smarthealthcare.service.IExaminationService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private IExaminationService examinationService;

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        var profile = profileRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));
        model.addAttribute("profile", profile);
        return "doctor/home";
    }

    // Hiển thị danh sách lịch hẹn của bác sĩ đang login
    @GetMapping("/appointments")
    public String viewAppointments(Model model, Principal principal) {
        String email = principal.getName();
        Doctor doctor = doctorRepository.findByUser_Profile_Email(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ"));
        List<Appointment> appointments = appointmentRepository
                .findByDoctor_DoctorIdAndStatus(doctor.getDoctorId(), re.cntt4.smarthealthcare.constant.AppointmentStatus.PENDING);
        model.addAttribute("appointments", appointments);
        return "doctor/appointment_list";
    }

    // Hiển thị form khám bệnh
    @GetMapping("/examination/{id}")
    public String showExaminationForm(@PathVariable Integer id, Model model) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn"));

        ExaminationRequest request = new ExaminationRequest();
        request.setAppointmentId(appointment.getAppointmentId()); // gán ID vào DTO

        model.addAttribute("appointment", appointment);
        model.addAttribute("medicines", medicineRepository.findAll());
        model.addAttribute("examinationRequest", request);
        return "doctor/examination_form";
    }


    // Xử lý lưu kết quả khám bệnh (Transaction)
    @PostMapping("/examination/complete")
    public String completeExamination(@Valid @ModelAttribute ExaminationRequest request,
                                      BindingResult result,
                                      Principal principal,
                                      Model model) {
        if (result.hasErrors()) {
            // Trả lại form với danh sách thuốc và lỗi
            model.addAttribute("medicines", medicineRepository.findAll());
            return "doctor/examination_form";
        }
        // Nếu không lỗi thì lưu và redirect
        Doctor doctor = doctorRepository.findByUser_Profile_Email(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ"));
        examinationService.completeExamination(doctor, request);
        return "redirect:/doctor/appointments";
    }


}

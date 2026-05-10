package re.cntt4.smarthealthcare.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import re.cntt4.smarthealthcare.dto.appointment.AppointmentRequest;
import re.cntt4.smarthealthcare.repository.authentication.DoctorRepository;
import re.cntt4.smarthealthcare.repository.authentication.SpecialtyRepository;
import re.cntt4.smarthealthcare.service.IAppointmentService;

@Controller
@RequestMapping("/patient/appointments")
public class AppointmentController {

    @Autowired
    private IAppointmentService appointmentService;
    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("appointmentRequest", new AppointmentRequest());
        model.addAttribute("specialties", specialtyRepository.findAll());
        model.addAttribute("doctors", java.util.List.of());
        return "patient/appointment_form";
    }

    @GetMapping("/specialty/{id}")
    public String showDoctorsBySpecialty(@PathVariable("id") Integer specialtyId, Model model) {
        AppointmentRequest request = new AppointmentRequest();
        request.setSpecialtyId(specialtyId);
        model.addAttribute("appointmentRequest", request);
        model.addAttribute("specialties", specialtyRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findBySpecialty_SpecialtyId(specialtyId));
        return "patient/appointment_form";
    }

    @PostMapping("/book")
    public String bookAppointment(@Valid @ModelAttribute AppointmentRequest request,
                                  BindingResult result,
                                  Model model,
                                  java.security.Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            model.addAttribute("specialties", specialtyRepository.findAll());
            model.addAttribute("doctors", request.getSpecialtyId() != null
                    ? doctorRepository.findBySpecialty_SpecialtyId(request.getSpecialtyId())
                    : java.util.List.of());
            return "patient/appointment_form";
        }
        try {
            // ✅ Lấy email từ user đăng nhập
            String email = principal.getName();
            Integer patientId = appointmentService.findPatientIdByEmail(email);

            appointmentService.bookAppointment(
                    patientId,
                    request.getDoctorId(),
                    request.getAppointmentDate(),
                    request.getAppointmentTime()
            );
            return "redirect:/patient/appointments/view";
        } catch (IllegalArgumentException e) {

            result.rejectValue(
                    "appointmentTime",
                    "error.appointmentTime",
                    e.getMessage()
            );

            model.addAttribute("specialties", specialtyRepository.findAll());

            model.addAttribute("doctors",
                    request.getSpecialtyId() != null
                            ? doctorRepository.findBySpecialty_SpecialtyId(request.getSpecialtyId())
                            : java.util.List.of());

            return "patient/appointment_form";
        }
    }

    @GetMapping("/view")
    public String viewAppointments(Model model, java.security.Principal principal) {
        // Lấy email hoặc username từ user đăng nhập
        String email = principal.getName();
        Integer patientId = appointmentService.findPatientIdByEmail(email);
        // hoặc findPatientIdByUsername nếu bạn dùng username

        // Chỉ lấy lịch hẹn của bệnh nhân này
        model.addAttribute("appointments", appointmentService.getAppointmentsByPatientId(patientId));
        return "patient/appointment_list";
    }

}


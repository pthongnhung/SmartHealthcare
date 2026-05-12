package re.cntt4.smarthealthcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import re.cntt4.smarthealthcare.entity.Patient;
import re.cntt4.smarthealthcare.entity.UserProfile;
import re.cntt4.smarthealthcare.repository.UserProfileRepository;
import re.cntt4.smarthealthcare.service.MedicalHistoryService;

import java.security.Principal;

@Controller
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final UserProfileRepository profileRepository;
    private final MedicalHistoryService medicalHistoryService;

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        UserProfile profile = profileRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));
        model.addAttribute("profile", profile);
        return "patient/home";
    }

    @GetMapping("/history")
    public String viewHistory(Model model, Principal principal) {
        UserProfile profile = profileRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));

        Patient patient = profile.getUser().getPatient(); // giả sử UserProfile liên kết với User → Patient
        model.addAttribute("history", medicalHistoryService.getHistoryByPatient(patient.getPatientId()));
        return "patient/history";
    }

    //  Xem danh sách đơn thuốc của bệnh nhân
    @GetMapping("/prescriptions")
    public String viewPrescriptions(Model model, Principal principal) {
        UserProfile profile = profileRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));
        Patient patient = profile.getUser().getPatient();

        model.addAttribute("prescriptions", patient.getPrescriptions());
        return "patient/prescriptions";
    }

    // Cập nhật thông tin hồ sơ bệnh nhân
    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        UserProfile profile = profileRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));
        model.addAttribute("profile", profile);
        return "patient/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute UserProfile updatedProfile, Principal principal) {
        UserProfile profile = profileRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));

        profile.setFullName(updatedProfile.getFullName());
        profile.setPhone(updatedProfile.getPhone());
        profile.setAddress(updatedProfile.getAddress());
        profileRepository.save(profile);

        return "redirect:/patient/profile";
    }
}

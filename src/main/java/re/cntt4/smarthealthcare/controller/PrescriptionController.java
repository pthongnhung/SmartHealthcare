package re.cntt4.smarthealthcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import re.cntt4.smarthealthcare.service.IPrescriptionService;

@Controller
@RequestMapping("/admin/prescriptions")
public class PrescriptionController {

    @Autowired
    private IPrescriptionService prescriptionService;

    // Hiển thị danh sách đơn thuốc
    @GetMapping
    public String listPrescriptions(Model model) {

        model.addAttribute(
                "prescriptions",
                prescriptionService.getAllPrescriptions()
        );

        return "admin/prescription_list";
    }

    // Cấp phát thuốc
    @PostMapping("/dispense/{id}")
    public String dispensePrescription(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {

        try {

            prescriptionService.dispenseMedicine(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Cấp phát thuốc thành công!"
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/admin/prescriptions";
    }
}
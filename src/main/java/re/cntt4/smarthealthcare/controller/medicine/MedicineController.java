package re.cntt4.smarthealthcare.controller.medicine;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import re.cntt4.smarthealthcare.dto.medicine.MedicineRequest;
import re.cntt4.smarthealthcare.entity.Medicine;
import re.cntt4.smarthealthcare.service.medicine.IMedicineService;

import java.util.List;

@Controller
@RequestMapping("/admin/medicines")
public class MedicineController {
    private final IMedicineService service;

    public MedicineController(IMedicineService service) {
        this.service = service;
    }

    @GetMapping
    public String listMedicines(Model model) {
        List<Medicine> medicines = service.getAll();
        model.addAttribute("medicines", medicines);
        return "medicine-list";
    }

    @GetMapping("/add")
    public String viewAdd(Model model) {
        model.addAttribute("medicineRequest", new MedicineRequest());
        return "medicine-form";
    }

    @PostMapping("/add")
    public String handleAdd(@Valid @ModelAttribute("medicineRequest") MedicineRequest request,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("medicineRequest", request);
            return "medicine-form";
        }
        service.create(request);
        return "redirect:/admin/medicines";
    }

    @GetMapping("/edit/{id}")
    public String viewEdit(@PathVariable Integer id, Model model) {
        Medicine medicine = service.getById(id);
        if (medicine == null) {
            return "redirect:/admin/medicines";
        }
        MedicineRequest dto = new MedicineRequest();
        dto.setMedicineId(medicine.getMedicineId());
        dto.setName(medicine.getName());
        dto.setDescription(medicine.getDescription());
        dto.setStockQuantity(medicine.getStockQuantity());
        dto.setUnit(medicine.getUnit());
        dto.setPrice(medicine.getPrice());
        model.addAttribute("medicineRequest", dto);
        return "medicine-form-edit";
    }

    @PostMapping("/edit")
    public String handleUpdate(@Valid @ModelAttribute("medicineRequest") MedicineRequest request,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("medicineRequest", request);
            return "medicine-form-edit";
        }
        service.update(request.getMedicineId(), request);
        return "redirect:/admin/medicines";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/admin/medicines";
    }
}

package re.cntt4.smarthealthcare.controller.medicine;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import re.cntt4.smarthealthcare.dto.medecine.MedicineRequest;
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
    public String listMedicines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Medicine> medicinePage = service.getAllMedicine(null, pageable);

        model.addAttribute("medicinePage", medicinePage);
        model.addAttribute("currentPage", page);

        return "admin/medicine/medicine-list";
    }


    @GetMapping("/add")
    public String viewAdd(Model model) {

        model.addAttribute("medicineRequest", new MedicineRequest());

        return "admin/medicine/medicine-form";
    }

    @PostMapping("/add")
    public String handleAdd(
            @Valid @ModelAttribute("medicineRequest") MedicineRequest request,
            BindingResult result,
            Model model
    ) {

        if (result.hasErrors()) {

            model.addAttribute("medicineRequest", request);

            return "admin/medicine/medicine-form";
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

        return "admin/medicine/medicine-form-edit";
    }

    @PostMapping("/edit")
    public String handleUpdate(
            @Valid @ModelAttribute("medicineRequest") MedicineRequest request,
            BindingResult result,
            Model model
    ) {

        if (result.hasErrors()) {

            model.addAttribute("medicineRequest", request);

            return "admin/medicine/medicine-form-edit";
        }

        service.update(request.getMedicineId(), request);

        return "redirect:/admin/medicines";
    }


    @GetMapping("/delete/{id}")
    public String viewDelete(@PathVariable Integer id, Model model) {
        Medicine medicine = service.getById(id);
        if (medicine == null) return "redirect:/admin/medicines";
        model.addAttribute("medicine", medicine);
        return "admin/medicine/medicine-delete";
    }

    @PostMapping("/delete")
    public String handleDelete(@RequestParam Integer id) {
        service.delete(id);
        return "redirect:/admin/medicines";
    }

}
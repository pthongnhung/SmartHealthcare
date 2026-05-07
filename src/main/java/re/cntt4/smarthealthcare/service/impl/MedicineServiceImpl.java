package re.cntt4.smarthealthcare.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import re.cntt4.smarthealthcare.dto.medicine.MedicineRequest;
import re.cntt4.smarthealthcare.entity.Medicine;
import re.cntt4.smarthealthcare.repository.medicine.MedicineRepository;
import re.cntt4.smarthealthcare.service.medicine.IMedicineService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements IMedicineService {
    private final MedicineRepository repository;

    @Override
    public Medicine create(MedicineRequest request) {
        Medicine medicine = new Medicine();
        medicine.setName(request.getName());
        medicine.setDescription(request.getDescription());
        medicine.setStockQuantity(request.getStockQuantity());
        medicine.setUnit(request.getUnit());
        medicine.setPrice(request.getPrice());
        return repository.save(medicine);
    }

    @Override
    public List<Medicine> getAll() {
        return repository.findAll();
    }

    @Override
    public Medicine getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Medicine update(Integer id, MedicineRequest request) {
        return repository.findById(id).map(existing -> {
            existing.setName(request.getName());
            existing.setDescription(request.getDescription());
            existing.setStockQuantity(request.getStockQuantity());
            existing.setUnit(request.getUnit());
            existing.setPrice(request.getPrice());
            return repository.save(existing);
        }).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Medicine> getAllMedicine(String search, Pageable pageable) {
        if (search == null || search.isEmpty()) {
            return repository.findAll(pageable);
        }
        // Nếu muốn search theo tên thuốc
        return repository.findAll(pageable); // hoặc viết query custom: findAllByNameContains(search, pageable)
    }
}


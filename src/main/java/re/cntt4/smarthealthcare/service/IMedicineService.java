package re.cntt4.smarthealthcare.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import re.cntt4.smarthealthcare.dto.MedicineRequest;
import re.cntt4.smarthealthcare.entity.Medicine;

import java.util.List;

public interface IMedicineService {
    Medicine create(MedicineRequest request);
    List<Medicine> getAll();
    Medicine getById(Integer id);
    Medicine update(Integer id, MedicineRequest request);
    void delete(Integer id);

    // phân trang + search
    Page<Medicine> getAllMedicine(String search, Pageable pageable);
}


package re.cntt4.smarthealthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.cntt4.smarthealthcare.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
}

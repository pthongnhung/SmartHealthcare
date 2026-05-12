package re.cntt4.smarthealthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.cntt4.smarthealthcare.entity.PrescriptionDetail;

public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetail, Integer> {
    void deleteByPrescription_PrescriptionId(Integer prescriptionId);

}


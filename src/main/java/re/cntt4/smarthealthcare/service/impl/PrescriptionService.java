package re.cntt4.smarthealthcare.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import re.cntt4.smarthealthcare.constant.PrescriptionStatus;
import re.cntt4.smarthealthcare.entity.Medicine;
import re.cntt4.smarthealthcare.entity.Prescription;
import re.cntt4.smarthealthcare.entity.PrescriptionDetail;

import re.cntt4.smarthealthcare.repository.MedicineRepository;
import re.cntt4.smarthealthcare.repository.PrescriptionRepository;
import re.cntt4.smarthealthcare.service.IPrescriptionService;

import java.util.List;

@Service
public class PrescriptionService implements IPrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    @Transactional
    public void dispenseMedicine(Integer prescriptionId) {

        Prescription prescription =
                prescriptionRepository.findById(prescriptionId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Không tìm thấy đơn thuốc"));

        // Chỉ cho phép cấp phát đơn đang chờ
        if (prescription.getStatus() != PrescriptionStatus.PENDING) {

            throw new IllegalArgumentException(
                    "Đơn thuốc này không thể cấp phát!"
            );
        }

        // Kiểm tra tồn kho
        for (PrescriptionDetail detail : prescription.getDetails()) {

            Medicine medicine = detail.getMedicine();

            if (medicine.getStockQuantity() < detail.getQuantity()) {

                throw new IllegalArgumentException(
                        "Thuốc "
                                + medicine.getName()
                                + " không đủ tồn kho!"
                );
            }
        }

        // Trừ kho
        for (PrescriptionDetail detail : prescription.getDetails()) {

            Medicine medicine = detail.getMedicine();

            medicine.setStockQuantity(
                    medicine.getStockQuantity() - detail.getQuantity()
            );

            medicineRepository.save(medicine);
        }

        // Cập nhật trạng thái
        prescription.setStatus(PrescriptionStatus.DISPENSED);

        prescriptionRepository.save(prescription);
    }
}
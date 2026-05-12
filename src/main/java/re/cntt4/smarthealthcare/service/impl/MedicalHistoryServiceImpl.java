package re.cntt4.smarthealthcare.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import re.cntt4.smarthealthcare.dto.MedicalHistoryDTO;
import re.cntt4.smarthealthcare.dto.MedicineDetailDTO;
import re.cntt4.smarthealthcare.entity.MedicalRecord;
import re.cntt4.smarthealthcare.repository.MedicalRecordRepository;
import re.cntt4.smarthealthcare.service.MedicalHistoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    public List<MedicalHistoryDTO> getHistoryByPatient(Integer patientId) {
        List<MedicalRecord> records = medicalRecordRepository.findFullHistoryByPatient(patientId);

        return records.stream().map(record -> {
            MedicalHistoryDTO dto = new MedicalHistoryDTO();
            dto.setAppointmentId(record.getAppointment().getAppointmentId());
            dto.setAppointmentDate(record.getAppointment().getAppointmentDate());
            dto.setDoctorName(record.getDoctor().getUser().getUsername()); // dùng getName thay vì getFullName
            dto.setDiagnosis(record.getDiagnosis());
            dto.setSymptoms(record.getSymptoms());

            // Prescription là 1-1, nên lấy trực tiếp
            if (record.getPrescription() != null) {
                List<MedicineDetailDTO> meds = record.getPrescription().getDetails().stream()
                        .map(pd -> {
                            MedicineDetailDTO md = new MedicineDetailDTO();
                            md.setMedicineName(pd.getMedicine().getName());
                            md.setQuantity(pd.getQuantity());
                            md.setDosage(pd.getDosage());
                            return md;
                        }).toList();
                dto.setMedicines(meds);
            }

            return dto;
        }).toList();
    }
}

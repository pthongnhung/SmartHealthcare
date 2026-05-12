package re.cntt4.smarthealthcare.service;

import re.cntt4.smarthealthcare.dto.MedicalHistoryDTO;
import java.util.List;

public interface MedicalHistoryService {
    List<MedicalHistoryDTO> getHistoryByPatient(Integer patientId);
}

package re.cntt4.smarthealthcare.service;


import re.cntt4.smarthealthcare.dto.ExaminationRequest;
import re.cntt4.smarthealthcare.entity.Doctor;

public interface IExaminationService {
    void completeExamination(Doctor doctor, ExaminationRequest request);
}

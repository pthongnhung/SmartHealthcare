package re.cntt4.smarthealthcare.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import re.cntt4.smarthealthcare.constant.AppointmentStatus;
import re.cntt4.smarthealthcare.constant.PrescriptionStatus;
import re.cntt4.smarthealthcare.dto.ExaminationRequest;
import re.cntt4.smarthealthcare.entity.*;
import re.cntt4.smarthealthcare.repository.*;
import re.cntt4.smarthealthcare.service.IExaminationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExaminationServiceImpl implements IExaminationService {

    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionDetailRepository prescriptionDetailRepository;
    private final MedicineRepository medicineRepository;

    @Override
    @Transactional
    public void completeExamination(Doctor doctor, ExaminationRequest request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lịch hẹn"));

        if (!appointment.getStatus().equals(AppointmentStatus.PENDING)) {
            throw new IllegalStateException("Chỉ có thể khám lịch hẹn đang ở trạng thái CHỜ KHÁM");
        }

        // 1. Cập nhật trạng thái lịch hẹn
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        // 2. MedicalRecord: nếu có thì update, nếu chưa có thì tạo mới
        MedicalRecord record = medicalRecordRepository.findByAppointment_AppointmentId(appointment.getAppointmentId())
                .orElseGet(() -> {
                    MedicalRecord newRecord = new MedicalRecord();
                    newRecord.setAppointment(appointment);
                    return newRecord;
                });

        record.setDoctor(doctor);
        record.setSymptoms(request.getSymptoms());
        record.setDiagnosis(request.getDiagnosis());
        medicalRecordRepository.save(record);

        // 3. Prescription: nếu có thì update, nếu chưa có thì tạo mới
        Prescription prescription = prescriptionRepository.findByMedicalRecord_RecordId(record.getRecordId())
                .orElseGet(() -> {
                    Prescription newPrescription = new Prescription();
                    newPrescription.setMedicalRecord(record);
                    newPrescription.setPatient(appointment.getPatient());
                    newPrescription.setStatus(PrescriptionStatus.PENDING);
                    newPrescription.setIsActive(true);
                    return newPrescription;
                });

        prescriptionRepository.save(prescription);

        // 4. PrescriptionDetail: xóa chi tiết cũ, ghi lại chi tiết mới
        prescriptionDetailRepository.deleteByPrescription_PrescriptionId(prescription.getPrescriptionId());

        List<Medicine> medicines = medicineRepository.findAllById(request.getMedicineIds());
        for (Medicine medicine : medicines) {
            PrescriptionDetail detail = new PrescriptionDetail();
            detail.setPrescription(prescription);
            detail.setMedicine(medicine);
            detail.setQuantity(1); // có thể thêm input số lượng
            detail.setDosage("2 viên/ngày"); // có thể thêm input liều dùng
            prescriptionDetailRepository.save(detail);
        }
    }

}

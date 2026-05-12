package re.cntt4.smarthealthcare.dto;

import java.time.LocalDate;
import java.util.List;

public class MedicalHistoryDTO {
    private Integer appointmentId;
    private LocalDate appointmentDate;
    private String doctorName;
    private String diagnosis;
    private String symptoms;
    private List<MedicineDetailDTO> medicines;

    // getters & setters

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public List<MedicineDetailDTO> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicineDetailDTO> medicines) {
        this.medicines = medicines;
    }
}

package re.cntt4.smarthealthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.cntt4.smarthealthcare.entity.MedicalRecord;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
    Optional<MedicalRecord> findByAppointment_AppointmentId(Integer appointmentId);
    @Query("SELECT mr FROM MedicalRecord mr " +
            "JOIN FETCH mr.appointment a " +
            "JOIN FETCH mr.doctor d " +
            "JOIN FETCH mr.prescription p " +
            "JOIN FETCH p.details pd " +
            "JOIN FETCH pd.medicine m " +
            "WHERE a.patient.patientId = :patientId")
    List<MedicalRecord> findFullHistoryByPatient(@Param("patientId") Integer patientId);


}

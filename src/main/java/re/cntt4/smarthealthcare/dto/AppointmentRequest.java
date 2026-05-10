package re.cntt4.smarthealthcare.dto.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentRequest {



    @NotNull(message = "Doctor ID không được để trống")
    private Integer doctorId;

    @NotNull(message = "Specialty ID không được để trống")
    private Integer specialtyId;

    @NotNull(message = "Ngày khám không được để trống")
    @Future(message = "Ngày khám phải ở tương lai")
    private LocalDate appointmentDate;

    @NotNull(message = "Giờ khám không được để trống")
    private LocalTime appointmentTime;
}

package re.cntt4.smarthealthcare.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExaminationRequest {

    @NotNull(message = "Appointment ID không được để trống")
    private Integer appointmentId;

    @NotEmpty(message = "Triệu chứng không được để trống")
    private String symptoms;

    @NotEmpty(message = "Chẩn đoán không được để trống")
    private String diagnosis;

    @NotEmpty(message = "Phải chọn ít nhất một thuốc")
    private List<Integer> medicineIds;
}

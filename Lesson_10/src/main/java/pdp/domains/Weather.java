package pdp.domains;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Weather {
    private Integer id;
    private Integer cityId;
    private String cityName;
    @NotNull(message = "date.null")
    private LocalDate date;
    @NotNull(message = "temperature.null")
    private Integer temperature;
}

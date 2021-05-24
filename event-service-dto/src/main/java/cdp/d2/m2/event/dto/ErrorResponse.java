package cdp.d2.m2.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse extends RepresentationModel<ErrorResponse> {

    private String message;

}

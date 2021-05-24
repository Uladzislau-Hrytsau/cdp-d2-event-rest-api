package cdp.d2.m2.event.dto;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto extends RepresentationModel<EventDto> {

    @ApiModelProperty(
            notes = "The database generated event identifier",
            dataType = "Long",
            example = "1",
            required = true
    )
    private Long id;
    @ApiModelProperty(
            notes = "Event title",
            dataType = "String",
            example = "make a complaint",
            required = true
    )
    private String title;
    @ApiModelProperty(
            notes = "Event place",
            dataType = "String",
            example = "Belarus",
            required = true
    )
    private String place;
    @ApiModelProperty(
            notes = "Event speaker",
            dataType = "String",
            example = "Uladzislau",
            required = true
    )
    private String speaker;
    @ApiModelProperty(
            notes = "Event type",
            dataType = "String",
            example = "Party",
            required = true
    )
    private String eventType;
    @ApiModelProperty(
            notes = "Event creation time",
            dataType = "LocalDateTime",
            example = "2021-05-17T18:47:52.69",
            required = true
    )
    private LocalDateTime dateTime;

}

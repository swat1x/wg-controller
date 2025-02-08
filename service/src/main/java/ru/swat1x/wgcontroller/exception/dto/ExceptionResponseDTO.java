package ru.swat1x.wgcontroller.exception.dto;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDTO {

    String message;

}

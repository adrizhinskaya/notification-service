package org.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.enums.OperationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные пользователя")
public class EmailRequest {
    @Schema(description = "Тип операции", example = "CREATE")
    private OperationType operation;
    @Schema(description = "Почтовый адрес", maxLength = 254, minLength = 6, example = "ivan.petrov@mail.ru")
    private String email;
}


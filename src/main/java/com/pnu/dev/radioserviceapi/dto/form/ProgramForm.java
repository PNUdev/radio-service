package com.pnu.dev.radioserviceapi.dto.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ProgramForm {

    @NotEmpty(message = "Заголовок не може бути порожнім")
    private String title;

    @NotEmpty(message = "Опис не може бути порожнім")
    @Size(max = 1000, message = "Розмір опису не може перевищувати 1000 символів")
    private String description;

    @NotEmpty(message = "Посилання на зображення має бути присутнім")
    private String imageUrl;

}

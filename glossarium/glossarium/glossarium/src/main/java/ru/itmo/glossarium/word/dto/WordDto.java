package ru.itmo.glossarium.word.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordDto {
    private Integer id;
    private String word;
    private String description;
}

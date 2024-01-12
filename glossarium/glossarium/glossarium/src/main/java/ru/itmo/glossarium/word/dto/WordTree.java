package ru.itmo.glossarium.word.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordTree {
    private Integer id;
    private String word;
    private String description;
    private List<WordTree> children;

    public WordTree(Integer id, String word, String description) {
        this.id = id;
        this.word = word;
        this.description = description;
    }
}

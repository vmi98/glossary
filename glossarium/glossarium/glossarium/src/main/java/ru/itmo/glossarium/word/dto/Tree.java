package ru.itmo.glossarium.word.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tree {
    private String name;
    private String description;
    private List<Tree> children;

    public Tree(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

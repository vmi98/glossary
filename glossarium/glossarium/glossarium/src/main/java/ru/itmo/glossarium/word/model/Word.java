package ru.itmo.glossarium.word.model;


import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "words")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Word {
    @Id
    @Column(name = "word_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wordId;

    @Column(nullable = false, length = 100)
    private String word;

    @Column(nullable = false, length = 1000)
    private String description;

    private Integer parentId;

    public Word(String word, String description, Integer parentId) {
        this.word = word;
        this.description = description;
        this.parentId = parentId;
    }
}

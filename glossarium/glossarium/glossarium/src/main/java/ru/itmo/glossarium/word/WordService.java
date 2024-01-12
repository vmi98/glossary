package ru.itmo.glossarium.word;

import org.springframework.data.domain.Pageable;
import ru.itmo.glossarium.word.dto.Tree;
import ru.itmo.glossarium.word.dto.WordCreateDto;
import ru.itmo.glossarium.word.dto.WordDto;
import ru.itmo.glossarium.word.dto.WordTree;
import ru.itmo.glossarium.word.model.Word;

import java.util.List;

public interface WordService {
    WordDto create(WordCreateDto dto);

    Tree getTree();

    List<WordDto> search(String text, Pageable pageable);
}

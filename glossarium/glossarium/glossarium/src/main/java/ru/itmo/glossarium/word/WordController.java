package ru.itmo.glossarium.word;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.glossarium.PageableCreate;
import ru.itmo.glossarium.word.dto.Tree;
import ru.itmo.glossarium.word.dto.WordCreateDto;
import ru.itmo.glossarium.word.dto.WordDto;
import ru.itmo.glossarium.word.dto.WordTree;
import ru.itmo.glossarium.word.model.Word;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/words")
@AllArgsConstructor
@Slf4j
@Validated
public class WordController {
    private WordService wordService;

    @PostMapping
    public WordDto create(@Valid @RequestBody WordCreateDto dto) {
        WordDto wordDto = wordService.create(dto);
        log.info("Add new word {}", wordDto);
        return wordDto;
    }

    @GetMapping("/tree")
    public Tree getTree() {
        Tree word = wordService.getTree();
        log.info("tree words {}, {}", word);
        return word;
    }

    @GetMapping("/search")
    public List<WordDto> search(@RequestParam String text,
                                @RequestParam(name = "from", defaultValue = "0") @Min(0) int from,
                                @RequestParam(name = "size", defaultValue = "10") @Min(0) int size) {
        List<WordDto> words = wordService.search(text, PageableCreate.pageableCreate(from, size));
        log.info("Get list words {}, {}", text, words);
        return words;
    }
}
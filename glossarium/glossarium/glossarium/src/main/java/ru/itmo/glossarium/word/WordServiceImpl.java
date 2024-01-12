package ru.itmo.glossarium.word;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itmo.glossarium.exception.ObjectNotFoundException;
import ru.itmo.glossarium.word.dto.Tree;
import ru.itmo.glossarium.word.dto.WordCreateDto;
import ru.itmo.glossarium.word.dto.WordDto;
import ru.itmo.glossarium.word.dto.WordTree;
import ru.itmo.glossarium.word.model.Word;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class WordServiceImpl implements WordService {
    private WordRepository wordRepository;

    @Override
    public WordDto create(WordCreateDto dto) {
        Word newWord = WordMapper.toWord(dto);
        Word word = wordRepository.save(newWord);

        return WordMapper.toWordDto(word);
    }

    @Override
    public Tree getTree() {
        Optional<Word> word = wordRepository.findById(1);
        WordTree wordTree = WordMapper.toWordTree(word.get());

        Tree tree = WordMapper.toTree(wordTree);

        addChildrens(List.of(tree));

        return tree;
    }

    private void addChildren(List<WordTree> wordsTree) {
        for (WordTree word : wordsTree) {
            List<Word> wordsChildren = wordRepository.findWordsByParentId(word.getId());

            if (!wordsChildren.isEmpty()) {
                List<WordTree> wordTrees = wordsChildren
                        .stream()
                        .map(WordMapper::toWordTree)
                        .collect(Collectors.toList());
                word.setChildren(wordTrees);
                addChildren(word.getChildren());
            }
        }
    }

    private void addChildrens(List<Tree> wordTree) {
        for (Tree tree : wordTree) {
            Word word = wordRepository.findWordByWord(tree.getName());
            List<Word> wordsChildren = wordRepository.findWordsByParentId(word.getWordId());

            if (!wordsChildren.isEmpty()) {
                List<Tree> wordTrees = wordsChildren
                        .stream()
                        .map(WordMapper::toTree)
                        .collect(Collectors.toList());
                tree.setChildren(wordTrees);
                addChildrens(tree.getChildren());
            }
        }
    }

    public List<WordDto> search(String text, Pageable pageable) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }

        return wordRepository.search(text, pageable).stream()
                .map(WordMapper::toWordDto)
                .collect(Collectors.toList());
    }

    private Word findWordByIdAndCheck(int id) {
        return wordRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Word with id " + id + " not found"));
    }
}

package ru.itmo.glossarium.word;

import ru.itmo.glossarium.word.dto.Tree;
import ru.itmo.glossarium.word.dto.WordCreateDto;
import ru.itmo.glossarium.word.dto.WordDto;
import ru.itmo.glossarium.word.dto.WordTree;
import ru.itmo.glossarium.word.model.Word;

public class WordMapper {

    public static Word toWord(WordCreateDto wordCreateDto) {
        return new Word(wordCreateDto.getWord(), wordCreateDto.getDescription(), wordCreateDto.getParentId());
    }

    public static WordDto toWordDto(Word word) {
        return new WordDto(word.getWordId(), word.getWord(), word.getDescription());
    }

    public static WordTree toWordTree(Word word) {
        return new WordTree(word.getWordId(), word.getWord(), word.getDescription());
    }

    public static Tree toTree(WordTree wordTree) {
        return new Tree(wordTree.getWord(), wordTree.getDescription());
    }

    public static Tree toTree(Word word) {
        return new Tree(word.getWord(), word.getDescription());
    }
}

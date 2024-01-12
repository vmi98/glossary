package ru.itmo.glossarium.word;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.glossarium.word.model.Word;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Integer> {
    List<Word> findWordsByParentId(int parentId);

    Word findWordByWord(String word);

    @Query("select w " +
            "from Word w " +
            "where upper(w.word) like upper(concat('%', ?1, '%')) " +
            "or upper(w.description) like upper(concat('%', ?1, '%'))")
    List<Word> search(String text, Pageable pageable);
}

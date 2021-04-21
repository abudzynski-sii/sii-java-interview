package pl.sii.interview.service;

import org.junit.jupiter.api.Test;
import pl.sii.interview.demo.model.InterviewItem;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Do not change the test expectations and do not modify interviewItemsArray.
 */
class InterviewTest {

    @Test
    void embeddedClasses_creation() {
        Object interviewQuestion = null;
        Object interviewAnswer = null;

        /* TODO create the instances of InterviewQuestion and InterviewAnswer */

        assertEquals(interviewQuestion.getClass(), InterviewItem.InterviewQuestion.class);
        assertEquals(interviewAnswer.getClass(), InterviewItem.InterviewAnswer.class);
    }

    final InterviewItem[] interviewItemsArray = new InterviewItem[]{
            InterviewItem.builder().question("Abc ?").answer("Xyz").build(),
            InterviewItem.builder().question("Abc ?").answer("Good").build(),
            InterviewItem.builder().question("Abc ?").answer("Good").build(),
            InterviewItem.builder().question("Abc ??").answer("Maybe C?").build(),
            InterviewItem.builder().question("Abc ?").answer(null).build(),
            InterviewItem.builder().question("Is this real?").answer("Maybe Yes").build(),
            InterviewItem.builder().question("Is this real?").answer("Maybe No").build(),
    };

    @Test
    void arrayItems_filtering() {
        List<InterviewItem> resultList = emptyList();

        /* TODO create a list containing all InterviewItem objects from the interviewItemsArray, for which:
           - the answer property contains 'y' character
           - the question property ends with a single '?' character and
        */
        resultList = Arrays.stream(interviewItemsArray)
                .filter(interviewItem -> interviewItem.getAnswer() != null && interviewItem.getQuestion() != null)
                .filter(interviewItem -> interviewItem.getAnswer().contains("y"))
                .filter(interviewItem -> this.endsWithSingleQuestionMark(interviewItem.getQuestion()))
                .collect(Collectors.toList());

        assertThat(resultList).hasSize(3);
        assertThat(resultList).contains(InterviewItem.builder().question("Abc ?").answer("Xyz").build());
        assertThat(resultList).contains(InterviewItem.builder().question("Is this real?").answer("Maybe Yes").build());
        assertThat(resultList).contains(InterviewItem.builder().question("Is this real?").answer("Maybe No").build());
    }

    @Test
    void arrayItems_toMap_uniqueEntries() {
        Map<String, ? extends Collection<String>> resultMap = emptyMap();

        /* TODO from the interviewItemsArray create a map {key: question, answers: [answer1, answer2]}:
           - questions should be unique
           - answers to the questions should be unique
        */
        resultMap = Arrays.stream(interviewItemsArray)
                .collect(Collectors.groupingBy(InterviewItem::getQuestion,
                        Collectors.mapping(InterviewItem::getAnswer, Collectors.toSet())));

        assertThat(resultMap).hasSize(3);
        assertThat(resultMap).containsKeys("Abc ?", "Abc ??", "Is this real?");
        assertThat(resultMap.get("Abc ?"))
                .hasSize(3)
                .contains("Xyz", "Good", null);
        assertThat(resultMap.get("Abc ??"))
                .hasSize(1)
                .contains("Maybe C?");
        assertThat(resultMap.get("Is this real?"))
                .hasSize(2)
                .contains("Maybe Yes", "Maybe No");
    }

    private boolean endsWithSingleQuestionMark(String s) {
        return Optional.ofNullable(s)
                .filter(s1 -> s1.endsWith("?"))
                .filter(s1 -> !s1.endsWith("??"))
                .isPresent();
    }

}

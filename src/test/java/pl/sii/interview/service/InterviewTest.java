package pl.sii.interview.service;

import org.junit.jupiter.api.Test;
import pl.sii.interview.demo.model.InterviewItem;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Do not change the test expectations and do not modify interviewItemsArray.
 */
class InterviewTest {

    @Test
    void embeddedClasses_creation() {
        Object interviewQuestion = new InterviewItem.InterviewQuestion();
        Object interviewAnswer = new InterviewItem.InterviewAnswer();

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
        List<InterviewItem> resultList = Arrays.asList(interviewItemsArray);
        /* TODO create a list containing all InterviewItem objects from the interviewItemsArray, for which:
           - the answer property contains 'y' character
           - the question property ends with a single '?' character and
        */

//        Podejscie bez stream - klasyczne

//        List<InterviewItem> filteredList = new ArrayList();
//
//        for (InterviewItem interviewItem : resultList) {
//            String question = interviewItem.getQuestion();
//            String answer = interviewItem.getAnswer();
//            if (answer != null && !question.substring(question.length() - 2).equals("??") && question.substring(question.length() - 1).equals("?") && answer.contains("y")) {
//                filteredList.add(interviewItem);
//            }
//        }
//        resultList = filteredList;

        resultList = resultList.stream()
                .filter(i -> i.getAnswer() != null
                        && !i.getQuestion().substring(i.getQuestion().length() - 2).equals("??")
                        && i.getQuestion().substring(i.getQuestion().length() - 1).equals("?")
                        && i.getAnswer().contains("y")).collect(Collectors.toList());

        assertThat(resultList).hasSize(3);
        assertThat(resultList).contains(InterviewItem.builder().question("Abc ?").answer("Xyz").build());
        assertThat(resultList).contains(InterviewItem.builder().question("Is this real?").answer("Maybe Yes").build());
        assertThat(resultList).contains(InterviewItem.builder().question("Is this real?").answer("Maybe No").build());
    }

    @Test
    void arrayItems_toMap_uniqueEntries() {
        Map<String, Set<String>> resultMap = new HashMap<>();

        /* TODO from the interviewItemsArray create a map {key: question, answers: [answer1, answer2]}:
           - questions should be unique
           - answers to the questions should be unique
        */

        List<InterviewItem> interviewItems = Arrays.asList(interviewItemsArray);
        for (InterviewItem interviewItem : interviewItems) {
            String question = interviewItem.getQuestion();
            String answer = interviewItem.getAnswer();
            HashSet<String> objects = new HashSet<>();
            objects.add(answer);

            if (resultMap.get(question) == null) {
                resultMap.put(question, objects);
            } else {
                resultMap.get(question).add(answer);
            }

        }


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

}

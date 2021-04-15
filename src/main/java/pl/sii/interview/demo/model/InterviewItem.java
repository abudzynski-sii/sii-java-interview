package pl.sii.interview.demo.model;

import lombok.*;

@Value
@Builder
@AllArgsConstructor
public class InterviewItem {
    String question;
    String answer;
    Integer score;

    public static class InterviewAnswer{
        String value;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InterviewQuestion{
        String value;
    }
}

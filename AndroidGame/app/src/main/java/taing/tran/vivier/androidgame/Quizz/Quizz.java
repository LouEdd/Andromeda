package taing.tran.vivier.androidgame.Quizz;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import taing.tran.vivier.androidgame.R;

public class Quizz {
    private String resource;
    private String question;
    private List<String> answers;
    private String answer;

    private Quizz(String resource, String question, List<String> answers, String answer) {
        this.resource = resource;
        this.question = question;
        this.answers = answers;
        this.answer = answer;
    }

    public static Quizz createQuizz(List<String> list) {
        String resource = list.get(0);
        String question = list.get(1);
        List<String> answers = Arrays.asList(list.get(2).split(";"));
        String answer = list.get(3);


        return new Quizz(resource, question, answers, answer);
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getResource() {
        return resource;
    }
}

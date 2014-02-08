package no.tdt4100.spillprosjekt.objects;

/**
 * Created by eiriksylliaas on 08.02.14.
 */
public class WordSend {
    private String word;
    private Integer position;
    private Integer queue;

    public WordSend(String word, int position, int queue) {
        this.word = word;
        this.position = position;
        this.queue = queue;
    }

    public String getWord() {
        return word;
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getQueue() {
        return queue;
    }

}

package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap map;
    public HistoryTextHandler(NGramMap map1){
        map = map1;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String response = "";
        for(int i = 0; i < words.size(); i++){
            response += words.get(i).toString()+": " + map.weightHistory(words.get(i), startYear, endYear).toString() + "\n";
        }
        return response;

    }
}

package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap map;
    public HistoryHandler(NGramMap map1){
        map = map1;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        ArrayList<TimeSeries> lts = new ArrayList<>();
        for (String word : words) {
            lts.add(map.weightHistory(word, q.startYear(), q.endYear()));
        }
        XYChart chart = Plotter.generateTimeSeriesChart(words, lts);
        return Plotter.encodeChartAsString(chart);
    }
}

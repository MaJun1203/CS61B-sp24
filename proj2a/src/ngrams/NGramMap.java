package ngrams;

import java.sql.Time;
import java.util.Collection;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;
import static utils.Utils.*;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    HashMap<String,TimeSeries> hash_word;
    TimeSeries ts_count;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        In in_word = new In(wordsFilename);
        In in_count = new In(countsFilename);
        hash_word = new HashMap<>();
        ts_count = new TimeSeries();
        while(!in_word.isEmpty()){
            String line = in_word.readLine();
            String[] splitline = line.split("\t");
            int key_ts = Integer.parseInt(splitline[1]);
            double value =Integer.parseInt(splitline[2]);
            if(hash_word.isEmpty() || !hash_word.containsKey(splitline[0])){
                TimeSeries ts = new TimeSeries();
                ts.put(key_ts,value);
                hash_word.put(splitline[0],ts);
            }else{
                hash_word.get(splitline[0]).put(key_ts,value);
            }

        }
        while(!in_count.isEmpty()){
            String line_count = in_count.readLine();
            String[] splitline_count = line_count.split(",");
            int key_count = Integer.parseInt(splitline_count[0]);
            double value_count = Double.parseDouble(splitline_count[1]);
            ts_count.put(key_count,value_count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if(hash_word.containsKey(word)){
            TimeSeries ts = new TimeSeries();
            for(int num : hash_word.get(word).keySet()){
                if(num >= startYear && num <= endYear){
                    ts.put(num, hash_word.get(word).get(num));
                }
            }
            return ts;
        }
        return null;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if(hash_word.containsKey(word)){
            TimeSeries ts = new TimeSeries();
            for(int num : hash_word.get(word).keySet()){
                ts.put(num, hash_word.get(word).get(num));
            }
            return ts;
        }
        return null;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries ts = new TimeSeries();
        if(ts_count.isEmpty()){
            return ts;
        }
        ts.putAll(ts_count);
        return ts;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        if(!hash_word.containsKey(word)){
            return ts;
        }else{
            for(int year : hash_word.get(word).keySet()){
                if(year >= startYear && year <= endYear){
                    ts.put(year,hash_word.get(word).get(year)/ts_count.get(year));
                }
            }
            return ts;
        }
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries ts = new TimeSeries();
        if(!hash_word.containsKey(word)){
            return ts;
        }else{
            for(int year : hash_word.get(word).keySet()){
                ts.put(year,hash_word.get(word).get(year)/ts_count.get(year));
            }
            return ts;
        }
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for(int year = startYear; year <= endYear; year++){
            ts.put(year,0.0);
        }
        for(String str : words){
            if(!weightHistory(str,startYear,endYear).isEmpty()){
                TimeSeries ts_wei = weightHistory(str, startYear, endYear);
                for(int year = startYear; year <= endYear; year++){
                    if(ts_wei.containsKey(year)){
                        ts.put(year,ts.get(year)+ts_wei.get(year));
                    }
                }
            }
        }
        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries ts = new TimeSeries();
        for(int year = MIN_YEAR; year <= MAX_YEAR; year++){
            ts.put(year,0.0);
        }
        for(String str : words){
            if(!weightHistory(str).isEmpty()){
                TimeSeries ts_wei = weightHistory(str);
                for(int year = MIN_YEAR; year <= MAX_YEAR; year++){
                    if(ts_wei.containsKey(year)){
                        ts.put(year,ts.get(year)+ts_wei.get(year));
                    }
                }
            }
        }
        Iterator<Map.Entry<Integer, Double>> iterator = ts.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Double> entry = iterator.next();
            if (entry.getValue() == 0.0) {
                iterator.remove();
            }
        }
        return ts;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
    public static void main(String[] args) {
        NGramMap ngm = new NGramMap(TOP_49887_WORDS_FILE, TOTAL_COUNTS_FILE);
        System.out.println(ngm.countHistory("airport"));
        System.out.println(ngm.weightHistory("dog",2000,2020));
    }
}

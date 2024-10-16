package ngrams;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for(int year : ts.keySet()){
            if(year >= startYear && year <= endYear){
                this.put(year, ts.get(year));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        return new ArrayList<>(this.keySet());
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> ls = new ArrayList<>();
        for(int year : this.keySet()){
            ls.add(this.get(year));
        }
        return ls;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries ts_1 = new TimeSeries();
        if(this.isEmpty() && ts.isEmpty()){
            return ts_1;
        }
        for(int year : this.keySet()){
            if(ts.containsKey(year)){
                ts_1.put(year,this.get(year) + ts.get(year));
            }else{
                ts_1.put(year,this.get(year));
            }
        }for(int year : ts.keySet()){
            if(!ts_1.containsKey(year)){
                ts_1.put(year,ts.get(year));
            }
        }
        return ts_1;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries ts_1 = new TimeSeries();
        for(int year : this.keySet()){
            if(!ts.containsKey(year)){
                throw new IllegalArgumentException();
            }else{
                ts_1.put(year, this.get(year)/ts.get(year));
            }
        }
        return ts_1;
    }

    public static void main(String[] args) {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 0.0);
        dogPopulation.put(1992, 50.0);
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        System.out.println(totalPopulation);
        TimeSeries dividePopulation = catPopulation.dividedBy(dogPopulation);
        System.out.println(dividePopulation);
    }
}

package fr.selquicode.go4lunch.ui.utils;

public class RatingCalculator {

    /**
     * To calculate the rating of a restaurant from 5 stars to 3
     * @param rating type float
     * @return new rating on 3 starts
     */
    public static float calculateRating(float rating){
        return rating * 3 / 5;
    }
}

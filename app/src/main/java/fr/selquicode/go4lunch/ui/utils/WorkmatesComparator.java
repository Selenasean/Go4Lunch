package fr.selquicode.go4lunch.ui.utils;

import java.util.Comparator;

import fr.selquicode.go4lunch.ui.workmates.WorkmatesViewState;

public class WorkmatesComparator {

    /**
     * To sort by workmate who choose a restaurant first
     */
    public static class ChoiceComparator implements Comparator<WorkmatesViewState> {
        @Override
        public int compare(WorkmatesViewState w1, WorkmatesViewState w2) {
            if(w1.hasChosenRestaurant() && w2.hasChosenRestaurant()){
                return 0;
            };
            if(w1.hasChosenRestaurant() && !w2.hasChosenRestaurant()){
                return -1;
            }
            return 1;
        }
    }
}

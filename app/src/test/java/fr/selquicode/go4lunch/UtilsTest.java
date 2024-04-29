package fr.selquicode.go4lunch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import static com.google.common.truth.Truth.assertThat;

import java.util.Arrays;
import java.util.List;

import fr.selquicode.go4lunch.ui.utils.IdCreator;
import fr.selquicode.go4lunch.ui.utils.RatingCalculator;
import fr.selquicode.go4lunch.ui.utils.WorkmatesComparator;
import fr.selquicode.go4lunch.ui.workmates.WorkmatesViewState;

@RunWith(JUnit4.class)
public class UtilsTest {

    @Test
    public void idCreatorTest(){
        String userA = "A";
        String userB = "B";
        String stringExpected = "B_A";

        assertThat(IdCreator.createMessageUid(userB, userA)).isEqualTo(stringExpected);
        assertThat(IdCreator.createMessageUid(userA, userB)).isEqualTo(stringExpected);
    }

    @Test
    public void ratingCalculatorTest(){
        float rating = 4;
        float floatExpected = 2.4F;

        assertThat(RatingCalculator.calculateRating(rating)).isEqualTo(floatExpected);
    }

    @Test
    public void WorkmatesComparatorTest(){
        //workmate who choose a restaurant
        WorkmatesViewState workmate1 = new WorkmatesViewState(
                "id1",
                "name1",
                "photoUrl1",
                "restaurantId1",
                "nameRestaurant1"
        );

        //workmate who doesn't choose
        WorkmatesViewState workmate2 = new WorkmatesViewState(
                "id2",
                "name2",
                "photoUrl2",
                null,
                null
        );

        //create a list with first the user who doesn't choose a restaurant and then
        //the user who choose
        List<WorkmatesViewState> workmates = Arrays.asList(workmate2, workmate1);
        //sort the list to have the workmate who choose a restaurant first
        workmates.sort(new WorkmatesComparator.ChoiceComparator());

        assertThat(workmates.get(0)).isEqualTo(workmate1);
        assertThat(workmates.get(1)).isEqualTo(workmate2);
    }

}

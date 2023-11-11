package fr.selquicode.go4lunch.ui.list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.annotation.NonNullApi;

import java.util.Objects;

import fr.selquicode.go4lunch.data.model.PlaceOpeningHours;
import fr.selquicode.go4lunch.data.model.PlacePhoto;

/**
 * This is the model of the view that the data have to fit into
 * ViewState contains data model use for displaying the UI
 * Here is about a list of restaurant
 */
public class ListViewState {

    @NonNull
    private final String id;

    @NonNull
    private final String nameRestaurant, vicinity;

    @Nullable
    private    Boolean opening;

    @NonNull
    private PlacePhoto restaurantImg;

    @NonNull private double ratings;


    /**
     * Constructor
     * @param id                type String
     * @param nameRestaurant    type String
     * @param vicinity          type String
     * @param restaurantImg     type Image
     * @param opening           type Boolean
     * @param ratings           type double
     */
    public ListViewState(String id,
                         @NonNull String nameRestaurant,
                         @NonNull String vicinity,
                         @Nullable PlacePhoto restaurantImg,
                         @Nullable Boolean opening,
                         @NonNull double ratings
                         ){
        this.id = id;
        this.nameRestaurant = nameRestaurant;
        this.vicinity  = vicinity;
        this.opening = opening;
        this.restaurantImg = restaurantImg;
        this.ratings = ratings;

    }

    // GETTERS
    public String getId() {
        return id;
    }

    @NonNull
    public String getNameRestaurant() {
        return nameRestaurant;
    }

    @NonNull
    public String getVicinity() {
        return vicinity;
    }

    @Nullable
    public Boolean getOpening() {
        return opening;
    }

    @Nullable
    public PlacePhoto getRestaurantImg() {
        return restaurantImg;
    }

    public double getRatings() {
        return ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListViewState)) return false;
        ListViewState that = (ListViewState) o;
        return getId() == that.getId() && getOpening() == that.getOpening() && getRatings() == that.getRatings() && getNameRestaurant().equals(that.getNameRestaurant()) && getVicinity().equals(that.getVicinity()) && getRestaurantImg().equals(that.getRestaurantImg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNameRestaurant(), getVicinity(), getOpening(), getRestaurantImg(), getRatings());
    }

    @Override
    public String toString() {
        return "ListViewState{" +
                "id=" + id +
                ", nameRestaurant='" + nameRestaurant + '\'' +
                ", vicinity ='" + vicinity + '\'' +
                ", opening=" + opening +
                ", restaurantImg=" + restaurantImg +
                ", ratings=" + ratings +
                '}';
    }

}

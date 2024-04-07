package fr.selquicode.go4lunch.ui.list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

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
    private final Boolean opening;

    @NonNull
    private final String distance;

    @Nullable
    private final PlacePhoto restaurantImg;

    private final float ratings;

    private final Long workmateEatingCount;


    /**
     * Constructor
     *
     * @param id                  type String
     * @param nameRestaurant      type String
     * @param vicinity            type String
     * @param restaurantImg       type PlacePhoto
     * @param distance            type String
     * @param opening             type Boolean
     * @param ratings             type float
     * @param workmateEatingCount type Long
     */
    public ListViewState(@NonNull String id,
                         @NonNull String nameRestaurant,
                         @NonNull String vicinity,
                         @Nullable PlacePhoto restaurantImg,
                         @NonNull String distance,
                         @Nullable Boolean opening,
                         float ratings,
                         Long workmateEatingCount) {
        this.id = id;
        this.nameRestaurant = nameRestaurant;
        this.vicinity = vicinity;
        this.opening = opening;
        this.restaurantImg = restaurantImg;
        this.distance = distance;
        this.ratings = ratings;
        this.workmateEatingCount = workmateEatingCount;
    }

    // GETTERS
    @NonNull
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

    @NonNull
    public String getDistance() {
        return distance;
    }

    public float getRatings() {
        return ratings;
    }

    public Long getWorkmateEatingCount() {
        return workmateEatingCount;
    }

    //UTILS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListViewState)) return false;
        ListViewState that = (ListViewState) o;
        return getId().equals(that.getId()) && getDistance().equals(that.getDistance()) && getOpening() == that.getOpening() && getRatings() == that.getRatings() && getNameRestaurant().equals(that.getNameRestaurant()) && getVicinity().equals(that.getVicinity()) && getRestaurantImg().equals(that.getRestaurantImg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNameRestaurant(), getVicinity(), getOpening(), getRestaurantImg(), getDistance(), getRatings());
    }

    @NonNull
    @Override
    public String toString() {
        return "ListViewState{" +
                "id=" + id +
                ", nameRestaurant='" + nameRestaurant + '\'' +
                ", vicinity ='" + vicinity + '\'' +
                ", opening=" + opening +
                ", restaurantImg=" + restaurantImg +
                ", distance =" + distance +
                ", ratings=" + ratings +
                '}';
    }

}

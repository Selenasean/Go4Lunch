package fr.selquicode.go4lunch.ui.list;

import android.media.Image;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * This is the model of the view that the data have to fit into
 * ViewState contains data model use for displaying the UI
 * Here is about a list of restaurant
 */
public class ListViewState {

    @NonNull
    private final int id;

    @NonNull
    private final String nameRestaurant, distance, foodType, address;

    @NonNull
    private boolean opening;

    @NonNull
    private Image restaurantImg;

    @NonNull private int ratings;


    /**
     * Constructor
     * @param id                type int
     * @param nameRestaurant    type String
     * @param distance          type String
     * @param foodType          type String
     * @param address           type String
     * @param restaurantImg     type Image
     * @param opening           type Boolean
     * @param ratings           type int
     */
    public ListViewState(int id,
                         @NonNull String nameRestaurant,
                         @NonNull String distance,
                         @NonNull String foodType,
                         @NonNull String address,
                         @NonNull Image restaurantImg,
                         @NonNull boolean opening,
                         @NonNull int ratings
                         ){
        this.id = id;
        this.nameRestaurant = nameRestaurant;
        this.distance = distance;
        this.foodType = foodType;
        this.address = address;
        this.opening = opening;
        this.restaurantImg = restaurantImg;
        this.ratings = ratings;

    }

    // GETTERS
    public int getId() {
        return id;
    }

    @NonNull
    public String getNameRestaurant() {
        return nameRestaurant;
    }

    @NonNull
    public String getDistance() {
        return distance;
    }

    @NonNull
    public String getFoodType() {
        return foodType;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    @NonNull
    public boolean getOpening() {
        return opening;
    }

    @NonNull
    public Image getRestaurantImg() {
        return restaurantImg;
    }

    public int getRatings() {
        return ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListViewState)) return false;
        ListViewState that = (ListViewState) o;
        return getId() == that.getId() && getOpening() == that.getOpening() && getRatings() == that.getRatings() && getNameRestaurant().equals(that.getNameRestaurant()) && getDistance().equals(that.getDistance()) && getFoodType().equals(that.getFoodType()) && getAddress().equals(that.getAddress()) && getRestaurantImg().equals(that.getRestaurantImg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNameRestaurant(), getDistance(), getFoodType(), getAddress(), getOpening(), getRestaurantImg(), getRatings());
    }

    @Override
    public String toString() {
        return "ListViewState{" +
                "id=" + id +
                ", nameRestaurant='" + nameRestaurant + '\'' +
                ", distance='" + distance + '\'' +
                ", foodType='" + foodType + '\'' +
                ", address='" + address + '\'' +
                ", opening=" + opening +
                ", restaurantImg=" + restaurantImg +
                ", ratings=" + ratings +
                '}';
    }

}

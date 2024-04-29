package fr.selquicode.go4lunch.repositories;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import android.location.Location;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.model.PlaceAutocompletePrediction;
import fr.selquicode.go4lunch.data.model.PlaceDetailsResponse;
import fr.selquicode.go4lunch.data.model.PlacesAutocompleteResponse;
import fr.selquicode.go4lunch.data.model.PlacesNearbySearchResponse;
import fr.selquicode.go4lunch.data.place.PlaceRepository;
import fr.selquicode.go4lunch.data.place.PlacesAPI;
import fr.selquicode.go4lunch.utils.LiveDataTestUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Unit Test on PlaceRepository
 */
@RunWith(JUnit4.class)
public class PlaceRepositoryTest extends TestCase {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final PlacesAPI placesAPI = mock();
    private final Call<PlacesNearbySearchResponse> getPlacesCallMock = mock();
    private final Call<PlaceDetailsResponse> getPlaceDetailsCallMock = mock();
    private final Call<PlacesAutocompleteResponse> getSearchedPlacesCallMock = mock();

    private final PlaceRepository placeRepository = new PlaceRepository(placesAPI);

    private Location mockLocation(Double longitude, Double latitude){
        Location location = mock();
        when(location.getLongitude()).thenReturn(longitude);
        when(location.getLatitude()).thenReturn(latitude);

        return location;
    }

    private Place createPlace(String id){
        return new Place(
                id,
                null,
                "vicinity",
                "name",
                null,
                Collections.emptyList(),
                3,
                "0123456789",
                "website"
        );
    }

    private PlaceAutocompletePrediction createPlaceAutocompletePrediction(String id){
        return new PlaceAutocompletePrediction(
                "description",
                id
        );
    }

    @Test
    public void getPlaces() throws InterruptedException {
        when(placesAPI.getListOfPlaces(anyString())).thenReturn(getPlacesCallMock);

        //method to test
        LiveData<List<Place>> placesLivedata = placeRepository.getPlaces(
                mockLocation(1.0, 2.0)
        );

        ArgumentCaptor<Callback<PlacesNearbySearchResponse>> argumentCaptor = ArgumentCaptor.captor();
        verify(getPlacesCallMock).enqueue(argumentCaptor.capture());

        //get the callback
        Callback<PlacesNearbySearchResponse> callback = argumentCaptor.getValue();
        //simulate the response
        PlacesNearbySearchResponse nearbySearchResponse = new PlacesNearbySearchResponse(Arrays.asList(
                createPlace("id1"), createPlace("id2"))
        );
        callback.onResponse(getPlacesCallMock, Response.success(nearbySearchResponse));

        List<Place> places = LiveDataTestUtils.getOrAwaitValue(placesLivedata);

        assertThat(places).hasSize(2);
        assertThat(places.get(0).getPlaceId()).isEqualTo("id1");
        assertThat(places.get(1).getPlaceId()).isEqualTo("id2");
        verify(placesAPI).getListOfPlaces("2.0,1.0");
        verifyNoMoreInteractions(placesAPI);
    }



    @Test
    public void getPlaceDetails() throws InterruptedException{
        when(placesAPI.getDetailOfPlace(anyString())).thenReturn(getPlaceDetailsCallMock);

        //method to test
        LiveData<Place> placeDetailsLiveData = placeRepository.getPlaceDetails("id1");

        ArgumentCaptor<Callback<PlaceDetailsResponse>> argumentCaptor = ArgumentCaptor.captor();
        verify(getPlaceDetailsCallMock).enqueue(argumentCaptor.capture());

        //get the callback
        Callback<PlaceDetailsResponse> callback = argumentCaptor.getValue();
        //simulate response
        PlaceDetailsResponse placeDetailsResponse = new PlaceDetailsResponse(createPlace("id1"));
        callback.onResponse(getPlaceDetailsCallMock, Response.success(placeDetailsResponse));

        Place placeDetails = LiveDataTestUtils.getOrAwaitValue(placeDetailsLiveData);

        assertThat(placeDetails.getPlaceId()).isEqualTo("id1");
        verify(placesAPI).getDetailOfPlace("id1");
        verifyNoMoreInteractions(placesAPI);
    }

    @Test
    public void getSearchedPlaces() throws InterruptedException{
        when(placesAPI.getSearchedPlaces(anyString(), anyString())).thenReturn(getSearchedPlacesCallMock);

        //method to test
        placeRepository.searchedPlaces("search", mockLocation(1.0, 2.0));
        LiveData<List<String>> placesSearchedIdListLD = placeRepository.getSearchedPlaces();

        ArgumentCaptor<Callback<PlacesAutocompleteResponse>> argumentCaptor = ArgumentCaptor.captor();
        verify(getSearchedPlacesCallMock).enqueue(argumentCaptor.capture()); // not invoked

        //get the callback
        Callback<PlacesAutocompleteResponse> callback = argumentCaptor.getValue();
        //simulate response
        PlacesAutocompleteResponse placesAutocompleteResponse = new PlacesAutocompleteResponse(Arrays.asList(
                createPlaceAutocompletePrediction("id1"),
                createPlaceAutocompletePrediction("id2"),
                createPlaceAutocompletePrediction("id3")
        ));
        callback.onResponse(getSearchedPlacesCallMock, Response.success(placesAutocompleteResponse));
        List<String> listExpected = Arrays.asList("id1","id2","id3");

        List<String> listActual = LiveDataTestUtils.getOrAwaitValue(placesSearchedIdListLD);

        assertThat(listActual).isEqualTo(listExpected);
        verify(placesAPI).getSearchedPlaces("search", "2.0,1.0");
        verifyNoMoreInteractions(placesAPI);
    }
}
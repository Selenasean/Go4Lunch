package fr.selquicode.go4lunch.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.location.Location;
import android.os.Looper;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.utils.LiveDataTestUtils;

@RunWith(JUnit4.class)
public class LocationRepositoryTest {

    private final FusedLocationProviderClient fusedLocationProviderClientMock = mock();
    private final LocationResult locationResultMock = mock();
    private final Looper looperMock = mock();

    private final LocationRepository locationRepository = new LocationRepository(fusedLocationProviderClientMock, looperMock);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    //UTILS METHOD
    private Location mockLocation(Double longitude, Double latitude){
        Location location = mock();
        when(location.getLongitude()).thenReturn(longitude);
        when(location.getLatitude()).thenReturn(latitude);

        return location;
    }

    //TEST
    @Test
    public void getLocationLiveData() throws InterruptedException{
        //get the callback
        ArgumentCaptor<LocationCallback> argumentCaptor = ArgumentCaptor.captor();
        Location locationMock = mockLocation(1.0,2.0);
        when(locationResultMock.getLastLocation()).thenReturn(locationMock);

        //method called
        locationRepository.startLocationRequest();

        verify(fusedLocationProviderClientMock).requestLocationUpdates(
                any(),
                argumentCaptor.capture(),
                Mockito.eq(looperMock)
        );
        LocationCallback callback = argumentCaptor.getValue();
        callback.onLocationResult(locationResultMock);

        //method to verify
        Location locationExpected = LiveDataTestUtils.getOrAwaitValue(locationRepository.getLocationLiveData());

        Truth.assertThat(locationExpected.getLatitude()).isEqualTo(2.0);
        Truth.assertThat(locationExpected.getLongitude()).isEqualTo(1.0);

    }

    @Test
    public void stopLocationRequest(){
        //methods called
        locationRepository.startLocationRequest();
        locationRepository.stopLocationRequest();

        verify(fusedLocationProviderClientMock,times(2)).removeLocationUpdates(any(LocationCallback.class));
    }
}

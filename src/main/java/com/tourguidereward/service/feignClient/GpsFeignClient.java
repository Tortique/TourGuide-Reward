package com.tourguidereward.service.feignClient;

import com.tourguidereward.domain.NearByAttraction;
import com.tourguidereward.domain.User;
import com.tourguidereward.domain.location.Attraction;
import com.tourguidereward.domain.location.VisitedLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "gpsservice", url = "localhost:8082")
public interface GpsFeignClient {
    @GetMapping("/gps/getUserLocation")
    VisitedLocation getUserLocation(@RequestBody User user);

    @GetMapping("/gps/getAttractions")
    List<Attraction> getAttractions();

    @GetMapping("/gps/nearByAttractions")
    List<NearByAttraction> getNearByAttraction(@RequestParam VisitedLocation visitedLocation);
}

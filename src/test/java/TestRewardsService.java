import com.tourguidereward.RewardApplication;
import com.tourguidereward.domain.User;
import com.tourguidereward.domain.UserAndReward;
import com.tourguidereward.domain.UserReward;
import com.tourguidereward.domain.location.Attraction;
import com.tourguidereward.domain.location.Location;
import com.tourguidereward.domain.location.VisitedLocation;
import com.tourguidereward.service.RewardsService;
import com.tourguidereward.service.feignClient.GpsFeignClient;
import com.tourguidereward.util.GetDistance;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes =RewardApplication.class)
public class TestRewardsService {

    @Autowired
    private GetDistance getDistance;

    @Autowired
    private RewardsService rewardsService;

    @Autowired
    private GpsFeignClient gpsFeignClient;

    @Test
    public void calculateRewards() {
        UUID uuid = UUID.randomUUID();
        Location location = new Location(33.817595D, -117.922008D);
        VisitedLocation visitedLocation = new VisitedLocation(uuid, location, new Date());
        List<VisitedLocation> visitedLocations = new ArrayList<>();
        visitedLocations.add(visitedLocation);
        List<UserReward> userRewards = new ArrayList<>();
        UserAndReward user = new UserAndReward(uuid, visitedLocations, userRewards);

        assertEquals(0, user.getUserRewards().size());

        rewardsService.calculateRewards(user);

        assertEquals(1, user.getUserRewards().size());
    }

    @Test
    public void isWithinAttractionProximity() {
        List<Attraction> attractions = new ArrayList<>();
        attractions.add(new Attraction("Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D));
        Attraction attraction = attractions.get(0);
        Assertions.assertTrue(getDistance.isWithinAttractionProximity(attraction, attraction));
    }

    @Test
    public void getDistance() {
        Attraction attraction1 = gpsFeignClient.getAttractions().get(0);
        Attraction attraction2 = gpsFeignClient.getAttractions().get(1);
        double result = getDistance.getDistance(attraction1, attraction2);

        MatcherAssert.assertThat(result, equalTo(774.5207212649109));
    }
}

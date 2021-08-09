package com.tourguidereward.service;

import com.tourguidereward.domain.UserAndReward;
import com.tourguidereward.domain.UserReward;
import com.tourguidereward.domain.location.Attraction;
import com.tourguidereward.domain.location.VisitedLocation;
import com.tourguidereward.service.feignClient.GpsFeignClient;
import com.tourguidereward.util.GetDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class RewardsService implements IRewardsService {
    private final RewardCentral rewardsCentral;
    @Autowired
    private GetDistance getDistance;

    @Autowired
    private GpsFeignClient gpsFeignClient;

    public RewardsService(RewardCentral rewardCentral) {
        this.rewardsCentral = rewardCentral;
    }

    public List<UserReward> calculateRewards(UserAndReward user) {
        CopyOnWriteArrayList<VisitedLocation> userLocations = new CopyOnWriteArrayList<>(user.getVisitedLocations());
        CopyOnWriteArrayList<Attraction> attractions = new CopyOnWriteArrayList<>(gpsFeignClient.getAttractions());

        userLocations.forEach(visitedLocation -> attractions.stream()
                .filter(attraction -> getDistance.nearAttraction(visitedLocation, attraction))
                .forEach(attraction -> {
                    if (user.getUserRewards().stream().noneMatch(reward -> reward.attraction.attractionName.equals(attraction.attractionName))) {
                        user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
                    }
                }));
        return user.getUserRewards();
    }

    public int getRewardPoints(Attraction attraction, UserAndReward user) {
        return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
    }
}

package com.tourguidereward.controller;

import com.tourguidereward.domain.UserAndReward;
import com.tourguidereward.domain.UserReward;
import com.tourguidereward.domain.location.Location;
import com.tourguidereward.service.IRewardsService;
import com.tourguidereward.util.GetDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rewardCentral.RewardCentral;

import java.util.List;
import java.util.UUID;

@RestController
public class RewardController {
    @Autowired
    GetDistance getDistance;

    @Autowired
    IRewardsService rewardsService;

    @Autowired
    RewardCentral rewardCentral;

    @GetMapping("/getAttractionRewardsPoints/{attractionId}/{userId}")
    int getAttractionRewardsPoints(@PathVariable("attractionId") UUID attractionId,
                                   @PathVariable("userId") UUID userId) {
        return rewardCentral.getAttractionRewardPoints(attractionId, userId);
    }

    @RequestMapping("/calculateRewards")
    List<UserReward> calculateRewards(@RequestBody UserAndReward user) {
        return rewardsService.calculateRewards(user);
    }

    @GetMapping("/getDistance")
    double getDistance(@RequestParam Location location, @RequestParam Location location2) {
        return getDistance.getDistance(location, location2);
    }
}

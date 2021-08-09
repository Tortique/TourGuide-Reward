package com.tourguidereward.service;

import com.tourguidereward.domain.UserAndReward;
import com.tourguidereward.domain.UserReward;
import com.tourguidereward.domain.location.Attraction;

import java.util.List;

public interface IRewardsService {
    List<UserReward> calculateRewards(UserAndReward user);

    int getRewardPoints(Attraction attraction, UserAndReward user);
}

package com.tourguidereward;

import com.tourguidereward.service.RewardsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rewardCentral.RewardCentral;

@Configuration
public class TourGuideModule {
	
	@Bean
	public RewardsService getRewardsService() {
		return new RewardsService(getRewardCentral());
	}
	
	@Bean
	public RewardCentral getRewardCentral() {
		return new RewardCentral();
	}
	
}

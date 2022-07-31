package com.crowdfunding.app.service;

import com.crowdfunding.common.dto.ContributionRequestDTO;
import com.crowdfunding.common.dto.ContributionResponseDTO;

public interface IContributionService {
	public ContributionResponseDTO createContribution(String tokenHeader,ContributionRequestDTO contrb);
}

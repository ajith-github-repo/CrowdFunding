package com.crowdfunding.app.validations;

import com.crowdfunding.common.dto.ContributionRequestDTO;

public interface IContributionIOValidator {

public void validate(ContributionRequestDTO contrib);
}

package com.healthgov.model;

public enum InfrastructureStatus {

	OPERATIONAL, // Open and usable
	UNDER_MAINTENANCE, // Closed for repair
	TEMPORARILY_CLOSED, // Closed but expected to reopen
	DECOMMISSIONED // Permanently closed
}

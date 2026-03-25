package com.healthgov.model;

public enum ResourceStatus {
	PENDING, // Not yet approved / not yet available
	ACTIVE, // In use / available
	COMPLETED, // Fully utilized / purpose fulfilled
	INACTIVE // Temporarily not usable
}
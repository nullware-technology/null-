package com.nullware.ms_users.repository;

import com.nullware.ms_users.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepository extends JpaRepository<Plan, Long> {
}

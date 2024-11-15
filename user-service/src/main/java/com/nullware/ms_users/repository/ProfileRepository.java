package com.nullware.ms_users.repository;

import com.nullware.ms_users.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}

package com.nullware.ms_auth.repository;

import com.nullware.ms_auth.dtos.UserAuthDTO;
import com.nullware.ms_auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        SELECT new com.nullware.ms_auth.dtos.UserAuthDTO(
            u.name, 
            u.email, 
            u.password, 
            p
        )
        FROM User u
        LEFT JOIN u.plan p
        WHERE u.email = :email
    """)
    Optional<UserAuthDTO> findUserDTOByEmail(@Param("email") String email);

    Optional<User> findByEmail(String email);
}

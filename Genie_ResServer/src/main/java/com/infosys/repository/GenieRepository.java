package com.infosys.repository;

import com.infosys.model.Genie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenieRepository extends JpaRepository<Genie, Long> {
    Optional<Genie> findByEmail(String email);

    @Query("SELECT g FROM Genie g LEFT JOIN FETCH g.wishes WHERE g.email = :email")
    Optional<Genie> findByEmailWithWishes(@Param("email") String email);
}

package com.tinie.Services.repositories;

import com.tinie.Services.models.LoginRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoginEntryRepository extends JpaRepository<LoginRecords, Long> {

    /**
     * Retrieve a {@link LoginRecords} matching given {@code phonenumber}
     * @param phoneNumber Phone number to match on
     * @return An {@link Optional} of {@link LoginRecords} or {@link Optional#empty()}
     */
    @Query(value = "select * from login_records where phone_number = :phone", nativeQuery = true)
    Optional<LoginRecords> findByPhoneNumber(@Param("phone") long phoneNumber);
}


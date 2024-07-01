package com.example.timetracker.repository;

import com.example.timetracker.entity.Location;
import com.example.timetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByUseremail(String email);
}


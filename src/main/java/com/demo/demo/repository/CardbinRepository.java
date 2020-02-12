package com.demo.demo.repository;

import com.demo.demo.entity.Attribute;
import com.demo.demo.entity.Cardbin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardbinRepository extends JpaRepository<Cardbin, Integer> {
    Optional<Cardbin> findByBinNumber(Integer binNumber);
}

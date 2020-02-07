package com.demo.demo.repository;

import com.demo.demo.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
    Optional<Attribute> findByKey(String key);
    List<Attribute> findAllByCardbin_Id(Integer id);
}

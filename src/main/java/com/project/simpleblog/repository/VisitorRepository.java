package com.project.simpleblog.repository;

import com.project.simpleblog.models.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor,Long> {

    Optional<Visitor> findByFullname(String fullname);

}

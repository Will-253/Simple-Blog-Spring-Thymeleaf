package com.project.simpleblog.service;

import com.project.simpleblog.models.Visitor;
import com.project.simpleblog.repository.VisitorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public Optional<Visitor> findByFullname(String fullname) {
        return visitorRepository.findByFullname(fullname);
    }

    public Visitor saveVisitor(Visitor visitor) {
        return visitorRepository.save(visitor);
    }
}

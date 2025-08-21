package com.project.simpleblog.service;

import com.project.simpleblog.models.Author;
import com.project.simpleblog.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> GetById(Long id){
        if (id==null) {
            return Optional.empty();
        }
        return authorRepository.findById(id);
    }

    public Author addAuthor(Author author) {
        if (author==null || author.getFullName()==null||author.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be null or empty");
        }

        Optional<Author> existingAuthor= authorRepository.findByFullName(author.getFullName());
        if (existingAuthor.isPresent()) {
            return existingAuthor.get();
        }
        return authorRepository.save(author);
    }

    public Optional<Author> findByFullName(String fullName) {
        return authorRepository.findByFullName(fullName);
    }
}

package ru.sterkhov_kirill.NauJava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sterkhov_kirill.NauJava.dto.CustomFilterBookReq;
import ru.sterkhov_kirill.NauJava.dto.UserCollectionsRes;
import ru.sterkhov_kirill.NauJava.repository.BookRepository;
import ru.sterkhov_kirill.NauJava.repository.UserCollectionRepositoryCriteriaImpl;

import java.util.Optional;

@RestController
@RequestMapping("/custom")
@RequiredArgsConstructor
public class CustomController {

    private final UserCollectionRepositoryCriteriaImpl userCollectionRepositoryCriteria;
    private final BookRepository bookRepository;

    @PostMapping("/book/filter")
    public ResponseEntity<?> getBooksWithFilter(@RequestBody CustomFilterBookReq req) {
        return ResponseEntity.of(
                Optional.ofNullable(
                        bookRepository.findByGenreAndIsAvailableForOnline(req.genre(), req.isAvailableForOnline())
                ));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getCollectionsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(UserCollectionsRes.fromEntities(userCollectionRepositoryCriteria.findByUsername(username)));
    }
}

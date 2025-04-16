package ru.sterkhov_kirill.NauJava.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(unique = true)
    private String isbn;

    private String genre;

    @Column(name = "total_copies", nullable = false)
    private int totalCopies = 1;

    @Column(name = "available_copies", nullable = false)
    private int availableCopies = 1;

    @Column(name = "is_available_for_online")
    private boolean isAvailableForOnline = Boolean.FALSE;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "books")
    private List<UserCollectionEntity> userCollections = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<ReservationEntity> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<CheckoutEntity> checkouts = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookCollectionBookEntity> bookCollections = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

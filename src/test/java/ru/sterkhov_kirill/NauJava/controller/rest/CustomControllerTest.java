package ru.sterkhov_kirill.NauJava.controller.rest;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.sterkhov_kirill.NauJava.dto.CustomFilterBookReq;
import ru.sterkhov_kirill.NauJava.entity.BookEntity;
import ru.sterkhov_kirill.NauJava.entity.UserCollectionEntity;
import ru.sterkhov_kirill.NauJava.entity.UserEntity;
import ru.sterkhov_kirill.NauJava.repository.BookRepository;
import ru.sterkhov_kirill.NauJava.repository.UserCollectionRepositoryCriteriaImpl;

import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(controllers = CustomController.class)
@AutoConfigureMockMvc
class CustomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookRepository bookRepository;

    @MockitoBean
    private UserCollectionRepositoryCriteriaImpl userCollectionRepositoryCriteria;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void getBooksWithFilter_shouldReturn200AndList() {
        BookEntity b1 = new BookEntity();
        b1.setId(1L);
        b1.setTitle("A");
        BookEntity b2 = new BookEntity();
        b2.setId(2L);
        b2.setTitle("B");
        when(bookRepository.findByGenreAndIsAvailableForOnline("SciFi", true))
                .thenReturn(List.of(b1, b2));

        RestAssuredMockMvc
                .given()
                .auth().with(user("test").roles("USER"), csrf())
                .contentType(ContentType.JSON)
                .body(new CustomFilterBookReq("SciFi", true))
                .when()
                .post("/custom/book/filter")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("id", hasItems(1, 2));

        verify(bookRepository).findByGenreAndIsAvailableForOnline("SciFi", true);
    }

    @Test
    void getBooksWithFilter_shouldReturn404WhenNull() {
        when(bookRepository.findByGenreAndIsAvailableForOnline("Drama", false))
                .thenReturn(null);

        RestAssuredMockMvc
                .given()
                .auth().with(user("test").roles("USER"), csrf())
                .contentType(ContentType.JSON)
                .body(new CustomFilterBookReq("Drama", false))
                .when()
                .post("/custom/book/filter")
                .then()
                .statusCode(404);

        verify(bookRepository).findByGenreAndIsAvailableForOnline("Drama", false);
    }

    @Test
    void getBooksWithFilter_shouldReturn400OnBadRequest() {
        RestAssuredMockMvc
                .given()
                .auth().with(user("test").roles("USER"), csrf())
                .contentType(ContentType.JSON)
                .when()
                .post("/custom/book/filter")
                .then()
                .statusCode(400);

        RestAssuredMockMvc
                .given()
                .auth().with(user("test").roles("USER"), csrf())
                .contentType(ContentType.JSON)
                .body("{\"wrongField\":\"x\"}")
                .when()
                .post("/custom/book/filter")
                .then()
                .statusCode(400);
    }

    @Test
    void getCollectionsByUsername_shouldReturn200AndList() {
        UserEntity user = new UserEntity();
        user.setUsername("john");
        UserCollectionEntity c1 = new UserCollectionEntity();
        c1.setId(10L);
        c1.setUser(user);
        UserCollectionEntity c2 = new UserCollectionEntity();
        c2.setId(20L);
        c2.setUser(user);
        when(userCollectionRepositoryCriteria.findByUsername("john"))
                .thenReturn(List.of(c1, c2));

        RestAssuredMockMvc
                .given()
                .auth().with(user("test").roles("USER"))
                .when()
                .get("/custom/user/john")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("collections.size()", is(2));

        verify(userCollectionRepositoryCriteria).findByUsername("john");
    }

    @Test
    void getCollectionsByUsername_shouldReturn200EmptyList() {
        when(userCollectionRepositoryCriteria.findByUsername("unknown"))
                .thenReturn(List.of());

        RestAssuredMockMvc
                .given()
                .auth().with(user("test").roles("USER"))
                .when()
                .get("/custom/user/unknown")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(0));

        verify(userCollectionRepositoryCriteria).findByUsername("unknown");
    }
}
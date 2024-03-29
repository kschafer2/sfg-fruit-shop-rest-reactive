package guru.springframework.sfgfruitshoprestreactive.controllers;

import guru.springframework.sfgfruitshoprestreactive.domain.Category;
import guru.springframework.sfgfruitshoprestreactive.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static guru.springframework.sfgfruitshoprestreactive.controllers.CategoryController.CATEGORIES_BASE_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class CategoryControllerTest {

    public static final String ID = "id";
    public static final String CATEGORY_ID_URL = CATEGORIES_BASE_URL + "/" + ID;
    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void getAllCategoriesTest() {
        given(categoryRepository.findAll())
                .willReturn(Flux.just(
                Category.builder().name("name1").build(),
                Category.builder().name("name2").build()));

        webTestClient.get()
                .uri(CATEGORIES_BASE_URL)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getCategoryByIdTest() {
        given(categoryRepository.findById(ID))
                .willReturn(Mono.just(Category.builder().name("name").build()));

        webTestClient.get()
                .uri(CATEGORY_ID_URL)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Category.class);
    }

    @Test
    public void createNewCategoryTest() {
        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(new Category()));

        Mono<Category> categoryToSave = Mono.just(new Category());

        webTestClient.post()
                .uri(CATEGORIES_BASE_URL)
                .body(categoryToSave, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void overwriteCategoryTest() {
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(new Category()));

        Mono<Category> categoryToUpdate = Mono.just(new Category());

        webTestClient.put()
                .uri(CATEGORY_ID_URL)
                .body(categoryToUpdate, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
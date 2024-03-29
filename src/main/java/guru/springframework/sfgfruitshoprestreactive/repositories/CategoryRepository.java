package guru.springframework.sfgfruitshoprestreactive.repositories;

import guru.springframework.sfgfruitshoprestreactive.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}

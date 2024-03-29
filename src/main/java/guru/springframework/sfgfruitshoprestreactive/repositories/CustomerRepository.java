package guru.springframework.sfgfruitshoprestreactive.repositories;

import guru.springframework.sfgfruitshoprestreactive.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}

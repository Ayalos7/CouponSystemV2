package Ayal.ProjectSpring.Repositories;

import Ayal.ProjectSpring.Beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    Customer findById(int id);

    Customer findByEmailAndPassword(String email, String password);

    Customer findByEmail(String email);
}

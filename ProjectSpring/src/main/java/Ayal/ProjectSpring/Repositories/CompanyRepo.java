package Ayal.ProjectSpring.Repositories;

import Ayal.ProjectSpring.Beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

    Company findById(int id);

    Company findByEmailAndPassword(String email, String password);

    Company findByEmail(String email);

    Company findByName(String name);
}

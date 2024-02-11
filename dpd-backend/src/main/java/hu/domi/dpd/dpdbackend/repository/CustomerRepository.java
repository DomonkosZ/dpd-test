package hu.domi.dpd.dpdbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import hu.domi.dpd.dpdbackend.entity.CustomerData;

public interface CustomerRepository extends JpaRepository<CustomerData, Long>  {

}

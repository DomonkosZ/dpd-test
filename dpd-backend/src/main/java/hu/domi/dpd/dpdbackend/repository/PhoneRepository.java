package hu.domi.dpd.dpdbackend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import hu.domi.dpd.dpdbackend.entity.CustomerData;
import hu.domi.dpd.dpdbackend.entity.PhoneNumber;

public interface PhoneRepository extends JpaRepository<PhoneNumber, Long>  {
    List<PhoneNumber> findByUser(CustomerData customer);

    void deleteAllByUser(CustomerData customerData);
}

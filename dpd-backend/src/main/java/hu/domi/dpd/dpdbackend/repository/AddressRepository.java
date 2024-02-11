package hu.domi.dpd.dpdbackend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import hu.domi.dpd.dpdbackend.entity.Address;
import hu.domi.dpd.dpdbackend.entity.CustomerData;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(CustomerData customer);

    void deleteAllByUser(CustomerData customerData);
}

package hu.domi.dpd.dpdbackend.entity;

import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customer_data")
@Data
public class CustomerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String placeOfBirth;

    @Column(nullable = false)
    private String motherName;

    @Column(nullable = false)
    private String tajNumber;

    private String taxIdentifier;

    @Column(nullable = false)
    private String email;

}

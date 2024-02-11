package hu.domi.dpd.dpdbackend.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "address")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private CustomerData user;

    @Column
    private String zipcode;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private String houseNumber;

    @Column
    private String additionalInfo;
}


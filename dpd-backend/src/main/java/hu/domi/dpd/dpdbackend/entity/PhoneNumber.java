package hu.domi.dpd.dpdbackend.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "phone_number")
@Data
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private CustomerData user;

    private String phoneNumber;
    private String phoneType;

}

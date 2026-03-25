package domain;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
public class AppUser extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    //Login details
    @Column(name = "user_name", unique = true, length = 50)
    public String userName;

    @Column(name = "password", length = 60)
    public String password;

    //Email
    @Column(unique = true, nullable = false)
    public String email;

    //Profile details
    @Column(name = "first_name", length = 60)
    public String firstName;
    @Column(name = "last_name", length = 60)
    public String lastName;
    public LocalDate birth;
    public String address;
    public String city;

    //Created at
    @Column(name = "created_at")
    public LocalDateTime createdAt = LocalDateTime.now();

    //Find user by email
    public static AppUser findByEmail(String email) {
        return find("email", email).firstResult();
    }


}

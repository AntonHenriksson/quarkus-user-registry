package domain;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
public class AppUser extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    //Roles for jwt
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    public Set<String> roles;

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

    //Find user by userName
    public static AppUser findByUserName(String userName) {
        return find("userName", userName).firstResult();
    }

}

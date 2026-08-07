package project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //tells JPA this class is db table    I used @Entity because the User class should be/MUST connected to the database. It allows JPA to map the class into a table, where each user account is saved as a row.

@Table(name = "users") //table name in db
public class User {

    @Id //pk dh el bymyz kol user 3n el tny 
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //generates the IDS automatically. 
    private Long id;  //stores the unique id 

    private String username;
    private String email;
    private String password;
    private String role;

    public User() { //empty constructor. JPA needs it to create objects automatically when reading from the database.
    }

    public User(String username, String email, String password, String role) {
        this.username = username; //save the entered email,pass and so on.
        this.email = email;
        this.password = password;
        this.role = role; //This saves the selected role, either ADMIN or USER.
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }
   
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

//This class represents the users table in the database. 
//I used @Entity to tell Spring Boot that this class should be mapped to a database table.
//I used @Table(name = "users") to name the table users.
//The id field is the primary key and it is generated automatically.
//The username, email, password, and role fields store the user account data.
// The role field is important because it separates admin users from normal users.
//The empty constructor is required by JPA, and the second constructor is used when creating a new user during registration.
//The getters and setters allow the controller and repository to read and update user data.


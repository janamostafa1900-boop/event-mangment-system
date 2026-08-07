package project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> { //user means:this repository works with the user table automatically. Long means:the primary key type of the user.
   //JpaRepository, which gives you ready-made database methods like:
//save()  findAll()  findById() delete()  So you do not need to write SQL manually.
	
	
    // used to check if username already exists
    User findByUsername(String username); //it acts like:SELECT * FROM users WHERE username = ?;

    // used for login
    User findByUsernameAndPassword(String username, String password); //acts like:SELECT * FROM users WHERE username = ? AND password = ?;

}
//these two method call it from the Authcontroller (repository methods are called from the controller).
//The repository is the database access layer. It separates database operations from the controller. Instead of writing SQL queries manually, I use JpaRepository methods like save and custom query methods like findByUsername. Without the repository, the system could not store users or check login data easily.

package project;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller //handles web request and return html pg.
public class AuthController {

    @Autowired //give the class what it need from outside instead of creating it from inside.
    private UserRepo userRepo; //nt3ml m3 db direct.

    @GetMapping("/")//el page el r2ysya lel website
    public String index() {//lma user opens the website the spring y48l el index
        return "redirect:/login";
    }  // when the user open the website for the first time he directly take him to the login pg.
    //runs when the user open the main website 
    @GetMapping("/register")
    public String registerPage(Model model) {//we added Modelmodel bec we want to send object to the html(Model is container to send data to html)
        model.addAttribute("user", new User());//create empty/new user obj and send it to register pg. 
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {   //take the info from the form    

        if (user.getUsername() == null || user.getUsername().trim().isEmpty() //take the username that inside the object,trim(remove the space from the start&end),empty(note the space t3tbr charc)
                || user.getEmail() == null || user.getEmail().trim().isEmpty()
                || user.getPassword() == null || user.getPassword().trim().isEmpty()
                || user.getRole() == null || user.getRole().trim().isEmpty()) {

            model.addAttribute("error", "All fields are required");
            return "register";
        }
        
        user.setUsername(user.getUsername().trim()); //fkrto en b3d ma yzbt 4klo y7fz 4klo el gdeed b2a ex" jana  "  after:"jana"
        user.setEmail(user.getEmail().trim());
        user.setRole(user.getRole().trim().toUpperCase());
        
        
        if (!user.getRole().equals("ADMIN") && !user.getRole().equals("USER")) { //if didnt choose admin and user print error. 
            model.addAttribute("error", "Please choose a valid role");
            return "register";
        }
        
        User oldUser = userRepo.findByUsername(user.getUsername());//search in the db about the user with the same username 

        if (oldUser != null) { //if username found 
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        userRepo.save(user); //save the user inside the db.

        model.addAttribute("message", "Account created successfully. Please login.");
        return "login";
    }
        
    @GetMapping("/login")
    public String loginPage(@CookieValue(value = "username", required = false) String usernameCookie,
                            Model model) {//Modelmodel use it to send info to html pg.

        model.addAttribute("user", new User());

        if (usernameCookie != null && !usernameCookie.isEmpty()) {
            model.addAttribute("lastUsername", usernameCookie);//if the cookie found send it to the pg 
        }

        return "login";
    }
    
    
    @PostMapping("/login")
    public String login(@ModelAttribute User loginUser,
                        HttpSession session,//we use session so that the website keeps remember the user after the login.
                        HttpServletResponse response, //use it to send response to the user specially something like response.addCookie(cookie);
                        Model model) {  //we use it to send errors or message to the html pg

        if (loginUser.getUsername() == null || loginUser.getUsername().trim().isEmpty()  //b3d 2zlt el msft still empty? 
                || loginUser.getPassword() == null || loginUser.getPassword().trim().isEmpty()) {

            model.addAttribute("error", "Username and password are required");
            return "login";
        }
        
        String username = loginUser.getUsername().trim(); //get the usename the the user wrote it with removing the spaces 
        String password = loginUser.getPassword();

        User user = userRepo.findByUsernameAndPassword(username, password); //stroe the login info insdie the db

        if (user == null) { //user not found in the db.
            model.addAttribute("error", "Wrong username or password"); //send error .
            return "login";
        }   
        
        session.setAttribute("loggedUser", user); //we store the user in the session  loggedUser:name of the attribute inside the session.,user:is the obj comeing back from the db after the login.
        session.setAttribute("userId", user.getId());
        session.setAttribute("role", user.getRole());

        Cookie cookie = new Cookie("username", user.getUsername()); //create new cookie.
        cookie.setMaxAge(60 * 60); //for one hour.
        cookie.setPath("/"); //cookie is available in all pages.
        response.addCookie(cookie);

        if ("ADMIN".equals(user.getRole())) { //is the user admin?
            return "redirect:/admin/home";
        }
 
        return "redirect:/user/home";
    }
    
    @GetMapping("/logout") //runs when user opens logout
    public String logout(HttpSession session) { //recieve the current session 
        session.invalidate(); //distroy the session 
        return "redirect:/login"; //return as if he didnt login yet.
    }
    @GetMapping("/admin/home")
    public String adminHome(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser"); //first bring the user that is stored inside the session (User)this is casting

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("user", user); //send the user to the pg.
        return "admin_home";
    }
    
    @GetMapping("/user/home")
    public String userHome(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"USER".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "user_home";
    }
}


        
    


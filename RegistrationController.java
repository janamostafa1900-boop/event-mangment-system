package project;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final RegistrationRepository registrationRepo;

    public RegistrationController(RegistrationRepository registrationRepo) {
        this.registrationRepo = registrationRepo;
    }

    @PostMapping("/register-event")
    public String registerForEvent(@RequestParam Long eventId,
                                   HttpSession session) {

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        Long userId = loggedUser.getId();

        boolean alreadyRegistered =
                registrationRepo.findByUserIdAndEventId(userId, eventId).isPresent();

        if (!alreadyRegistered) {
            Registration reg = new Registration(userId, eventId);
            registrationRepo.save(reg);
        }

        return "redirect:/my_events";
    }
}

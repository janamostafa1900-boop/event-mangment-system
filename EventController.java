package project;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private RegistrationRepository registrationRepo;

    @GetMapping("/events")
    public String showEvents(Model model, HttpSession session) {

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<Event> events = eventRepo.findAll();

        model.addAttribute("events", events);
        model.addAttribute("loggedUser", loggedUser);

        return "events";
    }

    @GetMapping("/my_events")
    public String myEvents(Model model, HttpSession session) {

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<Registration> registrations = registrationRepo.findByUserId(loggedUser.getId());
        List<Event> myEvents = new ArrayList<>();

        for (Registration reg : registrations) {
            eventRepo.findById(reg.getEventId()).ifPresent(myEvents::add);
        }

        model.addAttribute("myEvents", myEvents);
        model.addAttribute("user", loggedUser);

        return "my_events";
    }
}


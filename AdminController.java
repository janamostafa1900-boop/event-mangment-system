package project;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    private EventRepo eventRepo;

    @GetMapping("/admin/add-event")
    public String addEventPage(HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        return "add_event";
    }

    @PostMapping("/admin/add-event")
    public String addEvent(@ModelAttribute Event event, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        if (event.getTitle() == null || event.getTitle().trim().isEmpty()
                || event.getDate() == null || event.getDate().trim().isEmpty()
                || event.getLocation() == null || event.getLocation().trim().isEmpty()) {
            model.addAttribute("error", "Please fill all required fields");
            return "add_event";
        }

        eventRepo.save(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/admin/events")
    public String adminEvents(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("events", eventRepo.findAll());
        return "admin_events";
    }

    @GetMapping("/admin/delete-event/{id}")
    public String deleteEvent(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        eventRepo.deleteById(id);
        return "redirect:/admin/events";
    }
}


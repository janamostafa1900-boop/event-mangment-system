package project;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Registration")
public class Registration {

@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long id;
    private Long userId;
    private Long eventId;

public Registration() {
}
public Registration(Long userId, Long eventId) {
    this.userId = userId;
    this.eventId = eventId;
}
public Long getId() {
    return id;
}

public Long getUserId() {
    return userId;
}
public void setUserId(Long userId) {
    this.userId = userId;
}

public Long getEventId() {

    return eventId;
}
public void setEventId(Long eventId) {
    this.eventId = eventId;
}
}
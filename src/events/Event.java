package events;

import java.util.Date;
import java.util.UUID;

public class Event {
    private UUID id;
    private Date date;
    private TypeEvent type;

    public Event(TypeEvent type) {
        this.id = UUID.randomUUID();
        this.date = new Date();
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public TypeEvent getType() {
        return type;
    }
}

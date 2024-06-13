import java.util.UUID;

public abstract class Panel {
    private final UUID identifier;
    private Locker locker;

    public Panel(Locker locker) {
        this.identifier = UUID.randomUUID();
        this.locker = locker;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }
}

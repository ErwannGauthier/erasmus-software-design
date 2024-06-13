public interface State {
    void handleEvent(Event event);

    String getStatus();
}

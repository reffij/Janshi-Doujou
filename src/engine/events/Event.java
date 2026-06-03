package engine.events;

public class Event<V> {
    private final EventType type;
    private final double timeStamp;
    private final V data;

    public Event(EventType type, V data, double timeStamp) {
        this.type = type;
        this.data = data;
        this.timeStamp = timeStamp;
    }

    public Event(EventType type, V data) {
        this(type, data, -1);
    }

    public Event(EventType type) {
        this(type, null, -1);
    }

    public EventType getType() {
        return this.type;
    }

    public <V> V getData(Class<V> clazz) {
        return clazz.cast(data);
    }

    public String toString() {
        String dataString = "";
        if (!(this.data == null)) dataString = " Data:" + this.data.toString();
        return this.type.toString() + dataString;
    }

    public Event<V> withTime(double time) {
        return new Event<>(this.type, this.data, time);
    }

    public double getTimeStamp() {
        return this.timeStamp;
    }
}

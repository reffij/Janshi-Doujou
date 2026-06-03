package engine.commands;

public class Command<T extends Enum<T> & CommandType<T>, V> {
    private final T type;
    private final double timeStamp;
    private final V data;

    public Command(T type, V data, double timeStamp) {
        this.type = type;
        this.data = data;
        this.timeStamp = timeStamp;
    }

    public Command(T type, V data) {
        this(type, data, -1);
    }

    public Command(T type) {
        this(type, null, -1);
    }

    public T getType() {
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

    public Command<T, V> withTime(double time) {
        return new Command<>(this.type, this.data, time);
    }

    public double getTimeStamp() {
        return this.timeStamp;
    }

}

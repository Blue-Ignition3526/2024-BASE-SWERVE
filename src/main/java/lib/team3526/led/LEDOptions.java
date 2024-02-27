package lib.team3526.led;

public class LEDOptions {
    int channel;
    int length;

    public LEDOptions() {}

    public LEDOptions(int channel, int length) {
        this.channel = channel;
        this.length = length;
    }

    public int getChannel() {
        return channel;
    }

    public LEDOptions setChannel(int channel) {
        this.channel = channel;
        return this;
    }

    public int getLength() {
        return length;
    }

    public LEDOptions setLength(int length) {
        this.length = length;
        return this;
    }
}

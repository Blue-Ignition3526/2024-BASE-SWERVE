package lib.team3526.led;

import java.util.function.Consumer;

public class AddressableLEDSegment {
    private int start;
    private int end;
    private int length;

    public AddressableLEDBuffer data;
    private Consumer<AddressableLEDBuffer> animation = null;
    private boolean isPlayingAnimation = false;

    public AddressableLEDSegment(int start, int end) {
        this.start = start;
        this.end = end;
        this.length = end - start;

        this.data = new AddressableLEDBuffer(this.length);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getLength() {
        return length;
    }

    public AddressableLEDSegment setAnimation(Consumer<AddressableLEDBuffer> animation) {
        this.animation = animation;
        this.isPlayingAnimation = true;
        return this;
    }

    public AddressableLEDSegment stopAnimation() {
        this.isPlayingAnimation = false;
        return this;
    }

    public AddressableLEDSegment resumeAnimation() {
        this.isPlayingAnimation = true;
        return this;
    }

    public void update() {
        if (this.animation != null && isPlayingAnimation) this.animation.accept(data);
    }
}

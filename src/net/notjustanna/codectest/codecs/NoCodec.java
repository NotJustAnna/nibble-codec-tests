package net.notjustanna.codectest.codecs;

/**
 * A no-implementation codec.
 */
public class NoCodec implements Codec {
    private static final Codec INSTANCE = new NoCodec();

    private NoCodec() {
    }

    @Override
    public byte[] codec(byte[] src) {
        return src;
    }

    @Override
    public byte[] uncodec(byte[] bin) {
        return bin;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

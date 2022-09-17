package net.notjustanna.codectest.codecs;

/**
 * "Hafu" codec is a simple codec that uses the assumption `bytes of "src" are between 0 and 15`
 * to compress the byte array losslessly in half.
 */
public class HafuCodec implements Codec {
    public static final Codec INSTANCE = new HafuCodec();

    private HafuCodec() {
    }

    @Override
    public byte[] codec(byte[] src) {
        byte[] bin = new byte[src.length / 2];
        int i = 0;
        for (int j = 0; j < bin.length; j++) {
            bin[j] = ((byte) ((src[i++] << 4) | (src[i++] & 15)));
        }
        return bin;
    }

    @Override
    public byte[] uncodec(byte[] bin) {
        byte[] src = new byte[bin.length * 2];
        int i = 0;
        for (byte b : bin) {
            var v = ((int) b) & 0xff;
            src[i++] = (byte) (v >> 4);
            src[i++] = (byte) (v & 0b1111);
        }
        return src;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

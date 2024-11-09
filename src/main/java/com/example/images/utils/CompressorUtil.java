package com.example.images.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressorUtil {

    public static final int BITE_SIZE = 4 * 1024;

    public static byte[] compressFile(byte[] file) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(file);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(file.length);
        byte[] tmp = new byte[BITE_SIZE];

        while(!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp,0, size);
        }

        outputStream.close();
        return outputStream.toByteArray();
    }

    public static byte[] decompressFile(byte[] file) throws DataFormatException, IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(file.length);
        byte[] tmp = new byte[BITE_SIZE];

        while (!inflater.finished()) {
            int count = inflater.inflate(tmp);
            outputStream.write(tmp, 0, count);
        }

        outputStream.close();
        return outputStream.toByteArray();
    }
}

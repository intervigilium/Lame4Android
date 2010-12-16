/* Decoder.java
   A port of LAME for Android

   Copyright (c) 2010 Ethan Chen

   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2 of the License, or (at your option) any later version.
	
   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.intervigil.lame;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.sourceforge.lame.Lame;

import com.intervigil.wave.WaveWriter;

public class Decoder {
    private static final int MP3_BUFFER_SIZE = 1152;
    private static final int INPUT_STREAM_BUFFER = 8192;
    private WaveWriter waveWriter;
    private File inFile;
    private File outFile;

    private BufferedInputStream in;

    public Decoder(File inFile, File outFile) {
        this.inFile = inFile;
        this.outFile = outFile;
    }

    public void initialize() throws IOException {
        waveWriter = new WaveWriter(outFile, 44100, 2, 16);
        waveWriter.createWaveFile();
        in = new BufferedInputStream(new FileInputStream(inFile),
                INPUT_STREAM_BUFFER);
        Lame.initDecoder();
        Lame.configDecoder(in);
    }

    public void decode() throws IOException {
        if (waveWriter != null && in != null) {
            int len, samplesRead;
            short[] leftBuffer = new short[MP3_BUFFER_SIZE];
            short[] rightBuffer = new short[MP3_BUFFER_SIZE];
            byte[] buf = new byte[MP3_BUFFER_SIZE * 4];

            do {
                len = in.read(buf);
                if (len > 0) {
                    samplesRead = Lame.decodeMp3(buf, len, leftBuffer, rightBuffer);
                    // only write the left buffer until I figure out everything else
                    waveWriter.write(leftBuffer, samplesRead);
                }
            } while (len > -1);
        }
    }

    public void cleanup() {
        try {
            if (waveWriter != null) {
                waveWriter.closeWaveFile();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            // failed to close mp3 file or close output file
            // TODO: actually handle an error here
            e.printStackTrace();
        }
        Lame.closeDecoder();
    }
}

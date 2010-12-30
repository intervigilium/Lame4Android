/* Encoder.java
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sourceforge.lame.Lame;

import com.intervigil.wave.WaveReader;

public class Encoder {
    private static final int WAVE_CHUNK_SIZE = 8192;
    private static final int OUTPUT_STREAM_BUFFER = 8192;
    private WaveReader waveReader;
    private File inFile;
    private File outFile;

    private BufferedOutputStream out;

    public Encoder(File in, File out) {
        this.inFile = in;
        this.outFile = out;
    }

    public void initialize() throws FileNotFoundException, IOException {
        waveReader = new WaveReader(inFile);
        waveReader.openWave();
        out = new BufferedOutputStream(new FileOutputStream(outFile),
                OUTPUT_STREAM_BUFFER);
        Lame.initializeEncoder(waveReader.getSampleRate(),
                waveReader.getChannels());
    }

    public void setPreset(int preset) {
        if (waveReader != null && out != null) {
            Lame.setEncoderPreset(preset);
        }
    }

    public void encode() throws IOException {
        if (waveReader != null && out != null) {
            short[] left = new short[WAVE_CHUNK_SIZE];
            short[] right = new short[WAVE_CHUNK_SIZE];
            byte[] mp3Buf = new byte[OUTPUT_STREAM_BUFFER];
            int samplesRead;
            int bytesEncoded;

            while (true) {
                if (waveReader.getChannels() == 2) {
                    samplesRead = waveReader.read(left, right, WAVE_CHUNK_SIZE);
                    if (samplesRead > 0) {
                        bytesEncoded = Lame.encode(left, right,
                                samplesRead, mp3Buf, OUTPUT_STREAM_BUFFER);
                        out.write(mp3Buf, 0, bytesEncoded);
                    } else {
                        break;
                    }
                } else {
                    samplesRead = waveReader.read(left, WAVE_CHUNK_SIZE);
                    if (samplesRead > 0) {
                        bytesEncoded = Lame.encode(left, left,
                                samplesRead, mp3Buf, OUTPUT_STREAM_BUFFER);
                        out.write(mp3Buf, 0, bytesEncoded);
                    } else {
                        break;
                    }
                }
            }
            bytesEncoded = Lame.flushEncoder(mp3Buf, mp3Buf.length);
            out.write(mp3Buf, 0, bytesEncoded);
            // TODO: write Xing VBR/INFO tag to mp3 file here
            out.flush();
        }
    }

    public void cleanup() {
        try {
            if (waveReader != null) {
                waveReader.closeWaveFile();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            // failed to close wave file or close output file
            // TODO: actually handle an error here
            e.printStackTrace();
        }
        Lame.closeEncoder();
    }
}

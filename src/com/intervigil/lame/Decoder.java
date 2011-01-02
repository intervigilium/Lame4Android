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
    private static final int DEFAULT_FRAME_SIZE = 1152;
    private static final int INPUT_STREAM_BUFFER = 8192;
    private static final int MP3_SAMPLE_DELAY = 528;
    private static final int MP3_ENCODER_DELAY = 576;
    private WaveWriter waveWriter;
    private File inFile;
    private File outFile;

    private BufferedInputStream in;

    public Decoder(File inFile, File outFile) {
        this.inFile = inFile;
        this.outFile = outFile;
    }

    public void initialize() throws IOException {
        in = new BufferedInputStream(new FileInputStream(inFile),
                INPUT_STREAM_BUFFER);
        lameInitialize(in);

        waveWriter = new WaveWriter(outFile, Lame.getDecoderSampleRate(), Lame.getDecoderChannels(), 16);
        waveWriter.createWaveFile();
    }

    private static int lameInitialize(BufferedInputStream in) throws IOException {
        int ret = 0;
        ret = Lame.initializeDecoder();
        ret = Lame.configureDecoder(in);
        return ret;
    }

    public void decode() throws IOException {
        if (waveWriter != null && in != null) {
            int samplesRead = 0, offset = 0;
            int skip_start = 0, skip_end = 0;
            int delay = Lame.getDecoderDelay();
            int padding = Lame.getDecoderPadding();
            int frameSize = Lame.getDecoderFrameSize();
            int totalFrames = Lame.getDecoderTotalFrames();
            int frame = 0;
            short[] leftBuffer = new short[DEFAULT_FRAME_SIZE];
            short[] rightBuffer = new short[DEFAULT_FRAME_SIZE];

            if (delay > -1 || padding > -1) {
                if (delay > -1) {
                    skip_start = delay + (MP3_SAMPLE_DELAY + 1);
                }
                if (padding > -1) {
                    skip_end = padding - (MP3_SAMPLE_DELAY + 1);
                }
            } else {
                skip_start = MP3_ENCODER_DELAY + (MP3_SAMPLE_DELAY + 1);
            }

            while (true) {
                samplesRead = Lame.decodeFrame(in, leftBuffer, rightBuffer);
                offset = skip_start < samplesRead ? skip_start : samplesRead;
                skip_start -= offset;
                frame += samplesRead / frameSize;
                if (samplesRead >= 0) {
                    if (skip_end > DEFAULT_FRAME_SIZE && frame + 2 > totalFrames) {
                        samplesRead -= (skip_end - DEFAULT_FRAME_SIZE);
                        skip_end = DEFAULT_FRAME_SIZE;
                    } else if (frame == totalFrames && samplesRead == 0) {
                        samplesRead -= skip_end;
                    }

                    if (Lame.getDecoderChannels() == 2) {
                        waveWriter.write(leftBuffer, rightBuffer, offset, samplesRead);
                    } else {
                        waveWriter.write(leftBuffer, offset, samplesRead);
                    }
                } else {
                    break;
                }
            }
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

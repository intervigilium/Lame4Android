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
import java.io.FileNotFoundException;
import java.io.IOException;

import com.intervigil.wave.WaveWriter;

public class Decoder {
	private static final int MP3_BUFFER_SIZE = 1152;
	private static final int INPUT_STREAM_BUFFER = 8192;
	private WaveWriter waveWriter;
	private File inFile;
	private File outFile;

	private BufferedInputStream in;

	public Decoder(File in, File out) {
		this.inFile = in;
		this.outFile = out;
	}

	public void initialize() throws FileNotFoundException, IOException {
		waveWriter = new WaveWriter(outFile, 44100, 2, 16);
		waveWriter.createWaveFile();
		in = new BufferedInputStream(new FileInputStream(inFile), INPUT_STREAM_BUFFER);
		Lame.initDecoder();
	}

	public void decode() {
		if (waveWriter != null && in != null) {

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

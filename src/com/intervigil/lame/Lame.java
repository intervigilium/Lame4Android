/* Lame.java
   An auto-tune app for Android

   Copyright (c) 2010 Ethan Chen

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License along
   with this program; if not, write to the Free Software Foundation, Inc.,
   51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.intervigil.lame;

public class Lame {
	private static final String LAME_LIB = "lame";
	
	static {
		System.loadLibrary(LAME_LIB);
	}
	
	public static native int initializeLame();
	
	public static native int encodeShortBuffer(short[] leftChannel, short[] rightChannel, 
			int channelSamples, byte[] mp3Buffer, int bufferSize);
	
	public static native int encodeFlushBuffers(byte[] mp3Buffer, int bufferSize);
	
	public static native int closeLame();
	
	public static native int initDecoder();
	
	public static native int decodeMp3(byte[] mp3Buffer, int bufferSize, 
			short[] leftChannel, short[] rightChannel);
	
	public static native int closeDecoder();
}
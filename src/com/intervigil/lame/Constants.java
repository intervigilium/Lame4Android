/* Main.java
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

public class Constants {
    public static final int REQUEST_CODE_OPEN_FILE = 12768;
    public static final int REQUEST_CODE_SAVE_FILE = 12769;

    public static final int LAME_CONFIG_STEREO = 2;

    public static final int LAME_ERROR_FILE_CREATE = -3;
    public static final int LAME_ERROR_FILE_TYPE = -4;
    public static final int LAME_ERROR_ENCODE_IO = -5;
    public static final int LAME_ERROR_DECODE_IO = -6;
    public static final int LAME_ERROR_INIT_DECODER = -7;
}

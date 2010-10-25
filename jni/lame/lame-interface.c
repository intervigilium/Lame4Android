/* Lame.java
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
/*****************************************************************************/
#include "lame-interface.h"
#include "lame.h"
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <stdio.h>
#include <android/log.h>

static lame_global_flags *lame_context;
static hip_t hip_context;

JNIEXPORT jint JNICALL Java_com_intervigil_lame_Lame_initializeLame
  (JNIEnv *env, jclass class)
{
  if (!lame_context) {
    lame_context = lame_init();
    if (lame_context) {
      return lame_init_params(lame_context);
    }
  }
  return -1;
}


JNIEXPORT jint JNICALL Java_com_intervigil_lame_Lame_encodeShortBuffer
  (JNIEnv *env, jclass class, jshortArray leftChannel, jshortArray rightChannel,
		  jint channelSamples, jbyteArray mp3Buffer, jint bufferSize)
{
  // call lame_encode_buffer

  return 0;
}


JNIEXPORT jint JNICALL Java_com_intervigil_lame_Lame_encodeFlushBuffers
  (JNIEnv *env, jclass class, jbyteArray mp3Buffer, jint bufferSize)
{
  // call lame_encode_flush when near the end of pcm buffer

  return 0;
}


JNIEXPORT jint JNICALL Java_com_intervigil_lame_Lame_closeLame
  (JNIEnv *env, jclass class)
{
  if (lame_context) {
    return lame_close(lame_context);
  }
  return -1;
}


JNIEXPORT jint JNICALL Java_com_intervigil_lame_Lame_initDecoder
  (JNIEnv *env, jclass class)
{
  if (!hip_context) {
    hip_context = hip_decode_init();
    if (hip_context) {
      return 0;
    }
  }
  return -1;
}


JNIEXPORT jint JNICALL Java_com_intervigil_lame_Lame_decodeMp3
  (JNIEnv *env, jclass class, jbyteArray mp3Buffer, jint bufferSize,
		  jshortArray rightChannel, jshortArray leftChannel)
{
  // call hip_decode_headers to get mp3 header data
  // call hip_decode to get left/right channel data from mp3 buffer

  return 0;
}


JNIEXPORT jint JNICALL Java_com_intervigil_lame_Lame_closeDecoder
  (JNIEnv *env, jclass class)
{
  if (hip_context) {
	return hip_decode_exit(hip_context);
  }
  return -1;
}

#!/bin/bash

zipalign -v 4 Lame4Android.apk Lame4Android-aligned.apk
mv Lame4Android-aligned.apk Lame4Android.apk

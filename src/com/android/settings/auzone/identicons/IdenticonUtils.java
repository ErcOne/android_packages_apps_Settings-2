/*
 * Copyright (C) 2013 The ChameleonOS Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.auzone.identicons;

import com.android.internal.util.auzone.identicons.Identicon;

import android.annotation.ChaosLab;
import android.annotation.ChaosLab.Classification;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@ChaosLab(name="QuickStats", classification=Classification.NEW_CLASS)
public class IdenticonUtils {

    private static final byte[] JPG_HEADER = new byte[] { (byte)0xFF, (byte)0xD8 };

    public static boolean isIdenticon(byte[] data) {
        if (data == null || !isJpgFormat(data))
            return false;

        byte[] tag = Arrays.copyOfRange(data, data.length - 18, data.length - 2);
        String tagString;
        try {
            tagString = new String(tag, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            return false;
        }

        return Identicon.IDENTICON_MARKER.equals(tagString);
    }

    private static boolean isJpgFormat(byte[] data) {
        if (data.length < JPG_HEADER.length)
            return false;

        for (int i = 0; i < JPG_HEADER.length; i++) {
            if (data[i] != JPG_HEADER[i])
                return false;
        }

        return true;
    }
}

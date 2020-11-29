/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.storage;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BlobIdentifierGenerator {

    private static final int START_VALUE = 1000000;

    private static final int RANDOM_INTEGER_START_VALUE = 100000;

    private static final int RANDOM_INTEGER_END_VALUE = 1000000;

    private static final int TO_STRING_RADIX = 36;

    private final AtomicLong timeMillis = new AtomicLong(0);

    private final AtomicInteger integer = new AtomicInteger(START_VALUE);

    private final Random randomInteger = new Random();

    private long acquireTimeMillis() {
        var oldTimeMillis = this.timeMillis.get();
        var currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis != oldTimeMillis) {
            this.timeMillis.set(currentTimeMillis);
            integer.set(START_VALUE);
        }
        return currentTimeMillis;
    }

    public String generate() {
        var currentTimeMillis = this.acquireTimeMillis();
        var nextInt = integer.incrementAndGet();
        var randomInt = randomInteger.nextInt(RANDOM_INTEGER_END_VALUE - RANDOM_INTEGER_START_VALUE) + RANDOM_INTEGER_START_VALUE;
        return this.toString(currentTimeMillis, nextInt, randomInt);
    }

    private String toString(long timeMillis, int nextInt, int randomInt) {
        return Long.toString(timeMillis, TO_STRING_RADIX) + Integer.toString(nextInt, TO_STRING_RADIX) + Integer.toString(randomInt, TO_STRING_RADIX);
    }
}

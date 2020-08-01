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

package org.mallfoundry.security;

import java.util.Objects;

/**
 * 获得当前上下文中的用户对象。
 *
 * @author Zhi Tang
 */
public abstract class SubjectHolder {

    private static SubjectHolderStrategy holderStrategy;

    private static final SubjectSwitches SWITCHES = new SubjectSwitches();

    public static void setHolderStrategy(SubjectHolderStrategy strategy) {
        SubjectHolder.holderStrategy = Objects.requireNonNull(strategy, "Only non-null SubjectHolderStrategy instances are permitted");
    }

    public static Subject getSubject() {
        return SubjectHolder.holderStrategy.getSubject();
    }

    public static SubjectSwitches switchTo() {
        return SWITCHES;
    }
}

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

import java.util.concurrent.Callable;

/**
 * 系统用户，用于系统级别的操作。使得当前上下文具有系统级别的权限。
 * 为了安全起见，系统用户的权限只存在于 {@link #doRun(Runnable)} 和 {@link #doCall(Callable)} 两个方法内。
 * 如果你需要执行系统级别的操作，需要在这两方法内完成。
 *
 * @author Zhi Tang
 */
public interface SystemUser extends Subject {

    void doRun(Runnable runnable);

    <V> V doCall(Callable<V> callable) throws RuntimeException;
}

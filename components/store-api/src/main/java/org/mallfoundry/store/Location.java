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

package org.mallfoundry.store;

import java.util.Date;

public interface Location {

    String getId();

    boolean isDefaulted();

    void setDefaulted(boolean defaulted);

    boolean isActive();

    void setActive(boolean active);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getMobile();

    void setMobile(String mobile);

    String getZip();

    void setZip(String zip);

    String getLocation();

    void setLocation(String location);

    String getAddress();

    void setAddress(String address);

    Date getCreatedTime();
}

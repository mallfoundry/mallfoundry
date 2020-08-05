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

package org.mallfoundry.store.staff;

import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.identity.User;
import org.mallfoundry.identity.UserService;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.store.role.RoleService;
import org.mallfoundry.util.Copies;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultStaffService implements StaffService, StaffProcessorInvoker {

    private List<StaffProcessor> processors = Collections.emptyList();

    private final UserService userService;

    private final RoleService storeRoleService;

    private final StaffRepository staffRepository;

    public DefaultStaffService(UserService userService,
                               RoleService storeRoleService,
                               StaffRepository staffRepository) {
        this.userService = userService;
        this.storeRoleService = storeRoleService;
        this.staffRepository = staffRepository;
    }

    public void setProcessors(List<StaffProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    @Override
    public StaffQuery createStaffQuery() {
        return new DefaultStaffQuery();
    }

    @Override
    public StaffId createStaffId(String storeId, String staffId) {
        return new ImmutableStaffId(storeId, staffId);
    }

    @Override
    public Staff createStaff(StaffId staffId) {
        return this.staffRepository.create(staffId);
    }

    private User requiredUser(String staffId) {
        return this.userService.getUser(staffId)
                .orElseThrow(() -> StaffExceptions.notFound(staffId));
    }

    private Staff addStaffPeek(Staff staff) {
        var exists = this.staffRepository.findById(this.createStaffId(staff.getStoreId(), staff.getId())).isPresent();
        if (exists) {
            throw StaffExceptions.alreadyExists(staff.getId());
        }
        var user = this.requiredUser(staff.getId());
        staff.setAvatar(user.getAvatar());
        staff.create();
        return staff;
    }

    @Transactional
    @Override
    public Staff addStaff(Staff staff) {
        return Function.<Staff>identity()
                .compose(this.staffRepository::save)
                .compose(this::addStaffPeek)
                .compose(this::invokePreProcessBeforeAddStaff)
                .apply(staff);
    }

    private Staff updateStaff(Staff source, Staff dest) {
        Copies.notBlank(source::getName).trim(dest::setName);
        Copies.notBlank(source::getNumber).trim(dest::setNumber);
        Copies.notBlank(source::getCountryCode).trim(dest::setCountryCode);
        Copies.notBlank(source::getPhone).trim(dest::setPhone);
        return dest;
    }

    @Transactional
    @Override
    public Staff updateStaff(Staff newStaff) {
        return Function.<Staff>identity()
                .compose(this.staffRepository::save)
                .compose(this::invokePreProcessAfterUpdateStaff)
                .<Staff>compose(target -> this.updateStaff(newStaff, target))
                .compose(this::invokePreProcessBeforeUpdateStaff)
                .compose(this::requiredStaff)
                .apply(this.createStaffId(newStaff.getStoreId(), newStaff.getId()));
    }

    private Staff requiredStaff(StaffId staffId) {
        return this.getStaff(staffId).orElseThrow();
    }

    @Transactional
    @Override
    public void deleteStaff(StaffId staffId) {
        var staff = Function.<Staff>identity()
                .compose(this::invokePreProcessBeforeDeleteStaff)
                .compose(this::requiredStaff)
                .apply(staffId);
        this.staffRepository.delete(staff);
    }

    @Override
    public Optional<Staff> getStaff(StaffId staffId) {
        return this.staffRepository.findById(staffId);
    }

    @Override
    public SliceList<Staff> getStaffs(StaffQuery query) {
        return this.staffRepository.findAll(query);
    }

    @Transactional
    @Override
    public void addStaffRole(StaffId staffId, String roleId) {
        this.addStaffRoles(staffId, Set.of(roleId));
    }

    @Transactional
    @Override
    public void addStaffRoles(StaffId staffId, Set<String> roleIds) {
        var staff = this.requiredStaff(staffId);
        var rIds = roleIds.stream()
                .map(rId -> this.storeRoleService.createRoleId(staffId.getStoreId(), rId))
                .collect(Collectors.toUnmodifiableSet());
        var roles = this.storeRoleService.getRoles(rIds);
        staff.addRoles(roles);
    }

    @Transactional
    @Override
    public void removeStaffRole(StaffId staffId, String roleId) {
        this.removeStaffRoles(staffId, Set.of(roleId));
    }

    @Transactional
    @Override
    public void removeStaffRoles(StaffId staffId, Set<String> roleIds) {
        var staff = this.requiredStaff(staffId);
        var rIds = roleIds.stream()
                .map(rId -> this.storeRoleService.createRoleId(staffId.getStoreId(), rId))
                .collect(Collectors.toUnmodifiableSet());
        var roles = this.storeRoleService.getRoles(rIds);
        staff.removeRoles(roles);
    }

    @Override
    public Staff invokePreProcessBeforeAddStaff(Staff staff) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::preProcessBeforeAddStaff)
                .apply(staff);
    }

    @Override
    public Staff invokePreProcessBeforeUpdateStaff(Staff staff) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::preProcessBeforeUpdateStaff)
                .apply(staff);
    }

    @Override
    public Staff invokePreProcessAfterUpdateStaff(Staff staff) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::preProcessAfterUpdateStaff)
                .apply(staff);
    }

    @Override
    public Staff invokePreProcessBeforeDeleteStaff(Staff staff) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::preProcessBeforeDeleteStaff)
                .apply(staff);
    }
}

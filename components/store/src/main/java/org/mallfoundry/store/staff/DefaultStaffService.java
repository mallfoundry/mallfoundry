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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.identity.User;
import org.mallfoundry.identity.UserId;
import org.mallfoundry.identity.UserService;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.store.StoreId;
import org.mallfoundry.util.Copies;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DefaultStaffService implements StaffService, StaffProcessorInvoker, ApplicationEventPublisherAware {

    private List<StaffProcessor> processors = Collections.emptyList();

    private final UserService userService;

    private final StaffRepository staffRepository;

    private ApplicationEventPublisher eventPublisher;

    public DefaultStaffService(UserService userService, StaffRepository staffRepository) {
        this.userService = userService;
        this.staffRepository = staffRepository;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void setProcessors(List<StaffProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    @Override
    public StaffQuery createStaffQuery() {
        return new DefaultStaffQuery();
    }

    @Override
    public StaffId createStaffId(StoreId storeId, String staffId) {
        return new ImmutableStaffId(storeId.getTenantId(), storeId.getId(), staffId);
    }

    @Override
    public StaffId createStaffId(String storeId, String staffId) {
        return new ImmutableStaffId(null, storeId, staffId);
    }

    @Override
    public Staff createStaff(StaffId staffId) {
        return this.staffRepository.create(staffId);
    }

    private User getUser(Staff staff) {
        var userId = this.userService.createUserId(staff.getTenantId(), staff.getId());
        return this.userService.getUser(userId);
    }

    private Staff addStaffPeek(Staff staff) {
        var exists = this.staffRepository.findById(this.createStaffId(staff.getStoreId(), staff.getId())).isPresent();
        if (exists) {
            throw StaffExceptions.alreadyExists(staff.getId());
        }
        var user = this.getUser(staff);
        staff.setAvatar(user.getAvatar());
        staff.create();
        return staff;
    }

    @Transactional
    @Override
    public Staff addStaff(Staff staff) {
        return Function.<Staff>identity()
                .<Staff>compose(aStaff -> {
                    this.eventPublisher.publishEvent(new ImmutableStaffAddedEvent(aStaff));
                    return aStaff;
                })
                .compose(this.staffRepository::save)
                .compose(this::invokePreProcessAfterAddStaff)
                .compose(this::addStaffPeek)
                .compose(this::invokePreProcessBeforeAddStaff)
                .apply(staff);
    }

    @Override
    public Optional<Staff> findStaff(StaffId staffId) {
        return this.staffRepository.findById(staffId)
                .map(this::invokePostProcessAfterGetStaff);
    }

    @Override
    public List<Staff> getStaffs(UserId userId) {
        return this.staffRepository.findAllByUserId(userId);
    }

    @Override
    public SliceList<Staff> getStaffs(StaffQuery query) {
        return Function.<SliceList<Staff>>identity()
                .compose(this.staffRepository::findAll)
                .compose(this::invokePreProcessBeforeGetStaffs)
                .apply(query);
    }

    @Override
    public long countStaffs(StaffQuery query) {
        return Function.<Long>identity()
                .compose(this.staffRepository::count)
                .compose(this::invokePreProcessBeforeCountStaffs)
                .apply(query);
    }

    @Override
    public Staff getStaff(StaffId staffId) {
        return this.findStaff(staffId).orElseThrow();
    }

    private Staff updateStaff(Staff source, Staff dest) {
        Copies.notBlank(source::getName).trim(dest::setName);
        Copies.notBlank(source::getNumber).trim(dest::setNumber);
        Copies.notBlank(source::getCountryCode).trim(dest::setCountryCode);
        Copies.notBlank(source::getPhone).trim(dest::setPhone);
        if (CollectionUtils.isNotEmpty(source.getRoles())) {
            dest.setRoles(source.getRoles());
        }
        return dest;
    }

    @Transactional
    @Override
    public Staff updateStaff(Staff newStaff) {
        Staff preStaff;
        try {
            preStaff = Function.<Staff>identity()
                    .compose(this.staffRepository::save)
                    .compose(this::invokePreProcessAfterUpdateStaff)
                    .<Staff>compose(target -> this.updateStaff(newStaff, target))
                    .compose(this::invokePreProcessBeforeUpdateStaff)
                    .compose(this::getStaff)
                    .apply(this.createStaffId(newStaff.getStoreId(), newStaff.getId()));
        } finally {
            this.invokePreProcessAfterCompletion();
        }

        return Function.<Staff>identity()
                .<Staff>compose(staff -> {
                    this.eventPublisher.publishEvent(new ImmutableStaffChangedEvent(staff));
                    return staff;
                })
                .compose(this.staffRepository::save)
                .apply(preStaff);
    }

    @Transactional
    @Override
    public void activeStaff(StaffId staffId) {
        var staff = Function.<Staff>identity()
                .compose(this::invokePreProcessBeforeActiveStaff)
                .compose(this::getStaff)
                .apply(staffId);
        staff.active();
        this.staffRepository.save(staff);
    }

    @Override
    public void inactiveStaff(StaffId staffId) {
        var staff = Function.<Staff>identity()
                .compose(this::invokePreProcessBeforeInactiveStaff)
                .compose(this::getStaff)
                .apply(staffId);
        staff.inactive();
        this.staffRepository.save(staff);
    }

    @Transactional
    @Override
    public void deleteStaff(StaffId staffId) {
        var staff = Function.<Staff>identity()
                .compose(this::invokePreProcessAfterDeleteStaff)
                // invoke peek...
                .compose(this::invokePreProcessBeforeDeleteStaff)
                .compose(this::getStaff)
                .apply(staffId);
        this.staffRepository.delete(staff);
        this.eventPublisher.publishEvent(new ImmutableStaffDeletedEvent(staff));
    }

    @Transactional
    @Override
    public void clearStaffs(StoreId storeId) {
        this.staffRepository.deleteAllByStoreId(storeId);
    }

    @Override
    public Staff invokePreProcessBeforeAddStaff(Staff staff) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::preProcessBeforeAddStaff)
                .apply(staff);
    }

    @Override
    public Staff invokePreProcessAfterAddStaff(Staff staff) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::preProcessAfterAddStaff)
                .apply(staff);
    }

    @Override
    public Staff invokePostProcessAfterGetStaff(Staff staff) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::postProcessAfterGetStaff)
                .apply(staff);
    }

    @Override
    public StaffQuery invokePreProcessBeforeGetStaffs(StaffQuery query) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::preProcessBeforeGetStaffs)
                .apply(query);
    }

    @Override
    public StaffQuery invokePreProcessBeforeCountStaffs(StaffQuery query) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::preProcessBeforeCountStaffs)
                .apply(query);
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
    public Staff invokePreProcessBeforeActiveStaff(Staff staff) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::preProcessBeforeActiveStaff)
                .apply(staff);
    }

    @Override
    public Staff invokePreProcessBeforeInactiveStaff(Staff staff) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::preProcessBeforeInactiveStaff)
                .apply(staff);
    }

    @Override
    public Staff invokePreProcessBeforeDeleteStaff(Staff staff) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::preProcessBeforeDeleteStaff)
                .apply(staff);
    }

    @Override
    public Staff invokePreProcessAfterDeleteStaff(Staff staff) {
        return Processors.stream(this.processors)
                .map(StaffProcessor::preProcessAfterDeleteStaff)
                .apply(staff);
    }

    @Override
    public void invokePreProcessAfterCompletion() {
        this.processors.forEach(StaffProcessor::preProcessAfterCompletion);
    }
}

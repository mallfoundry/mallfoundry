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

package org.mallfoundry.store.initializing;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.store.StoreInitializing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class DefaultStoreInitializing implements StoreInitializing {

    private InitializingState state;

    private final List<InitializingStage> stages = new ArrayList<>();

    public DefaultStoreInitializing() {
        this.state = InitializingState.NEW;
    }

    @Override
    public InitializingStage addStage(String message) {
        var stage = new DefaultInitializingStage(message);
        this.stages.add(stage);
        stage.setPosition(this.stages.size());
        return stage;
    }

    @Override
    public void initialize() {
        this.state = InitializingState.INITIALIZING;
    }

    @Override
    public void configure() {
        this.state = InitializingState.CONFIGURING;
    }

    @Override
    public void complete() {
        this.state = InitializingState.INITIALIZED;
    }

    @Override
    public void fail() {
        this.state = InitializingState.FAILED;
    }

    @Getter
    @Setter
    private static class DefaultInitializingStage implements InitializingStage {
        private int position;
        private StageStatus status;
        private final String message;
        private final Date occurredTime;

        DefaultInitializingStage(String message) {
            this.message = message;
            this.occurredTime = new Date();
            this.status = StageStatus.PENDING;
        }

        @Override
        public void success() {
            this.status = StageStatus.SUCCESS;
        }

        @Override
        public void failure() {
            this.status = StageStatus.FAILURE;
        }
    }
}

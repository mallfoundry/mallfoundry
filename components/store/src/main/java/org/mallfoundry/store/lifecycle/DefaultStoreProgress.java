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

package org.mallfoundry.store.lifecycle;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.store.StoreProgress;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class DefaultStoreProgress implements StoreProgress {

    private ProgressState state;

    private final List<ProgressStage> stages = new ArrayList<>();

    public DefaultStoreProgress() {
        this.state = ProgressState.NEW;
    }

    @Override
    public ProgressStage addStage(String message) {
        var stage = new DefaultInitializingStage(message);
        this.stages.add(stage);
        stage.setPosition(this.stages.size());
        return stage;
    }

    @Override
    public void initializing() {
        this.state = ProgressState.INITIALIZING;
    }

    @Override
    public void initialized() {
        this.state = ProgressState.INITIALIZED;
    }

    @Override
    public void closing() {
        this.state = ProgressState.CLOSING;
    }

    @Override
    public void closed() {
        this.state = ProgressState.CLOSED;
    }

    @Override
    public void failed() {
        this.state = ProgressState.FAILED;
    }

    @Getter
    @Setter
    private static class DefaultInitializingStage implements ProgressStage {
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

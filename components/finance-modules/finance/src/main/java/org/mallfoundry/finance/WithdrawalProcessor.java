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

package org.mallfoundry.finance;

public interface WithdrawalProcessor {

    default Withdrawal preProcessBeforeApplyWithdrawal(Withdrawal withdrawal) {
        return withdrawal;
    }

    default Withdrawal preProcessAfterApplyWithdrawal(Withdrawal withdrawal) {
        return withdrawal;
    }

    default Withdrawal preProcessBeforeDisapproveWithdrawal(Withdrawal withdrawal) {
        return withdrawal;
    }

    default Withdrawal preProcessAfterDisapproveWithdrawal(Withdrawal withdrawal) {
        return withdrawal;
    }

    default Withdrawal preProcessBeforeCancelWithdrawal(Withdrawal withdrawal) {
        return withdrawal;
    }

    default Withdrawal preProcessAfterCancelWithdrawal(Withdrawal withdrawal) {
        return withdrawal;
    }

    default Withdrawal preProcessBeforeApproveWithdrawal(Withdrawal withdrawal) {
        return withdrawal;
    }

    default Withdrawal preProcessAfterApproveWithdrawal(Withdrawal withdrawal) {
        return withdrawal;
    }

    default Withdrawal preProcessBeforeSucceedWithdrawal(Withdrawal withdrawal) {
        return withdrawal;
    }

    default Withdrawal preProcessAfterSucceedWithdrawal(Withdrawal withdrawal) {
        return withdrawal;
    }

    default Withdrawal preProcessBeforeFailWithdrawal(Withdrawal withdrawal) {
        return withdrawal;
    }

    default Withdrawal preProcessAfterFailWithdrawal(Withdrawal withdrawal) {
        return withdrawal;
    }
}

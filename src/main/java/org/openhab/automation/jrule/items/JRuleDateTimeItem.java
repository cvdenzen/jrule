/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.automation.jrule.items;

import java.time.ZonedDateTime;
import java.util.Date;

import org.openhab.automation.jrule.internal.handler.JRuleEventHandler;
import org.openhab.automation.jrule.trigger.JRuleDateTimeTrigger;

/**
 * The {@link JRuleDateTimeItem} Items
 *
 * @author Joseph (Seaside) Hagberg - Initial contribution
 */
public class JRuleDateTimeItem extends JRuleItem implements JRuleDateTimeTrigger {

    protected JRuleDateTimeItem(String itemName) {
        super(itemName);
    }

    public static JRuleDateTimeItem forName(String itemName) {
        return JRuleItemRegistry.get(itemName, JRuleDateTimeItem.class);
    }

    public void sendCommand(Date date) {
        JRuleEventHandler.get().sendCommand(itemName, date);
    }

    public void sendCommand(ZonedDateTime zonedDateTime) {
        JRuleEventHandler.get().sendCommand(itemName, zonedDateTime);
    }

    public void postUpdate(Date date) {
        JRuleEventHandler.get().postUpdate(itemName, date);
    }

    public void postUpdate(ZonedDateTime zonedDateTime) {
        JRuleEventHandler.get().postUpdate(itemName, zonedDateTime);
    }

    public Date getState() {
        return JRuleEventHandler.get().getStateFromItemAsDate(itemName);
    }

    public ZonedDateTime getZonedDateTimeState() {
        return JRuleEventHandler.get().getStateFromItemAsZonedDateTime(itemName);
    }

    public Date getHistoricState(ZonedDateTime timestamp, String persistenceServiceId) {
        String state = JRulePersistenceExtentions.historicState(itemName, timestamp, persistenceServiceId);
        if (state != null) {
            return new Date(ZonedDateTime.parse(state).toInstant().toEpochMilli());
        } else {
            return null;
        }
    }
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(super.toString()).append(", ").append(getZonedDateTimeState().toString());
        return sb.toString();
    }
}

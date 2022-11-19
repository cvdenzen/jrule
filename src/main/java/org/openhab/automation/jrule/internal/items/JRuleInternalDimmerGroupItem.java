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
package org.openhab.automation.jrule.internal.items;

import java.util.Set;

import org.openhab.automation.jrule.internal.JRuleLog;
import org.openhab.automation.jrule.internal.handler.JRuleEventHandler;
import org.openhab.automation.jrule.items.JRuleDimmerGroupItem;
import org.openhab.automation.jrule.rules.value.JRuleIncreaseDecreaseValue;
import org.openhab.automation.jrule.rules.value.JRuleOnOffValue;
import org.openhab.automation.jrule.rules.value.JRulePercentValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link JRuleInternalColorGroupItem} Items
 *
 * @author Arne Seime - Initial contribution
 */
public class JRuleInternalDimmerGroupItem extends JRuleInternalDimmerItem implements JRuleDimmerGroupItem {

    private static final String LOG_NAME = "JRuleGroupDimmerItem";
    private static final Logger logger = LoggerFactory.getLogger(JRuleInternalDimmerGroupItem.class);

    public JRuleInternalDimmerGroupItem(String name, String label, String type, String id) {
        super(name, label, type, id);
    }

    public void sendCommand(double value) {
        final Set<String> groupMemberNames = JRuleEventHandler.get().getGroupMemberNames(name, false);
        groupMemberNames
                .forEach(m -> JRuleEventHandler.get().sendCommand(m, new JRulePercentValue(value).asStringValue()));
    }

    public void postUpdate(double value) {
        final Set<String> groupMemberNames = JRuleEventHandler.get().getGroupMemberNames(name, false);
        groupMemberNames
                .forEach(m -> JRuleEventHandler.get().postUpdate(m, new JRulePercentValue(value).asStringValue()));
    }

    public void sendCommand(int value) {
        final Set<String> groupMemberNames = JRuleEventHandler.get().getGroupMemberNames(name, false);
        groupMemberNames
                .forEach(m -> JRuleEventHandler.get().sendCommand(m, new JRulePercentValue(value).asStringValue()));
    }

    public void postUpdate(int value) {
        final Set<String> groupMemberNames = JRuleEventHandler.get().getGroupMemberNames(name, false);
        groupMemberNames
                .forEach(m -> JRuleEventHandler.get().postUpdate(m, new JRulePercentValue(value).asStringValue()));
    }

    public void sendCommand(JRuleIncreaseDecreaseValue value) {
        final Set<String> groupMemberNames = JRuleEventHandler.get().getGroupMemberNames(name, false);
        groupMemberNames.forEach(m -> JRuleEventHandler.get().sendCommand(m, value.asStringValue()));
    }

    public void postUpdate(JRuleIncreaseDecreaseValue value) {
        JRuleLog.error(logger, LOG_NAME, "PostUpdate on IncreaseDecrease not supported by openHab Core: {}",
                value.name().toString());
    }

    public void sendCommand(JRuleOnOffValue value) {
        final Set<String> groupMemberNames = JRuleEventHandler.get().getGroupMemberNames(name, false);
        groupMemberNames.forEach(m -> JRuleEventHandler.get().sendCommand(m, value.asStringValue()));
    }

    public void postUpdate(JRuleOnOffValue value) {
        final Set<String> groupMemberNames = JRuleEventHandler.get().getGroupMemberNames(name, false);
        groupMemberNames.forEach(m -> JRuleEventHandler.get().postUpdate(m, value.asStringValue()));
    }
}

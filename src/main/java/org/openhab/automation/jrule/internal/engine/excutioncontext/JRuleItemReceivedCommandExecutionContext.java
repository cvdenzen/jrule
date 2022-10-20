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
package org.openhab.automation.jrule.internal.engine.excutioncontext;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.openhab.automation.jrule.rules.JRule;
import org.openhab.automation.jrule.rules.JRuleEventState;
import org.openhab.automation.jrule.rules.event.JRuleEvent;
import org.openhab.automation.jrule.rules.event.JRuleItemEvent;
import org.openhab.core.events.AbstractEvent;
import org.openhab.core.items.events.ItemCommandEvent;

/**
 * The {@link JRuleItemReceivedCommandExecutionContext}
 *
 * @author Robert Delbrück - Initial contribution
 */
public class JRuleItemReceivedCommandExecutionContext extends JRuleItemExecutionContext {
    private final Optional<String> to;

    public JRuleItemReceivedCommandExecutionContext(JRule jRule, String logName, String[] loggingTags, String itemName,
            Method method, Optional<Double> lt, Optional<Double> lte, Optional<Double> gt, Optional<Double> gte,
            Optional<String> eq, Optional<String> neq, List<JRulePreconditionContext> preconditionContextList,
            Optional<String> to) {
        super(jRule, logName, loggingTags, itemName, method, lt, lte, gt, gte, eq, neq, preconditionContextList);
        this.to = to;
    }

    @Override
    public boolean match(AbstractEvent event) {
        return event instanceof ItemCommandEvent && ((ItemCommandEvent) event).getItemName().equals(this.getItemName())
                && to.map(s -> ((ItemCommandEvent) event).getItemCommand().toString().equals(s)).orElse(true)
                && super.matchCondition(((ItemCommandEvent) event).getItemCommand().toString());
    }

    @Override
    public JRuleEvent createJRuleEvent(AbstractEvent event) {
        return new JRuleItemEvent(this.getItemName(),
                new JRuleEventState(((ItemCommandEvent) event).getItemCommand().toString()), null);
    }
}

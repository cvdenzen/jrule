package org.openhab.automation.jrule.internal.engine;

import org.eclipse.jdt.annotation.Nullable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openhab.automation.jrule.internal.JRuleConfig;
import org.openhab.automation.jrule.internal.JRuleFactory;
import org.openhab.automation.jrule.internal.events.JRuleEventSubscriber;
import org.openhab.automation.jrule.internal.handler.JRuleEventHandler;
import org.openhab.automation.jrule.items.JRuleDateTimeItem;
import org.openhab.automation.jrule.rules.JRule;
import org.openhab.automation.jrule.rules.JRuleName;
import org.openhab.automation.jrule.rules.JRuleWhen;
import org.openhab.core.audio.AudioManager;
import org.openhab.core.audio.internal.AudioManagerImpl;
import org.openhab.core.events.Event;
import org.openhab.core.events.EventPublisher;
import org.openhab.core.i18n.LocaleProvider;
import org.openhab.core.i18n.TranslationProvider;
import org.openhab.core.internal.items.MetadataRegistryImpl;
import org.openhab.core.items.ItemRegistry;
import org.openhab.core.items.MetadataRegistry;
import org.openhab.core.items.events.ItemStateEvent;
import org.openhab.core.library.items.DateTimeItem;
import org.openhab.core.library.types.DateTimeType;
import org.openhab.core.voice.VoiceManager;
import org.openhab.core.voice.internal.VoiceManagerImpl;
import org.osgi.framework.Bundle;
import org.osgi.service.component.ComponentContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.time.ZonedDateTime;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.System.out;
import static org.mockito.Mockito.*;


import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

class JRuleEngineTest {

    private final Logger logger = LoggerFactory.getLogger(JRuleEngine.class);
    Map<String, Object> configMap = new HashMap<>();
    JRuleConfig jRuleConfig = new JRuleConfig(configMap);
    static JRuleDateTimeItem jRuleDateTimeItem;
    static MetadataRegistry metadataRegistry = new MetadataRegistryImpl();
    static ItemRegistry mockedItemRegistry = mock(ItemRegistry.class); //new ItemRegistryImpl(metadataRegistry);
    static JRuleFactory jf;
    static JRuleEventSubscriber jRuleEventSubscriber = new JRuleEventSubscriber();
    static EventPublisher eventPublisher = new EventPublisher() {
        @Override
        public void post(Event event) throws IllegalArgumentException, IllegalStateException {
            //throw new IllegalStateException("This test is not made to publish events");
            out.println("EventPublisher.post() called for event " + event);
        }
    };
    static LocaleProvider localeProvider = new LocaleProvider() {
        @Override
        public Locale getLocale() {
            return Locale.FRENCH;
        }
    };
    static AudioManager audioManager = new AudioManagerImpl();
    static TranslationProvider translationProvider = new TranslationProvider() {
        @Override
        public @Nullable String getText(@Nullable Bundle bundle, @Nullable String s, @Nullable String s1, @Nullable Locale locale) {
            return Locale.FRANCE.toString();
        }

        @Override
        public @Nullable String getText(@Nullable Bundle bundle, @Nullable String s, @Nullable String s1, @Nullable Locale locale, @Nullable Object @Nullable ... objects) {
            return null;
        }
    };
    static VoiceManager voiceManager = new VoiceManagerImpl(localeProvider, audioManager, eventPublisher, translationProvider);
    //ComponentContext mockedComponentContext;
    static ComponentContext mockedComponentContext = mock(ComponentContext.class);
    //when(mockedComponentContext.).thenReturn (null);

    @BeforeAll
    public static void testBeforeAll() throws Exception {
        out.println("Start TestBeforeAll()");

        jf = new JRuleFactory(
                new HashMap<String, Object>(),
                jRuleEventSubscriber,
                mockedItemRegistry,
                eventPublisher,
                voiceManager,
                mockedComponentContext
        );
        out.println("jf=" + jf);

        jRuleDateTimeItem = JRuleDateTimeItem.forName("_TestDateTimeItem"); //=mock(JRuleDateTimeItem.class);
        ZonedDateTime myZonedDateTime = ZonedDateTime.parse("2022-10-11T23:11:09+02:00");
        // This is not how it should work: jRuleDateTimeItem.postUpdate(myZonedDateTime);
        //out.println("mockedJRuleDateTimeItem.getZonedDateTimeState()="+mockedJRuleDateTimeItem.getZonedDateTimeState());
        //out.println("JRuleDateTimeItem.forName(\"TestDateTimeItemxx\")="+JRuleDateTimeItem.forName("TestDateTimeItemxx"));
        //out.println("JRuleDateTimeItem.forName(\"TestDateTimeItemxx\")="+JRuleDateTimeItem.forName("TestDateTimeItemxx"));
        //out.println("JRuleDateTimeItem.forName(\"TestDateTimeItemyy\")="+JRuleDateTimeItem.forName("TestDateTimeItemyy"));

        // Add the openhab item
        DateTimeItem mockedDateTimeItem = mock(DateTimeItem.class);
        when(mockedDateTimeItem.getName()).thenReturn("TestDateTimeItem");
        when(mockedDateTimeItem.getState()).thenReturn(new DateTimeType(myZonedDateTime));
        //when(mockedDateTimeItem.getStateAs(org.openhab.core.library.types.DateTimeType.class)).thenReturn(new DateTimeType(myZonedDateTime));
        //mockedDateTimeItem.send(DateTimeType.valueOf(myZonedDateTime.toString()));

        // Openhab
        //Registry mockedRegistry=mock(Registry.class);
        when(mockedItemRegistry.add(mockedDateTimeItem)).thenReturn(mockedDateTimeItem);
        when(mockedItemRegistry.getItem(mockedDateTimeItem.getName())).thenReturn(mockedDateTimeItem);

        out.println("Start itemRegistry.add(dateTimeItem)");
        mockedItemRegistry.add(mockedDateTimeItem);
    }

    public class JRuleTest extends JRule {
        @JRuleName("Test jRule 13 hours")
        // Test add with hour=13
        @JRuleWhen(hours = 13)
        public void TestruleInJUnitTestForJRuleEngineTest() {
            JRuleEngine jRuleEngine = JRuleEngine.get();
            JRule jRule = new JRule() {
                public String toString() {
                    return "Test jRulexx";
                }
            };
            Annotation[] annotations = jRule.getClass().getAnnotations();
            try {
                AnnotatedElement ae = JRuleEngineTest.class.getDeclaredMethod("add", JRuleEngineTest.class);
            } catch (Exception ex) {
                out.println("foutje");
            }
            //findAnnotation(jRule., JRuleName.class);
            //jRuleEngine.add(jRule);
        }


        @JRuleName("TestTestDateTimeItem")
        // Test add with DateTimeItem
        @JRuleWhen(item = "TestDateTimeItem", trigger = "at")
        public void TestruleInJUnitTestForJRuleEngineTestAt() {
            JRuleEngine jRuleEngine = JRuleEngine.get();
            JRule jRule = new JRule() {
                public String toString() {
                    return "Test jRulexx";
                }
            };
            Annotation[] annotations = jRule.getClass().getAnnotations();
            try {
                AnnotatedElement ae = JRuleEngineTest.class.getDeclaredMethod("add", JRuleEngineTest.class);
            } catch (Exception ex) {
                out.println("foutje");
            }
        }
    }

    /*
    Test the add function in JRuleEngine.
     */
    @Test
    void add() {
        out.println("Start add()");
        out.println("JRuleEngine.get()=" + JRuleEngine.get());
        JRuleEngine jRuleEngine = JRuleEngine.get();
        out.println("After get(): JRuleEngine.get()=" + JRuleEngine.get());
        // Create item
        JRuleDateTimeItem jRuleDateTimeItem = JRuleDateTimeItem.forName("TestDateTimeItem");
        jRuleDateTimeItem.postUpdate(ZonedDateTime.parse("2022-10-11T23:11:09+02:00"));
        out.println("Start new JRuleTest()");
        JRule jRule = new JRuleTest();
        out.println("Start add(jRule");
        // JRuleEventHandler must have an itemRegistry
        JRuleEventHandler.get().setItemRegistry(mockedItemRegistry);
        jRuleEngine.add(jRule);
        out.println("End of add() method in JRuleEngineTest");
    }
}
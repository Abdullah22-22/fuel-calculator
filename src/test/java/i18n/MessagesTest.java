package i18n;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessagesTest {

    @AfterEach
    void resetToEnglish() {
        Messages.setLocale(Messages.ENGLISH);
    }

    @Test
    void englishIsTheDefault() {
        Messages.setLocale(Messages.ENGLISH);
        assertEquals("Calculate Trip Cost", Messages.get("calculate.button"));
    }

    @Test
    void frenchBundleIsLoadedAsUtf8() {
        Messages.setLocale(Messages.FRENCH);
        assertEquals("Calculer le coût du trajet", Messages.get("calculate.button"));
    }

    @Test
    void japaneseBundleIsLoadedAsUtf8() {
        Messages.setLocale(Messages.JAPANESE);
        assertEquals("旅費を計算", Messages.get("calculate.button"));
        assertEquals("言語:", Messages.get("language.label"));
    }

    @Test
    void persianBundleIsLoadedAsUtf8() {
        Messages.setLocale(Messages.PERSIAN);
        assertEquals("محاسبه هزینه سفر", Messages.get("calculate.button"));
        assertEquals("زبان:", Messages.get("language.label"));
    }

    @Test
    void onlyPersianIsRightToLeft() {
        Messages.setLocale(Messages.PERSIAN);
        assertTrue(Messages.isRightToLeft());

        Messages.setLocale(Messages.ENGLISH);
        assertFalse(Messages.isRightToLeft());

        Messages.setLocale(Messages.JAPANESE);
        assertFalse(Messages.isRightToLeft());

        Messages.setLocale(Messages.FRENCH);
        assertFalse(Messages.isRightToLeft());
    }

    @Test
    void resultPlaceholdersAreReplaced() {
        Messages.setLocale(Messages.ENGLISH);
        assertEquals("Total fuel needed: 11.70 L | Total cost: 23.99",
                Messages.get("result.label", "11.70", "23.99"));
    }

    @Test
    void missingKeyIsMarked() {
        Messages.setLocale(Messages.ENGLISH);
        assertEquals("!no.such.key!", Messages.get("no.such.key"));
    }

    @Test
    void everyLocaleHasAllKeys() {
        String[] keys = {
                "app.title", "language.label", "distance.label",
                "consumption.label", "price.label", "calculate.button",
                "result.label", "invalid.input", "empty.input"
        };

        for (Locale locale : new Locale[]{
                Messages.ENGLISH, Messages.FRENCH, Messages.JAPANESE, Messages.PERSIAN}) {

            Messages.setLocale(locale);
            for (String key : keys) {
                String value = Messages.get(key);
                assertFalse(value.startsWith("!"), "Missing key " + key + " in " + locale);
                assertTrue(value.length() > 0);
            }
        }
    }
}
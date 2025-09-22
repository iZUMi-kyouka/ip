package rumi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

import rumi.utils.RumiDate;

public class RumiDateTest {
    @Test
    public void validateDateTime_validDateTimeInput_expectedBehaviour() {
        String dateStr = "30092024 816pm";
        RumiDate parsedDate1 = RumiDate.fromString(dateStr);
        dateStr = "30924 2016";
        RumiDate parsedDate2 = RumiDate.fromString(dateStr);
        assertEquals(parsedDate1, parsedDate2);

        dateStr = "22022026 0816";
        parsedDate1 = RumiDate.fromString(dateStr);
        dateStr = "22226 816am";
        parsedDate2 = RumiDate.fromString(dateStr);
        assertEquals(parsedDate1, parsedDate2);

        dateStr = "22/02/2026 8pm";
        parsedDate1 = RumiDate.fromString(dateStr);
        dateStr = "22-02-26 2000";
        parsedDate2 = RumiDate.fromString(dateStr);
        assertEquals(parsedDate1, parsedDate2);
    }

    @Test
    public void parseDateTime_validNonDateTimeInput_expectedBehaviour() {
        String dateStr = "tommorow";
        RumiDate parsedDate1 = RumiDate.fromString(dateStr);
        dateStr = "tommorow";
        RumiDate parsedDate2 = RumiDate.fromString(dateStr);
        assertEquals(parsedDate1, parsedDate2);
    }

    @Test
    public void validateDateTime_invalidInput_exceptionThrown() {
        String dateStr = "31092024 2300";
        assertThrows(DateTimeException.class, () -> RumiDate.fromString(dateStr));

        String dateStr2 = "30022028";
        assertThrows(DateTimeException.class, () -> RumiDate.fromString(dateStr2));

        String dateStr3 = "290205 1300";
        assertThrows(DateTimeException.class, () -> RumiDate.fromString(dateStr3));
    }
}

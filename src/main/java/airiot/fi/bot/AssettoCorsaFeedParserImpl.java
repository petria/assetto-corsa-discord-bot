package airiot.fi.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Petri Airio on 8.3.2017.
 */
@Component
@Slf4j
public class AssettoCorsaFeedParserImpl implements AssettoCorsaFeedParser {


    @Override
    public AssettoCorsaEvent parseFeedLine(String line) {

        for (KnownLine knownLine : KnownLine.values()) {
            if (line.matches(knownLine.getPattern())) {
                AssettoCorsaEvent event = parseKnowLine(knownLine, line);
                return event;
            }

        }
        return null;
    }

    private AssettoCorsaEvent parseKnowLine(KnownLine knownLine, String line) {
        String ptr = knownLine.getPattern();
        Pattern pattern = Pattern.compile(ptr);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            AssettoCorsaEvent event = new AssettoCorsaEvent(knownLine);
            String capture = matcher.group(1);
            String format = knownLine.getFormat();
            String formatted = String.format(format, capture);
            event.setMessage(formatted);
            return event;
        }

        log.error("parse failed: {} - {}", knownLine, line);
        return null;
    }
}

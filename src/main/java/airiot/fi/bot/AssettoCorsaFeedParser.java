package airiot.fi.bot;

/**
 * Created by Petri Airio on 8.3.2017.
 */
public interface AssettoCorsaFeedParser {

    AssettoCorsaEvent parseFeedLine(String line);

}

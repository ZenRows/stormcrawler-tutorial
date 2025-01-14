package com.tutorial.spouts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class URLSpout extends BaseRichSpout {
    private static final Logger logger = Logger.getLogger(URLSpout.class.getName());

    private SpoutOutputCollector collector;
    private BufferedReader reader;
    private boolean eofReached;

    @Override
    public void open(Map<String, Object> config, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        try {
            // initialize the BufferedReader with the seed file path
            reader = new BufferedReader(new FileReader("D:/TechWithId/test-storm/stormcrawler-tutorial/seeds.txt"));
            eofReached = false; // initialize EOF flag
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error opening file: {0}", e.getMessage());
        }
    }

    @Override
    public void nextTuple() {
        if (eofReached) {
            return; // stop emitting tuples once EOF is reached
        }

        try {
            // read a line (URL) from the seed file
            String url = reader.readLine();
            if (url != null) {
                // log and emit the URL as a tuple
                logger.log(Level.INFO, "Emitting URL: {0}", url); // Log the emitted URL
                collector.emit(new Values(url));
            } else {
                // reached end of file
                logger.info("No more URLs to emit.");
                eofReached = true;  // set EOF flag
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file: {0}", e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            // close the BufferedReader
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error closing file: {0}", e.getMessage());
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // declare the output field for the spout
        declarer.declare(new Fields("url"));
    }
}

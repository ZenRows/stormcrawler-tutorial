This repository contains code associated with ZenRows' blog. 

The framework was generated with the StormCrawler Maven Archetype and customized into a full-fledged web crawler that stores data into CSV.

# Prerequisites

You need to install Apache Storm. The instructions on [setting up a Storm cluster](https://storm.apache.org/releases/2.6.2/Setting-up-a-Storm-cluster.html) should help. Alternatively, 
the [stormcrawler-docker](https://github.com/DigitalPebble/stormcrawler-docker) project contains resources for running Apache Storm on Docker.

# Build the Project
Build the project framework with Maven using the following command:

``` sh
mvn archetype:generate -DarchetypeGroupId=org.apache.stormcrawler -DarchetypeArtifactId=stormcrawler-archetype -DarchetypeVersion=3.1.0
```

# Compilation

Generate an uberjar with

``` sh
mvn clean package
```

# Running the crawl

You can now submit the topology using the storm command:

``` sh
python storm.py local <FULL_PROJECT_PATH>target/tormcrawler-tutorial-1.0.jar --local-ttl 60 com.tutorial.CrawlTopology -- -conf <FULL_PROJECT_PATH>/crawler-conf.yaml
```

This will run the topology in local mode for 60 seconds. Simply use the 'storm jar' to start the topology in distributed mode, where it will run indefinitely.

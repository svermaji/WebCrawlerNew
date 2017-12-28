# WebCrawler

Java application to WebCrawl using 
  - Maven
  - Junits
  - Jsoup library
  - logback
  - apache String utils

This program as of now checks base url, fetch page and prepare all urls for:
  - href attribute

Assumptions/Notes:
   - Running PC is not behind firewall/proxy settings - If yes, ensure you are able to access http url by doing needful
   - Java8 will be used
   - Windows machine is used to build the program
   - Attaching log for reference what final output generated
   - Javadoc added for logics, please refer

How to compile:
   - Use "maven install" command that will download dependencies and create jar

How to execute:
   - From folder that has dependencies and WebCrawler jar, run WebCrawler

With more time
    - Performance can be improved
    - Error handling can be improved

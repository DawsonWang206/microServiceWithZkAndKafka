# cloudDemo
One simple micro service used by Spring Cloud, Zookeeper and Kafka, Redis  
## License  
Apache 2.0  
## Contact info  
> Author: ***Dawson Wang***  
> Contact: ***wdt985@live.cn***  
> 
## Technical stack
* Spring Boot 2.7.11
* Spring Web
* Spring Cloud 2021.0.6
    - spring-cloud-starter-zookeeper-discovery
    - spring-cloud-starter-openfeign
    - spring-cloud-starter-zookeeper-config
    - spring-cloud-starter-gateway
* Spring spring-kafka 2.8.11
* spring-boot-starter-data-redis
* lombok
* Apache Zookeeper
* Apache Kafka  

## Structure
* `client1` or `client2` is a micro service use rpc through OpenFeign to consume each   
other restful Api, each app as a kafka sender and receiver to produce and consume Kafka  
message
* To start these services you need to config Zookeeper and Kafka settings and start them at the first

[Resource link](https://github.com/DawsonWang206/cloudDemo.git "链接地址")





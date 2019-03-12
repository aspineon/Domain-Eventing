[![Stories in Ready](https://badge.waffle.io/tfredrich/Domain-Eventing.png?label=ready)](https://waffle.io/tfredrich/Domain-Eventing)  

Overview
========
Domain-Eventing implements the Domain Events concept from Eric Evans' Domain Driven Design.  Enables simple messaging
for domain models within a single JVM, or using a distributed event bus, message across a cluster of JVMs.

This simple Java library provides a Singleton interface (see DomainEvents class) to create event bus(ses) and to
publish events (which are just POJOs) throughout the domain layer (POJOs must be Serializable for a distributed
event bus on Hazelcast).

Event handlers simply implement the EventHandler interface, which has two methods, handle(Object) and handles(Class).
The handle() method is the implementation for processing the domain event and handles() returns a boolean indicating
whether that particular EventHandler can process the given event.

Why Domain Eventing Instead of Messaging or ESB?
================================================
Messaging systems or ESB (Enterprise Service Bus) are very heavy and resource intensive. Small, quick, inter-application
messages don't usually need to be broadcast enterprise wide.  For instance, within an eventual-consistency database
model, cascade deletes may occur asynchronously, outside of the request.  This is a great candidate for inter-application
eventing instead of leveraging full-up JMS or other messaging system.

The domain eventing model supported is publish/subscribe (pub/sub)--sending messages to all subscribers that can process it.
There is no concept within this library of point-to-point or single consumer for a message and is, therefore, left as
an exercise for the reader... :-)

Event Production
================
In the core model, published events stay within the current Java virtual machine (JVM).  This is the simplest and fastest option.
However, as published events are in an in-memory queue, it is possible to lose messages if the JVM goes down unexpectedly.

Other implementations, either Hazelcast or Apache Ignite, can be configured to replicate events across a cluster of nodes
minimizing event loss. 

Maven Usage
===========
Stable:
```xml
		<dependency>
			<groupId>com.strategicgains.domain-eventing</groupId>
			<artifactId>domain-eventing-core</artifactId>
			<version>1.0</version>
		</dependency>
```
OR (for Akka-based eventing):
```xml
		<dependency>
			<groupId>com.strategicgains.domain-eventing</groupId>
			<artifactId>domain-eventing-akka</artifactId>
			<version>1.0</version>
		</dependency>
```
OR (for Hazelcast-based eventing):
```xml
		<dependency>
			<groupId>com.strategicgains.domain-eventing</groupId>
			<artifactId>domain-eventing-hazelcast</artifactId>
			<version>1.0</version>
		</dependency>
```
Development:
```xml
		<dependency>
			<groupId>com.strategicgains.domain-eventing</groupId>
			<artifactId>domain-eventing-core</artifactId>
			<version>2.0.0-SNAPSHOT</version>
		</dependency>
```
OR (for Apache Ignite-based eventing):
```xml
		<dependency>
			<groupId>com.strategicgains.domain-eventing</groupId>
			<artifactId>domain-eventing-ignite</artifactId>
			<version>2.0.0-SNAPSHOT</version>
		</dependency>
```
OR (for Hazelcast-based eventing):
```xml
		<dependency>
			<groupId>com.strategicgains.domain-eventing</groupId>
			<artifactId>domain-eventing-hazelcast</artifactId>
			<version>2.0.0-SNAPSHOT</version>
		</dependency>
```

Or download the jar directly from: 
http://search.maven.org/#search%7Cga%7C1%7C%22domain-eventing%22

Note that to use the SNAPSHOT version, you must enable snapshots and a repository in your pom file as follows:
```xml
  <profiles>
    <profile>
       <id>allow-snapshots</id>
          <activation><activeByDefault>true</activeByDefault></activation>
       <repositories>
         <repository>
           <id>snapshots-repo</id>
           <url>https://oss.sonatype.org/content/repositories/snapshots</url>
           <releases><enabled>false</enabled></releases>
           <snapshots><enabled>true</enabled></snapshots>
         </repository>
       </repositories>
     </profile>
  </profiles>
```

Release Notes
=============
### 2.0.0 - SNAPSHOT (in 'master' branch)
* Introduced concept of 'topic' for subscription to allow for larger messaging context (e.g. Kafka, RabbitMQ, etc.), which makes the Domain-Eventing library more of a messaging abstraction instead of just domain eventing.
* Introduced domain-eventing-ignite, supporting Apache Ignite.
* Upgraded to Hazelcast version 3.11.1
* Breaking Change: Removed Akka support due to lack of use. Let me know if I'm wrong.

### 1.0 - Release 10 Feb 2016
* Added domain-eventing-akka, supporting Akka-based eventing. Hazelcast eventing support will no longer be maintained unless somebody hollers.
* Requires Java 1.8 (due to Akka support).

### 0.4.5 - Released 2 Dec 2014
* Fixed misspelling of method LocalEventBusBuilder.addPublishableEventType().

### 0.4.4 - Released 29 May 2014
* Upgraded to Hazelcast 3.3 EA

### 0.4.3 - Released 4 Mar 2013
* Updated Hazelcast from 2.4 to 2.5

### 0.4.2 - Released 30 Jan 2013
* Introduced DomainEvents.publish(String, EventBus) to enable publishing to a specific, named EventBus implementation.
* Changed internal Map, handlersByEvent, to a ConcurrentHashMap instead of a HashMap, since it does get manipulated during execution.

### 0.4.1 - Released 16 Jan 2013
* Removed Ant build-related files
* Ensured Java 1.6 compatible artifact is released.

### 0.4.0 - Released 10 Jan 2013
* Introduced Maven build
* Released to Maven Central repository

### 0.3.0
* Introduced HazelCast for seamless intra-cluster (cross-node, multi-JVM) domain eventing.

### 0.2.0 - June 27, 2012
* Removed constraint of having to implement DomainEvent marker interface in event messages.
* Introduced EventQueue, allowing multiple EventMonitor threads to be processing events from the queue simultaneously.

### 0.1.0
* Initial release.

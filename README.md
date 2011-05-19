Overview
========
Domain-Eventing implements the Domain Events concept from Eric Evans' Domain Driven Design.

This simple Java library provides a Singleton interface (see DomainEvents class) to an EventMonitor thread and a simple
way to raise events (which implement DomainEvent marker interface) throughout the domain layer.

Event handlers simply implement the EventHandler interface, which has two methods, handle(DomainEvent) and handles(Class).
The handle() method is the implementation for dealing with the domain event and handles() returns a boolean indicating
whether that particular EventHandler can process the given DomainEvent class.

Event Flow
==========
### Domain Model
**DomainEvents.raise(event)** ----------> **eventMonitor.raise(event)** -----------> **concurrentQueue.add(event)**
                                                                             **monitorThread.notify()**

### Monitor Thread (on notify)
event = **concurrentQueue.poll()** -----> Get handlers that can            +---> **handler.handle(event)** ---+---> **wait()**
                                      process the given event.         |                ^             |
                                      Utilizes **handler.handles(event)**  |                |             |
                                      This collection of handlers      |          for each handler ---+
                                      is cached for later use. --------+

Usage
=====
1) Implement *DomainEvent* marker interface in the desired event types.
2) Implement *EventHandler* interface in class(es) to process appropriate events.
   a) Implement *handles(Class)* method to return true for each DomainEvent type that the handler can process.
   b) Implement *handle(DomainEvent)* to actually process the event.
3) Call *DomainEvents.register(EventHandler)* for each EventHandler implementation.
4) Call *DomainEvents.startMonitoring()* at the beginning of your application.
5) Call *DomainEvents.raise(DomainEvent)* in your domain code where events need to be raised.
   - repeat as necessary.
6) Call *DomainEvents.stopMonitoring()* at the end of your application.

Want more than a single thread and queue to handle your domain events?  Cool!  Then ignore the static foreign
methods in the *DomainEvents* class and utilize the *EventMonitor* thread alone.  Create as many instances of 
*EventMonitor* as you need.  Aside from the creation of *DomainEvent* and *EventHandler* implementations
(those parts are the same, see steps #1 & #2 above), here's the way to use *EventMonitor* on its own:

1) monitor = new EventMonitor()
2) monitor.register(EventHandler) for each EventHandler implementation.
2) monitor.start()
3) monitor.raise(DomainEvent) in your domain logic.
   - repeat as necessary
4) monitor.shutdown()
## How to use it?

- docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management - It will start RabbitMQ with the management UI accessible at http://localhost:15672 (username and password: guest/guest).
- It will create two queues and depending on path parameter will send message to queue1 or queue2
- start publisher and listeners (or any of them)
- call endpoint http://localhost:9000/publics/consumer or http://localhost:9000/publish/listener
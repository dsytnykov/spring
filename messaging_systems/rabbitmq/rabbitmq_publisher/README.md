It will create two queues and depending on path parameter will send message to queue1 or queue2
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
It will start RabbitMQ with the management UI accessible at http://localhost:15672 (username and password: guest/guest).
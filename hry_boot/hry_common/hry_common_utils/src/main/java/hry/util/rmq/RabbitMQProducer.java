package hry.util.rmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: liuchenghui
 * @Date: 2020/4/16 18:00
 * @Description: rabbitMQ 生产者
 */
@Component
@Slf4j
public class RabbitMQProducer {

    private RabbitAdmin rabbitAdmin;

    @Autowired
    public void setRabbitAdmin (RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate (RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private DirectExchange directExchange;

    @Autowired
    public void setDirectExchange (DirectExchange directExchange) {
        this.directExchange = directExchange;
    }

    /**
     * 有绑定Key的Exchange发送
     *
     * @param routingKey
     * @param msg
     */
    public void sendMessageToExchange (String routingKey, Object msg) {
        rabbitTemplate.convertAndSend(directExchange.getName(), routingKey, JSON.toJSONString(msg));
    }

    /**
     * 给queue发送消息
     *
     * @param queueName
     * @param msg
     */
    @Async
    public void sendMsgByQueue (String queueName, String msg) {
        Queue queue = new Queue(queueName, true, false, false);
        this.addQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(DirectExchange.DEFAULT).withQueueName();
        rabbitAdmin.declareBinding(binding);
        MessageProperties messageProperties = new MessageProperties();
        //设置消息内容的类型，默认是 application/octet-stream 会是 ASCII 码值
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        Message message = new Message(msg.getBytes(),messageProperties);
        rabbitTemplate.convertAndSend(DirectExchange.DEFAULT.getName(), queueName, message);
    }

    /**
     * 创建Exchange
     *
     * @param exchange
     */
    private void addExchange (AbstractExchange exchange) {
        rabbitAdmin.declareExchange(exchange);
    }

    /**
     * 删除一个Exchange
     *
     * @param exchangeName
     */
    public boolean deleteExchange (String exchangeName) {
        return rabbitAdmin.deleteExchange(exchangeName);
    }

    /**
     * 创建一个指定的Queue
     *
     * @param queue
     * @return queueName
     */
    private String addQueue (Queue queue) {
        return rabbitAdmin.declareQueue(queue);
    }

    /**
     * 删除一个queue
     *
     * @param queueName
     * @return true if the queue existed and was deleted.
     */
    public boolean deleteQueue (String queueName) {
        return rabbitAdmin.deleteQueue(queueName);
    }

    /**
     * 绑定一个队列到一个匹配型交换器使用一个routingKey
     *
     * @param queue
     * @param exchange
     * @param routingKey
     */
    public void addBinding (Queue queue, DirectExchange exchange, String routingKey) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
        rabbitAdmin.declareBinding(binding);
    }

    /**
     * 绑定一个队列到一个匹配型交换器
     *
     * @param queue
     * @param exchange
     */
    public void addBinding (Queue queue, DirectExchange exchange) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).withQueueName();
        rabbitAdmin.declareBinding(binding);
    }

    /**
     * 去掉一个binding
     *
     * @param binding
     */
    public void removeBinding (Binding binding) {
        rabbitAdmin.removeBinding(binding);
    }
}

package hry.util.rmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  @author: liuchenghui
 *  @Date: 2020/4/16 17:59
 *  @Description: rabbitMQ配置类
 *  参考：https://gitee.com/leichencode/rabbitmqDemo.git
 */
@Configuration
@Slf4j
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.exchangeName}")
    private String exchangeName;

    public RabbitMQConfig () {
        log.info("****************【系统启动中】加载rabbitMQ配置,****************");
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/16 15:51
     *  @Description: 获取连接工厂
     */
    @Bean
    public ConnectionFactory connectionFactory () {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/16 18:00
     *  @Description: 设置mq模版
     */
    @Bean
    public RabbitTemplate rabbitTemplate () {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setEncoding("utf-8");
        template.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("correlationData:{}; ack:{}; cause:{}", correlationData, ack, cause);
            if (!ack) {
                log.error("回调函数: confirm确认异常correlationData:{}; ack:{}; cause:{}", correlationData, ack, cause);
            }
        });
        template.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("return exchange: " + exchange + ", routingKey: "
                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
        });
        return template;
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/19 0:06
     *  @Description: 设置简单消息监听
     */
    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory){
        return new SimpleMessageListenerContainer(connectionFactory);
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/16 17:15
     *  @Description: 创建admin，来管理队列和交换机
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/16 17:29
     *  @Description: 创建直连交换机
     *    项目启动即能创建的Exchange
     *    可以创建各种类型的Exchange，父类都是 AbstractExchange
     *    这里举例Direct类型
     *    如果需要创建多个同类型可以用@Bean(name="beanName")，引用时用@Qualifier("beanName" )
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

}

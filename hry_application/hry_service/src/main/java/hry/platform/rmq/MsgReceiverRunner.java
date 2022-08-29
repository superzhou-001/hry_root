package hry.platform.rmq;

import hry.platform.utils.ConfigEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class MsgReceiverRunner implements CommandLineRunner {

    @Autowired
    private SimpleMessageListenerContainer simpleMessageListenerContainer;
    @Autowired
    private DirectExchange exchange;
    @Autowired
    private RabbitAdmin rabbitAdmin;

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/17 14:26
     *  @Description: 监听消息，并绑定队列与处理方法到监听容器中
     */
    @Override
    public void run (String... args) throws Exception {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MsgReceiverRunner());
        // 获取消息监听中所有队列名
        String[] queueNames = simpleMessageListenerContainer.getQueueNames();
        // 读取消费者配置信息
        Map<String, String> cfgMap = ConfigEnum.getQueueAndMethod();
        if (!cfgMap.isEmpty()) {
            Set<String> queues = new HashSet<>();
            cfgMap.forEach((key, val) -> {
                // 判断队列是否在当前监听队列中，如果不存在，创建队列并绑定
                if (!ArrayUtils.contains(queueNames, key)) {
                    Queue queue = new Queue(key, true, false, false);
                    rabbitAdmin.declareQueue(queue);
                    Binding binding = BindingBuilder.bind(queue).to(exchange).withQueueName();
                    rabbitAdmin.declareBinding(binding);
                }
                // 设置监听的队列及监听方法
                messageListenerAdapter.addQueueOrTagToMethodName(key, val);
                queues.add(key);
            });
            simpleMessageListenerContainer.addQueueNames(queues.toArray(new String[0]));
        }
        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);

    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/9 17:55
     *  @Description: 消息消费
     *  @RabbitHandler 代表此方法为接受到消息后的处理方法，但不会实现mq的重试机制
     */
    public void reqLogRecieved(byte[] content) {

    }

    public void testMethod(byte[] content) {
        // 消息处理
        String msg = new String(content, Charset.defaultCharset());
        log.info("MQ测试消息：{}" , msg);
    }
}

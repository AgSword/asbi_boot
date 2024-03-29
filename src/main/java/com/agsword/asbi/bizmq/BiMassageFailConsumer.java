package com.agsword.asbi.bizmq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbitmq.client.Channel;
import com.agsword.asbi.common.ErrorCode;
import com.agsword.asbi.constant.BiMqConstant;
import com.agsword.asbi.exception.BusinessException;
import com.agsword.asbi.model.entity.AiFrequency;
import com.agsword.asbi.model.entity.Chart;
import com.agsword.asbi.model.enums.ChartStatusEnum;
import com.agsword.asbi.service.AiFrequencyService;
import com.agsword.asbi.service.ChartService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author as
 */
@Component
@Slf4j
public class BiMassageFailConsumer {

    @Resource
    private ChartService chartService;

    @Resource
    private AiFrequencyService aiFrequencyService;

    /**
     * 监听死信队列
     *
     * @param message
     * @param channel
     * @param dekivery
     */
    @SneakyThrows
    @RabbitListener(queues = {BiMqConstant.BI_DLX_QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long dekivery) {
        // 接收到失败的信息，
        log.info("死信队列receiveMessage message{}", message);
        if (StringUtils.isBlank(message)) {
            channel.basicNack(dekivery, false, false);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "消息为空");
        }
        long chartId = Long.parseLong(message);
        Chart chart = chartService.getById(chartId);
        if (chart == null) {
            channel.basicNack(dekivery, false, false);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "图表不存在");
        }
        // 把图表标为失败
        chart.setChartStatus(ChartStatusEnum.FAILED.getValue());
        boolean updateById = chartService.updateById(chart);
        if (!updateById) {
            log.info("处理死信队列消息失败,失败图表id:{}", chart.getId());
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 把调用次数补偿给用户
        Long userId = chart.getUserId();
        QueryWrapper<AiFrequency> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        AiFrequency frequency = aiFrequencyService.getOne(wrapper);
        Integer remainFrequency = frequency.getRemainFrequency();
        frequency.setRemainFrequency(remainFrequency + 1);
        boolean result = aiFrequencyService.updateById(frequency);
        if (!result) {
            log.info("处理死信队列 补偿用户次数 消息失败,失败用户id:{}", userId);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 确认消息
        channel.basicAck(dekivery, false);
    }
    /**
     * 监听AI对话死信队列
     *
     * @param message
     * @param channel
     * @param dekivery
     */
    @SneakyThrows
    @RabbitListener(queues = {BiMqConstant.AI_DLX_QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveAiMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long dekivery) {
        // 接收到失败的信息，
        log.info("死信队列receiveMessage message{}", message);
        if (StringUtils.isBlank(message)) {
            channel.basicNack(dekivery, false, false);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "消息为空");
        }
        long chartId = Long.parseLong(message);
        Chart chart = chartService.getById(chartId);
        if (chart == null) {
            channel.basicNack(dekivery, false, false);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "图表不存在");
        }
        // 把图表标为失败
        chart.setChartStatus(ChartStatusEnum.FAILED.getValue());
        boolean updateById = chartService.updateById(chart);
        if (!updateById) {
            log.info("处理死信队列消息失败,失败的ai对话的id={}", chart.getId());
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 把调用次数补偿给用户
        Long userId = chart.getUserId();
        QueryWrapper<AiFrequency> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        AiFrequency frequency = aiFrequencyService.getOne(wrapper);
        Integer remainFrequency = frequency.getRemainFrequency();
        frequency.setRemainFrequency(remainFrequency + 1);
        boolean result = aiFrequencyService.updateById(frequency);
        if (!result) {
            log.info("处理死信队列 补偿用户次数 消息失败,失败用户id:{}", userId);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 确认消息
        channel.basicAck(dekivery, false);
    }

}

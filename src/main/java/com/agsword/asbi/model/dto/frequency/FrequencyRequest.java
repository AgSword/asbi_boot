package com.agsword.asbi.model.dto.frequency;

import lombok.Data;

import java.io.Serializable;

/**
 * @author as
 * 使用次数
 */
@Data
public class FrequencyRequest implements Serializable {
    private int frequency;
}

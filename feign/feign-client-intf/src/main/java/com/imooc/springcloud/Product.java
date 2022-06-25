package com.imooc.springcloud;

import lombok.Builder;
import lombok.Data;

/**
 * Created by simon.
 */
@Data
@Builder
public class Product {

    private Long productId;

    private String description;

    private Long stock;

}

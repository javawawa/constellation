package com.twosmall.constellation.service.impl;

import com.twosmall.constellation.entity.dao.OrderDao;
import com.twosmall.constellation.mapper.OrderMapper;
import com.twosmall.constellation.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author enzo
 * @since 2022-07-05
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, OrderDao> implements IOrderService {

}

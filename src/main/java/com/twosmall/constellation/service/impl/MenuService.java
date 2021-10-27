package com.twosmall.constellation.service.impl;

import com.twosmall.constellation.entity.dao.MenuDao;
import com.twosmall.constellation.mapper.MenuMapper;
import com.twosmall.constellation.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用配置表 服务实现类
 * </p>
 *
 * @author enzo
 * @since 2021-10-27
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, MenuDao> implements IMenuService {

}

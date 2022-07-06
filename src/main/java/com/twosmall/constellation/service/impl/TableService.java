package com.twosmall.constellation.service.impl;

import com.twosmall.constellation.entity.dao.TableDao;
import com.twosmall.constellation.mapper.TableMapper;
import com.twosmall.constellation.service.ITableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author enzo
 * @since 2022-06-08
 */
@Service
public class TableService extends ServiceImpl<TableMapper, TableDao> implements ITableService {

}

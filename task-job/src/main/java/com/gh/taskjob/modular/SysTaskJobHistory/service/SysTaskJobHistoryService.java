package com.gh.taskjob.modular.SysTaskJobHistory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gh.common.toolsclass.PageFilter;
import com.gh.common.toolsclass.ResultData;
import com.gh.taskjob.modular.SysTaskJobHistory.entity.SysTaskJobHistory;
import com.gh.taskjob.modular.SysTaskJobHistory.model.TaskHistorySortModel;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统-任务作业执行历史表 服务类
 * </p>
 *
 * @author gaohan
 * @since 2021-07-31
 */
public interface SysTaskJobHistoryService extends IService<SysTaskJobHistory> {

    boolean add(SysTaskJobHistory bo);

    ResultData<List<SysTaskJobHistory>> list(PageFilter<SysTaskJobHistory> filter);

    List<TaskHistorySortModel> listSort(SysTaskJobHistory bo);
}

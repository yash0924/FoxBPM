/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine.impl.cmd;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.Task;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

/**
 * 获取可退回的任务
 * @author ych
 *
 */
public class GetRollbackTasksCmd implements Command<List<Task>> {

	private String taskId;
	public GetRollbackTasksCmd(String taskId) {
		this.taskId = taskId;
	}
	
	 
	public List<Task> execute(CommandContext commandContext) {
		if(StringUtil.isEmpty(taskId)){
			throw ExceptionUtil.getException("10601201");
		}
		
		List<Task> results = new ArrayList<Task>();
		TaskEntity taskEntity = commandContext.getTaskManager().findTaskById(taskId);
		TokenEntity token = taskEntity.getToken();
		/** 获取当前令牌所有父亲令牌 */
		List<KernelTokenImpl> allParent = token.getAllParent();
		List<String> tokenIds = new ArrayList<String>();
		/** 创建出父令牌ID集合 */
		for (KernelTokenImpl tokenParent : allParent) {
			tokenIds.add(tokenParent.getId());
		}
		/** 将自己本身也添加进去 */
		tokenIds.add(token.getId());

		/** 查询相关联的完成的任务 */
		List<TaskEntity> tasks = commandContext.getTaskManager().findEndTasksByTokenIds(tokenIds);
		results.addAll(tasks);
		return results;
	}
}

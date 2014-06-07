package org.foxbpm.engine.test.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;

public class TaskServiceTest extends AbstractFoxBpmTestCase {

	public void testTaskQuery(){
		
		Authentication.setAuthenticatedUserId("2222");
		Map<String, Object> transientVariables=new HashMap<String, Object>();
		transientVariables.put("value", 10);
		ProcessInstance processInstance=runtimeService.startProcessInstanceById
		("process_foxbpm_1:1:ded8ceeb-6d8e-4cd9-b6e1-8e49a25beef7","bizkey",transientVariables, null);
		
		
		Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskNotEnd().singleResult();
		
		ExpandTaskCommand expandCommand = new ExpandTaskCommand();
		expandCommand.setCommandType("general");
		expandCommand.setTaskCommandId("HandleCommand_1");
		expandCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandCommand, null);
		
		taskService.createTaskQuery().taskAssignee("2222").taskCandidateUser("2222").taskNotEnd().listPage(0, 15);
		
		
		
		task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskNotEnd().singleResult();
		ExpandTaskCommand expandCommand2 = new ExpandTaskCommand();
		expandCommand2.setCommandType("general");
		expandCommand2.setTaskCommandId("HandleCommand_1");
		expandCommand2.setTaskId(task.getId());
		taskService.expandTaskComplete(expandCommand2, null);
		
		processInstance=runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		
		assertTrue(processInstance.isEnd());
		
	}
	
	public void testGetTaskCommand(){
		Authentication.setAuthenticatedUserId("2222");
		Map<String, Object> transientVariables=new HashMap<String, Object>();
		transientVariables.put("value", 10);
		ProcessInstance processInstance=runtimeService.startProcessInstanceById
		("process_foxbpm_1:1:ded8ceeb-6d8e-4cd9-b6e1-8e49a25beef7","bizkey",transientVariables, null);
		Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskNotEnd().singleResult();
		
		List<TaskCommand> taskCommands = taskService.getTaskCommandByTaskId(task.getId());
		System.out.println(taskCommands.size());
		
	}
	
	public void testGetTaskCommandByKey(){
		Authentication.setAuthenticatedUserId("2222");
		List<TaskCommand> taskCommands = taskService.getSubTaskCommandByKey("process_foxbpm_1");
		
		System.out.println(taskCommands);
	}
	
	
}
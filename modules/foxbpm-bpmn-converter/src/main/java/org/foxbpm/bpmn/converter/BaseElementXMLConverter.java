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
 * @author yangguangftlp
 */
package org.foxbpm.bpmn.converter;

import org.dom4j.Element;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.BpmnModel;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public abstract class BaseElementXMLConverter implements FlowElementFactory {
	public abstract Class<? extends BaseElement> getBpmnElementType();
	public abstract void convertXMLToModel(Element element, BaseElement baseElement);
	public abstract void convertModelToXML(Element element, BpmnModel model);
	public abstract String getXMLElementName();
}
/*
 * Copyright 2002-2011 the original author or authors.
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
 */

package org.springframework.web.servlet.mvc.method.annotation.support;

import org.springframework.core.MethodParameter;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.support.ModelMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.support.RedirectModel;

/**
 * Resolves {@link RedirectModel} method arguments. 
 * 
 * <p>This resolver must be listed ahead of the {@link ModelMethodProcessor}, 
 * which also resolves arguments of type {@link Model}.  
 *
 * @author Rossen Stoyanchev
 * @since 3.1
 */
public class RedirectModelMethodArgumentResolver implements HandlerMethodArgumentResolver {

	public boolean supportsParameter(MethodParameter parameter) {
		return RedirectModel.class.equals(parameter.getParameterType());
	}

	public Object resolveArgument(MethodParameter parameter, 
								  ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest, 
								  WebDataBinderFactory binderFactory) throws Exception {
		DataBinder dataBinder = binderFactory.createBinder(webRequest, null, null);
		ModelMap implicitModel = mavContainer.getModel();
		RedirectModel redirectModel = new RedirectModel(dataBinder, implicitModel);
		mavContainer.setRedirectModel(redirectModel);
		return redirectModel;
	}

}
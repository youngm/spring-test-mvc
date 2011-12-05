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

package org.springframework.test.web.server.samples.standalone.resultmatchers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.server.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Examples of expectations on created session attributes. 
 * 
 * @author Rossen Stoyanchev
 */
public class SessionAttributeResultMatcherTests {

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = standaloneSetup(new SimpleController()).build();
	}
	
	@Test
	public void testSessionAttributeEqualTo() throws Exception {
		this.mockMvc.perform(get("/"))
            .andExpect(request().sessionAttribute("locale", Locale.UK));
		
		// Hamcrest matchers...
		this.mockMvc.perform(get("/"))
	        .andExpect(request().sessionAttribute("locale", equalTo(Locale.UK)));
	}
	
	@Test
	public void testSessionAttributeMatcher() throws Exception {
		this.mockMvc.perform(get("/"))
	        .andExpect(request().sessionAttribute("locale", notNullValue()));
	}
	
	
	@Controller
	@SessionAttributes("locale")
	@SuppressWarnings("unused")
	private static class SimpleController {
		
		@ModelAttribute
		public void populate(Model model) {
			model.addAttribute("locale", Locale.UK);
		}
		
		@RequestMapping("/")
		public String handle() {
			return "view";
		}
	}
	
}
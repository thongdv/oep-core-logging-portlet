/**
 * Copyright (c) 2015 by Open eGovPlatform (http://http://openegovplatform.org/).
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.oep.core.logging.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/** 
 * ConfigUtil Class help to getValue configuration in file.
 * This class contains method get value
 * 
 * Version: 1.0
 *  
 * History: 
 *   DATE        AUTHOR      DESCRIPTION 
 *  ------------------------------------------------- 
 *  2-Apr-2015  liemnn    Create new
 */


public class ConfigUtil {
	
	
	public static final String DATABASE="org.oep.database";
	
	// value configuration path
	private static final String CONFIG_PATH = "/oep-config.properties";

	// this is static cache for value configuration
	private static Map<String, String> values = null;
	
	/** 
	 * This is function get value by key in file configuration of system.
	 * @param : key string 
	 * @param : defaultValue  , if not found return defautlvalue
	 * 
	 * Version: 1.0
	 *  
	 * History: 
	 *   DATE        AUTHOR      DESCRIPTION 
	 *  ------------------------------------------------- 
	 *  2-Apr-2015  liemnn    Create new
	 * @throws IOException 
	 */
	
	public static String getValue(String key,String defaultValue){
		
		if(values == null){ 
			// load from file config
			try {
				values = getConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (values != null) {
			if (values.containsKey(key)) {
				return values.get(key);
			}
		}
		return defaultValue;
		
	}
	
	private static Map<String, String> getConfig() throws IOException {
		
		Map<String, String> returnValue = new HashMap<String, String>();
		Properties prop = new Properties();
		InputStream is = ConfigUtil.class.getResourceAsStream(CONFIG_PATH);
		prop.load(is);
		is.close();
		Set<Object> keys = prop.keySet();
		if (keys != null && keys.size() > 0) {
			for (Object key : keys) {
				String temp = String.valueOf(key);
				returnValue.put(temp, prop.getProperty(temp));
			}
		}
		return returnValue;
	}
	
	static {
		if(values == null) { 
			// load from file config
			try {
				values = getConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
  
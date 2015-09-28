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

package org.oep.core.logging.service.persistence;

import java.util.Date;
import java.util.List;

import org.oep.core.logging.model.UserActivity;
import org.oep.core.logging.model.impl.UserActivityImpl;
import org.oep.core.logging.service.persistence.UserActivityFinder;
import org.oep.core.logging.util.CustomSQLUtil;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

public class UserActivityFinderImpl extends BasePersistenceImpl<UserActivity> implements UserActivityFinder{
	/** 
	 * This method find UserActivity by action,createdate
	 * 
	 * Version: OEP 2.0
	 *  
	 * History: 
	 *   DATE        AUTHOR      DESCRIPTION 
	 *  ------------------------------------------------- 
	 *  20-September-2015  ThongDV    Create new
	 * @param action
	 * @param fromDate
	 * @param toDate
	 * @param begin
	 * @param end
	 * @return List<UserActivity>
	 */
	public List<UserActivity> getByParam(long companyId,long groupId,String action, Date fromDate, Date toDate,int begin, int end){
		Session session = openSession();
		String strQuery = getQuery(GET_USERACTIVITY_BY_PARAM,action, fromDate, toDate);
		log.info("SQL query: "+strQuery);
		SQLQuery query = session.createSQLQuery(strQuery);
		query.addEntity("oep_logging_useractivity", UserActivityImpl.class);
		QueryPos queryPos = QueryPos.getInstance(query);
		setParam(companyId,groupId,queryPos,  action, fromDate, toDate); 
		return (List<UserActivity>) QueryUtil.list(query, getDialect(), begin, end);
	}
	
	/** 
	 * This method count UserActivity by parameter with custom sql
	 * 
	 * Version: OEP 2.0
	 *  
	 * History: 
	 *   DATE        AUTHOR      DESCRIPTION 
	 *  ------------------------------------------------- 
	 *  20-September-2015  ThongDV    Create new
	 * @param action
	 * @param fromDate
	 * @param toDate
	 * @param begin
	 * @param end
	 * @return Integer
	 */
	public int countByParam(long companyId,long groupId,String action, Date fromDate, Date toDate){
		Session session = openSession();
		String strQuery = getQuery(COUNT_USERACTIVITY_BY_PARAM, action, fromDate, toDate);
		log.info("SQL query: "+strQuery);
		SQLQuery query = session.createSQLQuery(strQuery);
		query.addScalar("total", Type.LONG);
		QueryPos queryPos = QueryPos.getInstance(query);
		setParam(companyId,groupId,queryPos, action, fromDate, toDate);
		Long total= (Long) query.uniqueResult();
		log.info("result: total="+total);
		return total.intValue();
	}
	
	 /** 
	 * This method get string query
	 * 
	 * Version: OEP 2.0
	 *  
	 * History: 
	 *   DATE        AUTHOR      DESCRIPTION 
	 *  ------------------------------------------------- 
	 *  20-September-2015  ThongDV    Create new
	 * @param action
	 * @param fromDate
	 * @param toDate
	 * @return String
	  */
	private String getQuery(String strQuery,String action, Date fromDate, Date toDate){
		String query= strQuery;
		String qHanhDong= "";
		String cond=" AND";
		if(fromDate != null){
			query = StringUtil.replace(query, "[$CREATEDATE_BEGIN$]", cond + CREATEDATE_BEGIN);
		} else {
			query = StringUtil.replace(query, "[$CREATEDATE_BEGIN$]", StringPool.BLANK);
		}
		if(toDate != null){
			query = StringUtil.replace(query, "[$CREATEDATE_END$]", cond + CREATEDATE_END);
		} else{
			query = StringUtil.replace(query, "[$CREATEDATE_END$]", StringPool.BLANK);
		}
		if(!"".equals(action)){
			qHanhDong += cond;
			qHanhDong += " action= ? ";
			query = StringUtil.replace(query, "[$ACTION$]", qHanhDong);
		} else {
			query = StringUtil.replace(query, "[$ACTION$]", StringPool.BLANK);
		}
		
		return query;
	}
	
	/** 
	 * This method set parameter for sql query
	 * 
	 * Version: OEP 2.0
	 *  
	 * History: 
	 *   DATE        AUTHOR      DESCRIPTION 
	 *  ------------------------------------------------- 
	 *  20-September-2015  ThongDV    Create new
	 * @param action
	 * @param fromDate
	 * @param toDate
	 */
	private void setParam(long companyId,long groupId,QueryPos queryPos,String action, Date fromDate, Date toDate ){
		queryPos.add(companyId);
		queryPos.add(groupId);
		if(fromDate != null){
			queryPos.add(fromDate);
		}
		if(toDate != null){
			queryPos.add(toDate);
		}
		if(!"".equals(action)){
			queryPos.add(action);
		}
	}
	
	public List<String> getDistinctAction(long companyId,long groupId){
		Session session;
		session= openSession();
		SQLQuery query= session.createSQLQuery(GET_DISTINCT_ACTION);
		query.addScalar("action", Type.STRING);
		QueryPos queryPos = QueryPos.getInstance(query);
		queryPos.add(companyId);
		queryPos.add(groupId);
		return (List<String>)QueryUtil.list(query, getDialect(), -1, -1);
	}
	
	private Log  log= LogFactoryUtil.getLog(UserActivityFinderImpl.class);
	private static final String GET_USERACTIVITY_BY_PARAM = CustomSQLUtil.get(UserActivityFinderImpl.class.getName(), "getUserActivity");
	private static final String COUNT_USERACTIVITY_BY_PARAM = CustomSQLUtil.get(UserActivityFinderImpl.class.getName(), "countUserActivity");
	private static final String CREATEDATE_BEGIN = CustomSQLUtil.get(UserActivityFinderImpl.class.getName(), "CREATEDATE_BEGIN");
	private static final String CREATEDATE_END = CustomSQLUtil.get(UserActivityFinderImpl.class.getName(), "CREATEDATE_END");
	private static final String GET_DISTINCT_ACTION=CustomSQLUtil.get(UserActivityFinderImpl.class.getName(), "getDistinctAction");
}

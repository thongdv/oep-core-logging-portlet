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

import org.oep.core.logging.model.NewUserLog;
import org.oep.core.logging.model.impl.NewUserLogImpl;
import org.oep.core.logging.service.persistence.NewUserLogFinder;
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

public class NewUserLogFinderImpl extends BasePersistenceImpl<NewUserLog> implements NewUserLogFinder{
	 /** 
		 * This method get User's new log by parameter(search user activity)
		 * 
		 * Version: OEP 2.0
		 *  
		 * History: 
		 *   DATE        AUTHOR      DESCRIPTION 
		 *  ------------------------------------------------- 
		 *  20-September-2015  ThongDV    Create new
		 * @param keyWord
		 * @param isUsername
		 * @param isEmail
		 * @param fromDate
		 * @param toDate
		 * @param begin
		 * @param end
		 * @return List<NewUserLog>
		 */
		public List<NewUserLog>  getByParam(long companyId,long groupId,String keyWord,boolean isUsername, boolean isEmail, Date fromDate, Date toDate, int begin, int end) {
			Session session = openSession();
			String strQuery= getQuery(GET_NEWUSERLOG_PARAM,keyWord,isUsername, isEmail, fromDate, toDate);
			log.info("SQL query: "+strQuery);
			SQLQuery query = session.createSQLQuery(strQuery);
			query.addEntity("oep_logging_newuserlog", NewUserLogImpl.class);
			QueryPos queryPos= QueryPos.getInstance(query);
			setParam(companyId,groupId,queryPos, keyWord, isUsername, isEmail, fromDate, toDate);
			return (List<NewUserLog>)QueryUtil.list(query, getDialect(), begin, end);
		}
		
		/** 
		 * This method count User's new log with by list parameter (search user activity)
		 * 
		 * Version: OEP 2.0
		 *  
		 * History: 
		 *   DATE        AUTHOR      DESCRIPTION 
		 *  ------------------------------------------------- 
		 *  20-September-2015  ThongDV    Create new
		 * @param keyWord
		 * @param isUsername
		 * @param isEmail
		 * @param fromDate
		 * @param toDate
		 * @return Integer
		 */
		public int countByParam(long companyId,long groupId,String keyWord,boolean isUsername, boolean isEmail, Date fromDate, Date toDate) {
			Session session = openSession();
			String strQuery= getQuery(COUNT_NEWUSERLOG_PARAM, keyWord,  isUsername, isEmail, fromDate, toDate);
			log.info("SQL query: "+strQuery);
			SQLQuery query = session.createSQLQuery(strQuery);
			query.addScalar("total", Type.LONG);
			QueryPos queryPos= QueryPos.getInstance(query);
			setParam(companyId,groupId,queryPos, keyWord,isUsername, isEmail, fromDate, toDate);
			Long total = (Long)query.uniqueResult();
			log.info("result: total="+total);
			return total.intValue();
		}
		
		 /** 
		 * This is method get string query
		 * 
		 * Version: OEP 2.0
		 *  
		 * History: 
		 *   DATE        AUTHOR      DESCRIPTION 
		 *  ------------------------------------------------- 
		 *  20-September-2015  ThongDV    Create new
		 * @return String
		 * @param keyWord
		 * @param isUsername
		 * @param isEmail
		 * @param fromDate
		 * @param toDate
		 */
		private String getQuery(String strQuery,String keyWord, boolean isUsername, boolean isEmail, Date fromDate, Date toDate){
			String query = strQuery;
			String qUsername = "";
			String qEmail = "";
			String cond =" AND ";
			if(fromDate != null){
				query = StringUtil.replace(query, "[$CREATEDATE_BEGIN$]", cond + CREATEDATE_BEGIN);
				cond= " AND ";
			}else{
				query = StringUtil.replace(query, "[$CREATEDATE_BEGIN$]", StringPool.BLANK);
			}
			if(toDate != null){
				query = StringUtil.replace(query, "[$CREATEDATE_END$]", cond + CREATEDATE_END);
				cond= " AND ";
			}else{
				query = StringUtil.replace(query, "[$CREATEDATE_END$]", StringPool.BLANK);
			}
			if(!"".equals(keyWord)) {
				if(isUsername){
					qUsername += cond;
					qUsername += " userName LIKE ? ";
					cond = " OR ";
					query = StringUtil.replace(query,"[$IS_USERNAME$]", qUsername);
				}else{
					query = StringUtil.replace(query,"[$IS_USERNAME$]", StringPool.BLANK);
				}
				if(isEmail){
					qEmail += cond;
					qEmail += " email LIKE ? ";
					cond = " AND ";
					query = StringUtil.replace(query,"[$IS_EMAIL$]", qEmail);
				}else{
					query = StringUtil.replace(query,"[$IS_EMAIL$]", StringPool.BLANK);
				}
			} else {
				query = StringUtil.replace(query,"[$IS_USERNAME$]", StringPool.BLANK);
				query = StringUtil.replace(query,"[$IS_EMAIL$]", StringPool.BLANK);
			}		
			return query;
		}
		
		 /** 
		 * This is method set parameter for sql query
		 * 
		 * Version: OEP 2.0
		 *  
		 * History: 
		 *   DATE        AUTHOR      DESCRIPTION 
		 *  ------------------------------------------------- 
		 *  20-September-2015  ThongDV    Create new
		 * @param keyWord
		 * @param isUsername
		 * @param isEmail
		 * @param fromDate
		 * @param toDate
		 */
		private void setParam(long companyId, long groupId,QueryPos queryPos,String keyWord,boolean isUsername, boolean isEmail, Date fromDate, Date toDate ){
			queryPos.add(companyId);
			queryPos.add(groupId);
			if(fromDate != null) {
				queryPos.add(fromDate);
			}
			if(toDate != null) {
				queryPos.add(toDate);
			}
			if(!"".equals(keyWord)) {
				if(isUsername){
					queryPos.add("%" + keyWord + "%");
				}
				if(isEmail){
					queryPos.add("%" + keyWord + "%");
				}
			}
		}
		
		private Log log= LogFactoryUtil.getLog(NewUserLogFinderImpl.class);
		private static final String GET_NEWUSERLOG_PARAM = CustomSQLUtil.get(NewUserLogFinderImpl.class.getName(), "getNewUserLog");
		private static final String COUNT_NEWUSERLOG_PARAM = CustomSQLUtil.get(NewUserLogFinderImpl.class.getName(), "countNewUserLog");
		private static final String CREATEDATE_BEGIN = CustomSQLUtil.get(NewUserLogFinderImpl.class.getName(), "CREATEDATE_BEGIN");
		private static final String CREATEDATE_END = CustomSQLUtil.get(NewUserLogFinderImpl.class.getName(), "CREATEDATE_END");

}

/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package org.oep.core.logging.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.persistence.UserPersistence;

import org.oep.core.logging.model.NewUserLog;
import org.oep.core.logging.service.NewUserLogLocalService;
import org.oep.core.logging.service.persistence.NewUserLogFinder;
import org.oep.core.logging.service.persistence.NewUserLogPersistence;
import org.oep.core.logging.service.persistence.UserActivityFinder;
import org.oep.core.logging.service.persistence.UserActivityPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the new user log local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link org.oep.core.logging.service.impl.NewUserLogLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see org.oep.core.logging.service.impl.NewUserLogLocalServiceImpl
 * @see org.oep.core.logging.service.NewUserLogLocalServiceUtil
 * @generated
 */
public abstract class NewUserLogLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements NewUserLogLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link org.oep.core.logging.service.NewUserLogLocalServiceUtil} to access the new user log local service.
	 */

	/**
	 * Adds the new user log to the database. Also notifies the appropriate model listeners.
	 *
	 * @param newUserLog the new user log
	 * @return the new user log that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NewUserLog addNewUserLog(NewUserLog newUserLog)
		throws SystemException {
		newUserLog.setNew(true);

		return newUserLogPersistence.update(newUserLog);
	}

	/**
	 * Creates a new new user log with the primary key. Does not add the new user log to the database.
	 *
	 * @param id the primary key for the new new user log
	 * @return the new new user log
	 */
	@Override
	public NewUserLog createNewUserLog(long id) {
		return newUserLogPersistence.create(id);
	}

	/**
	 * Deletes the new user log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the new user log
	 * @return the new user log that was removed
	 * @throws PortalException if a new user log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public NewUserLog deleteNewUserLog(long id)
		throws PortalException, SystemException {
		return newUserLogPersistence.remove(id);
	}

	/**
	 * Deletes the new user log from the database. Also notifies the appropriate model listeners.
	 *
	 * @param newUserLog the new user log
	 * @return the new user log that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public NewUserLog deleteNewUserLog(NewUserLog newUserLog)
		throws SystemException {
		return newUserLogPersistence.remove(newUserLog);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(NewUserLog.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return newUserLogPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.oep.core.logging.model.impl.NewUserLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return newUserLogPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.oep.core.logging.model.impl.NewUserLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return newUserLogPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return newUserLogPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) throws SystemException {
		return newUserLogPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public NewUserLog fetchNewUserLog(long id) throws SystemException {
		return newUserLogPersistence.fetchByPrimaryKey(id);
	}

	/**
	 * Returns the new user log with the primary key.
	 *
	 * @param id the primary key of the new user log
	 * @return the new user log
	 * @throws PortalException if a new user log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public NewUserLog getNewUserLog(long id)
		throws PortalException, SystemException {
		return newUserLogPersistence.findByPrimaryKey(id);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return newUserLogPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the new user logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.oep.core.logging.model.impl.NewUserLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of new user logs
	 * @param end the upper bound of the range of new user logs (not inclusive)
	 * @return the range of new user logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<NewUserLog> getNewUserLogs(int start, int end)
		throws SystemException {
		return newUserLogPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of new user logs.
	 *
	 * @return the number of new user logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getNewUserLogsCount() throws SystemException {
		return newUserLogPersistence.countAll();
	}

	/**
	 * Updates the new user log in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param newUserLog the new user log
	 * @return the new user log that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NewUserLog updateNewUserLog(NewUserLog newUserLog)
		throws SystemException {
		return newUserLogPersistence.update(newUserLog);
	}

	/**
	 * Returns the new user log local service.
	 *
	 * @return the new user log local service
	 */
	public org.oep.core.logging.service.NewUserLogLocalService getNewUserLogLocalService() {
		return newUserLogLocalService;
	}

	/**
	 * Sets the new user log local service.
	 *
	 * @param newUserLogLocalService the new user log local service
	 */
	public void setNewUserLogLocalService(
		org.oep.core.logging.service.NewUserLogLocalService newUserLogLocalService) {
		this.newUserLogLocalService = newUserLogLocalService;
	}

	/**
	 * Returns the new user log persistence.
	 *
	 * @return the new user log persistence
	 */
	public NewUserLogPersistence getNewUserLogPersistence() {
		return newUserLogPersistence;
	}

	/**
	 * Sets the new user log persistence.
	 *
	 * @param newUserLogPersistence the new user log persistence
	 */
	public void setNewUserLogPersistence(
		NewUserLogPersistence newUserLogPersistence) {
		this.newUserLogPersistence = newUserLogPersistence;
	}

	/**
	 * Returns the new user log finder.
	 *
	 * @return the new user log finder
	 */
	public NewUserLogFinder getNewUserLogFinder() {
		return newUserLogFinder;
	}

	/**
	 * Sets the new user log finder.
	 *
	 * @param newUserLogFinder the new user log finder
	 */
	public void setNewUserLogFinder(NewUserLogFinder newUserLogFinder) {
		this.newUserLogFinder = newUserLogFinder;
	}

	/**
	 * Returns the user activity local service.
	 *
	 * @return the user activity local service
	 */
	public org.oep.core.logging.service.UserActivityLocalService getUserActivityLocalService() {
		return userActivityLocalService;
	}

	/**
	 * Sets the user activity local service.
	 *
	 * @param userActivityLocalService the user activity local service
	 */
	public void setUserActivityLocalService(
		org.oep.core.logging.service.UserActivityLocalService userActivityLocalService) {
		this.userActivityLocalService = userActivityLocalService;
	}

	/**
	 * Returns the user activity persistence.
	 *
	 * @return the user activity persistence
	 */
	public UserActivityPersistence getUserActivityPersistence() {
		return userActivityPersistence;
	}

	/**
	 * Sets the user activity persistence.
	 *
	 * @param userActivityPersistence the user activity persistence
	 */
	public void setUserActivityPersistence(
		UserActivityPersistence userActivityPersistence) {
		this.userActivityPersistence = userActivityPersistence;
	}

	/**
	 * Returns the user activity finder.
	 *
	 * @return the user activity finder
	 */
	public UserActivityFinder getUserActivityFinder() {
		return userActivityFinder;
	}

	/**
	 * Sets the user activity finder.
	 *
	 * @param userActivityFinder the user activity finder
	 */
	public void setUserActivityFinder(UserActivityFinder userActivityFinder) {
		this.userActivityFinder = userActivityFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();

		PersistedModelLocalServiceRegistryUtil.register("org.oep.core.logging.model.NewUserLog",
			newUserLogLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"org.oep.core.logging.model.NewUserLog");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	@Override
	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != _classLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected Class<?> getModelClass() {
		return NewUserLog.class;
	}

	protected String getModelClassName() {
		return NewUserLog.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = newUserLogPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = org.oep.core.logging.service.NewUserLogLocalService.class)
	protected org.oep.core.logging.service.NewUserLogLocalService newUserLogLocalService;
	@BeanReference(type = NewUserLogPersistence.class)
	protected NewUserLogPersistence newUserLogPersistence;
	@BeanReference(type = NewUserLogFinder.class)
	protected NewUserLogFinder newUserLogFinder;
	@BeanReference(type = org.oep.core.logging.service.UserActivityLocalService.class)
	protected org.oep.core.logging.service.UserActivityLocalService userActivityLocalService;
	@BeanReference(type = UserActivityPersistence.class)
	protected UserActivityPersistence userActivityPersistence;
	@BeanReference(type = UserActivityFinder.class)
	protected UserActivityFinder userActivityFinder;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private String _beanIdentifier;
	private ClassLoader _classLoader;
	private NewUserLogLocalServiceClpInvoker _clpInvoker = new NewUserLogLocalServiceClpInvoker();
}
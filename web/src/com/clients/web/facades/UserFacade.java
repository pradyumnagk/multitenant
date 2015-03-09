/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2012 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package com.clients.web.facades;

import com.clients.web.controllers.forms.registration.RegistrationInfo;
import com.clients.web.data.UserData;


public interface UserFacade
{

	UserData registerUser(RegistrationInfo info);

	boolean login(UserData user);

	UserData getUser(String login);

	UserData getUser();

	boolean userExists(String login);

	boolean isLoggedIn();

	boolean checkCredentials(String login, String password);

}

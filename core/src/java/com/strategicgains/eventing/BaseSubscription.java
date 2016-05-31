/*
    Copyright 2016, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package com.strategicgains.eventing;

/**
 * 
 * @author tfredrich
 * @since 23 May 2016
 */
public class BaseSubscription
implements Subscription
{
	private Consumer consumer;

	public BaseSubscription(Consumer consumer)
	{
		super();
		this.consumer = consumer;
	}

	@Override
	public Consumer getConsumer()
	{
		return consumer;
	}
}
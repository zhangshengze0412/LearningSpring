# 注册BeanDefinition

### 接口BeanDefinitionRegistry

BeanDefinitionRegistry定义了对BeanDefinition管理操作，包括注册，删除等等

BeanDefinitionRegistry#registerBeanDefinition（String beanName，BeanDefinition beanDefinition），用于BeanDefinition的注册，默认实现类DefaultListableBeanFactory实现了该接口

~~~java
// 注册BeanDefinition
@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
			throws BeanDefinitionStoreException {

		Assert.hasText(beanName, "Bean name must not be empty");
		Assert.notNull(beanDefinition, "BeanDefinition must not be null");

		if (beanDefinition instanceof AbstractBeanDefinition) {
			try {
                // 对BeanDefinition进行校验
				((AbstractBeanDefinition) beanDefinition).validate();
			}
			catch (BeanDefinitionValidationException ex) {
				throw new BeanDefinitionStoreException(beanDefinition.getResourceDescription(), beanName,
						"Validation of bean definition failed", ex);
			}
		}

        // 在容器里对beanName的BeanDefinition进行查找，判断是否已经存在
		BeanDefinition existingDefinition = this.beanDefinitionMap.get(beanName);
		if (existingDefinition != null) {
            // 判断是否可以覆盖，Spring 2.1 之后人为的改为fasle
			if (!isAllowBeanDefinitionOverriding()) {
				throw new BeanDefinitionOverrideException(beanName, beanDefinition, existingDefinition);
			}
            // 角色判断
			else if (existingDefinition.getRole() < beanDefinition.getRole()) {
				// e.g. was ROLE_APPLICATION, now overriding with ROLE_SUPPORT or ROLE_INFRASTRUCTURE
				if (logger.isInfoEnabled()) {
					logger.info("Overriding user-defined bean definition for bean '" + beanName +
							"' with a framework-generated bean definition: replacing [" +
							existingDefinition + "] with [" + beanDefinition + "]");
				}
			}
            // 判断已存在的BeanDefinition和目前需要注册的BeanDefinition是否相等
			else if (!beanDefinition.equals(existingDefinition)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Overriding bean definition for bean '" + beanName +
							"' with a different definition: replacing [" + existingDefinition +
							"] with [" + beanDefinition + "]");
				}
			}
			else {
				if (logger.isTraceEnabled()) {
					logger.trace("Overriding bean definition for bean '" + beanName +
							"' with an equivalent definition: replacing [" + existingDefinition +
							"] with [" + beanDefinition + "]");
				}
			}
            // 注册，放入到容器中（ConcurrentHashMap）
			this.beanDefinitionMap.put(beanName, beanDefinition);
		}
        // 不存在同名的BeanDefinition
		else {
            // 是否正在创建该Bean
			if (hasBeanCreationStarted()) {
				// Cannot modify startup-time collection elements anymore (for stable iteration)
				synchronized (this.beanDefinitionMap) {
					this.beanDefinitionMap.put(beanName, beanDefinition);
					List<String> updatedDefinitions = new ArrayList<>(this.beanDefinitionNames.size() + 1);
					updatedDefinitions.addAll(this.beanDefinitionNames);
					updatedDefinitions.add(beanName);
					this.beanDefinitionNames = updatedDefinitions;
					removeManualSingletonName(beanName);
				}
			}
			else {
				// Still in startup registration phase
                // 存放在容器中，ConcurrentHashMap是无序的，通过在ArrayList保存beanName存在注册BeanDefinition的实现
				this.beanDefinitionMap.put(beanName, beanDefinition);
				this.beanDefinitionNames.add(beanName);
				removeManualSingletonName(beanName);
			}
			this.frozenBeanDefinitionNames = null;
		}

		if (existingDefinition != null || containsSingleton(beanName)) {
			resetBeanDefinition(beanName);
		}
	}
~~~



### 接口SingletonBeanRegistry

SingletonBeanRegistry接口用于管理单体对象的注册，方法SingletonBeanRegistry#registerSingleton（String beanName, Object singletonObject）用于单体对象的注册，同样该方法被DefaultListableBeanFactory所实现

~~~java
@Override
public void registerSingleton(String beanName, Object singletonObject) throws IllegalStateException {
    // 注册单体对象
	super.registerSingleton(beanName, singletonObject);
    // 更新容器内手工注册的单体集
	updateManualSingletonNames(set -> set.add(beanName), set -> !this.beanDefinitionMap.containsKey(beanName));
	clearByTypeCache();
}

@Override
public void registerSingleton(String beanName, Object singletonObject) throws IllegalStateException {
	Assert.notNull(beanName, "Bean name must not be null");
	Assert.notNull(singletonObject, "Singleton object must not be null");
    // 加锁，先获取后添加不是原子操作
	synchronized (this.singletonObjects) {
		Object oldObject = this.singletonObjects.get(beanName);
		if (oldObject != null) {
			throw new IllegalStateException("Could not register object [" + singletonObject + "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
		}
		addSingleton(beanName, singletonObject);
	}
}

/**
 * Add the given singleton object to the singleton cache of this factory.
 * <p>To be called for eager registration of singletons.
 * @param beanName the name of the bean
 * @param singletonObject the singleton object
 */
protected void addSingleton(String beanName, Object singletonObject) {
	synchronized (this.singletonObjects) {
        // ConcurrentHashMap
		this.singletonObjects.put(beanName, singletonObject);
		this.singletonFactories.remove(beanName);
		this.earlySingletonObjects.remove(beanName);
        // LinkedHashSet 按插入顺序
		this.registeredSingletons.add(beanName);
	}
}
~~~



### 非Spring 容器管理对象

在ConfigurableListableBeanFactory中定义了registerResolvableDependency（Class<?>  dependencyType, @Nullable Object autowiredVale）用于注册 非Spring容器管理对象

DefaultListableBeanFactory中实现类该接口

~~~java
@Override
public void registerResolvableDependency(Class<?> dependencyType, @Nullable Object autowiredValue) {
	Assert.notNull(dependencyType, "Dependency type must not be null");
	if (autowiredValue != null) {
		if (!(autowiredValue instanceof ObjectFactory || dependencyType.isInstance(autowiredValue))) {
			throw new IllegalArgumentException("Value [" + autowiredValue +
					"] does not implement specified dependency type [" + dependencyType.getName() + "]");
		}
		this.resolvableDependencies.put(dependencyType, autowiredValue);
	}
}
~~~


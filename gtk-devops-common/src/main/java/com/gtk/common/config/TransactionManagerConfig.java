package com.gtk.common.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * @description 通过AOP切面设置全局事务，拦截service包下面所有方法
 */
@Configuration
public class TransactionManagerConfig {
	private static final int TX_METHOD_TIMEOUT=30000;
	/*定义切点变量：拦截com.gtk..*.service..*包下所有类的所有方法,返回值类型任意的方法*/
	private static final String AOP_POINTCUT_EXPRESSION="execution (* com.gtk..*.service..*.*(..))";
	@Autowired
	private PlatformTransactionManager platformTransactionManager;

	/**
	 * @author cuihaichen
	 * @description springBoot事务配置
	 */
	@Bean
	public TransactionInterceptor txAdvice(){
		/*事务管理规则，声明具备事务管理的方法名*/
		NameMatchTransactionAttributeSource source=new NameMatchTransactionAttributeSource();
		/*只读事物、不做更新删除等*/
		/*当前存在事务就用当前的事务，当前不存在事务就创建一个新的事务*/
		RuleBasedTransactionAttribute readOnlyRule=new RuleBasedTransactionAttribute();
		/*设置当前事务是否为只读事务，true为只读*/
		readOnlyRule.setReadOnly(true);
		/* transactiondefinition 定义事务的隔离级别；
		 * PROPAGATION_NOT_SUPPORTED事务传播级别5，以非事务运行，如果当前存在事务，则把当前事务挂起*/
		//readOnlyRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
		readOnlyRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_NEVER);
		
		RuleBasedTransactionAttribute requireRule=new RuleBasedTransactionAttribute();
		/*抛出异常后执行切点回滚*/
		requireRule.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		/*PROPAGATION_REQUIRED:事务隔离性为1，若当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值。 */
		requireRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		/*设置事务失效时间，如果超过30秒，则回滚事务*/
		requireRule.setTimeout(TX_METHOD_TIMEOUT);
		Map<String,TransactionAttribute> txMap=new HashMap<>();
		
		//开启事务的方法
		txMap.put("add*",requireRule);
		txMap.put("save*", requireRule);
		txMap.put("insert*",requireRule);
		txMap.put("update*",requireRule);
		txMap.put("delete*",requireRule);
		txMap.put("remove*",requireRule);
		txMap.put("del*",requireRule);
		
		//不启用事物
		txMap.put("get*",readOnlyRule);
		txMap.put("query*", readOnlyRule);
		txMap.put("find*", readOnlyRule);
		txMap.put("select*",readOnlyRule);
		source.setNameMap(txMap);
		TransactionInterceptor txAdvice=new TransactionInterceptor(platformTransactionManager, source);
		return txAdvice;
	}

	/**
	 * @author cuihaichen
	 * @description 利用AspectJExpressionPointcut设置切面=切点+通知（写成内部bean的方式）
	 * 
	 */
	@Bean
	public Advisor txAdviceAdvisor(){
		AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
		/*声明和设置需要拦截的方法,用切点语言描写*/
		pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
		/*设置切面=切点pointcut+通知TxAdvice*/
		return new DefaultPointcutAdvisor(pointcut, txAdvice());
	}
}

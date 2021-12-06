package com.gtk.common.annotion;

import com.gtk.common.utils.RedisKeySupport;
import com.gtk.common.utils.SerializeUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author gaoqisen
 * @Description: Redis切面缓存
 * @date 2021/7/12 11:02
 * @UpdateDesc <p>
 * 2021/7/1211:02
 * @UpdateAuthor </p>
 * <p>
 * 2021/7/1211:02
 * @UpdateAuthor </p>
 */
@Slf4j
@Aspect
@Component
@Order(10)
public class RedisCacheAspect {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisCacheAspect(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Pointcut(value = "@annotation(com.gtk.common.annotion.RedisCache)")
    public void redisCacheAspectPointcut() {}

    @Around("redisCacheAspectPointcut()")
    public Object redisCache(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedisCache annotation = method.getAnnotation(RedisCache.class);

        String key = annotation.key();
        int time = annotation.time();
        String bizKey = annotation.bizKey();

        String redisKey;
        if(bizKey != null && !bizKey.isEmpty()) {
            redisKey = RedisKeySupport.generateCommonKey(key);
        } else {
            ExpressionParser parser = new SpelExpressionParser();
            EvaluationContext ctx = new StandardEvaluationContext();

            //获取方法的参数名和参数值
            List<String> paramNameList = Arrays.asList(signature.getParameterNames());
            List<Object> paramList = Arrays.asList(joinPoint.getArgs());

            // 将方法的参数名和参数值一一对应的放入上下文中
            for (int i = 0; i < paramNameList.size(); i++) {
                ctx.setVariable(paramNameList.get(i), paramList.get(i));
            }

            //解析SpEL表达式获取值
            String value = (String) parser.parseExpression(bizKey).getValue(ctx);
            //解析后的业务代码
            redisKey = RedisKeySupport.generateCommonKey(key, value);
        }
        String dataStr = stringRedisTemplate.opsForValue().get(redisKey);
        if(dataStr != null && !dataStr.isEmpty()) {
            Object obj = SerializeUtils.str2Object(dataStr);
            log.info("Redis切面缓存-缓存命中: {}", redisKey);
            return obj;
        }

        log.info("Redis切面未命中缓存-请求接口获取数据 redisKey: {}", redisKey);
        Object obj = joinPoint.proceed();
        stringRedisTemplate.opsForValue().set(redisKey, SerializeUtils.object2Str(obj), time, TimeUnit.SECONDS);
        return obj;
    }

}

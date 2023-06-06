package com.atguigu.ssyx.common.constant;

/**
 * Redis常量配置类
 * set name admin
 */
public class RedisConst {

    public static final String SKUKEY_PREFIX = "sku:";
    public static final String SKUKEY_SUFFIX = ":info";
    //单位：秒
    public static final long SKUKEY_TIMEOUT = 24 * 60 * 60;
    // 定义变量，记录空对象的缓存过期时间 缓存穿透key的过期时间
    public static final long SKUKEY_TEMPORARY_TIMEOUT = 10 * 60;

    //单位：秒 尝试获取锁的最大等待时间
    public static final long SKULOCK_EXPIRE_PX1 = 1;
    //单位：秒 锁的持有时间
    public static final long SKULOCK_EXPIRE_PX2 = 1;
    public static final String SKULOCK_SUFFIX = ":lock";

    public static final String USER_KEY_PREFIX = "user:";
    public static final String USER_CART_KEY_SUFFIX = ":cart";
    public static final long USER_CART_EXPIRE = 60 * 60 * 24 * 7;
    public static final String SROCK_INFO = "stock:info:";
    public static final String ORDER_REPEAT = "order:repeat:";

    //用户登录
    public static final String USER_LOGIN_KEY_PREFIX = "user:login:";
    public static final String ADMIN_LOGIN_KEY_PREFIX = "admin:login:";
    //    public static final String userinfoKey_suffix = ":info";
    public static final int USERKEY_TIMEOUT = 365;
    public static final String ORDER_SKU_MAP = "order:sku:";

    //秒杀商品前缀
    public static final String SECKILL_TIME_MAP = "seckill:time:map";
    public static final String SECKILL_SKU_MAP = "seckill:sku:map";
    public static final String SECKILL_SKU_LIST = "seckill:sku:list:";
    public static final String SECKILL_USER_MAP = "seckill:user:map:";
    public static final String SECKILL_ORDERS_USERS = "seckill:orders:users";
    public static final String SECKILL_STOCK_PREFIX = "seckill:stock:";
    public static final String SECKILL_USER = "seckill:user:";
    //用户锁定时间 单位：秒
    public static final int SECKILL__TIMEOUT = 60 * 60 * 1;
}

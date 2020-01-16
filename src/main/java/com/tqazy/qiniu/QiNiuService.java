package com.tqazy.qiniu;

/**
 * 七牛云自定义服务接口
 * @author 陶其
 * @date 2020-01-16
 */
public interface QiNiuService {

    String KEY1_NUM = "45a8f09af69987895c89588af78aada8e19a668a";
    String KEY2_NUM = "1fba0cab499be4b93eabff9ae4aafc98728972b9";

    /**
     * 初始化七牛云参数
     * @param domainName 域名
     * @param path 资源访问地址 样例:/xxx/xxx/xxx.xxx
     * @param dateTime URL过期时间 样例:yyyy-MM-dd hh24:mm:ss
     * @param key1 密钥1
     * @param key2 密钥2
     * @return
     */
    String initParam(String domainName, String path, String dateTime, String key );




}

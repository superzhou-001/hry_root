package hry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.data.PictureRenderData;
import hry.bean.JsonResult;
import hry.redis.RedisService;
import hry.redis.utils.LockUtil;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import hry.util.email.EmailParam;
import hry.util.email.EmailUtils;
import hry.util.excel.ExcelUtil;
import hry.util.httpRequest.IpUtil;
import hry.util.httpRequest.UserAgentUtils;
import hry.util.poi.PoiUtil;
import hry.util.rmq.RabbitMQProducer;
import hry.util.sms.SmsSendUtils;
import hry.util.sms.SmsSendVo;
import hry.util.ukey.UkeyUtil;
import hry.util.upload.FileUploadContext;
import hry.util.upload.FileUploadUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: liuchenghui
 * @Date: 2020/3/26 18:31
 * @Description: 测试类
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RabbitMQProducer rabbitMQProducer;
    @Autowired
    private FileUploadContext fileUploadContext;

    /**
     * @author: liuchenghui
     * @Date: 2020/4/22 17:22
     * @Description: 测试MQ
     */
    @RequestMapping("/testMQ")
    @UnAuthentication
    public void test() {
        rabbitMQProducer.sendMsgByQueue("test", "HELLO MQ");
    }

    /**
     * @author: liuchenghui
     * @Date: 2020/3/24 17:42
     * @Description: 短信测试
     */
    @PostMapping("/sendSms")
    @UnAuthentication
    public void sendSmsTest(String mobilePhone) {
        SmsSendVo smsSendParam = new SmsSendVo(mobilePhone, "", "1", null);
        String sendSmsCode = SmsSendUtils.sendSms(smsSendParam);
    }

    /**
     * @author: liuchenghui
     * @Date: 2020/3/26 18:31
     * @Description: 测试发送邮件
     */
    @PostMapping("/sendEmail")
    @UnAuthentication
    public void sendEmailTest(String email, String token, HttpServletRequest request) {
        List<String> toList = new ArrayList<>();
        EmailParam param = new EmailParam();
        if (StringUtils.isNotEmpty(email)) {
            String[] tos = email.split(",");
            for (String to : tos) {
                toList.add(to);
            }
        }
        param.setToEmails(toList);
        param.setLangCode("zh_CN");
        param.setType("1");
        param.setFishingCode("910208");
        param.setEmailClassName("hryEmailServiceImpl");
        param.setNewForgetEmail(false);
        // 获取设备类型传递参数
        if ("4".equals(param)) {
            param.setToken(token);
            param.setUrl("");
            // 获取ip
            param.setIp(IpUtil.getIp());
            // 获取设备类型
            String deviceType = UserAgentUtils.getDevicetype(request);
            param.setDeviceType(deviceType);
            // 获取系统类型
            String osType = UserAgentUtils.getOsName(request);
            param.setOsType(osType);
            // 获取浏览器类型
            String browserType = UserAgentUtils.getBrowserName(request);
            param.setBrowserType(browserType);
        }
        // 发送邮件
        EmailUtils.sendEmailByThread(param);
    }

    /**
     * @author: liuchenghui
     * @Date: 2020/3/27 10:40
     * @Description: 上传文件测试
     */
    @PostMapping("/uploadFile")
    @UnAuthentication
    public void uploadFileTest(@RequestParam("file") MultipartFile[] files) {
        List<String> pathImg = FileUploadUtil.uploadFile(fileUploadContext, files, "ossFileUploadStrategy", false);
        System.out.println(StringUtils.join(pathImg, ","));
    }

    /**
     * @author: liuchenghui
     * @Date: 2020/3/27 10:40
     * @Description: 导入excel
     */
    @PostMapping("/importExcel")
    @UnAuthentication
    public void importExcel(@RequestParam("file") MultipartFile file) {
        try {
            // 获取的信息包括列名和值
            List<List<Object>> lists = ExcelUtil.importExcel(file);
            System.out.println(JSON.toJSONString(lists));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: liuchenghui
     * @Date: 2020/3/27 10:40
     * @Description: 导出excel
     */
    @GetMapping("/exportExcel")
    @UnAuthentication
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获得输出流
            OutputStream output = response.getOutputStream();
            // 头部数据
            List<LinkedHashMap> topList = new ArrayList<>();
            // 标题
            LinkedHashMap<String, String> titleMap = new LinkedHashMap<>();
            // title 设置标题，key固定
            titleMap.put("title", "测试导出表");
            // 表头信息
            LinkedHashMap<String, String> headMap = new LinkedHashMap<String, String>();
            headMap.put("name", "姓名");
            headMap.put("sex", "性别");
            headMap.put("subject", "科目");
            headMap.put("score", "分数");
            topList.add(titleMap);
            topList.add(headMap);

            // 数据集合，下面的字段名必须和上面的map对象key或者数据实体类参数保持一致
            List<JSONObject> objects = new ArrayList<>();
            for (int i = 0; i < 1000000; i++) {
                JSONObject result = new JSONObject();
                result.put("name", "张三" + i);
                result.put("sex", "男");
                result.put("subject", "数学");
                result.put("score", 1 + i);
                objects.add(result);
            }
            JSONArray objectsList = JSONArray.parseArray(objects.toString());
            // 设置应用类型，以及编码
            response.setContentType("application/msexcel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "filename=" + new String("测试导出表.xlsx/测试导出表.xls".getBytes("gb2312"), "iso8859-1"));
            ExcelUtil.exportExcel(topList, objectsList, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: liuchenghui
     * @Date: 2020/4/23 17:56
     * @Description: ukey测试方法
     */
    @GetMapping("/testUkey")
    @UnAuthentication
    public void testUkey(String pass) {
        boolean b = UkeyUtil.validDynamicPass("leijinrong-cs", "2345" + pass);
        if (b) {
            System.out.println("验证成功");
        } else {
            System.out.println("验证失败");
        }

    }

    /**
     * 功能描述: 测试POI打印word
     *
     * @param null
     * @return:
     * @auther: yaozh
     * @date: 2020/4/26 16:53
     */
    @GetMapping("getDoc")
    @UnAuthentication
    public void getDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("银行", "中国人民银行");
        dataMap.put("姓名", "姚卓");
        dataMap.put("img", new PictureRenderData(120, 120, "C:\\Users\\lenovo\\Desktop\\11.png "));
        String newWordName = "信息.doc";
        String path = "C:\\Users\\lenovo\\Desktop\\test01.docx";
        //调用打印word的函数
        PoiUtil.docDownload(request, response, newWordName, dataMap, path);

    }


    /**
     * 测试redisson分布式锁
     */
    @RequestMapping("/testRedissonLock")
    @UnAuthentication
    public void redissonTest() {
        String key = "redisson_key";
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.err.println("=============线程开启============" + Thread.currentThread().getName());
                        /*
                         * distributedLocker.lock(key,10L); //直接加锁，获取不到锁则一直等待获取锁
                         * Thread.sleep(100); //获得锁之后可以进行相应的处理
                         * System.err.println("======获得锁后进行相应的操作======"+Thread.
                         * currentThread().getName());
                         * distributedLocker.unlock(key); //解锁
                         * System.err.println("============================="+
                         * Thread.currentThread().getName());
                         */

                        // 尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
                        boolean isGetLock = LockUtil.tryLock(key, TimeUnit.SECONDS, 5, 10);
                        if (isGetLock) {
                            System.out.println("线程:" + Thread.currentThread().getName() + ",获取到了锁");
                            Thread.sleep(100); // 获得锁之后可以进行相应的处理
                            System.err.println("======获得锁后进行相应的操作======" + Thread.currentThread().getName());
                            LockUtil.unlock(key);
                            System.err.println("=============================" + Thread.currentThread().getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
    }

    /**
     * 测试redisson分布式锁注解
     */
    @RequestMapping("/testRedissonLockAop")
    @UnAuthentication
    @ControllerLogger
    public void testRedissonLockAop() {
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    lockAop();
                }
            });
            t.start();
        }

    }

    /**
     * 测试redisson分布式锁注解
     */
    @ApiOperation(value = "测试redisson分布式锁注解", notes = "测试redisson分布式锁注解")
    @GetMapping("/testRedissonLockAop1")
    @UnAuthentication
    public void testRedissonLockAop1() throws InterruptedException {
        /*for (int i = 0; i < 2; i++) {
            new Thread(() -> lockAop(),"thread1-"+i).start();
        }*/
        lockAop();
    }

    /**
     * 测试redisson分布式锁注解
     */
    @ApiOperation(value = "测试redisson分布式锁注解", notes = "测试redisson分布式锁注解")
    @GetMapping("/testRedissonLockAop2")
    @UnAuthentication
    public void testRedissonLockAop2() {
        /*for (int i = 0; i < 2; i++) {
            new Thread(() -> lockAop(),"thread2-"+i).start();
        }*/
        lockAop();

    }

    public void lockAop() {
        try {
            System.out.println("线程:" + Thread.currentThread().getName() + ",获取到了锁");
            Thread.sleep(4000); // 获得锁之后可以进行相应的处理
            System.err.println("=============================" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "redis集群", notes = "redis集群")
    @RequestMapping("/redisClusterTest")
    @UnAuthentication
    //@ResponseBody
    public void redisClusterTest() {
        /*redisClusterService.set("12", "12");
        System.out.println(redisClusterService.get("12"));*/

        String s = redisService.get("test");
        System.out.println(s);
        System.out.println("11");
    }

    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort("192.168.0.52", 3000));
        JedisCluster jedisCluster = null;
        try {  //connectionTimeout：指的是连接一个url的连接等待时间 //soTimeout：指的是连接上一个url，获取response的返回等待时间
            jedisCluster = new JedisCluster(jedisClusterNode, 6000, 5000, 10, "Credit2016Admin", config);
            System.out.println(jedisCluster.set("cluster", "zhuge"));
            System.out.println(jedisCluster.get("cluster"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisCluster != null) jedisCluster.close();
        }
    }


    /**
     * <p> 查询全部分类 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:38:17
     */
    @ApiOperation(value = "测试登录", notes = "测试登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/testLogin")
    public JsonResult findAll () {
        return new JsonResult(true);
    }

}

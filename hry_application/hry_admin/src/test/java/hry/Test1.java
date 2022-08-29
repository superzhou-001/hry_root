package hry;

import hry.core.thread.ThreadPoolUtils;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.newuser.service.NewAppUserService;
import hry.util.PasswordHelper;
import hry.util.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {
    @Autowired
    NewAppUserService newAppUserService;

    @Test
    public void test(){
        long s = System.currentTimeMillis();
        for(int i = 1; i <= 100000; i++){
            NewAppUser appUser = new NewAppUser();
            appUser.setUserName("test"+i);
            String uuid = UUIDUtil.getUUID();
            appUser.setSalt(uuid);
            appUser.setPassWord(PasswordHelper.md5("123456",uuid));
            newAppUserService.save(appUser);
        }
        long e = System.currentTimeMillis();

        System.out.println("总共耗时"+(e-s));
        //总共耗时253829毫秒      253秒
    }



    @Test
    public void test2(){



        long s = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(10);

        for(int i = 1 ; i <=10 ; i++){
            int start = (i-1)*10000+1;
            int end = i*10000;
            System.out.println("start="+start+"\tend="+end);
            Task task = new Task(start,end,newAppUserService,countDownLatch);
            ThreadPoolUtils.execute(task);
        }



        try {
            countDownLatch.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        long e = System.currentTimeMillis();
        System.out.println("总共耗时"+(e-s));
        //总共耗时154310   154秒 20线程
        //总共耗时165148   165秒 10线程
    }

}


class  Task extends  Thread{



    private int start;
    private int end;
    private NewAppUserService newAppUserService ;
    private CountDownLatch countDownLatch;


    public Task(int start,int end,NewAppUserService newAppUserService,CountDownLatch countDownLatch){
        this.start = start;
        this.end = end;
        this.newAppUserService = newAppUserService;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void run() {

        for(int i = start; i <= end; i++){
            NewAppUser appUser = new NewAppUser();
            appUser.setUserName("test"+i);
            String uuid = UUIDUtil.getUUID();
            appUser.setSalt(uuid);
            appUser.setPassWord(PasswordHelper.md5("123456",uuid));
            newAppUserService.save(appUser);
        }

        countDownLatch.countDown();
        System.out.println(Thread.currentThread().getName()+"\t执行完成");

    }
}

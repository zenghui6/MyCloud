package com.zenghui.wangpan;

import com.zenghui.wangpan.entity.MyFile;
import com.zenghui.wangpan.mapper.MyFileMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName FileTest
 * @Description: TODO
 * @Author zeng
 * @Date 2020/3/10
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileTest {

    @Autowired
    protected MyFileMapper myFileMapper;

    @Test
    public void test(){
        List<MyFile> listRoot = myFileMapper.listRootFileByStoreId(5);

        System.out.println(listRoot);

        List<MyFile> files = myFileMapper.listFileByFolderId(5);
        System.out.println(files);
    }
}

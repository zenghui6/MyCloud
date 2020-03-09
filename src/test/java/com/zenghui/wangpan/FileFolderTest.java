package com.zenghui.wangpan;

import com.zenghui.wangpan.entity.FileFolder;
import com.zenghui.wangpan.service.FileFolderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName FileFolderTest
 * @Description: TODO
 * @Author zeng
 * @Date 2020/3/9
 **/
@RunWith(SpringRunner.class)

@SpringBootTest
public class FileFolderTest {
    @Autowired
    FileFolderService fileFolderService;

    @Test
    public void test(){

        List<FileFolder> folderNavigation = new LinkedList<>();

        FileFolder fileFolder = fileFolderService.getById(5);
//        System.out.println(fileFolder);

        FileFolder fileFolder1 = ReturnRead(fileFolder);
        System.out.println(fileFolder1);

        folderNavigation.add(fileFolder1);
    }

    private FileFolder ReturnRead(FileFolder fileFolder){
        if (fileFolder.getParentFolderId() == 0){
            return fileFolder;
        }
        FileFolder pf = fileFolderService.getById(fileFolder.getParentFolderId());
        return ReturnRead(pf);
    }
}

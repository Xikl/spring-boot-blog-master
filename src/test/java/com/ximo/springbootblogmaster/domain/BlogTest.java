package com.ximo.springbootblogmaster.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.stream.Collectors.toList;

/**
 * @author 朱文赵
 * @date 2018/5/7
 * @description
 */
public class BlogTest {

    private List<Integer> list = new ArrayList<>();

    private CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    @Before
    public void setup() {
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        copyOnWriteArrayList.add(1);
        copyOnWriteArrayList.add(2);
        copyOnWriteArrayList.add(2);
        copyOnWriteArrayList.add(3);
    }

    /**
     * 删除一个 第二个就报错
     * 会抛出 ConcurrentModificationException
     */
    @Test
    public void testListDeleteError() {
        for (Integer integer : list) {
            if (integer.equals(2)) {
                list.remove(integer);
            }
        }
        System.out.println(list);
    }

    /**
     * 删除一个立马跳出
     */
    @Test
    public void testListDeleteErrorButNoThrowException() {
        for (Integer integer : list) {
            if (integer.equals(2)) {
                list.remove(integer);
                break;
            }
        }
        System.out.println(list);
    }

    @Test
    public void testListDeleteCorrect() {
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).equals(2)) {
                list.remove(i);
            }
        }
        System.out.println(list);
    }

    @Test
    public void testListDeleteCorrectByIterator() {
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer nextItem = iterator.next();
            if (nextItem.equals(2)) {
                iterator.remove();
            }
        }
        System.out.println(list);
    }

    @Test
    public void testListDeleteByLambda() {
        list.removeIf(nextItem -> nextItem.equals(2));
        System.out.println(list);
    }

    @Test
    public void testListDeleteByLambdaFilter() {
        List<Integer> result = list.stream().filter(e -> !e.equals(2)).collect(toList());
        System.out.println(result);
    }

    @Test
    public void testCopyOnWriteArrayListDelete() {
        for (Integer integer : copyOnWriteArrayList) {
            if (integer.equals(2)) {
                copyOnWriteArrayList.remove(integer);
            }
        }
        System.out.println(copyOnWriteArrayList);
    }

}
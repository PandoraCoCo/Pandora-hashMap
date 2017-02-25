package com.pandora.test;

import java.util.HashMap;
import java.util.Map;
import com.pandora.map.XsHashMap;
import com.pandora.map.XsMap;


public class Test {

	public static void main(String[] args) {
		XsMap<String, String> map = new XsHashMap<String, String>();
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			map.put("key"+i, "v"+i);
		}
		for (int i = 0; i < 1000; i++) {
			map.put("key"+i, "vi"+i);
		}

		/*for (int i = 0; i < 1000; i++) {
			System.out.println("key" + i + " : " + map.get("key" + i));
		}*/
		long t2 = System.currentTimeMillis();
		System.out.println("自定义Map长度为：" + map.size());
		System.out.println("自定义Map耗时为：" + (t2-t1) + "ms");
		
		Map<String, String> jdkMap = new HashMap<String, String>();
		long t3 = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			jdkMap.put("key"+i, "v"+i);
		}
		for (int i = 0; i < 1000; i++) {
			jdkMap.put("key"+i, "vi"+i);
		}

		/*for (int i = 0; i < 1000; i++) {
			System.out.println("key" + i + " : " + jdkMap.get("key" + i));
		}*/
		long t4 = System.currentTimeMillis();
		System.out.println("JDK Map长度为：" + jdkMap.size());
		System.out.println("JDK Map耗时为：" + (t4-t3) + "ms");

		System.out.println("这一条是使用Idea提交的代码。");
		System.out.println("这是通过git命令添加的一条代码");
	}
}

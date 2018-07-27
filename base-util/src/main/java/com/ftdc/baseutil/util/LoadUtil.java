package com.ftdc.baseutil.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class LoadUtil {

	private static Logger logger = LoggerFactory.getLogger(LoadUtil.class);

	private static Set<ProcessLoad> proceNameSet = new HashSet<>();

	/**
	 * 启动失败，3次自动重启，每次启动间隔8秒
	 *
	 * @param procName
	 *            进程名称
	 * @param runnable
	 *            启动任务
	 */
	public static void load(String procName, Runnable runnable) {
		loadWithTime(procName, runnable, 8);
	}

	public static void loadWithTime(String procName, Runnable runnable, int secords) {
		ProcessLoad processLoad = new ProcessLoad(procName);
		proceNameSet.add(processLoad);
		for (int i = 1; i <= 3; i++) {
			// 记录启动次数
			processLoad.count = i;
			try {
				runnable.run();
				processLoad.startStatus = 3;// 启动成功
				break;
			} catch (Exception e) {
				try {
					logger.info("第{}次启动失败,进入等待", i);
					Thread.sleep(secords * 1000);
				} catch (InterruptedException e1) {
					logger.info("等待被中断", e1);
				}
				if (i == 3) {
					logger.info("启动异常", e);
					processLoad.startStatus = 2;// 启动失败
				}
			}
		}
	}

	public static class ProcessLoad {
		public String name;
		// 1 启动中 2 失败 3 成功
		public int startStatus = 1;
		public int count;

		public ProcessLoad(String name) {
			this.name = name;
		}

		public String getStatus() {
			if (startStatus == 1)
				return "启动中";
			else if (startStatus == 2)
				return "失败";
			else if (startStatus == 3)
				return "成功";
			return startStatus + "";
		}
	}
}

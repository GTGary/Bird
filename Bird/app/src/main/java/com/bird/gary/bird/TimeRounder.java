package com.bird.gary.bird;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;

/**
 * 循环请求控制器
 * <p>
 * 注意：需要在请求开始和结果处调用调用setRequestStatus(int status) 主动设置请求状态来控制循环
 * </p>
 * <p>
 * 不设置的话默认无限循环
 * </p>
 * 
 * @author Wall
 * 
 */
public class TimeRounder {

	/**
	 * 循环器触发方法
	 * 
	 * @author Wall
	 * 
	 */
	public interface RounderEvent {
		/**
		 * 循环出错
		 * 
		 * @param message
		 */
		public void rounderError(String message);

		/**
		 * 循环要进行的工作
		 */
		public void rounderWorking();
	}

	public static final int TYPE_SECOND = 100001;
	public static final int TYPE_MICROSECOND = 100002;
	public static final int ROUNDERING = 1;
	/**
	 * 是否正在执行的标志位，防止重复启动造成并发执行
	 */
	private boolean isRunning = false;
	/**
	 * 统计连续出错总数
	 */
	private int ErrorCount = 0;
	/**
	 * 记录上次请求的三项标志位 0：上次请求未返回 1：上次请求正确返回 2：上次请求错误返回
	 */
	private int lastRequestStatus = 1;
	/**
	 * 整体异常标志位
	 */
	private boolean isErrorStatus = false;
	private RounderEvent event = null;
	/**
	 * 脉冲发送计时器
	 */
	private Timer pulseTimer = null;
	/**
	 * 脉冲间隔时间
	 */
	private int pulse = 0;
	private int maxErrorsCount = 0;

	private int type = 0;

	private MyHandler handler;

	/**
	 * 循环请求控制器,注意：需要在请求开始和结果处调用setRequestStatus(int status) 主动设置请求状态来控制循环
	 * 
	 * @param type
	 *            脉冲级别 TYPE_SECOND秒级 ，TYPE_MICROSECOND 毫秒级
	 * @param pulse
	 *            脉冲间隔时间
	 * @param maxErrorsCount
	 *            请求容错上限，连续出错到达上限停止Rounder
	 * @param event
	 *            Rounder的事件接口
	 */
	public TimeRounder(int type, int pulse, int maxErrorsCount, RounderEvent event) {
		this.pulse = pulse;
		this.maxErrorsCount = maxErrorsCount;
		this.event = event;
		this.type = type;
		this.handler = new MyHandler(this);
	}

	/**
	 * 是否正在运行
	 * 
	 * @return 是否运行标志位
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * 动态修改间隔时间 Timer貌似是一次性的，所以需要重新创建一个timer来修改脉冲时间
	 * 
	 * @param pulse
	 */
	public void setPulse(int pulse) {
		this.pulse = pulse;
		if (pulseTimer != null) {
			pulseTimer.cancel();
			pulseTimer = null;
		}
		isRunning = false;
		start();
	}

	/**
	 * 设置请求状态：0正在请求，1：请求成功，2：请求失败
	 * 
	 * @param status
	 */
	public void setRequestStatus(int status) {
		lastRequestStatus = status;
	}

	private static class MyHandler extends Handler {
		private WeakReference<TimeRounder> activityWeakReference;

		public MyHandler(TimeRounder activity) {
			activityWeakReference = new WeakReference<TimeRounder>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			TimeRounder timeRouder = activityWeakReference.get();
			if (timeRouder != null) {
				if (msg.what == ROUNDERING) {
					if (!timeRouder.isErrorStatus) {
						switch (timeRouder.lastRequestStatus) {
						case 0:
							// 请求未返回，本次轮空
							timeRouder.ErrorCount++;
							if (timeRouder.ErrorCount >= timeRouder.maxErrorsCount) {
								timeRouder.isErrorStatus = true;
							}
							break;
						case 1:
							// 正常情况，继续
							timeRouder.ErrorCount = 0;
							// TODO 正常请求
							if (timeRouder.event != null) {
								timeRouder.event.rounderWorking();
							}
							break;
						case 2:
							// 请求出错，再试
							timeRouder.ErrorCount++;
							if (timeRouder.ErrorCount >= timeRouder.maxErrorsCount) {
								timeRouder.isErrorStatus = true;
							} else {
								// TODO 正常请求
								if (timeRouder.event != null) {
									timeRouder.event.rounderWorking();
								}
							}
							break;
						}
					} else {
						// 出错，中断循环器
						if (timeRouder.event != null) {
							timeRouder.event.rounderError("超出容错上限---停止循环");
						}
						if (timeRouder.pulseTimer != null) {
							timeRouder.pulseTimer.cancel();
							timeRouder.pulseTimer = null;
						}
						// 通知主线程
					}
				}
			}
		}
	}

	/**
	 * 启动
	 */
	public void start() {
		if (isRunning) {
			stop();
		}
		isRunning = true;
		// 数据初始化
		isErrorStatus = false;
		ErrorCount = 0;
		lastRequestStatus = 1;
		if (pulseTimer == null) {
			pulseTimer = new Timer();
		}
		if (type == TYPE_SECOND) {
			pulseTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					Message msg = new Message();
					msg.what = ROUNDERING;
					handler.sendMessage(msg);
				}
			}, 10, pulse * 1000);
		} else {
			pulseTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					Message msg = new Message();
					msg.what = ROUNDERING;
					handler.sendMessage(msg);
				}
			}, 10, pulse * 1);
		}
	}

	/**
	 * 主动停止循环
	 */
	public void stop() {
		if (pulseTimer != null) {
			pulseTimer.cancel();
			pulseTimer = null;
		}
		isRunning = false;
	}
}
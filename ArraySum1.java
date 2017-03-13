/*
 * Copyright 2016 LBK
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.*;

public class ArraySum1 {

	private Integer[] array;
	private int sum;
	private int maxCount;
	private int lowDigit;

	private Deque<Integer> calResult = new ArrayDeque<Integer>();
	private Deque<Integer> prevStack = new ArrayDeque<>();

	public ArraySum1() {

	}

	public ArraySum1(Integer[] array, int sum, int maxCount, int lowDigit) {
		this.array = array;
		this.sum = sum;
		this.maxCount = maxCount;
		this.lowDigit = lowDigit;
	}

	public void setArray(Integer[] array) {
		this.array = array;
		clear();
	}

	public void setSum(int sum) {
		this.sum = sum;
		clear();
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
		clear();
	}

	public void setLowDigit(int lowDigit) {
		this.lowDigit = lowDigit;
		clear();
	}

	/**
	 * 清除之前的计算记录
	 */
	public void clear() {
		calResult.clear();
		prevStack.clear();
	}

	/**
	 * 获得计算结果数组的大小
	 */
	public int getResultCount() {
		return calResult.size();
	}

	/**
	 * 获得计算的结果的总
	 */
	public int getResultSum() {
		int sum = 0;
		for (int result : calResult) {
			sum += result;
		}
		return sum;
	}

	public Collection<Integer> getResultCollection() {
		return Collections.unmodifiableCollection(calResult);
	}

	public boolean calNextCollection() {
		calResult.clear();
		return calNextNumber(0, sum, maxCount);
	}

	private boolean calNextNumber(int start, int sum, int maxCount) {
		if (sum <= 0 || maxCount <= 0) {
			return false;
		}
		Integer prevEnd = prevStack.pollLast(); // 取出上一次成功时的状态
		start = prevEnd == null ? start : Math.max(start, prevEnd);
		for (int i = start; i < array.length; i++) {
			int nextSum = sum - array[i];
			calResult.offerLast(array[i]);
			// 完全满足 或者 低位一致
			if ((nextSum == 0 && maxCount - 1 == 0) || nextSum % lowDigit == 0) {
				prevStack.clear(); // 清除可能残余的上次的状态
				prevStack.offerLast(i + 1); // 保存下次迭代时开始的起点
				return true;
			}
			if (calNextNumber(i + 1, nextSum, maxCount - 1)) {
				prevStack.offerLast(i);
				return true;
			}
			calResult.pollLast();
		}
		return false;
	}

	@Override
	public String toString() {
		return "ArraySum1 [array=" + Arrays.toString(array) + ", sum=" + sum + ", count=" + maxCount + ", lowDigit="
				+ lowDigit + ", calResult=" + calResult + ", prevStack=" + prevStack + "]";
	}

}

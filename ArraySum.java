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

public class ArraySum {

	private final int[] array;
	private final int sum;
	private final int count;

	private Deque<Integer> calResult = new ArrayDeque<Integer>();
	
	public ArraySum(int[] array, int sum, int count) {
		this.array = array;
		this.sum = sum;
		this.count = count;
	}

	public Collection<Integer> getResultCollection() {
		return Collections.unmodifiableCollection(calResult);
	}

	/**
	 * 计算出其中一个符合条件的数字集合
	 */
	public boolean calCollection() {
		return calNextNumber(0, sum, count);
	}

	/**
	 * 通过尝试, 计算出下一个可能的数字.
	 * @param start 要尝试的第一个元素的索引
	 * @param sum 需要达到的总和
	 * @param count 需要数字的数量
	 * @return 找到下一个数字, 返回true, 否则返回false.
	 */
	private boolean calNextNumber(int start, int sum, int count) {
		if(sum == 0 && count ==0){	// 符合条件
			return true;
		}
		// 剩余的数字不足 或者 选择的数的和已经超过了总和
		if (start + count > array.length || sum <= 0) {
			return false;
		}
	    for (int i=start; i<array.length; i++) {
	        int nextSum = sum - array[i];
	        calResult.offerLast(array[i]);  // 将可能的结果压入结果栈中
	        if(calNextNumber(i + 1, nextSum, count - 1)){
	            return true;
	        }
	        calResult.pollLast();   // 不符合要求, 从结果栈中弹出
	    }
		return false;
	}

	public static void main(String[] args) {
		ArraySum arraySum = new ArraySum(Data.ARRAY1, Data.SUM1, Data.COUNT1);
		if (arraySum.calCollection()) {
			System.out.println(arraySum.getResultCollection());
		}
		else{
			System.out.println("没有符合条件的数组");
		}
	}

}

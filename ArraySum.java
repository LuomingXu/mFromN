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
	 * ���������һ���������������ּ���
	 */
	public boolean calCollection() {
		return calNextNumber(0, sum, count);
	}

	/**
	 * ͨ������, �������һ�����ܵ�����.
	 * @param start Ҫ���Եĵ�һ��Ԫ�ص�����
	 * @param sum ��Ҫ�ﵽ���ܺ�
	 * @param count ��Ҫ���ֵ�����
	 * @return �ҵ���һ������, ����true, ���򷵻�false.
	 */
	private boolean calNextNumber(int start, int sum, int count) {
		if(sum == 0 && count ==0){	// ��������
			return true;
		}
		// ʣ������ֲ��� ���� ѡ������ĺ��Ѿ��������ܺ�
		if (start + count > array.length || sum <= 0) {
			return false;
		}
	    for (int i=start; i<array.length; i++) {
	        int nextSum = sum - array[i];
	        calResult.offerLast(array[i]);  // �����ܵĽ��ѹ����ջ��
	        if(calNextNumber(i + 1, nextSum, count - 1)){
	            return true;
	        }
	        calResult.pollLast();   // ������Ҫ��, �ӽ��ջ�е���
	    }
		return false;
	}

	public static void main(String[] args) {
		ArraySum arraySum = new ArraySum(Data.ARRAY1, Data.SUM1, Data.COUNT1);
		if (arraySum.calCollection()) {
			System.out.println(arraySum.getResultCollection());
		}
		else{
			System.out.println("û�з�������������");
		}
	}

}

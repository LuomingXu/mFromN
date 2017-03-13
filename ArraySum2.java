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

public class ArraySum2 {

	private final int[] array;
	private final int sum;
	private final int count;
	private List<ArraySum1> arraySums = new ArrayList<ArraySum1>();

	public ArraySum2(final int[] array, int sum, int count) {
		this.array = array;
		this.sum = sum;
		this.count = count;
		Map<Integer, List<Integer>> map = new HashMap<>();
		splitArray(map);
		createArraySum(map);
	}

	private void splitArray(Map<Integer, List<Integer>> map) {
		for (int i = 0; i < array.length; i++) {
			int digit = 0;
			int temp = array[i];
			for (; temp % 10 == 0; temp /= 10) {
				digit++;
			}
			List<Integer> tempList = map.get(digit);
			if (tempList == null) {
				tempList = new ArrayList<>();
				map.put(digit, tempList);
			}
			tempList.add(array[i]);
		}
	}

	private void createArraySum(Map<Integer, List<Integer>> map) {
		Integer[] keys = map.keySet().toArray(new Integer[0]);
		Arrays.sort(keys);
		for (Integer key : keys) {
			arraySums.add(new ArraySum1(map.get(key).toArray(new Integer[0]), 0, 0, (int) Math.pow(10, key + 1)));
		}
	}

	public boolean calCollection() {
		return calNextArray(0, sum, count);
	}

	private boolean calNextArray(int index, int sum, int count) {
		if (sum == 0 && count == 0) { // 计算完成
			return true;
		}
		if (index >= arraySums.size()) { // 没有更多分组了
			return false;
		}
		ArraySum1 sum1 = arraySums.get(index);
		sum1.setSum(sum);
		sum1.setMaxCount(count);
		while (sum1.calNextCollection()) { // 计算下一个低位符合条件的集合
			// 递归下一组, 寻找符合条件的集合
			if (calNextArray(index + 1, sum - sum1.getResultSum(),
					count - sum1.getResultCount())) {
				return true;
			}
		}
		return false;
	}

	public List<Integer> getResultList() {
		List<Integer> list = new ArrayList<Integer>();
		for (ArraySum1 sum : arraySums) {
			list.addAll(sum.getResultCollection());
		}
		return list;
	}

	public static void main(String[] args) {
		ArraySum2 arraySum = new ArraySum2(Data.ARRAY, Data.SUM, Data.COUNT);
		arraySum.calCollection();
		System.out.println(arraySum.getResultList());
	}

}

package com.pandora.map;

public class XsHashMap<K, V> implements XsMap<K, V> {

	private static final int DEFAULT_LENGTH = 16;

	private static final int MAX_LENGTH = 1 << 30;

	private static final float DEFAULT_LOADFACTOR = 0.75F;

	private final float loadfactor;

	private int size;

	private int threshold;

	private Entry[] table = null;

	public XsHashMap(int length, float loadfactor) {
		// 当长度小于0时抛出异常
		if (length <= 0) {
			throw new IllegalArgumentException("IllegalArgumentException：" + length);
		}

		// 当长度大于最大长度则等于最大长度
		if (length >= MAX_LENGTH) {
			length = MAX_LENGTH;
		}

		// 当负载因子不符合规范时抛出异常
		if (loadfactor <= 0 || Float.isNaN(loadfactor)) {
			throw new IllegalArgumentException("IllegalArgumentException：" + loadfactor);
		}

		int capacity = 1;
		while (capacity < length) {
			capacity <<= 1;
		}

		this.loadfactor = loadfactor;
		this.threshold = (int) (capacity * loadfactor);
		this.table = new Entry[capacity];
	}

	public XsHashMap(int length) {
		this(length, DEFAULT_LOADFACTOR);
	}

	public XsHashMap() {
		this.loadfactor = DEFAULT_LOADFACTOR;
		this.threshold = (int) (DEFAULT_LENGTH * DEFAULT_LOADFACTOR);
		this.table = new Entry[DEFAULT_LENGTH];
	}

	/**
	 * 取模法找出下标。模拟hash函数取下标
	 */
	private int getIndex(K k, int length) {
		if (k == null) {
			return 0;
		}

		int index = k.hashCode() % length;
		return index >= 0 ? index : -index;
	}

	/**
	 * 新增Entry
	 * 
	 * @param index
	 * @param k
	 * @param v
	 */
	private void addEntry(int index, K k, V v) {
		Entry<K, V> e = table[index];
		table[index] = new Entry<K, V>(k, v, e);

		size++;
		if (size > threshold) {
			resize(table.length << 1);
		}
	}

	/**
	 * 扩容
	 * 
	 * @param newCapacity
	 */
	private void resize(int newCapacity) {
		Entry[] oldTable = table;
		if (oldTable.length == MAX_LENGTH) {
			threshold = Integer.MAX_VALUE;
			return;
		}

		Entry[] newTable = new Entry[newCapacity];
		transfer(newTable);
		table = newTable;
		threshold = (int) (newCapacity * loadfactor);
	}

	/**
	 * 数据转移
	 * 
	 * @param newTable
	 */
	private void transfer(Entry[] newTable) {
		Entry[] oldTable = table;
		for (int i = 0; i < oldTable.length; i++) {
			for (Entry<K, V> e = oldTable[i]; e != null; e = oldTable[i]) {
				oldTable[i] = e.next;
				int index = getIndex(e.getKey(), newTable.length);
				e.next = newTable[index];
				newTable[index] = e;
			}
		}
	}

	/**
	 * put
	 */
	@Override
	public V put(K k, V v) {

		// 获取下标
		int index = getIndex(k, table.length);
		for (Entry<K, V> e = table[index]; e != null; e = e.next) {
			if (k == e.getKey() || k.equals(e.getKey())) {
				V oldValue = e.getValue();
				e.value = v;
				return oldValue;
			}
		}

		addEntry(index, k, v);

		return v;
	}

	/**
	 * get
	 */
	@Override
	public V get(K k) {
		int index = getIndex(k, table.length);
		Entry<K, V> e = table[index];
		
		if (e != null) {
			do{
				if (k == e.getKey() || k.equals(e.getKey())) {
					return e.getValue();
				}
				e = e.next;
			} while (e != null);
		}

		return null;
	}

	@Override
	public int size() {
		return size;
	}

	class Entry<K, V> implements XsMap.Entry<K, V> {

		final K key;

		V value;

		Entry<K, V> next;

		public Entry(K key, V value, Entry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}
	}

}

package com.mocha.util.verify;

import com.mocha.common.utils.SerializationUtils;
import com.mocha.util.helper.ByteHelper;

public class CRC16Verifier extends Verifier {

	private short[] crcTable = new short[256];
	private static CRC16Verifier crc16Verifier = new CRC16Verifier();
	private int crcPloy = 0x1021;

	public CRC16Verifier() {
		computeCrcTable();
	}

	/**
	 * 获取类实例
	 * 
	 * @return CacheFactory instance
	 */
	public static CRC16Verifier getInstance() {
		return crc16Verifier;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getVerifyCode(Object code, boolean littleEndian) {
		int crc = (Integer) code;
		crc = crc & 0xFFFF;
		return ByteHelper.convertInt16(crc, littleEndian);
	}

	public byte[] getVerifyCode(Object initData) {
		byte[] data = SerializationUtils.serialize(initData);
		Object object = update(0, data, 0, data.length);
		return getVerifyCode(object, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object update(Object initData, byte[] data, int offset, int length) {
		int crc = (Integer) (initData == null ? 0 : initData);
		for (int i = 0; i < length; i++) {
			crc = ((crc & 0xFF) << 8)
					^ crcTable[(((crc & 0xFF00) >> 8) ^ data[i + offset]) & 0xFF];
		}
		return crc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int verifyLength() {
		return 2;
	}

	private short getCrcOfByte(int aByte) {
		int value = aByte << 8;
		for (int count = 7; count >= 0; count--) {
			if ((value & 0x8000) != 0) {
				value = (value << 1) ^ crcPloy;
			} else {
				value = value << 1;
			}
		}
		value = value & 0xFFFF;
		return (short) value;
	}

	private void computeCrcTable() {
		for (int i = 0; i < 256; i++) {
			crcTable[i] = getCrcOfByte(i);
		}
	}
}

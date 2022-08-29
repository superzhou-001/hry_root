/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0 
 * @Date:        2015年12月7日 下午3:26:31
 */
package hry.util;

import java.util.UUID;

/**
 * UUID 生成器
 * <p>
 * TODO
 * </p>
 * 
 * @author: Liu Shilei
 * @Date : 2015年12月7日 下午3:26:31
 */
public class UUIDUtil {

	/**
	 * 生成UUID
	 * <p>
	 * TODO
	 * </p>
	 * 
	 * @author: Liu Shilei
	 * @param: @return
	 * @return: String
	 * @Date : 2015年12月7日 下午3:27:56
	 * @throws:
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	public static String getPrototypeUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public void test1() {

		String p1 = "";
		String p2 = "";
		String p3 = "";
		String sql = "select * from table as a where 1=1 AND c.dealtime >= trunc (sysdate)";
		if (p1 != null || p2 != null || p3 != null) {
			if (p1 != null && p2 != null) {
				sql += "and ( 1=1 ";
			}
			if (p1 != null || p2 != null) {
				sql += " and (  1=1 ";
				if (p1 != null) {
					sql += " and a.hesiRemainDay >= " + p1;
				}
				if (p2 != null) {
					sql += " and a.hesiRemainDay >= " + p2;
				}
				sql += " ) ";
			}
			if (p3 != null) {
				if (p1 != null && p2 != null) {
					sql += "or";
				} else {
					sql += "and";
				}
				sql += "  a.riskcode IN ('" + p3 + "') ";
			}
			sql += " ) ";
		}

	}

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

}

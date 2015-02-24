
public class Main {
	public static void main(String[] fred) {
		String result = valueOf(5, 10);
		System.out.println(result);
	}
	static String valueOf(long i, int radix) {
		if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX)
			radix=10;
		if (radix == 10)
			return toString(i);
		char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);

        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = Integer.digits[(int)(-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = Integer.digits[(int)(-i)];

        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, (65 - charPos));
    }
	static String toString(long i) {
		if (i == Long.MIN_VALUE)
            return "-9223372036854775808";
        int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
        char[] buf = new char[size];
        getChars(i, size, buf);
        return new String(0, size, buf);
	}
}

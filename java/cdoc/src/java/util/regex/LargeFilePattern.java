package java.util.regex;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LargeFilePattern implements Serializable {
	public static final Function<Integer, Boolean> WHITE_SPACE_FN = (ch) -> ((((1 << Character.SPACE_SEPARATOR)
			| (1 << Character.LINE_SEPARATOR) | (1 << Character.PARAGRAPH_SEPARATOR)) >> Character
			.getType(ch)) & 1) != 0 || (ch >= 0x9 && ch <= 0xd) || (ch == 0x85),
			ALPHABETIC_FN = Character::isAlphabetic,
			JOIN_CONTROL_FN = (ch) -> (ch == 0x200C || ch == 0x200D);
	/**
	 * Regular expression modifier values. Instead of being passed as arguments,
	 * they can also be passed as inline modifiers. For example, the following
	 * statements have the same effect.
	 * 
	 * <pre>
	 * RegExp r1 = RegExp.compile(&quot;abc&quot;, LargeFilePattern.I | LargeFilePattern.M);
	 * RegExp r2 = RegExp.compile(&quot;(?im)abc&quot;, 0);
	 * </pre>
	 *
	 * The flags are duplicated so that the familiar Perl match flag names are
	 * available.
	 */

	/**
	 * Enables Unix lines mode.
	 *
	 * <p>
	 * In this mode, only the <tt>'\n'</tt> line terminator is recognized in the
	 * behavior of <tt>.</tt>, <tt>^</tt>, and <tt>$</tt>.
	 *
	 * <p>
	 * Unix lines mode can also be enabled via the embedded flag
	 * expression&nbsp;<tt>(?d)</tt>.
	 */
	public static final int UNIX_LINES = 0x01;

	/**
	 * Enables case-insensitive matching.
	 *
	 * <p>
	 * By default, case-insensitive matching assumes that only characters in the
	 * US-ASCII charset are being matched. Unicode-aware case-insensitive
	 * matching can be enabled by specifying the {@link #UNICODE_CASE} flag in
	 * conjunction with this flag.
	 *
	 * <p>
	 * Case-insensitive matching can also be enabled via the embedded flag
	 * expression&nbsp;<tt>(?i)</tt>.
	 *
	 * <p>
	 * Specifying this flag may impose a slight performance penalty.
	 * </p>
	 */
	public static final int CASE_INSENSITIVE = 0x02;

	/**
	 * Permits whitespace and comments in LargeFilePattern.
	 *
	 * <p>
	 * In this mode, whitespace is ignored, and embedded comments starting with
	 * <tt>#</tt> are ignored until the end of a line.
	 *
	 * <p>
	 * Comments mode can also be enabled via the embedded flag expression&nbsp;
	 * <tt>(?x)</tt>.
	 */
	public static final int COMMENTS = 0x04;

	/**
	 * Enables multiline mode.
	 *
	 * <p>
	 * In multiline mode the expressions <tt>^</tt> and <tt>$</tt> match just
	 * after or just before, respectively, a line terminator or the end of the
	 * input sequence. By default these expressions only match at the beginning
	 * and the end of the entire input sequence.
	 *
	 * <p>
	 * Multiline mode can also be enabled via the embedded flag expression&nbsp;
	 * <tt>(?m)</tt>.
	 * </p>
	 */
	public static final int MULTILINE = 0x08;

	/**
	 * Enables literal parsing of the LargeFilePattern.
	 *
	 * <p>
	 * When this flag is specified then the input string that specifies the
	 * LargeFilePattern is treated as a sequence of literal characters.
	 * Metacharacters or escape sequences in the input sequence will be given no
	 * special meaning.
	 *
	 * <p>
	 * The flags CASE_INSENSITIVE and UNICODE_CASE retain their impact on
	 * matching when used in conjunction with this flag. The other flags become
	 * superfluous.
	 *
	 * <p>
	 * There is no embedded flag character for enabling literal parsing.
	 * 
	 * @since 1.5
	 */
	public static final int LITERAL = 0x10;

	/**
	 * Enables dotall mode.
	 *
	 * <p>
	 * In dotall mode, the expression <tt>.</tt> matches any character,
	 * including a line terminator. By default this expression does not match
	 * line terminators.
	 *
	 * <p>
	 * Dotall mode can also be enabled via the embedded flag expression&nbsp;
	 * <tt>(?s)</tt>. (The <tt>s</tt> is a mnemonic for "single-line" mode,
	 * which is what this is called in Perl.)
	 * </p>
	 */
	public static final int DOTALL = 0x20;

	/**
	 * Enables Unicode-aware case folding.
	 *
	 * <p>
	 * When this flag is specified then case-insensitive matching, when enabled
	 * by the {@link #CASE_INSENSITIVE} flag, is done in a manner consistent
	 * with the Unicode Standard. By default, case-insensitive matching assumes
	 * that only characters in the US-ASCII charset are being matched.
	 *
	 * <p>
	 * Unicode-aware case folding can also be enabled via the embedded flag
	 * expression&nbsp;<tt>(?u)</tt>.
	 *
	 * <p>
	 * Specifying this flag may impose a performance penalty.
	 * </p>
	 */
	public static final int UNICODE_CASE = 0x40;

	/**
	 * Enables canonical equivalence.
	 *
	 * <p>
	 * When this flag is specified then two characters will be considered to
	 * match if, and only if, their full canonical decompositions match. The
	 * expression <tt>"a&#92;u030A"</tt>, for example, will match the string
	 * <tt>"&#92;u00E5"</tt> when this flag is specified. By default, matching
	 * does not take canonical equivalence into account.
	 *
	 * <p>
	 * There is no embedded flag character for enabling canonical equivalence.
	 *
	 * <p>
	 * Specifying this flag may impose a performance penalty.
	 * </p>
	 */
	public static final int CANON_EQ = 0x80;

	/**
	 * Enables the Unicode version of <i>Predefined character classes</i> and
	 * <i>POSIX character classes</i>.
	 *
	 * <p>
	 * When this flag is specified then the (US-ASCII only) <i>Predefined
	 * character classes</i> and <i>POSIX character classes</i> are in
	 * conformance with <a
	 * href="http://www.unicode.org/reports/tr18/"><i>Unicode Technical Standard
	 * #18: Unicode Regular Expression</i></a> <i>Annex C: Compatibility
	 * Properties</i>.
	 * <p>
	 * The UNICODE_CHARACTER_CLASS mode can also be enabled via the embedded
	 * flag expression&nbsp;<tt>(?U)</tt>.
	 * <p>
	 * The flag implies UNICODE_CASE, that is, it enables Unicode-aware case
	 * folding.
	 * <p>
	 * Specifying this flag may impose a performance penalty.
	 * </p>
	 * 
	 * @since 1.7
	 */
	public static final int UNICODE_CHARACTER_CLASS = 0x100;

	/*
	 * LargeFilePattern has only two serialized components: The LargeFilePattern
	 * string and the flags, which are all that is needed to recompile the
	 * LargeFilePattern when it is deserialized.
	 */

	/** use serialVersionUID from Merlin b59 for interoperability */
	public static final long serialVersionUID = 5073258162644648461L;

	/**
	 * The original regular-expression LargeFilePattern string.
	 *
	 * @serial
	 */
	public String pattern;

	/**
	 * The original LargeFilePattern flags.
	 *
	 * @serial
	 */
	public int flags;

	/**
	 * Boolean indicating this LargeFilePattern is compiled; this is necessary
	 * in order to lazily compile deserialized Patterns.
	 */
	public transient volatile boolean compiled = false;

	/**
	 * The normalized LargeFilePattern string.
	 */
	public transient String normalizedPattern;

	/**
	 * The starting point of state machine for the find operation. This allows a
	 * match to start anywhere in the input.
	 */
	public transient Node root;

	/**
	 * The root of object tree for a match operation. The LargeFilePattern is
	 * matched at the beginning. This may include a find that uses BnM or a
	 * First node.
	 */
	public transient Node matchRoot;

	/**
	 * Temporary storage used by parsing LargeFilePattern slice.
	 */
	public transient int[] buffer;

	/**
	 * Map the "name" of the "named capturing group" to its group id node.
	 */
	public transient volatile Map<String, Integer> namedGroups;

	/**
	 * Temporary storage used while parsing group references.
	 */
	public transient GroupHead[] groupNodes;

	/**
	 * Temporary null terminated code point array used by LargeFilePattern
	 * compiling.
	 */
	public transient int[] temp;

	/**
	 * The number of capturing groups in this LargeFilePattern. Used by matchers
	 * to allocate storage needed to perform a match.
	 */
	public transient int capturingGroupCount;

	/**
	 * The local variable count used by parsing tree. Used by matchers to
	 * allocate storage needed to perform a match.
	 */
	public transient int localCount;

	/**
	 * Index into the LargeFilePattern string that keeps track of how much has
	 * been parsed.
	 */
	public transient int cursor;

	/**
	 * Holds the length of the LargeFilePattern string.
	 */
	public transient int patternLength;

	/**
	 * If the Start node might possibly match supplementary characters. It is
	 * set to true during compiling if (1) There is supplementary char in
	 * LargeFilePattern, or (2) There is complement node of Category or Block
	 */
	public transient boolean hasSupplementary;

	/**
	 * Compiles the given regular expression into a LargeFilePattern.
	 *
	 * @param regex
	 *            The expression to be compiled
	 * @return the given regular expression compiled into a LargeFilePattern
	 * @throws PatternSyntaxException
	 *             If the expression's syntax is invalid
	 */
	public static LargeFilePattern compile(String regex) {
		return new LargeFilePattern(regex, 0);
	}

	/**
	 * Compiles the given regular expression into a LargeFilePattern with the
	 * given flags.
	 *
	 * @param regex
	 *            The expression to be compiled
	 *
	 * @param flags
	 *            Match flags, a bit mask that may include
	 *            {@link #CASE_INSENSITIVE}, {@link #MULTILINE}, {@link #DOTALL}
	 *            , {@link #UNICODE_CASE}, {@link #CANON_EQ},
	 *            {@link #UNIX_LINES}, {@link #LITERAL},
	 *            {@link #UNICODE_CHARACTER_CLASS} and {@link #COMMENTS}
	 *
	 * @return the given regular expression compiled into a LargeFilePattern
	 *         with the given flags
	 * @throws IllegalArgumentException
	 *             If bit values other than those corresponding to the defined
	 *             match flags are set in <tt>flags</tt>
	 *
	 * @throws PatternSyntaxException
	 *             If the expression's syntax is invalid
	 */
	public static LargeFilePattern compile(String regex, int flags) {
		return new LargeFilePattern(regex, flags);
	}

	/**
	 * Returns the regular expression from which this LargeFilePattern was
	 * compiled.
	 *
	 * @return The source of this LargeFilePattern
	 */
	public String pattern() {
		return pattern;
	}

	/**
	 * <p>
	 * Returns the string representation of this LargeFilePattern. This is the
	 * regular expression from which this LargeFilePattern was compiled.
	 * </p>
	 *
	 * @return The string representation of this LargeFilePattern
	 * @since 1.5
	 */
	public String toString() {
		return pattern;
	}

	/**
	 * Creates a LargeFileMatcher that will match the given input against this
	 * LargeFilePattern.
	 *
	 * @param input
	 *            The character sequence to be matched
	 *
	 * @return A new LargeFileMatcher for this LargeFilePattern
	 */
	public LargeFileMatcher matcher(CharSequence input) {
		if (!compiled) {
			synchronized (this) {
				if (!compiled)
					compile();
			}
		}
		LargeFileMatcher m = new LargeFileMatcher(this, input);
		return m;
	}

	/**
	 * Returns this LargeFilePattern's match flags.
	 *
	 * @return The match flags specified when this LargeFilePattern was compiled
	 */
	public int flags() {
		return flags;
	}

	/**
	 * Compiles the given regular expression and attempts to match the given
	 * input against it.
	 *
	 * <p>
	 * An invocation of this convenience method of the form
	 *
	 * <blockquote>
	 * 
	 * <pre>
	 * LargeFilePattern.matches(regex, input);
	 * </pre>
	 * 
	 * </blockquote>
	 *
	 * behaves in exactly the same way as the expression
	 *
	 * <blockquote>
	 * 
	 * <pre>
	 * LargeFilePattern.compile(regex).LargeFileMatcher(input).matches()
	 * </pre>
	 * 
	 * </blockquote>
	 *
	 * <p>
	 * If a LargeFilePattern is to be used multiple times, compiling it once and
	 * reusing it will be more efficient than invoking this method each time.
	 * </p>
	 *
	 * @param regex
	 *            The expression to be compiled
	 *
	 * @param input
	 *            The character sequence to be matched
	 * @return whether or not the regular expression matches on the input
	 * @throws PatternSyntaxException
	 *             If the expression's syntax is invalid
	 */
	public static boolean matches(String regex, CharSequence input) {
		LargeFilePattern p = LargeFilePattern.compile(regex);
		LargeFileMatcher m = p.matcher(input);
		return m.matches();
	}

	/**
	 * Splits the given input sequence around matches of this LargeFilePattern.
	 *
	 * <p>
	 * The array returned by this method contains each substring of the input
	 * sequence that is terminated by another subsequence that matches this
	 * LargeFilePattern or is terminated by the end of the input sequence. The
	 * substrings in the array are in the order in which they occur in the
	 * input. If this LargeFilePattern does not match any subsequence of the
	 * input then the resulting array has just one element, namely the input
	 * sequence in string form.
	 *
	 * <p>
	 * When there is a positive-width match at the beginning of the input
	 * sequence then an empty leading substring is included at the beginning of
	 * the resulting array. A zero-width match at the beginning however never
	 * produces such empty leading substring.
	 *
	 * <p>
	 * The <tt>limit</tt> parameter controls the number of times the
	 * LargeFilePattern is applied and therefore affects the length of the
	 * resulting array. If the limit <i>n</i> is greater than zero then the
	 * LargeFilePattern will be applied at most <i>n</i>&nbsp;-&nbsp;1 times,
	 * the array's length will be no greater than <i>n</i>, and the array's last
	 * entry will contain all input beyond the last matched delimiter. If
	 * <i>n</i> is non-positive then the LargeFilePattern will be applied as
	 * many times as possible and the array can have any length. If <i>n</i> is
	 * zero then the LargeFilePattern will be applied as many times as possible,
	 * the array can have any length, and trailing empty strings will be
	 * discarded.
	 *
	 * <p>
	 * The input <tt>"boo:and:foo"</tt>, for example, yields the following
	 * results with these parameters:
	 *
	 * <blockquote>
	 * <table cellpadding=1 cellspacing=0 * summary="Split examples showing regex, limit, and result">
	 * <tr>
	 * <th align="left"><i>Regex&nbsp;&nbsp;&nbsp;&nbsp;</i></th>
	 * <th align="left"><i>Limit&nbsp;&nbsp;&nbsp;&nbsp;</i></th>
	 * <th align="left"><i>Result&nbsp;&nbsp;&nbsp;&nbsp;</i></th>
	 * </tr>
	 * <tr>
	 * <td align=center>:</td>
	 * <td align=center>2</td>
	 * <td><tt>{ "boo", "and:foo" }</tt></td>
	 * </tr>
	 * <tr>
	 * <td align=center>:</td>
	 * <td align=center>5</td>
	 * <td><tt>{ "boo", "and", "foo" }</tt></td>
	 * </tr>
	 * <tr>
	 * <td align=center>:</td>
	 * <td align=center>-2</td>
	 * <td><tt>{ "boo", "and", "foo" }</tt></td>
	 * </tr>
	 * <tr>
	 * <td align=center>o</td>
	 * <td align=center>5</td>
	 * <td><tt>{ "b", "", ":and:f", "", "" }</tt></td>
	 * </tr>
	 * <tr>
	 * <td align=center>o</td>
	 * <td align=center>-2</td>
	 * <td><tt>{ "b", "", ":and:f", "", "" }</tt></td>
	 * </tr>
	 * <tr>
	 * <td align=center>o</td>
	 * <td align=center>0</td>
	 * <td><tt>{ "b", "", ":and:f" }</tt></td>
	 * </tr>
	 * </table>
	 * </blockquote>
	 *
	 * @param input
	 *            The character sequence to be split
	 *
	 * @param limit
	 *            The result threshold, as described above
	 *
	 * @return The array of strings computed by splitting the input around
	 *         matches of this LargeFilePattern
	 */
	public String[] split(CharSequence input, int limit) {
		long index = 0;
		boolean matchLimited = limit > 0;
		ArrayList<String> matchList = new ArrayList<>();
		LargeFileMatcher m = new LargeFileMatcher(this,input);

		// Add segments before each match found
		while (m.find()) {
			if (!matchLimited || matchList.size() < limit - 1) {
				if (index == 0 && index == m.start() && m.start() == m.end()) {
					// no empty leading substring included for zero-width match
					// at the beginning of the input char sequence.
					continue;
				}
				String match = input.subSequence((int) index, (int) m.start()).toString();
				matchList.add(match);
				index = m.end();
			} else if (matchList.size() == limit - 1) { // last one
				String match = input.subSequence((int) index, input.length()).toString();
				matchList.add(match);
				index = m.end();
			}
		}

		// If no match was found, return this
		if (index == 0)
			return new String[] { input.toString() };

		// Add remaining segment
		if (!matchLimited || matchList.size() < limit)
			matchList.add(input.subSequence((int) index, input.length()).toString());

		// Construct result
		int resultSize = matchList.size();
		if (limit == 0)
			while (resultSize > 0 && matchList.get(resultSize - 1).equals(""))
				resultSize--;
		String[] result = new String[resultSize];
		return matchList.subList(0, resultSize).toArray(result);
	}

	/**
	 * Splits the given input sequence around matches of this LargeFilePattern.
	 *
	 * <p>
	 * This method works as if by invoking the two-argument
	 * {@link #split(java.lang.CharSequence, int) split} method with the given
	 * input sequence and a limit argument of zero. Trailing empty strings are
	 * therefore not included in the resulting array.
	 * </p>
	 *
	 * <p>
	 * The input <tt>"boo:and:foo"</tt>, for example, yields the following
	 * results with these expressions:
	 *
	 * <blockquote>
	 * <table cellpadding=1 cellspacing=0 * summary="Split examples showing regex and result">
	 * <tr>
	 * <th align="left"><i>Regex&nbsp;&nbsp;&nbsp;&nbsp;</i></th>
	 * <th align="left"><i>Result</i></th>
	 * </tr>
	 * <tr>
	 * <td align=center>:</td>
	 * <td><tt>{ "boo", "and", "foo" }</tt></td>
	 * </tr>
	 * <tr>
	 * <td align=center>o</td>
	 * <td><tt>{ "b", "", ":and:f" }</tt></td>
	 * </tr>
	 * </table>
	 * </blockquote>
	 *
	 *
	 * @param input
	 *            The character sequence to be split
	 *
	 * @return The array of strings computed by splitting the input around
	 *         matches of this LargeFilePattern
	 */
	public String[] split(CharSequence input) {
		return split(input, 0);
	}

	/**
	 * Returns a literal LargeFilePattern <code>String</code> for the specified
	 * <code>String</code>.
	 *
	 * <p>
	 * This method produces a <code>String</code> that can be used to create a
	 * <code>LargeFilePattern</code> that would match the string <code>s</code>
	 * as if it were a literal LargeFilePattern.
	 * </p>
	 * Metacharacters or escape sequences in the input sequence will be given no
	 * special meaning.
	 *
	 * @param s
	 *            The string to be literalized
	 * @return A literal string replacement
	 * @since 1.5
	 */
	public static String quote(String s) {
		int slashEIndex = s.indexOf("\\E");
		if (slashEIndex == -1)
			return "\\Q" + s + "\\E";

		StringBuilder sb = new StringBuilder(s.length() * 2);
		sb.append("\\Q");
		slashEIndex = 0;
		int current = 0;
		while ((slashEIndex = s.indexOf("\\E", current)) != -1) {
			sb.append(s.substring(current, slashEIndex));
			current = slashEIndex + 2;
			sb.append("\\E\\\\E\\Q");
		}
		sb.append(s.substring(current, s.length()));
		sb.append("\\E");
		return sb.toString();
	}

	/**
	 * Recompile the LargeFilePattern instance from a stream. The original
	 * LargeFilePattern string is read in and the object tree is recompiled from
	 * it.
	 */
	public void readObject(java.io.ObjectInputStream s) throws java.io.IOException,
			ClassNotFoundException {

		// Read in all fields
		s.defaultReadObject();

		// Initialize counts
		capturingGroupCount = 1;
		localCount = 0;

		// if length > 0, the LargeFilePattern is lazily compiled
		compiled = false;
		if (pattern.length() == 0) {
			root = new Start(lastAccept);
			matchRoot = lastAccept;
			compiled = true;
		}
	}

	/**
	 * This public constructor is used to create all Patterns. The
	 * LargeFilePattern string and match flags are all that is needed to
	 * completely describe a LargeFilePattern. An empty LargeFilePattern string
	 * results in an object tree with only a Start node and a LastNode node.
	 */
	public LargeFilePattern(String p, int f) {
		pattern = p;
		flags = f;

		// to use UNICODE_CASE if UNICODE_CHARACTER_CLASS present
		if ((flags & UNICODE_CHARACTER_CLASS) != 0)
			flags |= UNICODE_CASE;

		// Reset group index count
		capturingGroupCount = 1;
		localCount = 0;

		if (pattern.length() > 0) {
			compile();
		} else {
			root = new Start(lastAccept);
			matchRoot = lastAccept;
		}
	}

	/**
	 * The LargeFilePattern is converted to normalizedD form and then a pure
	 * group is constructed to match canonical equivalences of the characters.
	 */
	public void normalize() {
		int lastCodePoint = -1;

		// Convert LargeFilePattern into normalizedD form
		normalizedPattern = Normalizer.normalize(pattern, Normalizer.Form.NFD);
		patternLength = normalizedPattern.length();

		// Modify LargeFilePattern to match canonical equivalences
		StringBuilder newPattern = new StringBuilder(patternLength);
		for (int i = 0; i < patternLength;) {
			int c = normalizedPattern.codePointAt(i);
			StringBuilder sequenceBuffer;
			if ((Character.getType(c) == Character.NON_SPACING_MARK) && (lastCodePoint != -1)) {
				sequenceBuffer = new StringBuilder();
				sequenceBuffer.appendCodePoint(lastCodePoint);
				sequenceBuffer.appendCodePoint(c);
				while (Character.getType(c) == Character.NON_SPACING_MARK) {
					i += Character.charCount(c);
					if (i >= patternLength)
						break;
					c = normalizedPattern.codePointAt(i);
					sequenceBuffer.appendCodePoint(c);
				}
				String ea = produceEquivalentAlternation(sequenceBuffer.toString());
				newPattern.setLength(newPattern.length() - Character.charCount(lastCodePoint));
				newPattern.append("(?:").append(ea).append(")");
			} else if (c == '[' && lastCodePoint != '\\') {
				i = normalizeCharClass(newPattern, i);
			} else {
				newPattern.appendCodePoint(c);
			}
			lastCodePoint = c;
			i += Character.charCount(c);
		}
		normalizedPattern = newPattern.toString();
	}

	/**
	 * Complete the character class being parsed and add a set of alternations
	 * to it that will match the canonical equivalences of the characters within
	 * the class.
	 */
	public int normalizeCharClass(StringBuilder newPattern, int i) {
		StringBuilder charClass = new StringBuilder();
		StringBuilder eq = null;
		int lastCodePoint = -1;
		String result;

		i++;
		charClass.append("[");
		while (true) {
			int c = normalizedPattern.codePointAt(i);
			StringBuilder sequenceBuffer;

			if (c == ']' && lastCodePoint != '\\') {
				charClass.append((char) c);
				break;
			} else if (Character.getType(c) == Character.NON_SPACING_MARK) {
				sequenceBuffer = new StringBuilder();
				sequenceBuffer.appendCodePoint(lastCodePoint);
				while (Character.getType(c) == Character.NON_SPACING_MARK) {
					sequenceBuffer.appendCodePoint(c);
					i += Character.charCount(c);
					if (i >= normalizedPattern.length())
						break;
					c = normalizedPattern.codePointAt(i);
				}
				String ea = produceEquivalentAlternation(sequenceBuffer.toString());

				charClass.setLength(charClass.length() - Character.charCount(lastCodePoint));
				if (eq == null)
					eq = new StringBuilder();
				eq.append('|');
				eq.append(ea);
			} else {
				charClass.appendCodePoint(c);
				i++;
			}
			if (i == normalizedPattern.length())
				throw error("Unclosed character class");
			lastCodePoint = c;
		}

		if (eq != null) {
			result = "(?:" + charClass.toString() + eq.toString() + ")";
		} else {
			result = charClass.toString();
		}

		newPattern.append(result);
		return i;
	}

	/**
	 * Given a specific sequence composed of a regular character and combining
	 * marks that follow it, produce the alternation that will match all
	 * canonical equivalences of that sequence.
	 */
	public String produceEquivalentAlternation(String source) {
		int len = countChars(source, 0, 1);
		if (source.length() == len)
			// source has one character.
			return source;

		String base = source.substring(0, len);
		String combiningMarks = source.substring(len);

		String[] perms = producePermutations(combiningMarks);
		StringBuilder result = new StringBuilder(source);

		// Add combined permutations
		for (int x = 0; x < perms.length; x++) {
			String next = base + perms[x];
			if (x > 0)
				result.append("|" + next);
			next = composeOneStep(next);
			if (next != null)
				result.append("|" + produceEquivalentAlternation(next));
		}
		return result.toString();
	}

	/**
	 * Returns an array of strings that have all the possible permutations of
	 * the characters in the input string. This is used to get a list of all
	 * possible orderings of a set of combining marks. Note that some of the
	 * permutations are invalid because of combining class collisions, and these
	 * possibilities must be removed because they are not canonically
	 * equivalent.
	 */
	public String[] producePermutations(String input) {
		if (input.length() == countChars(input, 0, 1))
			return new String[] { input };

		if (input.length() == countChars(input, 0, 2)) {
			int c0 = Character.codePointAt(input, 0);
			int c1 = Character.codePointAt(input, Character.charCount(c0));
			if (getClass(c1) == getClass(c0)) {
				return new String[] { input };
			}
			String[] result = new String[2];
			result[0] = input;
			StringBuilder sb = new StringBuilder(2);
			sb.appendCodePoint(c1);
			sb.appendCodePoint(c0);
			result[1] = sb.toString();
			return result;
		}

		int length = 1;
		int nCodePoints = countCodePoints(input);
		for (int x = 1; x < nCodePoints; x++)
			length = length * (x + 1);

		String[] temp = new String[length];

		int combClass[] = new int[nCodePoints];
		for (int x = 0, i = 0; x < nCodePoints; x++) {
			int c = Character.codePointAt(input, i);
			combClass[x] = getClass(c);
			i += Character.charCount(c);
		}

		// For each char, take it out and add the permutations
		// of the remaining chars
		int index = 0;
		int len;
		// offset maintains the index in code units.
		loop: for (int x = 0, offset = 0; x < nCodePoints; x++, offset += len) {
			len = countChars(input, offset, 1);
			for (int y = x - 1; y >= 0; y--) {
				if (combClass[y] == combClass[x]) {
					continue loop;
				}
			}
			StringBuilder sb = new StringBuilder(input);
			String otherChars = sb.delete(offset, offset + len).toString();
			String[] subResult = producePermutations(otherChars);

			String prefix = input.substring(offset, offset + len);
			for (int y = 0; y < subResult.length; y++)
				temp[index++] = prefix + subResult[y];
		}
		String[] result = new String[index];
		for (int x = 0; x < index; x++)
			result[x] = temp[x];
		return result;
	}

	public int getClass(int c) {
		return sun.text.Normalizer.getCombiningClass(c);
	}

	/**
	 * Attempts to compose input by combining the first character with the first
	 * combining mark following it. Returns a String that is the composition of
	 * the leading character with its first combining mark followed by the
	 * remaining combining marks. Returns null if the first two characters
	 * cannot be further composed.
	 */
	public String composeOneStep(String input) {
		int len = countChars(input, 0, 2);
		String firstTwoCharacters = input.substring(0, len);
		String result = Normalizer.normalize(firstTwoCharacters, Normalizer.Form.NFC);

		if (result.equals(firstTwoCharacters))
			return null;
		else {
			String remainder = input.substring(len);
			return result + remainder;
		}
	}

	/**
	 * Preprocess any \Q...\E sequences in `temp', meta-quoting them. See the
	 * description of `quotemeta' in perlfunc(1).
	 */
	public void RemoveQEQuoting() {
		final int pLen = patternLength;
		int i = 0;
		while (i < pLen - 1) {
			if (temp[i] != '\\')
				i += 1;
			else if (temp[i + 1] != 'Q')
				i += 2;
			else
				break;
		}
		if (i >= pLen - 1) // No \Q sequence found
			return;
		int j = i;
		i += 2;
		int[] newtemp = new int[j + 3 * (pLen - i) + 2];
		System.arraycopy(temp, 0, newtemp, 0, j);

		boolean inQuote = true;
		boolean beginQuote = true;
		while (i < pLen) {
			int c = temp[i++];
			if (!ASCII.isAscii(c) || ASCII.isAlpha(c)) {
				newtemp[j++] = c;
			} else if (ASCII.isDigit(c)) {
				if (beginQuote) {
					/*
					 * A unicode escape \[0xu] could be before this quote, and
					 * we don't want this numeric char to processed as part of
					 * the escape.
					 */
					newtemp[j++] = '\\';
					newtemp[j++] = 'x';
					newtemp[j++] = '3';
				}
				newtemp[j++] = c;
			} else if (c != '\\') {
				if (inQuote)
					newtemp[j++] = '\\';
				newtemp[j++] = c;
			} else if (inQuote) {
				if (temp[i] == 'E') {
					i++;
					inQuote = false;
				} else {
					newtemp[j++] = '\\';
					newtemp[j++] = '\\';
				}
			} else {
				if (temp[i] == 'Q') {
					i++;
					inQuote = true;
					beginQuote = true;
					continue;
				} else {
					newtemp[j++] = c;
					if (i != pLen)
						newtemp[j++] = temp[i++];
				}
			}

			beginQuote = false;
		}

		patternLength = j;
		temp = Arrays.copyOf(newtemp, j + 2); // double zero termination
	}

	/**
	 * Copies regular expression to an int array and invokes the parsing of the
	 * expression which will create the object tree.
	 */
	public void compile() {
		// Handle canonical equivalences
		if (has(CANON_EQ) && !has(LITERAL)) {
			normalize();
		} else {
			normalizedPattern = pattern;
		}
		patternLength = normalizedPattern.length();

		// Copy LargeFilePattern to int array for convenience
		// Use double zero to terminate LargeFilePattern
		temp = new int[patternLength + 2];

		hasSupplementary = false;
		int c, count = 0;
		// Convert all chars into code points
		for (int x = 0; x < patternLength; x += Character.charCount(c)) {
			c = normalizedPattern.codePointAt(x);
			if (isSupplementary(c)) {
				hasSupplementary = true;
			}
			temp[count++] = c;
		}

		patternLength = count; // patternLength now in code points

		if (!has(LITERAL))
			RemoveQEQuoting();

		// Allocate all temporary objects here.
		buffer = new int[32];
		groupNodes = new GroupHead[10];
		namedGroups = null;

		if (has(LITERAL)) {
			// Literal LargeFilePattern handling
			matchRoot = newSlice(temp, patternLength, hasSupplementary);
			matchRoot.next = lastAccept;
		} else {
			// Start recursive descent parsing
			matchRoot = expr(lastAccept);
			// Check extra LargeFilePattern characters
			if (patternLength != cursor) {
				if (peek() == ')') {
					throw error("Unmatched closing ')'");
				} else {
					throw error("Unexpected internal error");
				}
			}
		}

		// Peephole optimization
		if (matchRoot instanceof Slice) {
			root = BnM.optimize(matchRoot);
			if (root == matchRoot) {
				root = hasSupplementary ? new StartS(matchRoot) : new Start(matchRoot);
			}
		} else if (matchRoot instanceof Begin || matchRoot instanceof First) {
			root = matchRoot;
		} else {
			root = hasSupplementary ? new StartS(matchRoot) : new Start(matchRoot);
		}

		// Release temporary storage
		temp = null;
		buffer = null;
		groupNodes = null;
		patternLength = 0;
		compiled = true;
	}

	Map<String, Integer> namedGroups() {
		if (namedGroups == null)
			namedGroups = new HashMap<>(2);
		return namedGroups;
	}

	/**
	 * Used to accumulate information about a subtree of the object graph so
	 * that optimizations can be applied to the subtree.
	 */
	public static final class TreeInfo {
		public int minLength;
		public int maxLength;
		public boolean maxValid;
		public boolean deterministic;

		public TreeInfo() {
			reset();
		}

		public void reset() {
			minLength = 0;
			maxLength = 0;
			maxValid = true;
			deterministic = true;
		}
	}

	/*
	 * The following public methods are mainly used to improve the readability
	 * of the code. In order to let the Java compiler easily inline them, we
	 * should not put many assertions or error checks in them.
	 */

	/**
	 * Indicates whether a particular flag is set or not.
	 */
	public boolean has(int f) {
		return (flags & f) != 0;
	}

	/**
	 * Match next character, signal error if failed.
	 */
	public void accept(int ch, String s) {
		int testChar = temp[cursor++];
		if (has(COMMENTS))
			testChar = parsePastWhitespace(testChar);
		if (ch != testChar) {
			throw error(s);
		}
	}

	/**
	 * Mark the end of LargeFilePattern with a specific character.
	 */
	public void mark(int c) {
		temp[patternLength] = c;
	}

	/**
	 * Peek the next character, and do not advance the cursor.
	 */
	public int peek() {
		int ch = temp[cursor];
		if (has(COMMENTS))
			ch = peekPastWhitespace(ch);
		return ch;
	}

	/**
	 * Read the next character, and advance the cursor by one.
	 */
	public int read() {
		int ch = temp[cursor++];
		if (has(COMMENTS))
			ch = parsePastWhitespace(ch);
		return ch;
	}

	/**
	 * Advance the cursor by one, and peek the next character.
	 */
	public int next() {
		int ch = temp[++cursor];
		if (has(COMMENTS))
			ch = peekPastWhitespace(ch);
		return ch;
	}

	/**
	 * Advance the cursor by one, and peek the next character, ignoring the
	 * COMMENTS setting
	 */
	public int nextEscaped() {
		int ch = temp[++cursor];
		return ch;
	}

	/**
	 * If in xmode peek past whitespace and comments.
	 */
	public int peekPastWhitespace(int ch) {
		while (ASCII.isSpace(ch) || ch == '#') {
			while (ASCII.isSpace(ch))
				ch = temp[++cursor];
			if (ch == '#') {
				ch = peekPastLine();
			}
		}
		return ch;
	}

	/**
	 * If in xmode parse past whitespace and comments.
	 */
	public int parsePastWhitespace(int ch) {
		while (ASCII.isSpace(ch) || ch == '#') {
			while (ASCII.isSpace(ch))
				ch = temp[cursor++];
			if (ch == '#')
				ch = parsePastLine();
		}
		return ch;
	}

	/**
	 * xmode parse past comment to end of line.
	 */
	public int parsePastLine() {
		int ch = temp[cursor++];
		while (ch != 0 && !isLineSeparator(ch))
			ch = temp[cursor++];
		return ch;
	}

	/**
	 * xmode peek past comment to end of line.
	 */
	public int peekPastLine() {
		int ch = temp[++cursor];
		while (ch != 0 && !isLineSeparator(ch))
			ch = temp[++cursor];
		return ch;
	}

	/**
	 * Determines if character is a line separator in the current mode
	 */
	public boolean isLineSeparator(int ch) {
		if (has(UNIX_LINES)) {
			return ch == '\n';
		} else {
			return (ch == '\n' || ch == '\r' || (ch | 1) == '\u2029' || ch == '\u0085');
		}
	}

	/**
	 * Read the character after the next one, and advance the cursor by two.
	 */
	public int skip() {
		int i = cursor;
		int ch = temp[i + 1];
		cursor = i + 2;
		return ch;
	}

	/**
	 * Unread one next character, and retreat cursor by one.
	 */
	public void unread() {
		cursor--;
	}

	/**
	 * Internal method used for handling all syntax errors. The LargeFilePattern
	 * is displayed with a pointer to aid in locating the syntax error.
	 */
	public PatternSyntaxException error(String s) {
		return new PatternSyntaxException(s, normalizedPattern, cursor - 1);
	}

	/**
	 * Determines if there is any supplementary character or unpaired surrogate
	 * in the specified range.
	 */
	public boolean findSupplementary(int start, int end) {
		for (int i = start; i < end; i++) {
			if (isSupplementary(temp[i]))
				return true;
		}
		return false;
	}

	/**
	 * Determines if the specified code point is a supplementary character or
	 * unpaired surrogate.
	 */
	public static final boolean isSupplementary(int ch) {
		return ch >= Character.MIN_SUPPLEMENTARY_CODE_POINT || Character.isSurrogate((char) ch);
	}

	/**
	 * The following methods handle the main parsing. They are sorted according
	 * to their precedence order, the lowest one first.
	 */

	/**
	 * The expression is parsed with branch nodes added for alternations. This
	 * may be called recursively to parse sub expressions that may contain
	 * alternations.
	 */
	public Node expr(Node end) {
		Node prev = null;
		Node firstTail = null;
		Branch branch = null;
		Node branchConn = null;

		for (;;) {
			Node node = sequence(end);
			Node nodeTail = root; // double return
			if (prev == null) {
				prev = node;
				firstTail = nodeTail;
			} else {
				// Branch
				if (branchConn == null) {
					branchConn = new BranchConn();
					branchConn.next = end;
				}
				if (node == end) {
					// if the node returned from sequence() is "end"
					// we have an empty expr, set a null atom into
					// the branch to indicate to go "next" directly.
					node = null;
				} else {
					// the "tail.next" of each atom goes to branchConn
					nodeTail.next = branchConn;
				}
				if (prev == branch) {
					branch.add(node);
				} else {
					if (prev == end) {
						prev = null;
					} else {
						// replace the "end" with "branchConn" at its tail.next
						// when put the "prev" into the branch as the first
						// atom.
						firstTail.next = branchConn;
					}
					prev = branch = new Branch(prev, node, branchConn);
				}
			}
			if (peek() != '|') {
				return prev;
			}
			next();
		}
	}

	@SuppressWarnings("fallthrough")
	/**
	 * Parsing of sequences between alternations.
	 */
	public Node sequence(Node end) {
		Node head = null;
		Node tail = null;
		Node node = null;
		LOOP: for (;;) {
			int ch = peek();
			switch (ch) {
			case '(':
				// Because group handles its own closure,
				// we need to treat it differently
				node = group0();
				// Check for comment or flag group
				if (node == null)
					continue;
				if (head == null)
					head = node;
				else
					tail.next = node;
				// Double return: Tail was returned in root
				tail = root;
				continue;
			case '[':
				node = clazz(true);
				break;
			case '\\':
				ch = nextEscaped();
				if (ch == 'p' || ch == 'P') {
					boolean oneLetter = true;
					boolean comp = (ch == 'P');
					ch = next(); // Consume { if present
					if (ch != '{') {
						unread();
					} else {
						oneLetter = false;
					}
					node = family(oneLetter, comp);
				} else {
					unread();
					node = atom();
				}
				break;
			case '^':
				next();
				if (has(MULTILINE)) {
					if (has(UNIX_LINES))
						node = new UnixCaret();
					else
						node = new Caret();
				} else {
					node = new Begin();
				}
				break;
			case '$':
				next();
				if (has(UNIX_LINES))
					node = new UnixDollar(has(MULTILINE));
				else
					node = new Dollar(has(MULTILINE));
				break;
			case '.':
				next();
				if (has(DOTALL)) {
					node = new All();
				} else {
					if (has(UNIX_LINES))
						node = new UnixDot();
					else {
						node = new Dot();
					}
				}
				break;
			case '|':
			case ')':
				break LOOP;
			case ']': // Now interpreting dangling ] and } as literals
			case '}':
				node = atom();
				break;
			case '?':
			case '*':
			case '+':
				next();
				throw error("Dangling meta character '" + ((char) ch) + "'");
			case 0:
				if (cursor >= patternLength) {
					break LOOP;
				}
				// Fall through
			default:
				node = atom();
				break;
			}

			node = closure(node);

			if (head == null) {
				head = tail = node;
			} else {
				tail.next = node;
				tail = node;
			}
		}
		if (head == null) {
			return end;
		}
		tail.next = end;
		root = tail; // double return
		return head;
	}

	@SuppressWarnings("fallthrough")
	/**
	 * Parse and add a new Single or Slice.
	 */
	public Node atom() {
		int first = 0;
		int prev = -1;
		boolean hasSupplementary = false;
		int ch = peek();
		for (;;) {
			switch (ch) {
			case '*':
			case '+':
			case '?':
			case '{':
				if (first > 1) {
					cursor = prev; // Unwind one character
					first--;
				}
				break;
			case '$':
			case '.':
			case '^':
			case '(':
			case '[':
			case '|':
			case ')':
				break;
			case '\\':
				ch = nextEscaped();
				if (ch == 'p' || ch == 'P') { // Property
					if (first > 0) { // Slice is waiting; handle it first
						unread();
						break;
					} else { // No slice; just return the family node
						boolean comp = (ch == 'P');
						boolean oneLetter = true;
						ch = next(); // Consume { if present
						if (ch != '{')
							unread();
						else
							oneLetter = false;
						return family(oneLetter, comp);
					}
				}
				unread();
				prev = cursor;
				ch = escape(false, first == 0, false);
				if (ch >= 0) {
					append(ch, first);
					first++;
					if (isSupplementary(ch)) {
						hasSupplementary = true;
					}
					ch = peek();
					continue;
				} else if (first == 0) {
					return root;
				}
				// Unwind meta escape sequence
				cursor = prev;
				break;
			case 0:
				if (cursor >= patternLength) {
					break;
				}
				// Fall through
			default:
				prev = cursor;
				append(ch, first);
				first++;
				if (isSupplementary(ch)) {
					hasSupplementary = true;
				}
				ch = next();
				continue;
			}
			break;
		}
		if (first == 1) {
			return newSingle(buffer[0]);
		} else {
			return newSlice(buffer, first, hasSupplementary);
		}
	}

	public void append(int ch, int len) {
		if (len >= buffer.length) {
			int[] tmp = new int[len + len];
			System.arraycopy(buffer, 0, tmp, 0, len);
			buffer = tmp;
		}
		buffer[len] = ch;
	}

	/**
	 * Parses a backref greedily, taking as many numbers as it can. The first
	 * digit is always treated as a backref, but multi digit numbers are only
	 * treated as a backref if at least that many backrefs exist at this point
	 * in the regex.
	 */
	public Node ref(int refNum) {
		boolean done = false;
		while (!done) {
			int ch = peek();
			switch (ch) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				int newRefNum = (refNum * 10) + (ch - '0');
				// Add another number if it doesn't make a group
				// that doesn't exist
				if (capturingGroupCount - 1 < newRefNum) {
					done = true;
					break;
				}
				refNum = newRefNum;
				read();
				break;
			default:
				done = true;
				break;
			}
		}
		if (has(CASE_INSENSITIVE))
			return new CIBackRef(refNum, has(UNICODE_CASE));
		else
			return new BackRef(refNum);
	}

	/**
	 * Parses an escape sequence to determine the actual value that needs to be
	 * matched. If -1 is returned and create was true a new object was added to
	 * the tree to handle the escape sequence. If the returned value is greater
	 * than zero, it is the value that matches the escape sequence.
	 */
	public int escape(boolean inclass, boolean create, boolean isrange) {
		int ch = skip();
		switch (ch) {
		case '0':
			return o();
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			if (inclass)
				break;
			if (create) {
				root = ref((ch - '0'));
			}
			return -1;
		case 'A':
			if (inclass)
				break;
			if (create)
				root = new Begin();
			return -1;
		case 'B':
			if (inclass)
				break;
			if (create)
				root = new Bound(Bound.NONE, has(UNICODE_CHARACTER_CLASS));
			return -1;
		case 'C':
			break;
		case 'D':
			if (create)
				root = has(UNICODE_CHARACTER_CLASS) ? new Utype(UnicodeProp.DIGIT).complement()
						: new Ctype(ASCII.DIGIT).complement();
			return -1;
		case 'E':
		case 'F':
			break;
		case 'G':
			if (inclass)
				break;
			if (create)
				root = new LastMatch();
			return -1;
		case 'H':
			if (create)
				root = new HorizWS().complement();
			return -1;
		case 'I':
		case 'J':
		case 'K':
		case 'L':
		case 'M':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
			break;
		case 'R':
			if (inclass)
				break;
			if (create)
				root = new LineEnding();
			return -1;
		case 'S':
			if (create)
				root = has(UNICODE_CHARACTER_CLASS) ? new Utype(WHITE_SPACE_FN).complement()
						: new Ctype(ASCII.SPACE).complement();
			return -1;
		case 'T':
		case 'U':
			break;
		case 'V':
			if (create)
				root = new VertWS().complement();
			return -1;
		case 'W':
			if (create)
				root = has(UNICODE_CHARACTER_CLASS) ? new Utype(UnicodeProp.WORD).complement() : new Ctype(
						ASCII.WORD).complement();
			return -1;
		case 'X':
		case 'Y':
			break;
		case 'Z':
			if (inclass)
				break;
			if (create) {
				if (has(UNIX_LINES))
					root = new UnixDollar(false);
				else
					root = new Dollar(false);
			}
			return -1;
		case 'a':
			return '\007';
		case 'b':
			if (inclass)
				break;
			if (create)
				root = new Bound(Bound.BOTH, has(UNICODE_CHARACTER_CLASS));
			return -1;
		case 'c':
			return c();
		case 'd':
			if (create)
				root = has(UNICODE_CHARACTER_CLASS) ? new Utype(UnicodeProp.DIGIT) : new Ctype(
						ASCII.DIGIT);
			return -1;
		case 'e':
			return '\033';
		case 'f':
			return '\f';
		case 'g':
			break;
		case 'h':
			if (create)
				root = new HorizWS();
			return -1;
		case 'i':
		case 'j':
			break;
		case 'k':
			if (inclass)
				break;
			if (read() != '<')
				throw error("\\k is not followed by '<' for named capturing group");
			String name = groupname(read());
			if (!namedGroups().containsKey(name))
				throw error("(named capturing group <" + name + "> does not exit");
			if (create) {
				if (has(CASE_INSENSITIVE))
					root = new CIBackRef(namedGroups().get(name), has(UNICODE_CASE));
				else
					root = new BackRef(namedGroups().get(name));
			}
			return -1;
		case 'l':
		case 'm':
			break;
		case 'n':
			return '\n';
		case 'o':
		case 'p':
		case 'q':
			break;
		case 'r':
			return '\r';
		case 's':
			if (create)
				root = has(UNICODE_CHARACTER_CLASS) ? new Utype(UnicodeProp.WHITE_SPACE) : new Ctype(
						ASCII.SPACE);
			return -1;
		case 't':
			return '\t';
		case 'u':
			return u();
		case 'v':
			// '\v' was implemented as VT/0x0B in releases < 1.8 (though
			// undocumented). In JDK8 '\v' is specified as a predefined
			// character class for all vertical whitespace characters.
			// So [-1, root=VertWS node] pair is returned (instead of a
			// single 0x0B). This breaks the range if '\v' is used as
			// the start or end value, such as [\v-...] or [...-\v], in
			// which a single definite value (0x0B) is expected. For
			// compatibility concern '\013'/0x0B is returned if isrange.
			if (isrange)
				return '\013';
			if (create)
				root = new VertWS();
			return -1;
		case 'w':
			if (create)
				root = has(UNICODE_CHARACTER_CLASS) ? new Utype(UnicodeProp.WORD) : new Ctype(
						ASCII.WORD);
			return -1;
		case 'x':
			return x();
		case 'y':
			break;
		case 'z':
			if (inclass)
				break;
			if (create)
				root = new End();
			return -1;
		default:
			return ch;
		}
		throw error("Illegal/unsupported escape sequence");
	}

	/**
	 * Parse a character class, and return the node that matches it.
	 *
	 * Consumes a ] on the way out if consume is true. Usually consume is true
	 * except for the case of [abc&&def] where def is a separate right hand node
	 * with "understood" brackets.
	 */
	public CharProperty clazz(boolean consume) {
		CharProperty prev = null;
		CharProperty node = null;
		BitClass bits = new BitClass();
		boolean include = true;
		boolean firstInClass = true;
		int ch = next();
		for (;;) {
			switch (ch) {
			case '^':
				// Negates if first char in a class, otherwise literal
				if (firstInClass) {
					if (temp[cursor - 1] != '[')
						break;
					ch = next();
					include = !include;
					continue;
				} else {
					// ^ not first in class, treat as literal
					break;
				}
			case '[':
				firstInClass = false;
				node = clazz(true);
				if (prev == null)
					prev = node;
				else
					prev = union(prev, node);
				ch = peek();
				continue;
			case '&':
				firstInClass = false;
				ch = next();
				if (ch == '&') {
					ch = next();
					CharProperty rightNode = null;
					while (ch != ']' && ch != '&') {
						if (ch == '[') {
							if (rightNode == null)
								rightNode = clazz(true);
							else
								rightNode = union(rightNode, clazz(true));
						} else { // abc&&def
							unread();
							rightNode = clazz(false);
						}
						ch = peek();
					}
					if (rightNode != null)
						node = rightNode;
					if (prev == null) {
						if (rightNode == null)
							throw error("Bad class syntax");
						else
							prev = rightNode;
					} else {
						prev = intersection(prev, node);
					}
				} else {
					// treat as a literal &
					unread();
					break;
				}
				continue;
			case 0:
				firstInClass = false;
				if (cursor >= patternLength)
					throw error("Unclosed character class");
				break;
			case ']':
				firstInClass = false;
				if (prev != null) {
					if (consume)
						next();
					return prev;
				}
				break;
			default:
				firstInClass = false;
				break;
			}
			node = range(bits);
			if (include) {
				if (prev == null) {
					prev = node;
				} else {
					if (prev != node)
						prev = union(prev, node);
				}
			} else {
				if (prev == null) {
					prev = node.complement();
				} else {
					if (prev != node)
						prev = setDifference(prev, node);
				}
			}
			ch = peek();
		}
	}

	public CharProperty bitsOrSingle(BitClass bits, int ch) {
		/*
		 * Bits can only handle codepoints in [u+0000-u+00ff] range. Use
		 * "single" node instead of bits when dealing with unicode case folding
		 * for codepoints listed below. (1)Uppercase out of range: u+00ff,
		 * u+00b5 toUpperCase(u+00ff) -> u+0178 toUpperCase(u+00b5) -> u+039c
		 * (2)LatinSmallLetterLongS u+17f toUpperCase(u+017f) -> u+0053
		 * (3)LatinSmallLetterDotlessI u+131 toUpperCase(u+0131) -> u+0049
		 * (4)LatinCapitalLetterIWithDotAbove u+0130 toLowerCase(u+0130) ->
		 * u+0069 (5)KelvinSign u+212a toLowerCase(u+212a) ==> u+006B
		 * (6)AngstromSign u+212b toLowerCase(u+212b) ==> u+00e5
		 */
		if (ch < 256
				&& !(has(CASE_INSENSITIVE) && has(UNICODE_CASE) && (ch == 0xff || ch == 0xb5
						|| ch == 0x49 || ch == 0x69 || // I and i
						ch == 0x53 || ch == 0x73 || // S and s
						ch == 0x4b || ch == 0x6b || // K and k
						ch == 0xc5 || ch == 0xe5))) // A+ring
			return bits.add(ch, flags());
		return newSingle(ch);
	}

	/**
	 * Parse a single character or a character range in a character class and
	 * return its representative node.
	 */
	public CharProperty range(BitClass bits) {
		int ch = peek();
		if (ch == '\\') {
			ch = nextEscaped();
			if (ch == 'p' || ch == 'P') { // A property
				boolean comp = (ch == 'P');
				boolean oneLetter = true;
				// Consume { if present
				ch = next();
				if (ch != '{')
					unread();
				else
					oneLetter = false;
				return family(oneLetter, comp);
			} else { // ordinary escape
				boolean isrange = temp[cursor + 1] == '-';
				unread();
				ch = escape(true, true, isrange);
				if (ch == -1)
					return (CharProperty) root;
			}
		} else {
			next();
		}
		if (ch >= 0) {
			if (peek() == '-') {
				int endRange = temp[cursor + 1];
				if (endRange == '[') {
					return bitsOrSingle(bits, ch);
				}
				if (endRange != ']') {
					next();
					int m = peek();
					if (m == '\\') {
						m = escape(true, false, true);
					} else {
						next();
					}
					if (m < ch) {
						throw error("Illegal character range");
					}
					if (has(CASE_INSENSITIVE))
						return caseInsensitiveRangeFor(ch, m);
					else
						return rangeFor(ch, m);
				}
			}
			return bitsOrSingle(bits, ch);
		}
		throw error("Unexpected character '" + ((char) ch) + "'");
	}

	/**
	 * Parses a Unicode character family and returns its representative node.
	 */
	public CharProperty family(boolean singleLetter, boolean maybeComplement) {
		next();
		String name;
		CharProperty node = null;

		if (singleLetter) {
			int c = temp[cursor];
			if (!Character.isSupplementaryCodePoint(c)) {
				name = String.valueOf((char) c);
			} else {
				name = new String(temp, cursor, 1);
			}
			read();
		} else {
			int i = cursor;
			mark('}');
			while (read() != '}') {
			}
			mark('\000');
			int j = cursor;
			if (j > patternLength)
				throw error("Unclosed character family");
			if (i + 1 >= j)
				throw error("Empty character family");
			name = new String(temp, i, j - i - 1);
		}

		int i = name.indexOf('=');
		if (i != -1) {
			// property construct \p{name=value}
			String value = name.substring(i + 1);
			name = name.substring(0, i).toLowerCase(Locale.ENGLISH);
			if ("sc".equals(name) || "script".equals(name)) {
				node = unicodeScriptPropertyFor(value);
			} else if ("blk".equals(name) || "block".equals(name)) {
				node = unicodeBlockPropertyFor(value);
			} else if ("gc".equals(name) || "general_category".equals(name)) {
				node = charPropertyNodeFor(value);
			} else {
				throw error("Unknown Unicode property {name=<" + name + ">, " + "value=<" + value
						+ ">}");
			}
		} else {
			if (name.startsWith("In")) {
				// \p{inBlockName}
				node = unicodeBlockPropertyFor(name.substring(2));
			} else if (name.startsWith("Is")) {
				// \p{isGeneralCategory} and \p{isScriptName}
				name = name.substring(2);
				UnicodeProp uprop = UnicodeProp.forName(name);
				if (uprop != null)
					node = new Utype(uprop);
				if (node == null)
					node = CharPropertyNames.charPropertyFor(name);
				if (node == null)
					node = unicodeScriptPropertyFor(name);
			} else {
				if (has(UNICODE_CHARACTER_CLASS)) {
					UnicodeProp uprop = UnicodeProp.forPOSIXName(name);
					if (uprop != null)
						node = new Utype(uprop);
				}
				if (node == null)
					node = charPropertyNodeFor(name);
			}
		}
		if (maybeComplement) {
			if (node instanceof Category || node instanceof Block)
				hasSupplementary = true;
			node = node.complement();
		}
		return node;
	}

	/**
	 * Returns a CharProperty matching all characters belong to a UnicodeScript.
	 */
	public CharProperty unicodeScriptPropertyFor(String name) {
		final Character.UnicodeScript script;
		try {
			script = Character.UnicodeScript.forName(name);
		} catch (IllegalArgumentException iae) {
			throw error("Unknown character script name {" + name + "}");
		}
		return new Script(script);
	}

	/**
	 * Returns a CharProperty matching all characters in a UnicodeBlock.
	 */
	public CharProperty unicodeBlockPropertyFor(String name) {
		final Character.UnicodeBlock block;
		try {
			block = Character.UnicodeBlock.forName(name);
		} catch (IllegalArgumentException iae) {
			throw error("Unknown character block name {" + name + "}");
		}
		return new Block(block);
	}

	/**
	 * Returns a CharProperty matching all characters in a named property.
	 */
	public CharProperty charPropertyNodeFor(String name) {
		CharProperty p = CharPropertyNames.charPropertyFor(name);
		if (p == null)
			throw error("Unknown character property name {" + name + "}");
		return p;
	}

	/**
	 * Parses and returns the name of a "named capturing group", the trailing
	 * ">" is consumed after parsing.
	 */
	public String groupname(int ch) {
		StringBuilder sb = new StringBuilder();
		sb.append(Character.toChars(ch));
		while (ASCII.isLower(ch = read()) || ASCII.isUpper(ch) || ASCII.isDigit(ch)) {
			sb.append(Character.toChars(ch));
		}
		if (sb.length() == 0)
			throw error("named capturing group has 0 length name");
		if (ch != '>')
			throw error("named capturing group is missing trailing '>'");
		return sb.toString();
	}

	/**
	 * Parses a group and returns the head node of a set of nodes that process
	 * the group. Sometimes a double return system is used where the tail is
	 * returned in root.
	 */
	public Node group0() {
		boolean capturingGroup = false;
		Node head = null;
		Node tail = null;
		int save = flags;
		root = null;
		int ch = next();
		if (ch == '?') {
			ch = skip();
			switch (ch) {
			case ':': // (?:xxx) pure group
				head = createGroup(true);
				tail = root;
				head.next = expr(tail);
				break;
			case '=': // (?=xxx) and (?!xxx) lookahead
			case '!':
				head = createGroup(true);
				tail = root;
				head.next = expr(tail);
				if (ch == '=') {
					head = tail = new Pos(head);
				} else {
					head = tail = new Neg(head);
				}
				break;
			case '>': // (?>xxx) independent group
				head = createGroup(true);
				tail = root;
				head.next = expr(tail);
				head = tail = new Ques(head, INDEPENDENT);
				break;
			case '<': // (?<xxx) look behind
				ch = read();
				if (ASCII.isLower(ch) || ASCII.isUpper(ch)) {
					// named captured group
					String name = groupname(ch);
					if (namedGroups().containsKey(name))
						throw error("Named capturing group <" + name + "> is already defined");
					capturingGroup = true;
					head = createGroup(false);
					tail = root;
					namedGroups().put(name, capturingGroupCount - 1);
					head.next = expr(tail);
					break;
				}
				int start = cursor;
				head = createGroup(true);
				tail = root;
				head.next = expr(tail);
				tail.next = lookbehindEnd;
				TreeInfo info = new TreeInfo();
				head.study(info);
				if (info.maxValid == false) {
					throw error("Look-behind group does not have " + "an obvious maximum length");
				}
				boolean hasSupplementary = findSupplementary(start, patternLength);
				if (ch == '=') {
					head = tail = (hasSupplementary ? new BehindS(head, info.maxLength, info.minLength)
							: new Behind(head, info.maxLength, info.minLength));
				} else if (ch == '!') {
					head = tail = (hasSupplementary ? new NotBehindS(head, info.maxLength,
							info.minLength) : new NotBehind(head, info.maxLength, info.minLength));
				} else {
					throw error("Unknown look-behind group");
				}
				break;
			case '$':
			case '@':
				throw error("Unknown group type");
			default: // (?xxx:) inlined match flags
				unread();
				addFlag();
				ch = read();
				if (ch == ')') {
					return null; // Inline modifier only
				}
				if (ch != ':') {
					throw error("Unknown inline modifier");
				}
				head = createGroup(true);
				tail = root;
				head.next = expr(tail);
				break;
			}
		} else { // (xxx) a regular group
			capturingGroup = true;
			head = createGroup(false);
			tail = root;
			head.next = expr(tail);
		}

		accept(')', "Unclosed group");
		flags = save;

		// Check for quantifiers
		Node node = closure(head);
		if (node == head) { // No closure
			root = tail;
			return node; // Dual return
		}
		if (head == tail) { // Zero length assertion
			root = node;
			return node; // Dual return
		}

		if (node instanceof Ques) {
			Ques ques = (Ques) node;
			if (ques.type == POSSESSIVE) {
				root = node;
				return node;
			}
			tail.next = new BranchConn();
			tail = tail.next;
			if (ques.type == GREEDY) {
				head = new Branch(head, null, tail);
			} else { // Reluctant quantifier
				head = new Branch(null, head, tail);
			}
			root = tail;
			return head;
		} else if (node instanceof Curly) {
			Curly curly = (Curly) node;
			if (curly.type == POSSESSIVE) {
				root = node;
				return node;
			}
			// Discover if the group is deterministic
			TreeInfo info = new TreeInfo();
			if (head.study(info)) { // Deterministic
				head = root = new GroupCurly(head.next, curly.cmin, curly.cmax, curly.type,
						((GroupTail) tail).localIndex, ((GroupTail) tail).groupIndex, capturingGroup);
				return head;
			} else { // Non-deterministic
				int temp = ((GroupHead) head).localIndex;
				Loop loop;
				if (curly.type == GREEDY)
					loop = new Loop(this.localCount, temp);
				else
					// Reluctant Curly
					loop = new LazyLoop(this.localCount, temp);
				Prolog prolog = new Prolog(loop);
				this.localCount += 1;
				loop.cmin = curly.cmin;
				loop.cmax = curly.cmax;
				loop.body = head;
				tail.next = loop;
				root = loop;
				return prolog; // Dual return
			}
		}
		throw error("Internal logic error");
	}

	/**
	 * Create group head and tail nodes using double return. If the group is
	 * created with anonymous true then it is a pure group and should not affect
	 * group counting.
	 */
	public Node createGroup(boolean anonymous) {
		int localIndex = localCount++;
		int groupIndex = 0;
		if (!anonymous)
			groupIndex = capturingGroupCount++;
		GroupHead head = new GroupHead(localIndex);
		root = new GroupTail(localIndex, groupIndex);
		if (!anonymous && groupIndex < 10)
			groupNodes[groupIndex] = head;
		return head;
	}

	@SuppressWarnings("fallthrough")
	/**
	 * Parses inlined match flags and set them appropriately.
	 */
	public void addFlag() {
		int ch = peek();
		for (;;) {
			switch (ch) {
			case 'i':
				flags |= CASE_INSENSITIVE;
				break;
			case 'm':
				flags |= MULTILINE;
				break;
			case 's':
				flags |= DOTALL;
				break;
			case 'd':
				flags |= UNIX_LINES;
				break;
			case 'u':
				flags |= UNICODE_CASE;
				break;
			case 'c':
				flags |= CANON_EQ;
				break;
			case 'x':
				flags |= COMMENTS;
				break;
			case 'U':
				flags |= (UNICODE_CHARACTER_CLASS | UNICODE_CASE);
				break;
			case '-': // subFlag then fall through
				ch = next();
				subFlag();
			default:
				return;
			}
			ch = next();
		}
	}

	@SuppressWarnings("fallthrough")
	/**
	 * Parses the second part of inlined match flags and turns off
	 * flags appropriately.
	 */
	public void subFlag() {
		int ch = peek();
		for (;;) {
			switch (ch) {
			case 'i':
				flags &= ~CASE_INSENSITIVE;
				break;
			case 'm':
				flags &= ~MULTILINE;
				break;
			case 's':
				flags &= ~DOTALL;
				break;
			case 'd':
				flags &= ~UNIX_LINES;
				break;
			case 'u':
				flags &= ~UNICODE_CASE;
				break;
			case 'c':
				flags &= ~CANON_EQ;
				break;
			case 'x':
				flags &= ~COMMENTS;
				break;
			case 'U':
				flags &= ~(UNICODE_CHARACTER_CLASS | UNICODE_CASE);
			default:
				return;
			}
			ch = next();
		}
	}

	static final int MAX_REPS = 0x7FFFFFFF;

	static final int GREEDY = 0;

	static final int LAZY = 1;

	static final int POSSESSIVE = 2;

	static final int INDEPENDENT = 3;

	/**
	 * Processes repetition. If the next character peeked is a quantifier then
	 * new nodes must be appended to handle the repetition. Prev could be a
	 * single or a group, so it could be a chain of nodes.
	 */
	public Node closure(Node prev) {
		int ch = peek();
		switch (ch) {
		case '?':
			ch = next();
			if (ch == '?') {
				next();
				return new Ques(prev, LAZY);
			} else if (ch == '+') {
				next();
				return new Ques(prev, POSSESSIVE);
			}
			return new Ques(prev, GREEDY);
		case '*':
			ch = next();
			if (ch == '?') {
				next();
				return new Curly(prev, 0, MAX_REPS, LAZY);
			} else if (ch == '+') {
				next();
				return new Curly(prev, 0, MAX_REPS, POSSESSIVE);
			}
			return new Curly(prev, 0, MAX_REPS, GREEDY);
		case '+':
			ch = next();
			if (ch == '?') {
				next();
				return new Curly(prev, 1, MAX_REPS, LAZY);
			} else if (ch == '+') {
				next();
				return new Curly(prev, 1, MAX_REPS, POSSESSIVE);
			}
			return new Curly(prev, 1, MAX_REPS, GREEDY);
		case '{':
			ch = temp[cursor + 1];
			if (ASCII.isDigit(ch)) {
				skip();
				int cmin = 0;
				do {
					cmin = cmin * 10 + (ch - '0');
				} while (ASCII.isDigit(ch = read()));
				int cmax = cmin;
				if (ch == ',') {
					ch = read();
					cmax = MAX_REPS;
					if (ch != '}') {
						cmax = 0;
						while (ASCII.isDigit(ch)) {
							cmax = cmax * 10 + (ch - '0');
							ch = read();
						}
					}
				}
				if (ch != '}')
					throw error("Unclosed counted closure");
				if (((cmin) | (cmax) | (cmax - cmin)) < 0)
					throw error("Illegal repetition range");
				Curly curly;
				ch = peek();
				if (ch == '?') {
					next();
					curly = new Curly(prev, cmin, cmax, LAZY);
				} else if (ch == '+') {
					next();
					curly = new Curly(prev, cmin, cmax, POSSESSIVE);
				} else {
					curly = new Curly(prev, cmin, cmax, GREEDY);
				}
				return curly;
			} else {
				throw error("Illegal repetition");
			}
		default:
			return prev;
		}
	}

	/**
	 * Utility method for parsing control escape sequences.
	 */
	public int c() {
		if (cursor < patternLength) {
			return read() ^ 64;
		}
		throw error("Illegal control escape sequence");
	}

	/**
	 * Utility method for parsing octal escape sequences.
	 */
	public int o() {
		int n = read();
		if (((n - '0') | ('7' - n)) >= 0) {
			int m = read();
			if (((m - '0') | ('7' - m)) >= 0) {
				int o = read();
				if ((((o - '0') | ('7' - o)) >= 0) && (((n - '0') | ('3' - n)) >= 0)) {
					return (n - '0') * 64 + (m - '0') * 8 + (o - '0');
				}
				unread();
				return (n - '0') * 8 + (m - '0');
			}
			unread();
			return (n - '0');
		}
		throw error("Illegal octal escape sequence");
	}

	/**
	 * Utility method for parsing hexadecimal escape sequences.
	 */
	public int x() {
		int n = read();
		if (ASCII.isHexDigit(n)) {
			int m = read();
			if (ASCII.isHexDigit(m)) {
				return ASCII.toDigit(n) * 16 + ASCII.toDigit(m);
			}
		} else if (n == '{' && ASCII.isHexDigit(peek())) {
			int ch = 0;
			while (ASCII.isHexDigit(n = read())) {
				ch = (ch << 4) + ASCII.toDigit(n);
				if (ch > Character.MAX_CODE_POINT)
					throw error("Hexadecimal codepoint is too big");
			}
			if (n != '}')
				throw error("Unclosed hexadecimal escape sequence");
			return ch;
		}
		throw error("Illegal hexadecimal escape sequence");
	}

	/**
	 * Utility method for parsing unicode escape sequences.
	 */
	public int cursor() {
		return cursor;
	}

	public void setcursor(int pos) {
		cursor = pos;
	}

	public int uxxxx() {
		int n = 0;
		for (int i = 0; i < 4; i++) {
			int ch = read();
			if (!ASCII.isHexDigit(ch)) {
				throw error("Illegal Unicode escape sequence");
			}
			n = n * 16 + ASCII.toDigit(ch);
		}
		return n;
	}

	public int u() {
		int n = uxxxx();
		if (Character.isHighSurrogate((char) n)) {
			int cur = cursor();
			if (read() == '\\' && read() == 'u') {
				int n2 = uxxxx();
				if (Character.isLowSurrogate((char) n2))
					return Character.toCodePoint((char) n, (char) n2);
			}
			setcursor(cur);
		}
		return n;
	}

	//
	// Utility methods for code point support
	//

	public static final int countChars(CharSequence seq, int index, int lengthInCodePoints) {
		// optimization
		if (lengthInCodePoints == 1 && !Character.isHighSurrogate(seq.charAt(index))) {
			assert (index >= 0 && index < seq.length());
			return 1;
		}
		int length = seq.length();
		int x = index;
		if (lengthInCodePoints >= 0) {
			assert (index >= 0 && index < length);
			for (int i = 0; x < length && i < lengthInCodePoints; i++) {
				if (Character.isHighSurrogate(seq.charAt(x++))) {
					if (x < length && Character.isLowSurrogate(seq.charAt(x))) {
						x++;
					}
				}
			}
			return x - index;
		}

		assert (index >= 0 && index <= length);
		if (index == 0) {
			return 0;
		}
		int len = -lengthInCodePoints;
		for (int i = 0; x > 0 && i < len; i++) {
			if (Character.isLowSurrogate(seq.charAt(--x))) {
				if (x > 0 && Character.isHighSurrogate(seq.charAt(x - 1))) {
					x--;
				}
			}
		}
		return index - x;
	}

	public static final int countCodePoints(CharSequence seq) {
		int length = seq.length();
		int n = 0;
		for (int i = 0; i < length;) {
			n++;
			if (Character.isHighSurrogate(seq.charAt(i++))) {
				if (i < length && Character.isLowSurrogate(seq.charAt(i))) {
					i++;
				}
			}
		}
		return n;
	}

	/**
	 * Creates a bit vector for matching Latin-1 values. A normal BitClass never
	 * matches values above Latin-1, and a complemented BitClass always matches
	 * values above Latin-1.
	 */
	public static final class BitClass extends BmpCharProperty {
		public final boolean[] bits;

		public BitClass() {
			bits = new boolean[256];
		}

		public BitClass add(int c, int flags) {
			assert c >= 0 && c <= 255;
			if ((flags & CASE_INSENSITIVE) != 0) {
				if (ASCII.isAscii(c)) {
					bits[ASCII.toUpper(c)] = true;
					bits[ASCII.toLower(c)] = true;
				} else if ((flags & UNICODE_CASE) != 0) {
					bits[Character.toLowerCase(c)] = true;
					bits[Character.toUpperCase(c)] = true;
				}
			}
			bits[c] = true;
			return this;
		}

		public boolean isSatisfiedBy(int ch) {
			return ch < 256 && bits[ch];
		}
	}

	/**
	 * Returns a suitably optimized, single character LargeFileMatcher.
	 */
	public CharProperty newSingle(final int ch) {
		if (has(CASE_INSENSITIVE)) {
			int lower, upper;
			if (has(UNICODE_CASE)) {
				upper = Character.toUpperCase(ch);
				lower = Character.toLowerCase(upper);
				if (upper != lower)
					return new SingleU(lower);
			} else if (ASCII.isAscii(ch)) {
				lower = ASCII.toLower(ch);
				upper = ASCII.toUpper(ch);
				if (lower != upper)
					return new SingleI(lower, upper);
			}
		}
		if (isSupplementary(ch))
			return new SingleS(ch); // Match a given Unicode character
		return new Single(ch); // Match a given BMP character
	}

	/**
	 * Utility method for creating a string slice LargeFileMatcher.
	 */
	public Node newSlice(int[] buf, int count, boolean hasSupplementary) {
		int[] tmp = new int[count];
		if (has(CASE_INSENSITIVE)) {
			if (has(UNICODE_CASE)) {
				for (int i = 0; i < count; i++) {
					tmp[i] = Character.toLowerCase(Character.toUpperCase(buf[i]));
				}
				return hasSupplementary ? new SliceUS(tmp) : new SliceU(tmp);
			}
			for (int i = 0; i < count; i++) {
				tmp[i] = ASCII.toLower(buf[i]);
			}
			return hasSupplementary ? new SliceIS(tmp) : new SliceI(tmp);
		}
		for (int i = 0; i < count; i++) {
			tmp[i] = buf[i];
		}
		return hasSupplementary ? new SliceS(tmp) : new Slice(tmp);
	}

	/**
	 * The following classes are the building components of the object tree that
	 * represents a compiled regular expression. The object tree is made of
	 * individual elements that handle constructs in the LargeFilePattern. Each
	 * type of object knows how to match its equivalent construct with the
	 * match() method.
	 */

	/**
	 * Base class for all node classes. Subclasses should override the match()
	 * method as appropriate. This class is an accepting node, so its match()
	 * always returns true.
	 */
	static class Node extends Object {
		Node next;

		Node() {
			next = LargeFilePattern.accept;
		}

		/**
		 * This method implements the classic accept node.
		 */
		boolean match(LargeFileMatcher matcher, long from, CharSequence seq) {
			matcher.last = from;
			matcher.groups[0] = matcher.first;
			matcher.groups[1] = matcher.last;
			return true;
		}

		/**
		 * This method is good for all zero length assertions.
		 */
		boolean study(TreeInfo info) {
			if (next != null) {
				return next.study(info);
			} else {
				return info.deterministic;
			}
		}
	}

	public static class LastNode extends Node {
		/**
		 * This method implements the classic accept node with the addition of a
		 * check to see if the match occurred using all of the input.
		 */
		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			if (matcher.acceptMode == LargeFileMatcher.ENDANCHOR && i != matcher.to)
				return false;
			matcher.last = i;
			matcher.groups[0] = matcher.first;
			matcher.groups[1] = matcher.last;
			return true;
		}
	}

	/**
	 * Used for REs that can start anywhere within the input string. This
	 * basically tries to match repeatedly at each spot in the input string,
	 * moving forward after each try. An anchored search or a BnM will bypass
	 * this node completely.
	 */
	public static class Start extends Node {
		int minLength;

		public Start(Node node) {
			this.next = node;
			TreeInfo info = new TreeInfo();
			next.study(info);
			minLength = info.minLength;
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			if (i > matcher.to - minLength) {
				matcher.hitEnd = true;
				return false;
			}
			long guard = matcher.to - minLength;
			for (; i <= guard; i++) {
				if (next.match(matcher, i, seq)) {
					matcher.first = i;
					matcher.groups[0] = matcher.first;
					matcher.groups[1] = matcher.last;
					return true;
				}
			}
			matcher.hitEnd = true;
			return false;
		}

		public boolean study(TreeInfo info) {
			next.study(info);
			info.maxValid = false;
			info.deterministic = false;
			return false;
		}
	}

	/*
	 * StartS supports supplementary characters, including unpaired surrogates.
	 */
	public static final class StartS extends Start {
		public StartS(Node node) {
			super(node);
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			if (i > matcher.to - minLength) {
				matcher.hitEnd = true;
				return false;
			}
			long guard = matcher.to - minLength;
			while (i <= guard) {
				// if ((ret = next.match(matcher, i, seq)) || i ==
				// guard)
				if (next.match(matcher, i, seq)) {
					matcher.first = i;
					matcher.groups[0] = matcher.first;
					matcher.groups[1] = matcher.last;
					return true;
				}
				if (i == guard)
					break;
				// Optimization to move to the next character. This is
				// faster than countChars(seq, i, 1).
				if (Character.isHighSurrogate(seq.charAt(i++))) {
					if (i < seq.length() && Character.isLowSurrogate(seq.charAt(i))) {
						i++;
					}
				}
			}
			matcher.hitEnd = true;
			return false;
		}
	}

	/**
	 * Node to anchor at the beginning of input. This object implements the
	 * match for a \A sequence, and the caret anchor will use this if not in
	 * multiline mode.
	 */
	static final class Begin extends Node {
		boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			long fromIndex = (matcher.anchoringBounds) ? matcher.from : 0;
			if (i == fromIndex && next.match(matcher, i, seq)) {
				matcher.first = i;
				matcher.groups[0] = i;
				matcher.groups[1] = matcher.last;
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Node to anchor at the end of input. This is the absolute end, so this
	 * should not match at the last newline before the end as $ will.
	 */
	public static final class End extends Node {
		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			long endIndex = (matcher.anchoringBounds) ? matcher.to : matcher
					.getTextLength();
			if (i == endIndex) {
				matcher.hitEnd = true;
				return next.match(matcher, i, seq);
			}
			return false;
		}
	}

	/**
	 * Node to anchor at the beginning of a line. This is essentially the object
	 * to match for the multiline ^.
	 */
	public static final class Caret extends Node {
		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			long startIndex = matcher.from;
			long endIndex = matcher.to;
			if (!matcher.anchoringBounds) {
				startIndex = 0;
				endIndex = matcher.getTextLength();
			}
			// Perl does not match ^ at end of input even after newline
			if (i == endIndex) {
				matcher.hitEnd = true;
				return false;
			}
			if (i > startIndex) {
				char ch = seq.charAt(i - 1);
				if (ch != '\n' && ch != '\r' && (ch | 1) != '\u2029' && ch != '\u0085') {
					return false;
				}
				// Should treat /r/n as one newline
				if (ch == '\r' && seq.charAt(i) == '\n')
					return false;
			}
			return next.match(matcher, i, seq);
		}
	}

	/**
	 * Node to anchor at the beginning of a line when in unixdot mode.
	 */
	public static final class UnixCaret extends Node {
		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			long startIndex = matcher.from;
			long endIndex = matcher.to;
			if (!matcher.anchoringBounds) {
				startIndex = 0;
				endIndex = matcher.getTextLength();
			}
			// Perl does not match ^ at end of input even after newline
			if (i == endIndex) {
				matcher.hitEnd = true;
				return false;
			}
			if (i > startIndex) {
				char ch = seq.charAt(i - 1);
				if (ch != '\n') {
					return false;
				}
			}
			return next.match(matcher, i, seq);
		}
	}

	/**
	 * Node to match the location where the last match ended. This is used for
	 * the \G construct.
	 */
	public static final class LastMatch extends Node {
		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			if (i != matcher.oldLast)
				return false;
			return next.match(matcher, i, seq);
		}
	}

	/**
	 * Node to anchor at the end of a line or the end of input based on the
	 * multiline mode.
	 *
	 * When not in multiline mode, the $ can only match at the very end of the
	 * input, unless the input ends in a line terminator in which it matches
	 * right before the last line terminator.
	 *
	 * Note that \r\n is considered an atomic line terminator.
	 *
	 * Like ^ the $ operator matches at a position, it does not match the line
	 * terminators themselves.
	 */
	public static final class Dollar extends Node {
		public boolean multiline;

		public Dollar(boolean mul) {
			multiline = mul;
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			long endIndex = (matcher.anchoringBounds) ? matcher.to : matcher
					.getTextLength();
			if (!multiline) {
				if (i < endIndex - 2)
					return false;
				if (i == endIndex - 2) {
					char ch = seq.charAt(i);
					if (ch != '\r')
						return false;
					ch = seq.charAt(i + 1);
					if (ch != '\n')
						return false;
				}
			}
			// Matches before any line terminator; also matches at the
			// end of input
			// Before line terminator:
			// If multiline, we match here no matter what
			// If not multiline, fall through so that the end
			// is marked as hit; this must be a /r/n or a /n
			// at the very end so the end was hit; more input
			// could make this not match here
			if (i < endIndex) {
				char ch = seq.charAt(i);
				if (ch == '\n') {
					// No match between \r\n
					if (i > 0 && seq.charAt(i - 1) == '\r')
						return false;
					if (multiline)
						return next.match(matcher, i, seq);
				} else if (ch == '\r' || ch == '\u0085' || (ch | 1) == '\u2029') {
					if (multiline)
						return next.match(matcher, i, seq);
				} else { // No line terminator, no match
					return false;
				}
			}
			// Matched at current end so hit end
			matcher.hitEnd = true;
			// If a $ matches because of end of input, then more input
			// could cause it to fail!
			matcher.requireEnd = true;
			return next.match(matcher, i, seq);
		}
		@Override
		public boolean study(TreeInfo info) {
			next.study(info);
			return info.deterministic;
		}
	}

	/**
	 * Node to anchor at the end of a line or the end of input based on the
	 * multiline mode when in unix lines mode.
	 */
	public static final class UnixDollar extends Node {
		public boolean multiline;

		public UnixDollar(boolean mul) {
			multiline = mul;
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			long endIndex = (matcher.anchoringBounds) ? matcher.to : matcher
					.getTextLength();
			if (i < endIndex) {
				char ch = seq.charAt(i);
				if (ch == '\n') {
					// If not multiline, then only possible to
					// match at very end or one before end
					if (multiline == false && i != endIndex - 1)
						return false;
					// If multiline return next.match without setting
					// matcher.hitEnd
					if (multiline)
						return next.match(matcher, i, seq);
				} else {
					return false;
				}
			}
			// Matching because at the end or 1 before the end;
			// more input could change this so set hitEnd
			matcher.hitEnd = true;
			// If a $ matches because of end of input, then more input
			// could cause it to fail!
			matcher.requireEnd = true;
			return next.match(matcher, i, seq);
		}
		@Override
		public boolean study(TreeInfo info) {
			next.study(info);
			return info.deterministic;
		}
	}

	/**
	 * Node class that matches a Unicode line ending '\R'
	 */
	static final class LineEnding extends Node {
		boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			// (u+000Du+000A|[u+000Au+000Bu+000Cu+000Du+0085u+2028u+2029])
			if (i < matcher.to) {
				int ch = seq.charAt(i);
				if (ch == 0x0A || ch == 0x0B || ch == 0x0C || ch == 0x85 || ch == 0x2028
						|| ch == 0x2029)
					return next.match(matcher, i + 1, seq);
				if (ch == 0x0D) {
					i++;
					if (i < matcher.to && seq.charAt(i) == 0x0A)
						i++;
					return next.match(matcher, i, seq);
				}
			} else {
				matcher.hitEnd = true;
			}
			return false;
		}

		boolean study(TreeInfo info) {
			info.minLength++;
			info.maxLength += 2;
			return next.study(info);
		}
	}

	/**
	 * Abstract node class to match one character satisfying some boolean
	 * property.
	 */
	public static abstract class CharProperty extends Node {
		public abstract boolean isSatisfiedBy(int ch);

		public CharProperty complement() {
			return new CharProperty() {
				public boolean isSatisfiedBy(int ch) {
					return !CharProperty.this.isSatisfiedBy(ch);
				}
			};
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			if (i < matcher.to) {
				int ch = Character.codePointAt(seq, i);
				return isSatisfiedBy(ch)
						&& next.match(matcher, i + Character.charCount(ch), seq);
			} else {
				matcher.hitEnd = true;
				return false;
			}
		}

		public boolean study(TreeInfo info) {
			info.minLength++;
			info.maxLength++;
			return next.study(info);
		}
	}

	/**
	 * Optimized version of CharProperty that works only for properties never
	 * satisfied by Supplementary characters.
	 */
	public static abstract class BmpCharProperty extends CharProperty {
		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			if (i < matcher.to) {
				return isSatisfiedBy(seq.charAt(i)) && next.match(matcher, i + 1, seq);
			} else {
				matcher.hitEnd = true;
				return false;
			}
		}
	}

	/**
	 * Node class that matches a Supplementary Unicode character
	 */
	public static final class SingleS extends CharProperty {
		public final int c;

		public SingleS(int c) {
			this.c = c;
		}

		public boolean isSatisfiedBy(int ch) {
			return ch == c;
		}
	}

	/**
	 * Optimization -- matches a given BMP character
	 */
	public static final class Single extends BmpCharProperty {
		public final int c;

		public Single(int c) {
			this.c = c;
		}

		public boolean isSatisfiedBy(int ch) {
			return ch == c;
		}
	}

	/**
	 * Case insensitive matches a given BMP character
	 */
	public static final class SingleI extends BmpCharProperty {
		public final int lower;
		public final int upper;

		public SingleI(int lower, int upper) {
			this.lower = lower;
			this.upper = upper;
		}

		public boolean isSatisfiedBy(int ch) {
			return ch == lower || ch == upper;
		}
	}

	/**
	 * Unicode case insensitive matches a given Unicode character
	 */
	public static final class SingleU extends CharProperty {
		public final int lower;

		public SingleU(int lower) {
			this.lower = lower;
		}

		public boolean isSatisfiedBy(int ch) {
			return lower == ch || lower == Character.toLowerCase(Character.toUpperCase(ch));
		}
	}

	/**
	 * Node class that matches a Unicode block.
	 */
	public static final class Block extends CharProperty {
		public final Character.UnicodeBlock block;

		public Block(Character.UnicodeBlock block) {
			this.block = block;
		}

		public boolean isSatisfiedBy(int ch) {
			return block == Character.UnicodeBlock.of(ch);
		}
	}

	/**
	 * Node class that matches a Unicode script
	 */
	public static final class Script extends CharProperty {
		public final Character.UnicodeScript script;

		public Script(Character.UnicodeScript script) {
			this.script = script;
		}

		public boolean isSatisfiedBy(int ch) {
			return script == Character.UnicodeScript.of(ch);
		}
	}

	/**
	 * Node class that matches a Unicode category.
	 */
	public static final class Category extends CharProperty {
		public final int typeMask;

		public Category(int typeMask) {
			this.typeMask = typeMask;
		}

		public boolean isSatisfiedBy(int ch) {
			return (typeMask & (1 << Character.getType(ch))) != 0;
		}
	}

	/**
	 * Node class that matches a Unicode "type"
	 */
	public static final class Utype extends CharProperty {
		public final Function<Integer, Boolean> fn;
		public Utype(Function<Integer, Boolean> fn) {
			this.fn = fn;
		}
		public Utype(UnicodeProp up) {
			this.fn = up::is;
		}
		public boolean isSatisfiedBy(int ch) {
			return fn.apply(ch);
		}
	}

	/**
	 * Node class that matches a POSIX type.
	 */
	public static final class Ctype extends BmpCharProperty {
		public final int ctype;

		public Ctype(int ctype) {
			this.ctype = ctype;
		}

		public boolean isSatisfiedBy(int ch) {
			return ch < 128 && ASCII.isType(ch, ctype);
		}
	}

	/**
	 * Node class that matches a Perl vertical whitespace
	 */
	public static final class VertWS extends BmpCharProperty {
		public boolean isSatisfiedBy(int cp) {
			return (cp >= 0x0A && cp <= 0x0D) || cp == 0x85 || cp == 0x2028 || cp == 0x2029;
		}
	}

	/**
	 * Node class that matches a Perl horizontal whitespace
	 */
	public static final class HorizWS extends BmpCharProperty {
		public boolean isSatisfiedBy(int cp) {
			return cp == 0x09 || cp == 0x20 || cp == 0xa0 || cp == 0x1680 || cp == 0x180e
					|| cp >= 0x2000 && cp <= 0x200a || cp == 0x202f || cp == 0x205f || cp == 0x3000;
		}
	}

	/**
	 * Base class for all Slice nodes
	 */
	public static class SliceNode extends Node {
		public int[] buffer;

		public SliceNode(int[] buf) {
			buffer = buf;
		}

		public boolean study(TreeInfo info) {
			info.minLength += buffer.length;
			info.maxLength += buffer.length;
			return next.study(info);
		}
	}

	/**
	 * Node class for a case sensitive/BMP-only sequence of literal characters.
	 */
	public static final class Slice extends SliceNode {
		public Slice(int[] buf) {
			super(buf);
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			int[] buf = buffer;
			int len = buf.length;
			for (int j = 0; j < len; j++) {
				if ((i + j) >= matcher.to) {
					matcher.hitEnd = true;
					return false;
				}
				if (buf[j] != seq.charAt(i + j))
					return false;
			}
			return next.match(matcher, i + len, seq);
		}
	}

	/**
	 * Node class for a case_insensitive/BMP-only sequence of literal
	 * characters.
	 */
	public static class SliceI extends SliceNode {
		public SliceI(int[] buf) {
			super(buf);
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			int[] buf = buffer;
			int len = buf.length;
			for (int j = 0; j < len; j++) {
				if ((i + j) >= matcher.to) {
					matcher.hitEnd = true;
					return false;
				}
				int c = seq.charAt(i + j);
				if (buf[j] != c && buf[j] != ASCII.toLower(c))
					return false;
			}
			return next.match(matcher, i + len, seq);
		}
	}

	/**
	 * Node class for a unicode_case_insensitive/BMP-only sequence of literal
	 * characters. Uses unicode case folding.
	 */
	public static final class SliceU extends SliceNode {
		public SliceU(int[] buf) {
			super(buf);
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			int[] buf = buffer;
			int len = buf.length;
			for (int j = 0; j < len; j++) {
				if ((i + j) >= matcher.to) {
					matcher.hitEnd = true;
					return false;
				}
				int c = seq.charAt(i + j);
				if (buf[j] != c && buf[j] != Character.toLowerCase(Character.toUpperCase(c)))
					return false;
			}
			return next.match(matcher, i + len, seq);
		}
	}

	/**
	 * Node class for a case sensitive sequence of literal characters including
	 * supplementary characters.
	 */
	public static final class SliceS extends SliceNode {
		public SliceS(int[] buf) {
			super(buf);
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			int[] buf = buffer;
			int x = i;
			for (int j = 0; j < buf.length; j++) {
				if (x >= matcher.to) {
					matcher.hitEnd = true;
					return false;
				}
				int c = Character.codePointAt(seq, x);
				if (buf[j] != c)
					return false;
				x += Character.charCount(c);
				if (x > matcher.to) {
					matcher.hitEnd = true;
					return false;
				}
			}
			return next.match(matcher, x, seq);
		}
	}

	/**
	 * Node class for a case insensitive sequence of literal characters
	 * including supplementary characters.
	 */
	public static class SliceIS extends SliceNode {
		public SliceIS(int[] buf) {
			super(buf);
		}

		public int toLower(int c) {
			return ASCII.toLower(c);
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			int[] buf = buffer;
			int x = i;
			for (int j = 0; j < buf.length; j++) {
				if (x >= matcher.to) {
					matcher.hitEnd = true;
					return false;
				}
				int c = Character.codePointAt(seq, x);
				if (buf[j] != c && buf[j] != toLower(c))
					return false;
				x += Character.charCount(c);
				if (x > matcher.to) {
					matcher.hitEnd = true;
					return false;
				}
			}
			return next.match(matcher, x, seq);
		}
	}

	/**
	 * Node class for a case insensitive sequence of literal characters. Uses
	 * unicode case folding.
	 */
	public static final class SliceUS extends SliceIS {
		public SliceUS(int[] buf) {
			super(buf);
		}

		public int toLower(int c) {
			return Character.toLowerCase(Character.toUpperCase(c));
		}
	}

	public static boolean inRange(int lower, int ch, int upper) {
		return lower <= ch && ch <= upper;
	}

	/**
	 * Returns node for matching characters within an explicit value range.
	 */
	public static CharProperty rangeFor(final int lower, final int upper) {
		return new CharProperty() {
			public boolean isSatisfiedBy(int ch) {
				return inRange(lower, ch, upper);
			}
		};
	}

	/**
	 * Returns node for matching characters within an explicit value range in a
	 * case insensitive manner.
	 */
	public CharProperty caseInsensitiveRangeFor(final int lower, final int upper) {
		if (has(UNICODE_CASE))
			return new CharProperty() {
			public boolean isSatisfiedBy(int ch) {
					if (inRange(lower, ch, upper))
						return true;
					int up = Character.toUpperCase(ch);
					return inRange(lower, up, upper)
							|| inRange(lower, Character.toLowerCase(up), upper);
				}
			};
		return new CharProperty() {
			public boolean isSatisfiedBy(int ch) {
				return inRange(lower, ch, upper)
						|| ASCII.isAscii(ch)
						&& (inRange(lower, ASCII.toUpper(ch), upper) || inRange(lower,
								ASCII.toLower(ch), upper));
			}
		};
	}

	/**
	 * Implements the Unicode category ALL and the dot metacharacter when in
	 * dotall mode.
	 */
	public static final class All extends CharProperty {
		public boolean isSatisfiedBy(int ch) {
			return true;
		}
	}

	/**
	 * Node class for the dot metacharacter when dotall is not enabled.
	 */
	public static final class Dot extends CharProperty {
		public boolean isSatisfiedBy(int ch) {
			return (ch != '\n' && ch != '\r' && (ch | 1) != '\u2029' && ch != '\u0085');
		}
	}

	/**
	 * Node class for the dot metacharacter when dotall is not enabled but
	 * UNIX_LINES is enabled.
	 */
	public static final class UnixDot extends CharProperty {
		public boolean isSatisfiedBy(int ch) {
			return ch != '\n';
		}
	}

	/**
	 * The 0 or 1 quantifier. This one class implements all three types.
	 */
	public static final class Ques extends Node {
		public Node atom;
		public int type;

		public Ques(Node node, int type) {
			this.atom = node;
			this.type = type;
		}

		public boolean match(LargeFileMatcher matcher, long i, CharSequence seq) {
			switch (type) {
			case GREEDY:
				return (atom.match(matcher, i, seq) && next.match(matcher,
						matcher.last, seq)) || next.match(matcher, i, seq);
			case LAZY:
				return next.match(matcher, i, seq)
						|| (atom.match(matcher, i, seq) && next.match(matcher,
								matcher.last, seq));
			case POSSESSIVE:
				if (atom.match(matcher, i, seq))
					i = matcher.last;
				return next.match(matcher, i, seq);
			default:
				return atom.match(matcher, i, seq)
						&& next.match(matcher, matcher.last, seq);
			}
		}
		@Override
		public boolean study(TreeInfo info) {
			if (type != INDEPENDENT) {
				int minL = info.minLength;
				atom.study(info);
				info.minLength = minL;
				info.deterministic = false;
				return next.study(info);
			} else {
				atom.study(info);
				return next.study(info);
			}
		}
	}

	/**
	 * Handles the curly-brace style repetition with a specified minimum and
	 * maximum occurrences. The * quantifier is handled as a special case. This
	 * class handles the three types.
	 */
	public static final class Curly extends Node {
		public Node atom;
		public int type;
		public int cmin;
		public int cmax;

		public Curly(Node node, int cmin, int cmax, int type) {
			this.atom = node;
			this.type = type;
			this.cmin = cmin;
			this.cmax = cmax;
		}

		public boolean match(LargeFileMatcher matcher, long i, CharSequence seq) {
			int j;
			for (j = 0; j < cmin; j++) {
				if (atom.match(matcher, i, seq)) {
					i = matcher.last;
					continue;
				}
				return false;
			}
			if (type == GREEDY)
				return match0(matcher, i, j, seq);
			else if (type == LAZY)
				return match1(matcher, i, j, seq);
			else
				return match2(matcher, i, j, seq);
		}

		// Greedy match.
		// i is the index to start matching at
		// j is the number of atoms that have matched
		public boolean match0(LargeFileMatcher matcher, long i, long j, CharSequence seq) {
			if (j >= cmax) {
				// We have matched the maximum... continue with the rest of
				// the regular expression
				return next.match(matcher, i, seq);
			}
			long backLimit = j;
			while (atom.match(matcher, i, seq)) {
				// k is the length of this match
				long k = matcher.last - i;
				if (k == 0) // Zero length match
					break;
				// Move up index and number matched
				i = matcher.last;
				j++;
				// We are greedy so match as many as we can
				while (j < cmax) {
					if (!atom.match(matcher, i, seq))
						break;
					if (i + k != matcher.last) {
						if (match0(matcher, matcher.last, j + 1, seq))
							return true;
						break;
					}
					i += k;
					j++;
				}
				// Handle backing off if match fails
				while (j >= backLimit) {
					if (next.match(matcher, i, seq))
						return true;
					i -= k;
					j--;
				}
				return false;
			}
			return next.match(matcher, i, seq);
		}

		// Reluctant match. At this point, the minimum has been satisfied.
		// i is the index to start matching at
		// j is the number of atoms that have matched
		public boolean match1(LargeFileMatcher matcher, long i, int j, CharSequence seq) {
			for (;;) {
				// Try finishing match without consuming any more
				if (next.match(matcher, i, seq))
					return true;
				// At the maximum, no match found
				if (j >= cmax)
					return false;
				// Okay, must try one more atom
				if (!atom.match(matcher, i, seq))
					return false;
				// If we haven't moved forward then must break out
				if (i == matcher.last)
					return false;
				// Move up index and number matched
				i = matcher.last;
				j++;
			}
		}

		public boolean match2(LargeFileMatcher matcher, long i, int j, CharSequence seq) {
			for (; j < cmax; j++) {
				if (!atom.match(matcher, i, seq))
					break;
				if (i == matcher.last)
					break;
				i = matcher.last;
			}
			return next.match(matcher, i, seq);
		}

		public boolean study(TreeInfo info) {
			// Save original info
			int minL = info.minLength;
			int maxL = info.maxLength;
			boolean maxV = info.maxValid;
			boolean detm = info.deterministic;
			info.reset();

			atom.study(info);

			int temp = info.minLength * cmin + minL;
			if (temp < minL) {
				temp = 0xFFFFFFF; // arbitrary large number
			}
			info.minLength = temp;

			if (maxV & info.maxValid) {
				temp = info.maxLength * cmax + maxL;
				info.maxLength = temp;
				if (temp < maxL) {
					info.maxValid = false;
				}
			} else {
				info.maxValid = false;
			}

			if (info.deterministic && cmin == cmax)
				info.deterministic = detm;
			else
				info.deterministic = false;
			return next.study(info);
		}
	}

	/**
	 * Handles the curly-brace style repetition with a specified minimum and
	 * maximum occurrences in deterministic cases. This is an iterative
	 * optimization over the Prolog and Loop system which would handle this in a
	 * recursive way. The * quantifier is handled as a special case. If capture
	 * is true then this class saves group settings and ensures that groups are
	 * unset when backing off of a group match.
	 */
	public static final class GroupCurly extends Node {
		public Node atom;
		public int type;
		public int cmin;
		public int cmax;
		public int localIndex;
		public int groupIndex;
		public boolean capture;

		public GroupCurly(Node node, int cmin, int cmax, int type, int local, int group, boolean capture) {
			this.atom = node;
			this.type = type;
			this.cmin = cmin;
			this.cmax = cmax;
			this.localIndex = local;
			this.groupIndex = group;
			this.capture = capture;
		}
		@Override
		public boolean match(LargeFileMatcher matcher, long i, CharSequence seq) {
			long[] groups = matcher.groups;
			int[] locals = matcher.locals;
			int save0 = locals[localIndex];
			long save1 = 0;
			long save2 = 0;

			if (capture) {
				save1 = groups[groupIndex];
				save2 = groups[groupIndex + 1];
			}

			// Notify GroupTail there is no need to setup group info
			// because it will be set here
			locals[localIndex] = -1;

			boolean ret = true;
			for (int j = 0; j < cmin; j++) {
				if (atom.match(matcher, i, seq)) {
					if (capture) {
						groups[groupIndex] = i;
						groups[groupIndex + 1] = matcher.last;
					}
					i = matcher.last;
				} else {
					ret = false;
					break;
				}
			}
			if (ret) {
				if (type == GREEDY) {
					ret = match0(matcher, i, cmin, seq);
				} else if (type == LAZY) {
					ret = match1(matcher, i, cmin, seq);
				} else {
					ret = match2(matcher, i, cmin, seq);
				}
			}
			if (!ret) {
				locals[localIndex] = save0;
				if (capture) {
					groups[groupIndex] = save1;
					groups[groupIndex + 1] = save2;
				}
			}
			return ret;
		}

		// Aggressive group match
		public boolean match0(LargeFileMatcher matcher, long i, long j, CharSequence seq) {
			// don't back off passing the starting "j"
			long min = j;
			long[] groups = matcher.groups;
			long save0 = 0;
			long save1 = 0;
			if (capture) {
				save0 = groups[groupIndex];
				save1 = groups[groupIndex + 1];
			}
			for (;;) {
				if (j >= cmax)
					break;
				if (!atom.match(matcher, i, seq))
					break;
				long k = matcher.last - i;
				if (k <= 0) {
					if (capture) {
						groups[groupIndex] = i;
						groups[groupIndex + 1] = i + k;
					}
					i = i + k;
					break;
				}
				for (;;) {
					if (capture) {
						groups[groupIndex] = i;
						groups[groupIndex + 1] = i + k;
					}
					i = i + k;
					if (++j >= cmax)
						break;
					if (!atom.match(matcher, i, seq))
						break;
					if (i + k != matcher.last) {
						if (match0(matcher, i, j, seq))
							return true;
						break;
					}
				}
				while (j > min) {
					if (next.match(matcher, i, seq)) {
						if (capture) {
							groups[groupIndex + 1] = i;
							groups[groupIndex] = i - k;
						}
						return true;
					}
					// backing off
					i = i - k;
					if (capture) {
						groups[groupIndex + 1] = i;
						groups[groupIndex] = i - k;
					}
					j--;

				}
				break;
			}
			if (capture) {
				groups[groupIndex] = save0;
				groups[groupIndex + 1] = save1;
			}
			return next.match(matcher, i, seq);
		}

		// Reluctant matching
		public boolean match1(LargeFileMatcher matcher, long i, long j, CharSequence seq) {
			for (;;) {
				if (next.match(matcher, i, seq))
					return true;
				if (j >= cmax)
					return false;
				if (!atom.match(matcher, i, seq))
					return false;
				if (i == matcher.last)
					return false;
				if (capture) {
					matcher.groups[groupIndex] = i;
					matcher.groups[groupIndex + 1] = matcher.last;
				}
				i = matcher.last;
				j++;
			}
		}

		// Possessive matching
		public boolean match2(LargeFileMatcher matcher, long i, long j, CharSequence seq) {
			for (; j < cmax; j++) {
				if (!atom.match(matcher, i, seq)) {
					break;
				}
				if (capture) {
					matcher.groups[groupIndex] = i;
					matcher.groups[groupIndex + 1] = matcher.last;
				}
				if (i == matcher.last) {
					break;
				}
				i = matcher.last;
			}
			return next.match(matcher, i, seq);
		}
		@Override
		public boolean study(TreeInfo info) {
			// Save original info
			int minL = info.minLength;
			int maxL = info.maxLength;
			boolean maxV = info.maxValid;
			boolean detm = info.deterministic;
			info.reset();

			atom.study(info);

			int temp = info.minLength * cmin + minL;
			if (temp < minL) {
				temp = 0xFFFFFFF; // Arbitrary large number
			}
			info.minLength = temp;

			if (maxV & info.maxValid) {
				temp = info.maxLength * cmax + maxL;
				info.maxLength = temp;
				if (temp < maxL) {
					info.maxValid = false;
				}
			} else {
				info.maxValid = false;
			}

			if (info.deterministic && cmin == cmax) {
				info.deterministic = detm;
			} else {
				info.deterministic = false;
			}
			return next.study(info);
		}
	}

	/**
	 * A Guard node at the end of each atom node in a Branch. It serves the
	 * purpose of chaining the "match" operation to "next" but not the "study",
	 * so we can collect the TreeInfo of each atom node without including the
	 * TreeInfo of the "next".
	 */
	public static final class BranchConn extends Node {
		public BranchConn() {
		};

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			return next.match(matcher, i, seq);
		}

		public boolean study(TreeInfo info) {
			return info.deterministic;
		}
	}

	/**
	 * Handles the branching of alternations. Note this is also used for the ?
	 * quantifier to branch between the case where it matches once and where it
	 * does not occur.
	 */
	public static final class Branch extends Node {
		public Node[] atoms = new Node[2];
		public int size = 2;
		public Node conn;

		public Branch(Node first, Node second, Node branchConn) {
			conn = branchConn;
			atoms[0] = first;
			atoms[1] = second;
		}

		public void add(Node node) {
			if (size >= atoms.length) {
				Node[] tmp = new Node[atoms.length * 2];
				System.arraycopy(atoms, 0, tmp, 0, atoms.length);
				atoms = tmp;
			}
			atoms[size++] = node;
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			for (int n = 0; n < size; n++) {
				if (atoms[n] == null) {
					if (conn.next.match(matcher, i, seq))
						return true;
				} else if (atoms[n].match(matcher, i, seq)) {
					return true;
				}
			}
			return false;
		}

		public boolean study(TreeInfo info) {
			int minL = info.minLength;
			int maxL = info.maxLength;
			boolean maxV = info.maxValid;

			int minL2 = Integer.MAX_VALUE; // arbitrary large enough num
			int maxL2 = -1;
			for (int n = 0; n < size; n++) {
				info.reset();
				if (atoms[n] != null)
					atoms[n].study(info);
				minL2 = Math.min(minL2, info.minLength);
				maxL2 = Math.max(maxL2, info.maxLength);
				maxV = (maxV & info.maxValid);
			}

			minL += minL2;
			maxL += maxL2;

			info.reset();
			conn.next.study(info);

			info.minLength += minL;
			info.maxLength += maxL;
			info.maxValid &= maxV;
			info.deterministic = false;
			return false;
		}
	}

	/**
	 * The GroupHead saves the location where the group begins in the locals and
	 * restores them when the match is done.
	 *
	 * The matchRef is used when a reference to this group is accessed later in
	 * the expression. The locals will have a negative value in them to indicate
	 * that we do not want to unset the group if the reference doesn't match.
	 */
	static final class GroupHead extends Node {
		int localIndex;

		GroupHead(int localCount) {
			localIndex = localCount;
		}

		boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			int save = matcher.locals[localIndex];
			matcher.locals[localIndex] = i;
			boolean ret = next.match(matcher, i, seq);
			matcher.locals[localIndex] = save;
			return ret;
		}

		boolean matchRef(LargeFileMatcher matcher, int i, CharSequence seq) {
			int save = matcher.locals[localIndex];
			matcher.locals[localIndex] = ~i; // HACK
			boolean ret = next.match(matcher, i, seq);
			matcher.locals[localIndex] = save;
			return ret;
		}
	}

	/**
	 * Recursive reference to a group in the regular expression. It calls
	 * matchRef because if the reference fails to match we would not unset the
	 * group.
	 */
	static final class GroupRef extends Node {
		GroupHead head;

		GroupRef(GroupHead head) {
			this.head = head;
		}

		boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			return head.matchRef(matcher, i, seq)
					&& next.match(matcher, matcher.last, seq);
		}

		boolean study(TreeInfo info) {
			info.maxValid = false;
			info.deterministic = false;
			return next.study(info);
		}
	}

	/**
	 * The GroupTail handles the setting of group beginning and ending locations
	 * when groups are successfully matched. It must also be able to unset
	 * groups that have to be backed off of.
	 *
	 * The GroupTail node is also used when a previous group is referenced, and
	 * in that case no group information needs to be set.
	 */
	static final class GroupTail extends Node {
		int localIndex;
		int groupIndex;

		GroupTail(int localCount, int groupCount) {
			localIndex = localCount;
			groupIndex = groupCount + groupCount;
		}

		boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			int tmp = matcher.locals[localIndex];
			if (tmp >= 0) { // This is the normal group case.
				// Save the group so we can unset it if it
				// backs off of a match.
				long groupStart = matcher.groups[groupIndex];
				long groupEnd = matcher.groups[groupIndex + 1];

				matcher.groups[groupIndex] = tmp;
				matcher.groups[groupIndex + 1] = i;
				if (next.match(matcher, i, seq)) {
					return true;
				}
				matcher.groups[groupIndex] = groupStart;
				matcher.groups[groupIndex + 1] = groupEnd;
				return false;
			} else {
				// This is a group reference case. We don't need to save any
				// group info because it isn't really a group.
				matcher.last = i;
				return true;
			}
		}
	}

	/**
	 * This sets up a loop to handle a recursive quantifier structure.
	 */
	static final class Prolog extends Node {
		Loop loop;

		Prolog(Loop loop) {
			this.loop = loop;
		}

		boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			return loop.matchInit(matcher, i, seq);
		}

		boolean study(TreeInfo info) {
			return loop.study(info);
		}
	}

	/**
	 * Handles the repetition count for a greedy Curly. The matchInit is called
	 * from the Prolog to save the index of where the group beginning is stored.
	 * A zero length group check occurs in the normal match but is skipped in
	 * the matchInit.
	 */
	static class Loop extends Node {
		Node body;
		int countIndex; // local count index in LargeFileMatcher locals
		int beginIndex; // group beginning index
		int cmin, cmax;

		Loop(int countIndex, int beginIndex) {
			this.countIndex = countIndex;
			this.beginIndex = beginIndex;
		}

		boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			// Avoid infinite loop in zero-length case.
			if (i > matcher.locals[beginIndex]) {
				int count = matcher.locals[countIndex];

				// This block is for before we reach the minimum
				// iterations required for the loop to match
				if (count < cmin) {
					matcher.locals[countIndex] = count + 1;
					boolean b = body.match(matcher, i, seq);
					// If match failed we must backtrack, so
					// the loop count should NOT be incremented
					if (!b)
						matcher.locals[countIndex] = count;
					// Return success or failure since we are under
					// minimum
					return b;
				}
				// This block is for after we have the minimum
				// iterations required for the loop to match
				if (count < cmax) {
					matcher.locals[countIndex] = count + 1;
					boolean b = body.match(matcher, i, seq);
					// If match failed we must backtrack, so
					// the loop count should NOT be incremented
					if (!b)
						matcher.locals[countIndex] = count;
					else
						return true;
				}
			}
			return next.match(matcher, i, seq);
		}

		boolean matchInit(LargeFileMatcher matcher, int i, CharSequence seq) {
			int save = matcher.locals[countIndex];
			boolean ret = false;
			if (0 < cmin) {
				matcher.locals[countIndex] = 1;
				ret = body.match(matcher, i, seq);
			} else if (0 < cmax) {
				matcher.locals[countIndex] = 1;
				ret = body.match(matcher, i, seq);
				if (ret == false)
					ret = next.match(matcher, i, seq);
			} else {
				ret = next.match(matcher, i, seq);
			}
			matcher.locals[countIndex] = save;
			return ret;
		}

		boolean study(TreeInfo info) {
			info.maxValid = false;
			info.deterministic = false;
			return false;
		}
	}

	/**
	 * Handles the repetition count for a reluctant Curly. The matchInit is
	 * called from the Prolog to save the index of where the group beginning is
	 * stored. A zero length group check occurs in the normal match but is
	 * skipped in the matchInit.
	 */
	public static final class LazyLoop extends Loop {
		public LazyLoop(int countIndex, int beginIndex) {
			super(countIndex, beginIndex);
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			// Check for zero length group
			if (i > matcher.locals[beginIndex]) {
				int count = matcher.locals[countIndex];
				if (count < cmin) {
					matcher.locals[countIndex] = count + 1;
					boolean result = body.match(matcher, i, seq);
					// If match failed we must backtrack, so
					// the loop count should NOT be incremented
					if (!result)
						matcher.locals[countIndex] = count;
					return result;
				}
				if (next.match(matcher, i, seq))
					return true;
				if (count < cmax) {
					matcher.locals[countIndex] = count + 1;
					boolean result = body.match(matcher, i, seq);
					// If match failed we must backtrack, so
					// the loop count should NOT be incremented
					if (!result)
						matcher.locals[countIndex] = count;
					return result;
				}
				return false;
			}
			return next.match(matcher, i, seq);
		}

		public boolean matchInit(LargeFileMatcher matcher, int i, CharSequence seq) {
			int save = matcher.locals[countIndex];
			boolean ret = false;
			if (0 < cmin) {
				matcher.locals[countIndex] = 1;
				ret = body.match(matcher, i, seq);
			} else if (next.match(matcher, i, seq)) {
				ret = true;
			} else if (0 < cmax) {
				matcher.locals[countIndex] = 1;
				ret = body.match(matcher, i, seq);
			}
			matcher.locals[countIndex] = save;
			return ret;
		}

		public boolean study(TreeInfo info) {
			info.maxValid = false;
			info.deterministic = false;
			return false;
		}
	}

	/**
	 * Refers to a group in the regular expression. Attempts to match whatever
	 * the group referred to last matched.
	 */
	static class BackRef extends Node {
		int groupIndex;

		BackRef(int groupCount) {
			super();
			groupIndex = groupCount + groupCount;
		}

		boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			long j = matcher.groups[groupIndex];
			long k = matcher.groups[groupIndex + 1];

			long groupSize = k - j;
			// If the referenced group didn't match, neither can this
			if (j < 0)
				return false;

			// If there isn't enough input left no match
			if (i + groupSize > matcher.to) {
				matcher.hitEnd = true;
				return false;
			}
			// Check each new char to make sure it matches what the group
			// referenced matched last time around
			for (int index = 0; index < groupSize; index++)
				if (seq.charAt(i + index) != seq.charAt((int) (j + index)))
					return false;

			return next.match(matcher, i + groupSize, seq);
		}

		boolean study(TreeInfo info) {
			info.maxValid = false;
			return next.study(info);
		}
	}

	public static class CIBackRef extends Node {
		public int groupIndex;
		public boolean doUnicodeCase;

		public CIBackRef(int groupCount, boolean doUnicodeCase) {
			super();
			groupIndex = groupCount + groupCount;
			this.doUnicodeCase = doUnicodeCase;
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			long j = matcher.groups[groupIndex];
			long k = matcher.groups[groupIndex + 1];

			long groupSize = k - j;

			// If the referenced group didn't match, neither can this
			if (j < 0)
				return false;

			// If there isn't enough input left no match
			if (i + groupSize > matcher.to) {
				matcher.hitEnd = true;
				return false;
			}

			// Check each new char to make sure it matches what the group
			// referenced matched last time around
			int x = i;
			for (int index = 0; index < groupSize; index++) {
				int c1 = Character.codePointAt(seq, x);
				int c2 = Character.codePointAt(seq, (int) j);
				if (c1 != c2) {
					if (doUnicodeCase) {
						int cc1 = Character.toUpperCase(c1);
						int cc2 = Character.toUpperCase(c2);
						if (cc1 != cc2 && Character.toLowerCase(cc1) != Character.toLowerCase(cc2))
							return false;
					} else {
						if (ASCII.toLower(c1) != ASCII.toLower(c2))
							return false;
					}
				}
				x += Character.charCount(c1);
				j += Character.charCount(c2);
			}

			return next.match(matcher, i + groupSize, seq);
		}

		public boolean study(TreeInfo info) {
			info.maxValid = false;
			return next.study(info);
		}
	}

	/**
	 * Searches until the next instance of its atom. This is useful for finding
	 * the atom efficiently without passing an instance of it (greedy problem)
	 * and without a lot of wasted search time (reluctant problem).
	 */
	public static final class First extends Node {
		public Node atom;

		public First(Node node) {
			this.atom = BnM.optimize(node);
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			if (atom instanceof BnM) {
				return atom.match(matcher, i, seq)
						&& next.match(matcher, matcher.last, seq);
			}
			for (;;) {
				if (i > matcher.to) {
					matcher.hitEnd = true;
					return false;
				}
				if (atom.match(matcher, i, seq)) {
					return next.match(matcher, matcher.last, seq);
				}
				i += countChars(seq, i, 1);
				matcher.first++;
			}
		}
		@Override
		public boolean study(TreeInfo info) {
			atom.study(info);
			info.maxValid = false;
			info.deterministic = false;
			return next.study(info);
		}
	}

	public static final class Conditional extends Node {
		public Node cond, yes, not;

		public Conditional(Node cond, Node yes, Node not) {
			this.cond = cond;
			this.yes = yes;
			this.not = not;
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			if (cond.match(matcher, i, seq)) {
				return yes.match(matcher, i, seq);
			} else {
				return not.match(matcher, i, seq);
			}
		}

		public boolean study(TreeInfo info) {
			int minL = info.minLength;
			int maxL = info.maxLength;
			boolean maxV = info.maxValid;
			info.reset();
			yes.study(info);

			int minL2 = info.minLength;
			int maxL2 = info.maxLength;
			boolean maxV2 = info.maxValid;
			info.reset();
			not.study(info);

			info.minLength = minL + Math.min(minL2, info.minLength);
			info.maxLength = maxL + Math.max(maxL2, info.maxLength);
			info.maxValid = (maxV & maxV2 & info.maxValid);
			info.deterministic = false;
			return next.study(info);
		}
	}

	/**
	 * Zero width positive lookahead.
	 */
	static final class Pos extends Node {
		Node cond;

		Pos(Node cond) {
			this.cond = cond;
		}

		boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			long savedTo = matcher.to;
			boolean conditionMatched = false;

			// Relax transparent region boundaries for lookahead
			if (matcher.transparentBounds)
				matcher.to = matcher.getTextLength();
			try {
				conditionMatched = cond.match(matcher, i, seq);
			} finally {
				// Reinstate region boundaries
				matcher.to = savedTo;
			}
			return conditionMatched && next.match(matcher, i, seq);
		}
	}

	/**
	 * Zero width negative lookahead.
	 */
	static final class Neg extends Node {
		Node cond;

		Neg(Node cond) {
			this.cond = cond;
		}

		boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			long savedTo = matcher.to;
			boolean conditionMatched = false;

			// Relax transparent region boundaries for lookahead
			if (matcher.transparentBounds)
				matcher.to = matcher.getTextLength();
			try {
				if (i < matcher.to) {
					conditionMatched = !cond.match(matcher, i, seq);
				} else {
					// If a negative lookahead succeeds then more input
					// could cause it to fail!
					matcher.requireEnd = true;
					conditionMatched = !cond.match(matcher, i, seq);
				}
			} finally {
				// Reinstate region boundaries
				matcher.to = savedTo;
			}
			return conditionMatched && next.match(matcher, i, seq);
		}
	}

	/**
	 * For use with lookbehinds; matches the position where the lookbehind was
	 * encountered.
	 */
	public static Node lookbehindEnd = new Node() {
		@SuppressWarnings("unused")
		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			return i == matcher.lookbehindTo;
		}
	};

	/**
	 * Zero width positive lookbehind.
	 */
	public static class Behind extends Node {
		Node cond;
		int rmax, rmin;

		Behind(Node cond, int rmax, int rmin) {
			this.cond = cond;
			this.rmax = rmax;
			this.rmin = rmin;
		}

		boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			long savedFrom = matcher.from;
			boolean conditionMatched = false;
			long startIndex = (!matcher.transparentBounds) ? matcher.from : 0;
			long from = Math.max(i - rmax, startIndex);
			// Set end boundary
			int savedLBT = matcher.lookbehindTo;
			matcher.lookbehindTo = i;
			// Relax transparent region boundaries for lookbehind
			if (matcher.transparentBounds)
				matcher.from = 0;
			for (int j = i - rmin; !conditionMatched && j >= from; j--) {
				conditionMatched = cond.match(matcher, j, seq);
			}
			matcher.from = savedFrom;
			matcher.lookbehindTo = savedLBT;
			return conditionMatched && next.match(matcher, i, seq);
		}
	}

	/**
	 * Zero width positive lookbehind, including supplementary characters or
	 * unpaired surrogates.
	 */
	public static final class BehindS extends Behind {
		public BehindS(Node cond, int rmax, int rmin) {
			super(cond, rmax, rmin);
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			int rmaxChars = countChars(seq, i, -rmax);
			int rminChars = countChars(seq, i, -rmin);
			long savedFrom = matcher.from;
			long startIndex = (!matcher.transparentBounds) ? matcher.from : 0;
			boolean conditionMatched = false;
			long from = Math.max(i - rmaxChars, startIndex);
			// Set end boundary
			int savedLBT = matcher.lookbehindTo;
			matcher.lookbehindTo = i;
			// Relax transparent region boundaries for lookbehind
			if (matcher.transparentBounds)
				matcher.from = 0;

			for (int j = i - rminChars; !conditionMatched && j >= from; j -= j > from ? countChars(seq,
					j, -1) : 1) {
				conditionMatched = cond.match(matcher, j, seq);
			}
			matcher.from = savedFrom;
			matcher.lookbehindTo = savedLBT;
			return conditionMatched && next.match(matcher, i, seq);
		}
	}

	/**
	 * Zero width negative lookbehind.
	 */
	public static class NotBehind extends Node {
		public Node cond;
		public int rmax, rmin;

		public NotBehind(Node cond, int rmax, int rmin) {
			this.cond = cond;
			this.rmax = rmax;
			this.rmin = rmin;
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			int savedLBT = matcher.lookbehindTo;
			long savedFrom = matcher.from;
			boolean conditionMatched = false;
			long startIndex = (!matcher.transparentBounds) ? matcher.from : 0;
			long from = Math.max(i - rmax, startIndex);
			matcher.lookbehindTo = i;
			// Relax transparent region boundaries for lookbehind
			if (matcher.transparentBounds)
				matcher.from = 0;
			for (int j = i - rmin; !conditionMatched && j >= from; j--) {
				conditionMatched = cond.match(matcher, j, seq);
			}
			// Reinstate region boundaries
			matcher.from = savedFrom;
			matcher.lookbehindTo = savedLBT;
			return !conditionMatched && next.match(matcher, i, seq);
		}
	}

	/**
	 * Zero width negative lookbehind, including supplementary characters or
	 * unpaired surrogates.
	 */
	public static final class NotBehindS extends NotBehind {
		public NotBehindS(Node cond, int rmax, int rmin) {
			super(cond, rmax, rmin);
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			int rmaxChars = countChars(seq, i, -rmax);
			int rminChars = countChars(seq, i, -rmin);
			long savedFrom = matcher.from;
			int savedLBT = matcher.lookbehindTo;
			boolean conditionMatched = false;
			long startIndex = (!matcher.transparentBounds) ? matcher.from : 0;
			long from = Math.max(i - rmaxChars, startIndex);
			matcher.lookbehindTo = i;
			// Relax transparent region boundaries for lookbehind
			if (matcher.transparentBounds)
				matcher.from = 0;
			for (int j = i - rminChars; !conditionMatched && j >= from; j -= j > from ? countChars(seq,
					j, -1) : 1) {
				conditionMatched = cond.match(matcher, j, seq);
			}
			// Reinstate region boundaries
			matcher.from = savedFrom;
			matcher.lookbehindTo = savedLBT;
			return !conditionMatched && next.match(matcher, i, seq);
		}
	}

	/**
	 * Returns the set union of two CharProperty nodes.
	 */
	public static CharProperty union(final CharProperty lhs, final CharProperty rhs) {
		return new CharProperty() {
			public boolean isSatisfiedBy(int ch) {
				return lhs.isSatisfiedBy(ch) || rhs.isSatisfiedBy(ch);
			}
		};
	}

	/**
	 * Returns the set intersection of two CharProperty nodes.
	 */
	public static CharProperty intersection(final CharProperty lhs, final CharProperty rhs) {
		return new CharProperty() {
			public boolean isSatisfiedBy(int ch) {
				return lhs.isSatisfiedBy(ch) && rhs.isSatisfiedBy(ch);
			}
		};
	}

	/**
	 * Returns the set difference of two CharProperty nodes.
	 */
	public static CharProperty setDifference(final CharProperty lhs, final CharProperty rhs) {
		return new CharProperty() {
			public boolean isSatisfiedBy(int ch) {
				return !rhs.isSatisfiedBy(ch) && lhs.isSatisfiedBy(ch);
			}
		};
	}

	/**
	 * Handles word boundaries. Includes a field to allow this one class to deal
	 * with the different types of word boundaries we can match. The word
	 * characters include underscores, letters, and digits. Non spacing marks
	 * can are also part of a word if they have a base character, otherwise they
	 * are ignored for purposes of finding word boundaries.
	 */
	public static final class Bound extends Node {
		public static int LEFT = 0x1;
		public static int RIGHT = 0x2;
		public static int BOTH = 0x3;
		public static int NONE = 0x4;
		public int type;
		public boolean useUWORD;

		public Bound(int n, boolean useUWORD) {
			type = n;
			this.useUWORD = useUWORD;
		}

		public boolean isWord(int ch) {
			return useUWORD ? UnicodeProp.WORD.is(ch) : (ch == '_' || Character.isLetterOrDigit(ch));
		}

		public int check(LargeFileMatcher matcher, int i, CharSequence seq) {
			int ch;
			boolean left = false;
			long startIndex = matcher.from;
			long endIndex = matcher.to;
			if (matcher.transparentBounds) {
				startIndex = 0;
				endIndex = matcher.getTextLength();
			}
			if (i > startIndex) {
				ch = Character.codePointBefore(seq, i);
				left = (isWord(ch) || ((Character.getType(ch) == Character.NON_SPACING_MARK) && hasBaseCharacter(
						matcher, i - 1, seq)));
			}
			boolean right = false;
			if (i < endIndex) {
				ch = Character.codePointAt(seq, i);
				right = (isWord(ch) || ((Character.getType(ch) == Character.NON_SPACING_MARK) && hasBaseCharacter(
						matcher, i, seq)));
			} else {
				// Tried to access char past the end
				matcher.hitEnd = true;
				// The addition of another char could wreck a boundary
				matcher.requireEnd = true;
			}
			return ((left ^ right) ? (right ? LEFT : RIGHT) : NONE);
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			return (check(matcher, i, seq) & type) > 0 && next.match(matcher, i, seq);
		}
	}

	/**
	 * Non spacing marks only count as word characters in bounds calculations if
	 * they have a base character.
	 */
	public static boolean hasBaseCharacter(LargeFileMatcher matcher, int i, CharSequence seq) {
		long start = (!matcher.transparentBounds) ? matcher.from : 0;
		for (int x = i; x >= start; x--) {
			int ch = Character.codePointAt(seq, x);
			if (Character.isLetterOrDigit(ch))
				return true;
			if (Character.getType(ch) == Character.NON_SPACING_MARK)
				continue;
			return false;
		}
		return false;
	}

	/**
	 * Attempts to match a slice in the input using the Boyer-Moore string
	 * matching algorithm. The algorithm is based on the idea that the
	 * LargeFilePattern can be shifted farther ahead in the search text if it is
	 * matched right to left.
	 * <p>
	 * The LargeFilePattern is compared to the input one character at a time,
	 * from the rightmost character in the LargeFilePattern to the left. If the
	 * characters all match the LargeFilePattern has been found. If a character
	 * does not match, the LargeFilePattern is shifted right a distance that is
	 * the maximum of two functions, the bad character shift and the good suffix
	 * shift. This shift moves the attempted match position through the input
	 * more quickly than a naive one position at a time check.
	 * <p>
	 * The bad character shift is based on the character from the text that did
	 * not match. If the character does not appear in the LargeFilePattern, the
	 * LargeFilePattern can be shifted completely beyond the bad character. If
	 * the character does occur in the LargeFilePattern, the LargeFilePattern
	 * can be shifted to line the LargeFilePattern up with the next occurrence
	 * of that character.
	 * <p>
	 * The good suffix shift is based on the idea that some subset on the right
	 * side of the LargeFilePattern has matched. When a bad character is found,
	 * the LargeFilePattern can be shifted right by the LargeFilePattern length
	 * if the subset does not occur again in LargeFilePattern, or by the amount
	 * of distance to the next occurrence of the subset in the LargeFilePattern.
	 *
	 * Boyer-Moore search methods adapted from code by Amy Yu.
	 */
	public static class BnM extends Node {
		public int[] buffer;
		public int[] lastOcc;
		public int[] optoSft;

		/**
		 * Pre calculates arrays needed to generate the bad character shift and
		 * the good suffix shift. Only the last seven bits are used to see if
		 * chars match; This keeps the tables small and covers the heavily used
		 * ASCII range, but occasionally results in an aliased match for the bad
		 * character shift.
		 */
		public static Node optimize(Node node) {
			if (!(node instanceof Slice)) {
				return node;
			}

			int[] src = ((Slice) node).buffer;
			int patternLength = src.length;
			// The BM algorithm requires a bit of overhead;
			// If the LargeFilePattern is short don't use it, since
			// a shift larger than the LargeFilePattern length cannot
			// be used anyway.
			if (patternLength < 4) {
				return node;
			}
			int i, j;
			int[] lastOcc = new int[128];
			int[] optoSft = new int[patternLength];
			// Precalculate part of the bad character shift
			// It is a table for where in the LargeFilePattern each
			// lower 7-bit value occurs
			for (i = 0; i < patternLength; i++) {
				lastOcc[src[i] & 0x7F] = i + 1;
			}
			// Precalculate the good suffix shift
			// i is the shift amount being considered
			NEXT: for (i = patternLength; i > 0; i--) {
				// j is the beginning index of suffix being considered
				for (j = patternLength - 1; j >= i; j--) {
					// Testing for good suffix
					if (src[j] == src[j - i]) {
						// src[j..len] is a good suffix
						optoSft[j - 1] = i;
					} else {
						// No match. The array has already been
						// filled up with correct values before.
						continue NEXT;
					}
				}
				// This fills up the remaining of optoSft
				// any suffix can not have larger shift amount
				// then its sub-suffix. Why???
				while (j > 0) {
					optoSft[--j] = i;
				}
			}
			// Set the guard value because of unicode compression
			optoSft[patternLength - 1] = 1;
			if (node instanceof SliceS)
				return new BnMS(src, lastOcc, optoSft, node.next);
			return new BnM(src, lastOcc, optoSft, node.next);
		}

		public BnM(int[] src, int[] lastOcc, int[] optoSft, Node next) {
			this.buffer = src;
			this.lastOcc = lastOcc;
			this.optoSft = optoSft;
			this.next = next;
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			int[] src = buffer;
			int patternLength = src.length;
			long last = matcher.to - patternLength;

			// Loop over all possible match positions in text
			NEXT: while (i <= last) {
				// Loop over LargeFilePattern from right to left
				for (int j = patternLength - 1; j >= 0; j--) {
					int ch = seq.charAt(i + j);
					if (ch != src[j]) {
						// Shift search to the right by the maximum of the
						// bad character shift and the good suffix shift
						i += Math.max(j + 1 - lastOcc[ch & 0x7F], optoSft[j]);
						continue NEXT;
					}
				}
				// Entire LargeFilePattern matched starting at i
				matcher.first = i;
				boolean ret = next.match(matcher, i + patternLength, seq);
				if (ret) {
					matcher.first = i;
					matcher.groups[0] = matcher.first;
					matcher.groups[1] = matcher.last;
					return true;
				}
				i++;
			}
			// BnM is only used as the leading node in the unanchored case,
			// and it replaced its Start() which always searches to the end
			// if it doesn't find what it's looking for, so hitEnd is true.
			matcher.hitEnd = true;
			return false;
		}

		public boolean study(TreeInfo info) {
			info.minLength += buffer.length;
			info.maxValid = false;
			return next.study(info);
		}
	}

	/**
	 * Supplementary support version of BnM(). Unpaired surrogates are also
	 * handled by this class.
	 */
	public static final class BnMS extends BnM {
		public int lengthInChars;

		public BnMS(int[] src, int[] lastOcc, int[] optoSft, Node next) {
			super(src, lastOcc, optoSft, next);
			for (int x = 0; x < buffer.length; x++) {
				lengthInChars += Character.charCount(buffer[x]);
			}
		}

		public boolean match(LargeFileMatcher matcher, int i, CharSequence seq) {
			int[] src = buffer;
			int patternLength = src.length;
			long last = matcher.to - lengthInChars;

			// Loop over all possible match positions in text
			NEXT: while (i <= last) {
				// Loop over LargeFilePattern from right to left
				int ch;
				for (int j = countChars(seq, i, patternLength), x = patternLength - 1; j > 0; j -= Character
						.charCount(ch), x--) {
					ch = Character.codePointBefore(seq, i + j);
					if (ch != src[x]) {
						// Shift search to the right by the maximum of the
						// bad character shift and the good suffix shift
						int n = Math.max(x + 1 - lastOcc[ch & 0x7F], optoSft[x]);
						i += countChars(seq, i, n);
						continue NEXT;
					}
				}
				// Entire LargeFilePattern matched starting at i
				matcher.first = i;
				boolean ret = next.match(matcher, i + lengthInChars, seq);
				if (ret) {
					matcher.first = i;
					matcher.groups[0] = matcher.first;
					matcher.groups[1] = matcher.last;
					return true;
				}
				i += countChars(seq, i, 1);
			}
			matcher.hitEnd = true;
			return false;
		}
	}

	// /////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * This must be the very first initializer.
	 */
	static Node accept = new Node();

	static Node lastAccept = new LastNode();

	public static class CharPropertyNames {

		public static CharProperty charPropertyFor(String name) {
			CharPropertyFactory m = map.get(name);
			return m == null ? null : m.make();
		}

		public static abstract class CharPropertyFactory {
			public abstract CharProperty make();
		}

		public static void defCategory(String name, final int typeMask) {
			map.put(name, new CharPropertyFactory() {
				public CharProperty make() {
					return new Category(typeMask);
				}
			});
		}

		public static void defRange(String name, final int lower, final int upper) {
			map.put(name, new CharPropertyFactory() {
				public CharProperty make() {
					return rangeFor(lower, upper);
				}
			});
		}

		public static void defCtype(String name, final int ctype) {
			map.put(name, new CharPropertyFactory() {
				public CharProperty make() {
					return new Ctype(ctype);
				}
			});
		}

		public static abstract class CloneableProperty extends CharProperty implements Cloneable {
			public CloneableProperty clone() {
				try {
					return (CloneableProperty) super.clone();
				} catch (CloneNotSupportedException e) {
					throw new AssertionError(e);
				}
			}
		}

		public static void defClone(String name, final CloneableProperty p) {
			map.put(name, new CharPropertyFactory() {
				public CharProperty make() {
					return p.clone();
				}
			});
		}

		public static final HashMap<String, CharPropertyFactory> map = new HashMap<>();

		static {
			// Unicode character property aliases, defined in
			// http://www.unicode.org/Public/UNIDATA/PropertyValueAliases.txt
			defCategory("Cn", 1 << Character.UNASSIGNED);
			defCategory("Lu", 1 << Character.UPPERCASE_LETTER);
			defCategory("Ll", 1 << Character.LOWERCASE_LETTER);
			defCategory("Lt", 1 << Character.TITLECASE_LETTER);
			defCategory("Lm", 1 << Character.MODIFIER_LETTER);
			defCategory("Lo", 1 << Character.OTHER_LETTER);
			defCategory("Mn", 1 << Character.NON_SPACING_MARK);
			defCategory("Me", 1 << Character.ENCLOSING_MARK);
			defCategory("Mc", 1 << Character.COMBINING_SPACING_MARK);
			defCategory("Nd", 1 << Character.DECIMAL_DIGIT_NUMBER);
			defCategory("Nl", 1 << Character.LETTER_NUMBER);
			defCategory("No", 1 << Character.OTHER_NUMBER);
			defCategory("Zs", 1 << Character.SPACE_SEPARATOR);
			defCategory("Zl", 1 << Character.LINE_SEPARATOR);
			defCategory("Zp", 1 << Character.PARAGRAPH_SEPARATOR);
			defCategory("Cc", 1 << Character.CONTROL);
			defCategory("Cf", 1 << Character.FORMAT);
			defCategory("Co", 1 << Character.PRIVATE_USE);
			defCategory("Cs", 1 << Character.SURROGATE);
			defCategory("Pd", 1 << Character.DASH_PUNCTUATION);
			defCategory("Ps", 1 << Character.START_PUNCTUATION);
			defCategory("Pe", 1 << Character.END_PUNCTUATION);
			defCategory("Pc", 1 << Character.CONNECTOR_PUNCTUATION);
			defCategory("Po", 1 << Character.OTHER_PUNCTUATION);
			defCategory("Sm", 1 << Character.MATH_SYMBOL);
			defCategory("Sc", 1 << Character.CURRENCY_SYMBOL);
			defCategory("Sk", 1 << Character.MODIFIER_SYMBOL);
			defCategory("So", 1 << Character.OTHER_SYMBOL);
			defCategory("Pi", 1 << Character.INITIAL_QUOTE_PUNCTUATION);
			defCategory("Pf", 1 << Character.FINAL_QUOTE_PUNCTUATION);
			defCategory(
					"L",
					((1 << Character.UPPERCASE_LETTER) | (1 << Character.LOWERCASE_LETTER)
							| (1 << Character.TITLECASE_LETTER) | (1 << Character.MODIFIER_LETTER) | (1 << Character.OTHER_LETTER)));
			defCategory(
					"M",
					((1 << Character.NON_SPACING_MARK) | (1 << Character.ENCLOSING_MARK) | (1 << Character.COMBINING_SPACING_MARK)));
			defCategory(
					"N",
					((1 << Character.DECIMAL_DIGIT_NUMBER) | (1 << Character.LETTER_NUMBER) | (1 << Character.OTHER_NUMBER)));
			defCategory(
					"Z",
					((1 << Character.SPACE_SEPARATOR) | (1 << Character.LINE_SEPARATOR) | (1 << Character.PARAGRAPH_SEPARATOR)));
			defCategory("C", ((1 << Character.CONTROL) | (1 << Character.FORMAT)
					| (1 << Character.PRIVATE_USE) | (1 << Character.SURROGATE))); // Other
			defCategory(
					"P",
					((1 << Character.DASH_PUNCTUATION) | (1 << Character.START_PUNCTUATION)
							| (1 << Character.END_PUNCTUATION) | (1 << Character.CONNECTOR_PUNCTUATION)
							| (1 << Character.OTHER_PUNCTUATION)
							| (1 << Character.INITIAL_QUOTE_PUNCTUATION) | (1 << Character.FINAL_QUOTE_PUNCTUATION)));
			defCategory("S", ((1 << Character.MATH_SYMBOL) | (1 << Character.CURRENCY_SYMBOL)
					| (1 << Character.MODIFIER_SYMBOL) | (1 << Character.OTHER_SYMBOL)));
			defCategory(
					"LC",
					((1 << Character.UPPERCASE_LETTER) | (1 << Character.LOWERCASE_LETTER) | (1 << Character.TITLECASE_LETTER)));
			defCategory("LD", ((1 << Character.UPPERCASE_LETTER) | (1 << Character.LOWERCASE_LETTER)
					| (1 << Character.TITLECASE_LETTER) | (1 << Character.MODIFIER_LETTER)
					| (1 << Character.OTHER_LETTER) | (1 << Character.DECIMAL_DIGIT_NUMBER)));
			defRange("L1", 0x00, 0xFF); // Latin-1
			map.put("all", new CharPropertyFactory() {
				public CharProperty make() {
					return new All();
				}
			});

			// Posix regular expression character classes, defined in
			// http://www.unix.org/onlinepubs/009695399/basedefs/xbd_chap09.html
			defRange("ASCII", 0x00, 0x7F); // ASCII
			defCtype("Alnum", ASCII.ALNUM); // Alphanumeric characters
			defCtype("Alpha", ASCII.ALPHA); // Alphabetic characters
			defCtype("Blank", ASCII.BLANK); // Space and tab characters
			defCtype("Cntrl", ASCII.CNTRL); // Control characters
			defRange("Digit", '0', '9'); // Numeric characters
			defCtype("Graph", ASCII.GRAPH); // printable and visible
			defRange("Lower", 'a', 'z'); // Lower-case alphabetic
			defRange("Print", 0x20, 0x7E); // Printable characters
			defCtype("Punct", ASCII.PUNCT); // Punctuation characters
			defCtype("Space", ASCII.SPACE); // Space characters
			defRange("Upper", 'A', 'Z'); // Upper-case alphabetic
			defCtype("XDigit", ASCII.XDIGIT); // hexadecimal digits

			// Java character properties, defined by methods in Character.java
			defClone("javaLowerCase", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isLowerCase(ch);
				}
			});
			defClone("javaUpperCase", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isUpperCase(ch);
				}
			});
			defClone("javaAlphabetic", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isAlphabetic(ch);
				}
			});
			defClone("javaIdeographic", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isIdeographic(ch);
				}
			});
			defClone("javaTitleCase", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isTitleCase(ch);
				}
			});
			defClone("javaDigit", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isDigit(ch);
				}
			});
			defClone("javaDefined", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isDefined(ch);
				}
			});
			defClone("javaLetter", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isLetter(ch);
				}
			});
			defClone("javaLetterOrDigit", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isLetterOrDigit(ch);
				}
			});
			defClone("javaJavaIdentifierStart", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isJavaIdentifierStart(ch);
				}
			});
			defClone("javaJavaIdentifierPart", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isJavaIdentifierPart(ch);
				}
			});
			defClone("javaUnicodeIdentifierStart", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isUnicodeIdentifierStart(ch);
				}
			});
			defClone("javaUnicodeIdentifierPart", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isUnicodeIdentifierPart(ch);
				}
			});
			defClone("javaIdentifierIgnorable", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isIdentifierIgnorable(ch);
				}
			});
			defClone("javaSpaceChar", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isSpaceChar(ch);
				}
			});
			defClone("javaWhitespace", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isWhitespace(ch);
				}
			});
			defClone("javaISOControl", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isISOControl(ch);
				}
			});
			defClone("javaMirrored", new CloneableProperty() {
				public boolean isSatisfiedBy(int ch) {
					return Character.isMirrored(ch);
				}
			});
		}
	}

	/**
	 * Creates a predicate which can be used to match a string.
	 *
	 * @return The predicate which can be used for matching on a string
	 * @since 1.8
	 */
	public Predicate<String> asPredicate() {
		return s -> matcher(s).find();
	}

	/**
	 * Creates a stream from the given input sequence around matches of this
	 * LargeFilePattern.
	 *
	 * <p>
	 * The stream returned by this method contains each substring of the input
	 * sequence that is terminated by another subsequence that matches this
	 * LargeFilePattern or is terminated by the end of the input sequence. The
	 * substrings in the stream are in the order in which they occur in the
	 * input. Trailing empty strings will be discarded and not encountered in
	 * the stream.
	 *
	 * <p>
	 * If this LargeFilePattern does not match any subsequence of the input then
	 * the resulting stream has just one element, namely the input sequence in
	 * string form.
	 *
	 * <p>
	 * When there is a positive-width match at the beginning of the input
	 * sequence then an empty leading substring is included at the beginning of
	 * the stream. A zero-width match at the beginning however never produces
	 * such empty leading substring.
	 *
	 * <p>
	 * If the input sequence is mutable, it must remain constant during the
	 * execution of the terminal stream operation. Otherwise, the result of the
	 * terminal stream operation is undefined.
	 *
	 * @param input
	 *            The character sequence to be split
	 *
	 * @return The stream of strings computed by splitting the input around
	 *         matches of this LargeFilePattern
	 * @see #split(CharSequence)
	 * @since 1.8
	 */
	public Stream<String> splitAsStream(final CharSequence input) {
		class MatcherIterator implements Iterator<String> {
			public final LargeFileMatcher matcher;
			// The start position of the next sub-sequence of input
			// when current == input.length there are no more elements
			public long current;
			// null if the next element, if any, needs to obtained
			public String nextElement;
			// > 0 if there are N next empty elements
			public int emptyElementCount;

			public MatcherIterator() {
				this.matcher = matcher(input);
			}

			public String next() {
				if (!hasNext())
					throw new NoSuchElementException();

				if (emptyElementCount == 0) {
					String n = nextElement;
					nextElement = null;
					return n;
				} else {
					emptyElementCount--;
					return "";
				}
			}

			public boolean hasNext() {
				if (nextElement != null || emptyElementCount > 0)
					return true;

				if (current == input.length())
					return false;

				// Consume the next matching element
				// Count sequence of matching empty elements
				while (matcher.find()) {
					nextElement = input.subSequence((int) current, (int) matcher.start()).toString();
					current = matcher.end();
					if (!nextElement.isEmpty()) {
						return true;
					} else if (current > 0) { // no empty leading substring for
												// zero-width
												// match at the beginning of the
												// input
						emptyElementCount++;
					}
				}

				// Consume last matching element
				nextElement = input.subSequence((int) current, input.length()).toString();
				current = input.length();
				if (!nextElement.isEmpty()) {
					return true;
				} else {
					// Ignore a terminal sequence of matching empty elements
					emptyElementCount = 0;
					nextElement = null;
					return false;
				}
			}
		}
		return StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(new MatcherIterator(), Spliterator.ORDERED
						| Spliterator.NONNULL), false);
	}
}

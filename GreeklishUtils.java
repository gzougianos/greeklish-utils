package main;

import static java.lang.Character.valueOf;

import java.util.HashMap;
import java.util.Map;

public class GreeklishUtils {
	private static final char GREEK_UPPER_T = '\u03a4';
	private static final char LATIN_UPPER_T = 'T';

	private static final char GREEK_UPPER_H = '\u0397';
	private static final char LATIN_UPPER_H = 'H';

	private static final char GREEK_UPPER_K = '\u039a';
	private static final char LATIN_UPPER_K = 'K';

	private static final Map<Character, Character> MAPPINGS = new HashMap<>();
	static {
		MAPPINGS.put('α', valueOf('a'));
		MAPPINGS.put('ά', valueOf('a'));
		MAPPINGS.put('β', valueOf('b'));
		MAPPINGS.put('γ', valueOf('g'));
		MAPPINGS.put('δ', valueOf('d'));
		MAPPINGS.put('ε', valueOf('e'));
		MAPPINGS.put('έ', valueOf('e'));
		MAPPINGS.put('ζ', valueOf('z'));
		MAPPINGS.put('η', valueOf('h'));
		MAPPINGS.put('ή', valueOf('h'));
		MAPPINGS.put('ι', valueOf('i'));
		MAPPINGS.put('ϊ', valueOf('i'));
		MAPPINGS.put('ί', valueOf('i'));
		MAPPINGS.put('ΐ', valueOf('i'));
		MAPPINGS.put('κ', valueOf('k'));
		MAPPINGS.put('λ', valueOf('l'));
		MAPPINGS.put('μ', valueOf('m'));
		MAPPINGS.put('ν', valueOf('n'));
		MAPPINGS.put('ξ', valueOf('j'));
		MAPPINGS.put('ο', valueOf('o'));
		MAPPINGS.put('ό', valueOf('o'));
		MAPPINGS.put('π', valueOf('p'));
		MAPPINGS.put('ρ', valueOf('r'));
		MAPPINGS.put('σ', valueOf('s'));
		MAPPINGS.put('τ', valueOf('t'));
		MAPPINGS.put('υ', valueOf('u'));
		MAPPINGS.put('ύ', valueOf('u'));
		MAPPINGS.put('ϋ', valueOf('u'));
		MAPPINGS.put('ΰ', valueOf('u'));
		MAPPINGS.put('φ', valueOf('f'));
		MAPPINGS.put('χ', valueOf('x'));
		MAPPINGS.put('ψ', valueOf('c')); // should it be?
		MAPPINGS.put('ω', valueOf('w'));
		MAPPINGS.put('ώ', valueOf('w'));
		MAPPINGS.put('ς', valueOf('s'));

		MAPPINGS.put('Α', valueOf('A'));
		MAPPINGS.put('Ά', valueOf('A'));
		MAPPINGS.put('Β', valueOf('B'));
		MAPPINGS.put('Γ', valueOf('G'));
		MAPPINGS.put('Δ', valueOf('D'));
		MAPPINGS.put('Ε', valueOf('E'));
		MAPPINGS.put('Έ', valueOf('E'));
		MAPPINGS.put('Ζ', valueOf('Z'));
		MAPPINGS.put('Η', valueOf('H'));
		MAPPINGS.put('Ή', valueOf('H'));
		MAPPINGS.put('Ι', valueOf('I'));
		MAPPINGS.put('Ϊ', valueOf('I'));
		MAPPINGS.put('Ί', valueOf('I'));
		MAPPINGS.put('Κ', valueOf('K'));
		MAPPINGS.put('Λ', valueOf('L'));
		MAPPINGS.put('Μ', valueOf('M'));
		MAPPINGS.put('Ν', valueOf('N'));
		MAPPINGS.put('Η', valueOf('J'));
		MAPPINGS.put('Ο', valueOf('O'));
		MAPPINGS.put('Ό', valueOf('O'));
		MAPPINGS.put('Π', valueOf('P'));
		MAPPINGS.put('Ρ', valueOf('R'));
		MAPPINGS.put('Σ', valueOf('S'));
		MAPPINGS.put('Τ', valueOf('T'));
		MAPPINGS.put('Υ', valueOf('U'));
		MAPPINGS.put('Ύ', valueOf('U'));
		MAPPINGS.put('Ϋ', valueOf('U'));
		MAPPINGS.put('Φ', valueOf('F'));
		MAPPINGS.put('Χ', valueOf('X'));
		MAPPINGS.put('Ψ', valueOf('C'));
		MAPPINGS.put('Ω', valueOf('W'));
		MAPPINGS.put('Ώ', valueOf('W'));
	}

	private static boolean matchesTheta(char thetaCandidate, char leftPart, char rightPart) {
		if (thetaCandidate == 'θ')
			return leftPart == 't' && rightPart == 'h';

		if (thetaCandidate == 'Θ')
			return (leftPart == GREEK_UPPER_T || leftPart == LATIN_UPPER_T)
					&& (rightPart == LATIN_UPPER_H || rightPart == GREEK_UPPER_H || rightPart == 'h');

		return false;
	}

	private static boolean matchesKsi(char ksiCandidate, char leftPart, char rightPart) {
		if (ksiCandidate == 'ξ')
			return leftPart == 'k' && rightPart == 's';

		if (ksiCandidate == 'Ξ')
			return (leftPart == GREEK_UPPER_K || leftPart == LATIN_UPPER_K) && (rightPart == 'S' || rightPart == 's');

		return false;
	}

	private static boolean matchesPsi(char psiCandidate, char leftPart, char rightPart) {
		if (psiCandidate == 'ψ')
			return leftPart == 'p' && rightPart == 's';

		if (psiCandidate == 'Ψ')
			// greek upper Ρ look like latin upper P?
			return leftPart == 'P' && (rightPart == 'S' || rightPart == 's');

		return false;
	}

	private static boolean matchesDipthong(char dipthongCandidate, char leftPart, char rightPart) {
		return matchesTheta(dipthongCandidate, leftPart, rightPart)
				|| matchesKsi(dipthongCandidate, leftPart, rightPart)
				|| matchesPsi(dipthongCandidate, leftPart, rightPart);
	}

	private static boolean matchesMappedEitherSide(final char s1Char, final char s2Char) {
		Character mappedC1 = MAPPINGS.get(s1Char);
		if (mappedC1 != null) {
			if (mappedC1.charValue() == s2Char)
				return true;
		}

		Character mappedC2 = MAPPINGS.get(s2Char);
		if (mappedC2 != null) {
			if (s1Char == mappedC2.charValue())
				return true;

			// Happens only on dialitika eg ΰ
			return mappedC2.equals(mappedC1);
		}

		return false;
	}

	/**
	 * <p>
	 * Checks if s1 contains s2 ignoring Greek-English characters and toned Greek
	 * characters. For example the string <i>thelw na piw kafe</i> contains the
	 * string <i>&#00952;&#00941;&#00955;&#00969;</i>.
	 * </p>
	 * 
	 * @param s1 The first CharSequence
	 * @param s2 The second CharSequence
	 * @return {@code true} if s1 contains s2 ignoring greeklish
	 */
	public static boolean contains(CharSequence s1, CharSequence s2) {
		if (s2.length() == 0)
			return true;

		final int s1Length = s1.length();
		final int s2Length = s2.length();

		int s1Pointer = -1;
		int numOfFound = 0;

		while (s1Pointer < s1Length - 1) {
			s1Pointer++;
			final char s1Char = s1.charAt(s1Pointer);
			final char s2Char = s2.charAt(numOfFound);

			int leftToFind = s2Length - numOfFound;
			int leftToScan = s1Length - s1Pointer;

			// Must be *2 because of dipthongs
			// θθθθ contains thththth (double size)
			if (leftToFind > leftToScan * 2)
				return false;

			if (s1Char == s2Char) {
				if (++numOfFound == s2Length)
					return true;
				continue;
			}

			if (Character.isLetter(s1Char) && Character.isLetter(s2Char)) {
				if (matchesMappedEitherSide(s1Char, s2Char)) {
					if (++numOfFound == s2Length)
						return true;
					continue;
				}

				if (numOfFound < s2.length() - 1) {
					final char nextS2Char = s2.charAt(numOfFound + 1);
					if (matchesDipthong(s1Char, s2Char, nextS2Char)) {
						numOfFound++;
						if (++numOfFound == s2Length)
							return true;
						continue;
					}
				}

				if (s1Pointer < s1Length - 1) {
					char nextS2Char = s1.charAt(s1Pointer + 1);
					if (matchesDipthong(s2Char, s1Char, nextS2Char)) {
						s1Pointer++;
						if (++numOfFound == s2Length)
							return true;
						continue;
					}
				}
			}

			s1Pointer = s1Pointer - numOfFound;
			numOfFound = 0;
		}

		return false;
	}

	/**
	 * <p>
	 * Checks if s1 is equal to s2 ignoring Greek-English characters and toned Greek
	 * characters. For example the string <i>thelw</i> is equals to string
	 * <i>&#00952;&#00941;&#00955;&#00969;</i>.
	 * </p>
	 * 
	 * @param s1 The first CharSequence
	 * @param s2 The second CharSequence
	 * @return {@code true} if s1 equals s2 ignoring greeklish
	 */
	public static boolean isEqual(CharSequence s1, CharSequence s2) {
		final int s1Length = s1.length();
		final int s2Length = s2.length();

		if (s2Length == 0)
			return s1Length == 0;

		if (s2Length > s1Length * 2 || s1Length > s2Length * 2)
			return false;

		int s1Pointer = -1;
		int numOfFound = 0;

		while (s1Pointer < s1Length - 1) {
			s1Pointer++;
			final char s1Char = s1.charAt(s1Pointer);
			final char s2Char = s2.charAt(numOfFound);

			if (s1Char == s2Char) {
				if (++numOfFound == s2Length)
					return true;
				continue;
			}

			if (!Character.isLetter(s1Char) || !Character.isLetter(s2Char))
				return false;

			if (matchesMappedEitherSide(s1Char, s2Char)) {
				if (++numOfFound == s2Length)
					return true;
				continue;
			}

			if (numOfFound < s2.length() - 1) {
				final char nextS2Char = s2.charAt(numOfFound + 1);
				if (matchesDipthong(s1Char, s2Char, nextS2Char)) {
					numOfFound++;
					if (++numOfFound == s2Length)
						return true;
					continue;
				}
			}

			if (s1Pointer < s1Length - 1) {
				char nextS2Char = s1.charAt(s1Pointer + 1);
				if (matchesDipthong(s2Char, s1Char, nextS2Char)) {
					s1Pointer++;
					if (++numOfFound == s2Length)
						return true;
					continue;
				}
			}

			return false;
		}

		return false;
	}
}

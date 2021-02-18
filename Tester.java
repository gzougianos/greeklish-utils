package main;

import static main.GreeklishUtils.contains;
import static main.GreeklishUtils.isEqual;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Tester {
	@Test
	void contain() {
		assertTrue(contains("thwmas", "thwmas"));
		assertTrue(contains("thwmas", "thwm"));
		assertTrue(contains("θωμασ", "thwm"));
		assertTrue(contains("thwmas", "θωμ"));
		assertTrue(contains("thwmas", "thωμας"));
		assertFalse(contains("kostas", "kostass"));
		assertTrue(contains("kostaθ", "kostath"));
		assertTrue(contains("kostath1", "kostaθ1"));
		assertTrue(contains("kostaθ1", "koσταθ1"));
		assertTrue(contains("this is somethingφ", "f"));

		assertTrue(contains("βαρυ", "baru"));
		assertTrue(contains("baru", "βαρυ"));
		assertTrue(contains("ksksout", "ξξουτ"));
		assertTrue(contains("thelw", "θέλω"));
		assertTrue(contains("θέλω", "thelw"));
		assertTrue(contains("ξερω", "kserw"));
		assertTrue(contains("fffψερω", "pserw"));
		assertTrue(contains("φφpsariφφφ", "ψαρι"));
		assertTrue(contains("θθθθ", "thththth"));
		assertTrue(contains("thththth", "θθθθ"));
		assertTrue(contains("thththth", "θθ"));
		assertTrue(contains("psari", "ψάρι"));
		assertTrue(contains("ψάρι", "psari"));
		assertFalse(contains("αυτο είναι ένα κείμενο", "2"));

		assertFalse(contains("giorgos", "γιωργοs"));
		assertTrue(contains("giwrgo", "γιώrgο"));
		assertFalse(contains("Giorgos", "γιώrgοs"));
		assertFalse(contains("giorgos", "Gιωργοs"));
		assertTrue(contains("555 Giwrgos", "Gιωργο"));

		assertTrue(contains("23131Γιωργοfw2", "Gιωργο"));
		assertFalse(contains("23131Γιωργοfw2", "$$##GiwrgoGiwrgos"));
		assertTrue(contains("ο κώστασ θα πάει για καφέ", "kwstas"));
		assertTrue(contains("ο κώστασ θα πάει για καφέ", " kwstas"));
		assertTrue(contains("ο κώστασ θα πάει για καφέ", "o kwstas tha paei gia kafe"));
		assertTrue(contains("ο κώστασ θα πάει για καφέ i oxi", "o kwstas tha paei gia kafe"));

		assertTrue(contains("fantasou ο κώστασ θα πάει για καφέ i oxi", "o kwstas tha paei gia kafe"));

		assertTrue(contains("γιογιο", "gioγι"));
		assertTrue(contains("to ksulo", "ξυlo"));
		assertTrue(contains("to julo", "ξυlo"));
		assertTrue(contains("to julo", "ξulo"));
		assertTrue(contains("Co_XA___ΨΑΡΟΥΚΛΑFFF", "PSAROUKLA"));
		assertTrue(contains("Ψatha", "Psath"));
		assertTrue(contains("psaψα", "ψαψα"));

		assertTrue(contains("psinome", "ψινομε"));

		assertTrue(contains("", ""));
		assertTrue(contains("f", ""));
		assertTrue(contains("ρρςερες", ""));

		assertTrue(contains("357448315913758484941378914377450", "0"));
		assertFalse(contains("35744831591375848494137891437745", "0"));

		assertTrue(contains("as poume kati!!!", "!"));
		assertTrue(contains("as poume kati!!!", "που"));

		assertTrue(contains("thθth", "θthθ"));
		assertTrue(contains("aaaaa", "αάά"));

		assertTrue(contains("διαλΰτικα", "dialutika"));
		assertTrue(contains("διαλΰτικά", "διαλυτικα"));

		assertFalse(contains("ψώνια", "pionia"));

	}

	@Test
	void isEquals() {
		assertTrue(isEqual("ξέρω", "ξερω"));
		assertTrue(isEqual("thwmas", "thwmas"));
		assertTrue(isEqual("θωmas", "thwmas"));
		assertTrue(isEqual("thwmas", "θωmas"));
		assertTrue(isEqual("ψαρι", "cari"));

		assertTrue(isEqual("thωmas", "θwmas"));
		assertTrue(isEqual("ksks", "ξξ"));
		assertTrue(isEqual("ksks", "ξks"));

		assertTrue(isEqual("", ""));
		assertFalse(isEqual("g", ""));
		assertFalse(isEqual("", "g"));

		assertFalse(isEqual("thωmas", "θwmasθwmasθwmas"));
		assertFalse(isEqual("big string", "βιγ"));

		assertTrue(isEqual("11kappa", "11καππα"));

		assertTrue(isEqual("βαρυ", "baru"));
		assertTrue(isEqual("baru", "βαρυ"));

		assertTrue(isEqual("ksksout", "ξξουτ"));
		assertTrue(isEqual("thelw", "θέλω"));
		assertTrue(isEqual("θέλω", "thelw"));
		assertTrue(isEqual("ξερω", "kserw"));
		assertTrue(isEqual("@$4235ξερω", "@$4235kserw"));
		assertTrue(isEqual("@$4235ξερω@$4235", "@$4235kserw@$4235"));
		assertFalse(isEqual("thωmas", "Thwmas"));
		assertTrue(isEqual("Ξερω", "Kserw"));
		assertTrue(isEqual("Ξερω", "KSerw"));

		assertTrue(isEqual("julo", "ξulo"));
		assertTrue(isEqual("thθth", "θthθ"));

		assertFalse(isEqual("1thθth", "5θthθ"));
		assertFalse(isEqual("thθth", "5θthθ"));
		assertFalse(isEqual("1thθth", "θthθ"));
		assertTrue(isEqual("thththth", "θθθθ"));
		assertTrue(isEqual("θθθθ", "thththth"));
		assertTrue(isEqual("ΘΘ", "ΤhΤh"));
		assertTrue(isEqual("ΘΘ", "ΤΗΤh"));
		assertTrue(isEqual("ΘΘ", "ΤΗΤΗ"));

		assertTrue(isEqual("ο κώστασ θα πάει για καφέ", "o kwstas tha paei gia kafe"));
		assertTrue(isEqual("aaa", "αάά"));

		assertTrue(isEqual("διαλΰτικα", "dialutika"));
		assertTrue(isEqual("διαλΰτικα", "διαλυτικά"));
		assertTrue(isEqual("διαλΰτικα", "διαλυτΐκά"));

		assertTrue(isEqual("Θ", "TH"));// english T
		assertTrue(isEqual("Θ", "ΤH"));// greek T
		assertTrue(isEqual("Θ", "Τh"));// greek T
		assertTrue(isEqual("ΤHTH", "ΘΘ")); // greek and english T
		assertTrue(isEqual("Θ", "TH"));
		assertTrue(isEqual("Ξυλο", "Ksulo")); // greek K

		assertTrue(isEqual("thΘ", "θΘ"));
		assertTrue(isEqual("THΘ", "ΘΘ"));
		assertTrue(isEqual("ΤHΘ", "ΘΘ")); // greek T
		assertTrue(isEqual("ΤΗΘ", "ΘΘ")); // greek T, greek H
	}
}